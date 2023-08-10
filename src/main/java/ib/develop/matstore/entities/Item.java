package ib.develop.matstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.text.DecimalFormat;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false)
    private double unitPrice;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    @JsonIgnore
    private Order order;

    public double getTotal(){
        double total = quantity * unitPrice;
        String formated  = String.format("%.2f",total);
        return Double.parseDouble(formated.replace(",","."));
    }
}
