package ib.develop.matstore.controllers;

import ib.develop.matstore.common.controllers.BaseController;
import ib.develop.matstore.dto.PurchaseDTO;
import java.util.*;
import ib.develop.matstore.entities.Purchase;
import ib.develop.matstore.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController extends BaseController<Purchase,Long> {
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/new")
    public Purchase purchaseNewProduct(@RequestBody PurchaseDTO purchaseDTO){
        return purchaseService.purchaseNewProduct(purchaseDTO);
    }

    @PostMapping(value = "/exist",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Purchase purchaseExistProduct(
            @RequestParam("productId") long productId,
            @RequestParam("quantity") double quantity,
            @RequestParam("purchasePrice") double purchasePrice){
        return purchaseService.purchaseExistProduct(productId,quantity,purchasePrice);
    }

    @GetMapping("/search/{query}")
    public List<Purchase> search(@PathVariable String query, Pageable pageable){
        return purchaseService.search(query,pageable);
    }

    @Override
    public List<Purchase> findAll(Pageable pageable) {
        return purchaseService.getAll(pageable);
    }
}
