package ib.develop.matstore.dto;

import ib.develop.matstore.entities.Log;
import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogListDTO {

    private double total;

    private List<Log> log;
}
