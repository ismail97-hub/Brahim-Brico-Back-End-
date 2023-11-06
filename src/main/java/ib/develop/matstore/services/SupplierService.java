package ib.develop.matstore.services;


import ib.develop.matstore.common.services.BaseService;
import ib.develop.matstore.entities.Supplier;

public interface SupplierService extends BaseService<Supplier,Long> {

    Supplier newSupplier(String name,String phone);

    Supplier updateSupplier(long id,String name,String phone);
}
