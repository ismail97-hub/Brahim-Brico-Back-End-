package ib.develop.matstore.repositories;

import ib.develop.matstore.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository  extends JpaRepository<Supplier,Long> {


}
