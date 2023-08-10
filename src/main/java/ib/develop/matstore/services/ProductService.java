package ib.develop.matstore.services;

import ib.develop.matstore.common.enums.MeasureUnit;
import ib.develop.matstore.common.services.BaseService;
import ib.develop.matstore.entities.Item;
import ib.develop.matstore.entities.Product;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface ProductService extends BaseService<Product,Long> {

    Product update(long id, String label, double unitPrice, MeasureUnit unit);

    List<Product> search(String query, Pageable pageable);

}
