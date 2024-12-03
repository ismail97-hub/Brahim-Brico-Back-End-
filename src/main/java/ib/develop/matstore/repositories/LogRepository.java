package ib.develop.matstore.repositories;

import ib.develop.matstore.entities.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface LogRepository extends JpaRepository<Log,Long> {

    @Query(nativeQuery = true,value = "select ifnull(sum(amount),0) from log \n" +
            "where `date`  =  ?1 ")
    double getTotalByDate(LocalDate date);

    List<Log> findByDateOrderByTimeDesc(LocalDate date, Pageable pageable);
    List<Log> findByDateOrderByTimeDesc(LocalDate date);

}
