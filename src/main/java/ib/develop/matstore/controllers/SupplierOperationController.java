package ib.develop.matstore.controllers;

import ib.develop.matstore.common.controllers.BaseController;
import ib.develop.matstore.dto.requests.SupplierInvoiceOperationRequest;
import ib.develop.matstore.entities.SupplierOperation;
import ib.develop.matstore.services.SupplierOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierOperationController  {

    @Autowired
    private SupplierOperationService service;

    @DeleteMapping("/operation/{id}")
    public void delete(@PathVariable long id){
        service.delete(id);
    }

    @GetMapping("/{id}/operations")
    public List<SupplierOperation> getOperationsBySupplierId(@PathVariable long id){
        return service.getOperationsBySupplierId(id);
    }

    @PostMapping("/operation/invoice")
    SupplierOperation newInvoiceOperation(@RequestBody SupplierInvoiceOperationRequest request){
        return service.newInvoiceOperation(request);
    }
    @PostMapping(value = "/operation/cash",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    SupplierOperation newCashOperation(@RequestParam("supplierId") long supplierId,@RequestParam("credit") double credit){
        return service.newCashOperation(supplierId, credit);
    }

    @PostMapping(value = "/operation/discount",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    SupplierOperation newDiscountOperation(@RequestParam("supplierId") long supplierId,@RequestParam("credit") double credit){
        return service.newDiscountOperation(supplierId, credit);
    }
    @PostMapping(value = "/operation/cheque",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    SupplierOperation newChequeOperation(@RequestParam("supplierId") long supplierId,@RequestParam("credit") double credit,@RequestParam("reference") String reference){
        return service.newChequeOperation(supplierId, credit, reference);
    }

}
