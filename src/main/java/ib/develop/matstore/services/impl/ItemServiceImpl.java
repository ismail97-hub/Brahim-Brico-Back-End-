package ib.develop.matstore.services.impl;

import ib.develop.matstore.common.services.BaseServiceImpl;
import ib.develop.matstore.dto.requests.ItemRequest;
import ib.develop.matstore.entities.Item;
import ib.develop.matstore.entities.Order;
import ib.develop.matstore.entities.Product;
import ib.develop.matstore.services.ItemService;
import ib.develop.matstore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item,Long> implements ItemService {

    @Autowired
    private ProductService productService;

    @Override
    public Item saveItem(ItemRequest itemRequest, Order order) {
        Product product=null;
        if (itemRequest.getProductId()!=0) {
            product = productService.findById(itemRequest.getProductId());
        }
        Item item = save(Item.builder()
                .order(order)
                .product(product)
                .label(itemRequest.getLabel())
                .unitPrice(itemRequest.getUnitPrice())
                .quantity(itemRequest.getQuantity())
                .measureUnit(itemRequest.getMeasureUnit())
                .build());
        if (product!=null){
            product.setQuantity(product.getQuantity()-itemRequest.getQuantity());
            productService.save(product);
        }

        return item;
    }

    @Override
    public void deleteItem(long id) {
        Item item = findById(id);
        if (item.getProduct()!=null){
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity()+item.getQuantity());
            productService.save(product);
        }
        delete(id);
    }
}
