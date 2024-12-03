package ib.develop.matstore.repositories;

import ib.develop.matstore.entities.SupplierOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface SupplierOperationRepository extends JpaRepository<SupplierOperation,Long> {

    @Query(nativeQuery = true,value = "select * from supplier_operation so where supplier_id  = ?1")
    List<SupplierOperation> getOperationsBySupplierId(long supplierId);

}
