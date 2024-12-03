package ib.develop.matstore.services;

import ib.develop.matstore.common.services.BaseService;
import ib.develop.matstore.dto.requests.ItemRequest;
import ib.develop.matstore.entities.Item;
import ib.develop.matstore.entities.Order;

public interface ItemService extends BaseService<Item,Long>{
    Item saveItem(ItemRequest itemRequest, Order order);

    void deleteItem(long id);
}
