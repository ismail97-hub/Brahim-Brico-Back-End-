package ib.develop.matstore.dto.requests;

import ib.develop.matstore.entities.OperationItem;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SupplierInvoiceOperationRequest {

    private long supplierId;

    private List<OperationItemRequest> items;

    private String reference;
}
