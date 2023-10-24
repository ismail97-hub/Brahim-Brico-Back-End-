package ib.develop.matstore.services.impl;

import ib.develop.matstore.common.services.BaseServiceImpl;
import ib.develop.matstore.dto.DebtDTO;
import ib.develop.matstore.entities.Order;
import ib.develop.matstore.entities.Settlement;
import ib.develop.matstore.repositories.SettlementRepository;
import ib.develop.matstore.services.OrderService;
import ib.develop.matstore.services.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDateTime;

@Service
public class SettlementServiceImpl extends BaseServiceImpl<Settlement,Long> implements SettlementService {

    @Autowired
    private SettlementRepository repository;

    @Autowired
    private OrderService orderService;
    

    @Override
    public Settlement newSettlement(String clientPhone, double amountPaid) {
        double remainingAmount = amountPaid;
        DebtDTO debt = orderService.getDebtByClientPhone(clientPhone);
        List<Order> debtOrders = orderService.getDebtOrdersByClientPhone(clientPhone);

        for (var order : debtOrders) {
            double remainingBalance = order.getRemainingBalance();
            if (remainingBalance>=remainingAmount){
                orderService.payInvoice(order.getId(), remainingAmount);
                break;
            }else{
                orderService.payInvoice(order.getId(),remainingBalance);
                remainingAmount -= remainingBalance;
            }
        }
        double remainingBalance = debt.getRemainingBalance()-amountPaid;
        Settlement settlement = Settlement.builder()
                .date(LocalDateTime.now())
                .clientName(debt.getClientName())
                .clientPhone(clientPhone)
                .amountPaid(amountPaid)
                .remainingBalance(remainingBalance)
                .build();
        return save(settlement);
    }

    @Override
    public List<Settlement> search(String query, Pageable pageable) {
        return repository.search(query, pageable);
    }
}
