package ib.develop.matstore.common.services;

import ib.develop.matstore.entities.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<T,ID> {

    List<T> findAll(Pageable pageable);

    List<T> findAll();

    T findById(ID id);

    T save(T entity);

    void delete(ID id);

}
