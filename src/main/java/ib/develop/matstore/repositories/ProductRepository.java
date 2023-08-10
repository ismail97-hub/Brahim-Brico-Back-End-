package ib.develop.matstore.repositories;

import ib.develop.matstore.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(nativeQuery = true,
            value = "select * from product \n" +
            "WHERE MATCH(label) AGAINST(?1) or id = ?1",
           countQuery = "select count(*) from product \n" +
                   "WHERE MATCH(label) AGAINST(?1) or id = ?1")
    List<Product> search(String query, Pageable pageable);
}
