package ib.develop.matstore.repositories;

import ib.develop.matstore.entities.Settlement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement,Long> {

    @Query(nativeQuery = true,
            value = "SELECT * from settlement \n" +
            "WHERE MATCH(client_name,client_phone) AGAINST(?1)",
            countQuery = "SELECT count(*) from settlement \n" +
                    "WHERE MATCH(client_name,client_phone) AGAINST(?1)")
    List<Settlement> search(String query, Pageable pageable);
}
