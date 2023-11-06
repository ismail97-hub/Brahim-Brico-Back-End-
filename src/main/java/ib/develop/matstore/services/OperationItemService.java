package ib.develop.matstore.services;

import ib.develop.matstore.common.services.BaseService;
import ib.develop.matstore.dto.requests.OperationItemRequest;
import ib.develop.matstore.entities.OperationItem;
import ib.develop.matstore.entities.SupplierOperation;

public interface OperationItemService extends BaseService<OperationItem,Long> {

    OperationItem newItem(OperationItemRequest request, SupplierOperation so);

}
