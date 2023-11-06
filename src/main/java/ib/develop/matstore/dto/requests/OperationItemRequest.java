package ib.develop.matstore.dto.requests;

import ib.develop.matstore.common.enums.MeasureUnit;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OperationItemRequest {

    private String label;

    private double quantity;

    private MeasureUnit unit;

    private double unitPrice;
}
