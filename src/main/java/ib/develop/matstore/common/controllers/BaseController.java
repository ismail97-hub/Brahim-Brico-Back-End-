package ib.develop.matstore.common.controllers;

import ib.develop.matstore.common.services.BaseService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public class BaseController<T,ID> {

    @Autowired
    private BaseService<T, ID> service;

    @GetMapping(value = {"","/"})
    public List<T> findAll(Pageable pageable){
        return service.findAll(pageable);
    }

    @GetMapping(value = {"/{id}"})
    public T findById(@PathVariable ID id){
        return service.findById(id);
    }

    @PostMapping
    public T save(@RequestBody T entity){
        return service.save(entity);
    }

    @PutMapping
    public T update(@RequestBody T entity){return service.save(entity);}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id){
        service.delete(id);
    }
}
