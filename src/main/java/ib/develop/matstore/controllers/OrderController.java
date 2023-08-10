package ib.develop.matstore.controllers;

import ib.develop.matstore.common.controllers.BaseController;
import ib.develop.matstore.dto.requests.OrderRequest;
import ib.develop.matstore.entities.Order;
import ib.develop.matstore.services.OrderService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/order")
public class OrderController extends BaseController<Order,Long> {

    @Autowired
    private OrderService orderService;

    @PostMapping("/new")
    public long saveOrder(@RequestBody OrderRequest orderRequest) throws JRException, IOException {
        return orderService.saveOrder(orderRequest);
    }

    @GetMapping("/{id}/print")
    public void printOrder(@PathVariable long id) throws JRException, IOException {
        orderService.printOrder(id);
    }

    @GetMapping("/date/{date}")
    public List<Order> getOrdersByDate(@PathVariable String date, Pageable pageable){
        return orderService.getOrdersByDate(date,pageable);
    }

    @GetMapping("/search/{query}")
    public List<Order> search(@PathVariable String query, Pageable pageable){
        return orderService.search(query, pageable);
    }

}
