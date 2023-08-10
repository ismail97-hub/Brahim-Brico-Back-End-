package ib.develop.matstore.entities;

import ib.develop.matstore.common.enums.MeasureUnit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true,nullable = false)
    private String label;

    @Column(nullable = false)
    private double unitPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasureUnit measureUnit;

    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false)
    private LocalDateTime date;

    public double getTotal(){
        return unitPrice * quantity;
    }

}
