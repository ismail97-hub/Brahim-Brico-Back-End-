package ib.develop.matstore.services.impl;

import ib.develop.matstore.common.services.BaseServiceImpl;
import ib.develop.matstore.entities.Supplier;
import ib.develop.matstore.services.SupplierService;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl extends BaseServiceImpl<Supplier,Long> implements SupplierService {

    @Override
    public Supplier newSupplier(String name, String phone) {
        return save(Supplier.builder().name(name).phone(phone).build());
    }

    @Override
    public Supplier updateSupplier(long id, String name, String phone) {
        Supplier supplier = findById(id);
        supplier.setName(name);
        supplier.setPhone(phone);
        return save(supplier);
    }
}
