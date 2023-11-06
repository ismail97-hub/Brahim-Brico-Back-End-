package ib.develop.matstore.services;


import ib.develop.matstore.common.services.BaseService;
import ib.develop.matstore.dto.LogListDTO;
import ib.develop.matstore.entities.Log;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.*;

public interface LogService extends BaseService<Log,Long> {

    List<Log> findByDateOrderByTimeDesc(LocalDate date, Pageable pageable);
    List<Log> findByDateOrderByTimeDesc(LocalDate date);

    LogListDTO getLog(LocalDate date,Pageable pageable);

}
