package ib.develop.matstore.dto.update;
import ib.develop.matstore.dto.requests.ItemRequest;
import ib.develop.matstore.entities.Item;
import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderUpdateDTO {
    private long id;
    private List<ItemRequest> addedItems;
    private List<Long> deletedItems;
    private List<ItemUpdateDTO> updatedItems;
    private String clientName;
    private String clientPhone;
}
