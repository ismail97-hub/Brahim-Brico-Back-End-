package ib.develop.matstore.dto;

import lombok.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DebtListDTO {
   double total;
   List<DebtDTO> debts;
}
