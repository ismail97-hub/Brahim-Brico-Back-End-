package ib.develop.matstore.services.impl;

import ib.develop.matstore.common.enums.LogType;
import ib.develop.matstore.common.services.BaseServiceImpl;
import ib.develop.matstore.dto.DebtDTO;
import ib.develop.matstore.dto.DebtListDTO;
import ib.develop.matstore.dto.OrdersListDTO;
import ib.develop.matstore.dto.requests.ItemRequest;
import ib.develop.matstore.dto.requests.OrderRequest;
import ib.develop.matstore.dto.update.ItemUpdateDTO;
import ib.develop.matstore.dto.update.OrderUpdateDTO;
import ib.develop.matstore.entities.Item;
import ib.develop.matstore.entities.Log;
import ib.develop.matstore.entities.Order;
import ib.develop.matstore.repositories.OrderRepository;
import ib.develop.matstore.services.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order,Long> implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private InfoService infoService;

    @Autowired
    private LogService logService;

    @Autowired
    private OrderRepository repository;

    @Override
    public long saveOrder(OrderRequest orderRequest) throws JRException, IOException {
        String formated  = String.format("%.2f",orderRequest.getRemainingBalance());
        double remainingBalance = Double.parseDouble(formated.replace(",","."));

        Order savedOrder = save(Order.builder()
                .items(new ArrayList<>())
                .date(LocalDateTime.now())
                .amountPaid(orderRequest.getAmountPaid())
                .remainingBalance(remainingBalance)
                .discount(orderRequest.getDiscount())
                .clientPhone(orderRequest.getClientPhone())
                .clientName(orderRequest.getClientName())
                .build());
        for (var item: orderRequest.getItems()) {
            savedOrder.getItems().add(itemService.saveItem(item,savedOrder));
        }
        logService.save(Log.builder()
                        .date(LocalDate.now())
                        .time(Timestamp.valueOf(LocalDateTime.now()).getTime())
                        .type(LogType.INSTANT_PAYMENT)
                        .clientPhone(orderRequest.getClientPhone())
                        .clientName(orderRequest.getClientName())
                        .order(savedOrder)
                        .amount(orderRequest.getAmountPaid())
                .build());
        printOrder(savedOrder.getId());
        return savedOrder.getId();
    }

    @Override
    public long updateOrder(OrderUpdateDTO orderUpdateDTO) {
        Order order = findById(orderUpdateDTO.getId());

        if(order.getAmountPaid()==0&&order.getRemainingBalance()==0){
            order.setAmountPaid(order.getTotal());
            order.setRemainingBalance(0);
        }

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

        String formated  = String.format("%.2f",order.getTotal()-order.getAmountPaid());
        double remainingBalance = Double.parseDouble(formated.replace(",","."));
        System.out.println(remainingBalance);
        order.setRemainingBalance(remainingBalance);

        Order o =  save(order);
        return o.getId();
    }

    @Override
    public void printOrder(long id) throws JRException, IOException {

        JasperPrint jasperPrint = getJasperPrint(id);

        JasperPrintManager.printReport(jasperPrint,false);
    }

    @Override
    public byte[] downloadOrderPdf(long id) throws JRException {
        JasperPrint jasperPrint = getJasperPrint(id);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @Override
    public void setDiscount(long id, double discount) {
        Order order = findById(id);
        if(order.getRemainingBalance()!=0){
            if (discount<=order.getRemainingBalance()){
                double diff = discount - order.getDiscount();
                order.setDiscount(order.getDiscount()+diff);
                order.setRemainingBalance(order.getRemainingBalance()-diff);
                save(order);
            }else {
                throw new RuntimeException("The discount is greater than remaining balance");
            }
        }else {
            throw new RuntimeException("There is no remaining balance");
        }
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
        var orders = repository.search(query, pageable);
        var total = repository.getTotalBySearch(query);
        return OrdersListDTO.builder()
                .orders(orders)
                .total(total)
                .build();
    }

    @Override
    public DebtListDTO getDebts(Pageable pageable) {
        return DebtListDTO.builder()
                .total(repository.getTotalDebt())
                .debts(repository.getDebts(pageable))
                .build();
    }

    @Override
    public DebtListDTO searchDebt(String query, Pageable pageable) {
        query = "%"+query+"%";
        return DebtListDTO.builder()
                .total(repository.getTotalDebt())
                .debts(repository.searchDebt(query,pageable))
                .build();
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
        logService.save(Log.builder()
                .date(LocalDate.now())
                .time(Timestamp.valueOf(LocalDateTime.now()).getTime())
                .type(LogType.SETTLEMENT)
                .clientPhone(order.getClientPhone())
                .clientName(order.getClientName())
                .order(order)
                .amount(amountPaid)
                .build());
    }

    JasperPrint getJasperPrint(long id) throws JRException {
        Order order = findById(id);

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(order.getItems());

        JasperReport compileReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/invoice.jrxml"));

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String date =  order.getDate().format(format);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",order.getTotal());
        map.put("amountPaid",order.getAmountPaid());
        map.put("remainingBalance",order.getRemainingBalance());
        map.put("discount",order.getDiscount());
        map.put("date",date);
        map.put("clientName",order.getClientName());
        map.put("clientPhone",order.getClientPhone());
        map.put("id",order.getId());
        map.put("info",infoService.getInfo());
        map.put("datenow", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return JasperFillManager.fillReport(compileReport,map, beanCollectionDataSource);
    }
}
