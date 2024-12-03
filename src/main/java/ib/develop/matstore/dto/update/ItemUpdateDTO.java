package ib.develop.matstore.dto.update;

import ib.develop.matstore.common.enums.MeasureUnit;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemUpdateDTO {
    private long id;

    private double unitPrice;

    private String label;

    private MeasureUnit measureUnit;

    private double quantity;
}
