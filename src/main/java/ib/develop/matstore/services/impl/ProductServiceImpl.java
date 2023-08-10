package ib.develop.matstore.services.impl;

import ib.develop.matstore.common.enums.MeasureUnit;
import ib.develop.matstore.common.services.BaseServiceImpl;
import ib.develop.matstore.entities.Item;
import ib.develop.matstore.entities.Product;
import ib.develop.matstore.repositories.ProductRepository;
import ib.develop.matstore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product,Long> implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public Product save(Product entity) {
        entity.setDate(LocalDateTime.now());
        return super.save(entity);
    }

    @Override
    public Product update(long id, String label, double unitPrice, MeasureUnit unit) {
        Product product = findById(id);
        product.setLabel(label);
        product.setUnitPrice(unitPrice);
        product.setMeasureUnit(unit);
        return repository.save(product);
    }

    @Override
    public List<Product> search(String query, Pageable pageable) {
        return repository.search(query, pageable);
    }
}
