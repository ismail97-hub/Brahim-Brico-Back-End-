package ib.develop.matstore.controllers;

import ib.develop.matstore.common.controllers.BaseController;
import ib.develop.matstore.dto.DebtDTO;
import ib.develop.matstore.dto.DebtListDTO;
import ib.develop.matstore.dto.OrdersListDTO;
import ib.develop.matstore.dto.requests.OrderRequest;
import ib.develop.matstore.dto.update.OrderUpdateDTO;
import ib.develop.matstore.entities.Order;
import ib.develop.matstore.services.OrderService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
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

    @PostMapping("/new/print")
    public long saveOrderAndPrint(@RequestBody OrderRequest orderRequest) throws JRException, IOException {
        return orderService.saveOrderAndPrint(orderRequest);
    }

    @PutMapping("/update")
    public long updateOrder(@RequestBody OrderUpdateDTO orderUpdateDTO){
        return orderService.updateOrder(orderUpdateDTO);
    }

    @GetMapping("/{id}/print")
    public void printOrder(@PathVariable long id) throws JRException, IOException {
        orderService.printOrder(id);
    }

    @GetMapping(value = "/download/facture{id}",produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] downloadOrderPdf(@PathVariable long id) throws JRException {
        return orderService.downloadOrderPdf(id);
    }

    @PutMapping(value = "/{id}/discount",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void setDiscount(@PathVariable long id,@RequestParam("discount") double discount) {
        orderService.setDiscount(id,discount);
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
    public DebtListDTO getDebts(Pageable pageable){
        return orderService.getDebts(pageable);
    }

    @GetMapping("/debt/search/{query}")
    public DebtListDTO searchDebt(@PathVariable String query,Pageable pageable){
        return orderService.searchDebt(query, pageable);
    }

    @GetMapping("/{clientPhone}/debts")
    public List<Order> getDebtOrdersByClientPhone(@PathVariable String clientPhone,Pageable pageable){
        return orderService.getDebtOrdersByClientPhone(clientPhone,pageable);
    }

    @PostMapping(value = "/{id}/pay",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void payInvoice(@PathVariable long id,@RequestParam("amount") double amount){
        orderService.payInvoice(id,amount);
    }
}
