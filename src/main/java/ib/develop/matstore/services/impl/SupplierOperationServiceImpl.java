package ib.develop.matstore.services.impl;

import ib.develop.matstore.common.enums.OperationType;
import ib.develop.matstore.common.services.BaseService;
import ib.develop.matstore.common.services.BaseServiceImpl;
import ib.develop.matstore.dto.requests.SupplierInvoiceOperationRequest;
import ib.develop.matstore.entities.OperationItem;
import ib.develop.matstore.entities.SupplierOperation;
import ib.develop.matstore.repositories.SupplierOperationRepository;
import ib.develop.matstore.services.OperationItemService;
import ib.develop.matstore.services.SupplierOperationService;
import ib.develop.matstore.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.implementation.FixedValue.reference;

@Service
public class SupplierOperationServiceImpl extends BaseServiceImpl<SupplierOperation,Long> implements SupplierOperationService {
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private OperationItemService operationItemService;

    @Autowired
    private SupplierOperationRepository repository;

    @Override
    public void delete(Long id) {
        SupplierOperation so = findById(id);
        if (so.getItems()!=null){
            for (var item: so.getItems()) {
                operationItemService.delete(item.getId());
            }
        }
        super.delete(id);
    }

    @Override
    public List<SupplierOperation> getOperationsBySupplierId(long supplierId) {
        return repository.getOperationsBySupplierId(supplierId);
    }

    @Override
    public SupplierOperation newInvoiceOperation(SupplierInvoiceOperationRequest request) {

        SupplierOperation so = save(SupplierOperation.builder()
                .supplier(supplierService.findById(request.getSupplierId()))
                .date(LocalDateTime.now())
                .type(OperationType.INVOICE)
                .items(new ArrayList<>())
                .reference(request.getReference())
                .build());

        double debit = 0;
        for (var item: request.getItems()) {
            so.getItems().add(operationItemService.newItem(item,so));
            debit += item.getQuantity()* item.getUnitPrice();
        }

        so.setDebit(debit);

        return save(so);
    }

    @Override
    public SupplierOperation newCashOperation(long supplierId, double credit) {
        return save(SupplierOperation.builder()
                .supplier(supplierService.findById(supplierId))
                .credit(credit)
                .date(LocalDateTime.now())
                .type(OperationType.CASH)
                .build());
    }

    @Override
    public SupplierOperation newDiscountOperation(long supplierId, double credit) {
        return save(SupplierOperation.builder()
                .supplier(supplierService.findById(supplierId))
                .credit(credit)
                .date(LocalDateTime.now())
                .type(OperationType.DISCOUNT)
                .build());
    }

    @Override
    public SupplierOperation newChequeOperation(long supplierId, double credit, String reference) {
        return save(SupplierOperation.builder()
                .supplier(supplierService.findById(supplierId))
                .credit(credit)
                .date(LocalDateTime.now())
                .type(OperationType.CHEQUE)
                .reference(reference)
                .build());
    }
}
