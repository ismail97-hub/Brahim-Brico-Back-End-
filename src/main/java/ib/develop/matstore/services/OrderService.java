package ib.develop.matstore.services;

import ib.develop.matstore.common.services.BaseService;
import ib.develop.matstore.dto.DebtDTO;
import ib.develop.matstore.dto.OrdersListDTO;
import ib.develop.matstore.dto.requests.OrderRequest;
import ib.develop.matstore.dto.update.OrderUpdateDTO;
import ib.develop.matstore.entities.Order;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.io.IOException;

public interface OrderService extends BaseService<Order,Long> {

    long saveOrder(OrderRequest orderRequest) throws JRException, IOException;

    long updateOrder(OrderUpdateDTO orderUpdateDTO);

    void printOrder(long id) throws JRException,IOException;

    OrdersListDTO getOrdersByDate(String date, Pageable pageable);

    OrdersListDTO search(String query,Pageable pageable);

    List<DebtDTO> getDebts(Pageable pageable);

    List<DebtDTO> searchDebt(String query,Pageable pageable);

    List<Order> getDebtOrdersByClientPhone(String clientPhone,Pageable pageable);

    List<Order> getDebtOrdersByClientPhone(String clientPhone);

    DebtDTO getDebtByClientPhone(String clientPhone);

    void payInvoice(long orderId,double amountPaid);
}
