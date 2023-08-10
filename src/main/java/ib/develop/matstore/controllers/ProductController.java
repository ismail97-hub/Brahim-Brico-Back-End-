package ib.develop.matstore.controllers;

import ib.develop.matstore.common.controllers.BaseController;
import ib.develop.matstore.common.enums.MeasureUnit;
import java.util.*;
import ib.develop.matstore.entities.Product;
import ib.develop.matstore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController extends BaseController<Product,Long> {

    @Autowired
    private ProductService service;

    @PutMapping(value = "/{id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product updateById(@PathVariable long id,
                           @RequestParam("label") String label,
                           @RequestParam("unitPrice") double unitPrice,
                           @RequestParam("unit")MeasureUnit unit){
           return service.update(id, label, unitPrice, unit);
    }

    @GetMapping("/search/{query}")
    public List<Product> search(@PathVariable String query, Pageable pageable){
        return service.search(query, pageable);
    }
}
