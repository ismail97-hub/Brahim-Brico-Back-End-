package ib.develop.matstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ib.develop.matstore.common.enums.MeasureUnit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OperationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String label;

    private double quantity;

    @Enumerated(EnumType.STRING)
    private MeasureUnit unit;

    private double unitPrice;

    @ManyToOne
    @JoinColumn(name = "operation_id",nullable = false)
    @JsonIgnore
    private SupplierOperation operation;

    public double getTotal(){
        double total = quantity * unitPrice;
        String formated  = String.format("%.2f",total);
        return Double.parseDouble(formated.replace(",","."));
    }
}
