package ib.develop.matstore.services;

import ib.develop.matstore.common.enums.OperationType;
import ib.develop.matstore.common.services.BaseService;
import ib.develop.matstore.dto.requests.SupplierInvoiceOperationRequest;
import ib.develop.matstore.entities.OperationItem;
import ib.develop.matstore.entities.SupplierOperation;

import java.util.List;

public interface SupplierOperationService extends BaseService<SupplierOperation,Long> {

    List<SupplierOperation> getOperationsBySupplierId(long supplierId);

    SupplierOperation newInvoiceOperation(SupplierInvoiceOperationRequest request);
    SupplierOperation newCashOperation(long supplierId,double credit);
    SupplierOperation newDiscountOperation(long supplierId,double credit);
    SupplierOperation newChequeOperation(long supplierId,double credit,String reference);

}
