package ib.develop.matstore.services;

import ib.develop.matstore.common.services.BaseService;
import ib.develop.matstore.dto.requests.OrderRequest;
import ib.develop.matstore.entities.Order;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.io.IOException;

public interface OrderService extends BaseService<Order,Long> {

    long saveOrder(OrderRequest orderRequest) throws JRException, IOException;

    void printOrder(long id) throws JRException,IOException;

    List<Order> getOrdersByDate(String date, Pageable pageable);

    List<Order> search(String query,Pageable pageable);
}
