package ib.develop.matstore.repositories;

import ib.develop.matstore.entities.Purchase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

    @Query(nativeQuery = true,value = "select * from purchase  \n" +
            "WHERE MATCH(product) AGAINST(?1) \n" +
            "order by `date` desc ",
            countQuery = "select count(*) from purchase \n" +
            "WHERE MATCH(product) AGAINST(?1) \n" +
            "order by `date` desc ")
    List<Purchase> search(String query, Pageable pageable);

    @Query(nativeQuery = true,value = "select * from purchase  \n" +
            "order by `date` desc ",
            countQuery = "select count(*) from purchase  \n" +
                    "order by `date` desc ")
    List<Purchase> getAll(Pageable pageable);
}
