package ib.develop.matstore.controllers;

import ib.develop.matstore.dto.LogListDTO;
import ib.develop.matstore.entities.Log;
import ib.develop.matstore.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/log")
public class LogController {

    @Autowired
    private LogService service;

    @GetMapping("/{date}")
    public LogListDTO getByDateOrderByTimeDesc(@PathVariable LocalDate date, Pageable pageable){
        return service.getLog(date, pageable);
    }
}
