package ib.develop.matstore.controllers;

import ib.develop.matstore.common.controllers.BaseController;
import ib.develop.matstore.entities.Supplier;
import ib.develop.matstore.entities.SupplierOperation;
import ib.develop.matstore.services.SupplierOperationService;
import ib.develop.matstore.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController extends BaseController<Supplier,Long> {

    @Autowired
    private SupplierService service;


    @PostMapping(value = "/new",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Supplier newSupplier(@RequestParam("name") String name,
                                @RequestParam("phone") String phone){
        return service.newSupplier(name,phone);
    }


    @PutMapping(value = "/{id}/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Supplier updateSupplier(@PathVariable long id,
                                @RequestParam("name") String name,
                                @RequestParam("phone") String phone){
        return service.updateSupplier(id, name, phone);
    }


}
