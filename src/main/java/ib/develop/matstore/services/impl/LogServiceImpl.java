package ib.develop.matstore.services.impl;

import ib.develop.matstore.common.services.BaseService;
import ib.develop.matstore.common.services.BaseServiceImpl;
import ib.develop.matstore.dto.LogListDTO;
import ib.develop.matstore.entities.Log;
import ib.develop.matstore.repositories.LogRepository;
import ib.develop.matstore.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LogServiceImpl extends BaseServiceImpl<Log,Long> implements LogService {

    @Autowired
    private LogRepository repository;

    @Override
    public List<Log> findByDateOrderByTimeDesc(LocalDate date, Pageable pageable) {
        return repository.findByDateOrderByTimeDesc(date,pageable);
    }

    @Override
    public List<Log> findByDateOrderByTimeDesc(LocalDate date) {
        return repository.findByDateOrderByTimeDesc(date);
    }

    @Override
    public LogListDTO getLog(LocalDate date,Pageable pageable) {
        return LogListDTO.builder()
                .log(findByDateOrderByTimeDesc(date, pageable))
                .total(repository.getTotalByDate(date))
                .build();
    }
}
