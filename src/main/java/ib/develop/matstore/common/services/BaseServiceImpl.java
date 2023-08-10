package ib.develop.matstore.common.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class BaseServiceImpl<T,ID> implements BaseService<T,ID>{
    @Autowired
    private JpaRepository<T,ID> repository;

    @Override
    public List<T> findAll(Pageable pageable) {return repository.findAll(pageable).toList();}

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("A record with %s is not found",id)));
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(ID id) {
       repository.deleteById(id);
    }
}
