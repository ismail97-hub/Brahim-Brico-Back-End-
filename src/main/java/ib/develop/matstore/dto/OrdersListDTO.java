package ib.develop.matstore.dto;

import ib.develop.matstore.entities.Order;
import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersListDTO {
    private double total;

    private List<Order> orders;
}
