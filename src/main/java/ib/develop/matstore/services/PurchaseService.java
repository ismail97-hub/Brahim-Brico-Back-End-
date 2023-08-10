package ib.develop.matstore.services;

import ib.develop.matstore.common.services.BaseService;
import ib.develop.matstore.dto.PurchaseDTO;
import ib.develop.matstore.entities.Purchase;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface PurchaseService extends BaseService<Purchase,Long> {
    Purchase purchaseNewProduct(PurchaseDTO purchaseDTO);

    Purchase purchaseExistProduct(long productId,double quantity,double purchasePrice);

    List<Purchase> search(String query, Pageable pageable);

    List<Purchase> getAll(Pageable pageable);
}
