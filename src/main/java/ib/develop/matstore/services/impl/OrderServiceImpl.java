package ib.develop.matstore.services.impl;

import ib.develop.matstore.common.services.BaseServiceImpl;
import ib.develop.matstore.dto.requests.OrderRequest;
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
    public void printOrder(long id) throws JRException, IOException {
        Order order = findById(id);

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(order.getItems());

        JasperReport compileReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/invoice.jrxml"));

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String date =  order.getDate().format(format);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",order.getTotal());
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
    public List<Order> getOrdersByDate(String date, Pageable pageable) {
        return repository.getOrdersByDate(date, pageable);
    }

    @Override
    public List<Order> search(String query, Pageable pageable) {
        return repository.search(query, pageable);
    }
}
