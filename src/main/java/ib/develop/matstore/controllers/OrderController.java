package ib.develop.matstore.controllers;

import ib.develop.matstore.common.controllers.BaseController;
import ib.develop.matstore.dto.DebtDTO;
import ib.develop.matstore.dto.OrdersListDTO;
import ib.develop.matstore.dto.requests.OrderRequest;
import ib.develop.matstore.dto.update.OrderUpdateDTO;
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

    @PutMapping("/update")
    public long updateOrder(@RequestBody OrderUpdateDTO orderUpdateDTO){
        return orderService.updateOrder(orderUpdateDTO);
    }

    @GetMapping("/{id}/print")
    public void printOrder(@PathVariable long id) throws JRException, IOException {
        orderService.printOrder(id);
    }

    @GetMapping("/date/{date}")
    public OrdersListDTO getOrdersByDate(@PathVariable String date, Pageable pageable){
        return orderService.getOrdersByDate(date,pageable);
    }

    @GetMapping("/search/{query}")
    public OrdersListDTO search(@PathVariable String query, Pageable pageable){
        return orderService.search(query, pageable);
    }

    @GetMapping("/debts")
    public List<DebtDTO> getDebts(Pageable pageable){
        return orderService.getDebts(pageable);
    }

    @GetMapping("/debt/search/{query}")
    public List<DebtDTO> searchDebt(@PathVariable String query,Pageable pageable){
        return orderService.searchDebt(query, pageable);
    }

    @GetMapping("/{clientPhone}/debts")
    public List<Order> getDebtOrdersByClientPhone(@PathVariable String clientPhone,Pageable pageable){
        return orderService.getDebtOrdersByClientPhone(clientPhone,pageable);
    }
}
