package ib.develop.matstore.repositories;

import ib.develop.matstore.dto.DebtDTO;
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

    @Query(nativeQuery = true,value = "select ifnull(sum(amount_paid),0) from `order` o\n" +
            "where Date(o.`date`) = ?1\n" +
            "order by o.`date` desc")
    double getTotalByDate(String date);

    @Query(nativeQuery = true,
            value = "SELECT * from `order` \n" +
            "WHERE client_phone like ?1 or client_name like ?1 or id = ?1 \n" +
            "order by `date` desc",
            countQuery = "SELECT count(*) from `order` \n" +
            "WHERE client_phone like ?1 or client_name like ?1 or id = ?1 \n" +
            "order by `date` desc")
    List<Order> search(String query,Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT ifnull(sum(amount_paid),0) from `order`\n" +
                    "WHERE client_phone like ?1 or client_name like ?1 or id = ?1 \n" +
                    "order by `date` desc")
    double getTotalBySearch(String query);

    @Query(nativeQuery = true,
            value="select  o.client_name as clientName,o.client_phone as ClientPhone,count(o.id) as invoicesCount,sum(o.remaining_balance) as remainingBalance  from `order` o \n" +
            "where remaining_balance !=0 \n" +
            "group by o.client_phone \n" +
            "order by max(o.`date`) desc \n",
            countQuery = "SELECT COUNT(DISTINCT client_phone) AS count \n" +
            "FROM `order`\n" +
            "WHERE remaining_balance != 0;"
       )
    List<DebtDTO> getDebts(Pageable pageable);

    @Query(nativeQuery = true,
      value = "select  o.client_name as clientName,o.client_phone as ClientPhone,count(o.id) as invoicesCount,sum(o.remaining_balance) as remainingBalance  from `order` o\n" +
              "where remaining_balance !=0 and client_phone like ?1 or client_name like ?1 \n" +
              "group by o.client_phone \n" +
              "order by max(o.`date`) desc ",
      countQuery = "SELECT COUNT(DISTINCT client_phone) AS count FROM `order`\n" +
              "where remaining_balance !=0 and client_phone like ?1 or client_name like ?1 ")
    List<DebtDTO> searchDebt(String query,Pageable pageable);

    @Query(nativeQuery = true,
            value="select  o.client_name as clientName,o.client_phone as ClientPhone,count(o.id) as invoicesCount,sum(o.remaining_balance) as remainingBalance  from `order` o \n" +
                    "where remaining_balance !=0 and client_phone = ?1 \n" +
                    "group by o.client_phone \n" +
                    "order by o.`date` desc \n"
    )
    DebtDTO getDebtByClientPhone(String clientPhone);

    @Query(nativeQuery = true,
            value = "select * from `order` o \n" +
                    "where o.client_phone = ?1 and o.remaining_balance !=0 \n" +
                    "order by o.`date` desc",
            countQuery = "select count(*) from `order` o \n" +
                    "where o.client_phone = ?1 and o.remaining_balance !=0 \n" +
                    "order by o.`date` desc"
    )
    List<Order> getDebtOrdersByClientPhone(String clientPhone,Pageable pageable);

    @Query(nativeQuery = true,
            value = "select * from `order` o \n" +
                    "where o.client_phone = ?1 and o.remaining_balance !=0 \n" +
                    "order by o.`date` desc"
    )
    List<Order> getDebtOrdersByClientPhone(String clientPhone);
}
