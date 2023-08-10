package ib.develop.matstore.repositories;

import ib.develop.matstore.entities.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {


    @Query(nativeQuery = true,value = "select * from `order` o\n" +
            "where Date(o.`date`) = ?1 \n" +
            "order by o.`date` desc",
    countQuery = "select count(*) from `order` o\n" +
            "where Date(o.`date`) = ?1 \n" +
            "order by o.`date` desc")
    List<Order> getOrdersByDate(String date,Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT * from `order` \n" +
            "WHERE MATCH(client_name,client_phone) AGAINST(?1) or id = ?1 \n" +
            "order by `date` desc",
            countQuery = "SELECT count(*) from `order` \n" +
            "WHERE MATCH(client_name,client_phone) AGAINST(?1) or id = ?1 \n" +
            "order by `date` desc")
    List<Order> search(String query,Pageable pageable);
}
