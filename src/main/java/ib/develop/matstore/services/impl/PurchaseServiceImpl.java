package ib.develop.matstore.services.impl;

import ib.develop.matstore.common.services.BaseServiceImpl;
import ib.develop.matstore.dto.PurchaseDTO;
import ib.develop.matstore.entities.Item;
import ib.develop.matstore.entities.Product;
import ib.develop.matstore.entities.Purchase;
import ib.develop.matstore.repositories.ProductRepository;
import ib.develop.matstore.repositories.PurchaseRepository;
import ib.develop.matstore.services.ProductService;
import ib.develop.matstore.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseServiceImpl extends BaseServiceImpl<Purchase,Long> implements PurchaseService {

    @Autowired
    private ProductService productService;

    @Autowired
    private PurchaseRepository repository;

    @Override
    public Purchase purchaseNewProduct(PurchaseDTO purchaseDTO) {
        Product product = Product.builder()
                .label(purchaseDTO.getLabel())
                .quantity(purchaseDTO.getQuantity())
                .unitPrice(purchaseDTO.getSellingPrice())
                .measureUnit(purchaseDTO.getMeasureUnit())
                .date(LocalDateTime.now()).build();
        Product savedProduct = productService.save(product);
        Purchase purchase = Purchase.builder()
                .product(savedProduct.getLabel())
                .sellingPrice(purchaseDTO.getSellingPrice())
                .purchasePrice(purchaseDTO.getPurchasePrice())
                .quantity(purchaseDTO.getQuantity())
                .date(LocalDateTime.now())
                .build();
        return save(purchase);
    }

    @Override
    public Purchase purchaseExistProduct(long productId, double quantity, double purchasePrice) {
        Product product = productService.findById(productId);
        Purchase purchase = save(Purchase.builder()
                .product(product.getLabel())
                .purchasePrice(purchasePrice)
                .sellingPrice(product.getUnitPrice())
                .quantity(quantity)
                .date(LocalDateTime.now())
                .build());
        product.setQuantity(product.getQuantity()+quantity);
        productService.save(product);
        return purchase;
    }

    @Override
    public List<Purchase> search(String query, Pageable pageable) {
        return repository.search(query,pageable);
    }

    @Override
    public List<Purchase> getAll(Pageable pageable) {
        return repository.getAll(pageable);
    }
}
