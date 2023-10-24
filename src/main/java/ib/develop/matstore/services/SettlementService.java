package ib.develop.matstore.services;

import ib.develop.matstore.common.services.BaseService;
import ib.develop.matstore.entities.Settlement;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface SettlementService extends BaseService<Settlement,Long> {

    Settlement newSettlement(String clientPhone,double amountPaid);

    List<Settlement> search(String query, Pageable pageable);

}
