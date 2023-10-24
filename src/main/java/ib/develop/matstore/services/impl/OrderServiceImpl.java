package ib.develop.matstore.services.impl;

import ib.develop.matstore.common.services.BaseServiceImpl;
import ib.develop.matstore.dto.DebtDTO;
import ib.develop.matstore.dto.OrdersListDTO;
import ib.develop.matstore.dto.requests.ItemRequest;
import ib.develop.matstore.dto.requests.OrderRequest;
import ib.develop.matstore.dto.update.ItemUpdateDTO;
import ib.develop.matstore.dto.update.OrderUpdateDTO;
import ib.develop.matstore.entities.Item;
import ib.develop.matstore.entities.Order;
import ib.develop.matstore.repositories.OrderRepository;
import ib.develop.matstore.services.InfoService;
import ib.develop.matstore.services.ItemService;
import ib.develop.matstore.services.OrderService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order,Long> implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private InfoService infoService;

    @Autowired
    private OrderRepository repository;

    @Override
    public long saveOrder(OrderRequest orderRequest) throws JRException, IOException {
        Order savedOrder = save(Order.builder()
                .items(new ArrayList<>())
                .date(LocalDateTime.now())
                .amountPaid(orderRequest.getAmountPaid())
                .remainingBalance(orderRequest.getRemainingBalance())
                .clientPhone(orderRequest.getClientPhone())
                .clientName(orderRequest.getClientName())
                .build());
        for (var item: orderRequest.getItems()) {
            savedOrder.getItems().add(itemService.saveItem(item,savedOrder));
        }
        printOrder(savedOrder.getId());
        return savedOrder.getId();
    }

    @Override
    public long updateOrder(OrderUpdateDTO orderUpdateDTO) {
        Order order = findById(orderUpdateDTO.getId());
        if (!orderUpdateDTO.getClientName().isEmpty()){
            order.setClientName(orderUpdateDTO.getClientName());
        }
        if (!orderUpdateDTO.getClientPhone().isEmpty()){
            order.setClientPhone(orderUpdateDTO.getClientPhone());
        }
        if (!orderUpdateDTO.getUpdatedItems().isEmpty()){
            for (ItemUpdateDTO i: orderUpdateDTO.getUpdatedItems()) {
                Item item = itemService.findById(i.getId());
                item.setLabel(i.getLabel());
                item.setQuantity(i.getQuantity());
                item.setUnitPrice(i.getUnitPrice());
                item.setMeasureUnit(i.getMeasureUnit());
                itemService.save(item);
            }
        }
        if (!orderUpdateDTO.getAddedItems().isEmpty()){
            for (ItemRequest i: orderUpdateDTO.getAddedItems()) {
                itemService.saveItem(i,order);
            }
        }
        if (!orderUpdateDTO.getDeletedItems().isEmpty()){
            for (long id: orderUpdateDTO.getDeletedItems()) {
                itemService.deleteItem(id);
            }
        }
        order.setRemainingBalance(order.getTotal()-order.getAmountPaid());
        Order o =  save(order);
        return o.getId();
    }

    @Override
    public void printOrder(long id) throws JRException, IOException {
        Order order = findById(id);

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(order.getItems());

        JasperReport compileReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/invoice.jrxml"));

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String date =  order.getDate().format(format);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",order.getTotal());
        map.put("amountPaid",order.getAmountPaid());
        map.put("remainingBalance",order.getRemainingBalance());
        map.put("date",date);
        map.put("clientName",order.getClientName());
        map.put("clientPhone",order.getClientPhone());
        map.put("id",order.getId());
        map.put("info",infoService.getInfo());
        map.put("datenow", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,map, beanCollectionDataSource);

        JasperPrintManager.printReport(jasperPrint,false);
    }

    @Override
    public OrdersListDTO getOrdersByDate(String date, Pageable pageable) {
        var orders = repository.getOrdersByDate(date, pageable);
        var total = repository.getTotalByDate(date);
        return OrdersListDTO.builder()
                .orders(orders)
                .total(total)
                .build();
    }

    @Override
    public OrdersListDTO search(String query, Pageable pageable) {
        query = "%"+query+"%";
        var orders = repository.search(query, pageable);
        var total = repository.getTotalBySearch(query);
        return OrdersListDTO.builder()
                .orders(orders)
                .total(total)
                .build();
    }

    @Override
    public List<DebtDTO> getDebts(Pageable pageable) {
        return repository.getDebts(pageable);
    }

    @Override
    public List<DebtDTO> searchDebt(String query, Pageable pageable) {
        query = "%"+query+"%";
        return repository.searchDebt(query,pageable);
    }

    @Override
    public List<Order> getDebtOrdersByClientPhone(String clientPhone, Pageable pageable) {
        return repository.getDebtOrdersByClientPhone(clientPhone, pageable);
    }

    @Override
    public List<Order> getDebtOrdersByClientPhone(String clientPhone) {
        return repository.getDebtOrdersByClientPhone(clientPhone);
    }


    @Override
    public DebtDTO getDebtByClientPhone(String clientPhone) {
        return repository.getDebtByClientPhone(clientPhone);
    }

    @Override
    public void payInvoice(long orderId, double amountPaid) {
        Order order = findById(orderId);
        order.setAmountPaid(order.getAmountPaid()+amountPaid);
        order.setRemainingBalance(order.getRemainingBalance()-amountPaid);
        save(order);
    }
}
