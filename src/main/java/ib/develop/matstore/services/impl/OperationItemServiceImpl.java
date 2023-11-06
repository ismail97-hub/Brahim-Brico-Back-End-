package ib.develop.matstore.services.impl;

import ib.develop.matstore.common.services.BaseServiceImpl;
import ib.develop.matstore.dto.requests.OperationItemRequest;
import ib.develop.matstore.entities.OperationItem;
import ib.develop.matstore.entities.SupplierOperation;
import ib.develop.matstore.services.OperationItemService;
import ib.develop.matstore.services.SupplierOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationItemServiceImpl extends BaseServiceImpl<OperationItem,Long> implements OperationItemService {




    @Override
    public OperationItem newItem(OperationItemRequest request, SupplierOperation so) {
        return save(OperationItem.builder()
                .unit(request.getUnit())
                .label(request.getLabel())
                .operation(so)
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .build());
    }
}
