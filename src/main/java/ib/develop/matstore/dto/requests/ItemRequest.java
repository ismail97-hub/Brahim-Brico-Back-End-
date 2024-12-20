package ib.develop.matstore.dto.requests;


import ib.develop.matstore.common.enums.MeasureUnit;
import ib.develop.matstore.entities.Product;
import jakarta.persistence.ManyToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemRequest {
    private long productId;

    private double unitPrice;

    private String label;

    private MeasureUnit measureUnit;

    private double quantity;
}
