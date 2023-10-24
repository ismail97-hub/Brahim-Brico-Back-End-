package ib.develop.matstore.dto.requests;

import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderRequest {
    List<ItemRequest> items;
    String clientName;
    String clientPhone;
    double amountPaid;
    double remainingBalance;
}
