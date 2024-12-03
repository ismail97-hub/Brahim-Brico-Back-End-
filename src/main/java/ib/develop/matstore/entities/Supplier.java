package ib.develop.matstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "supplier")
    @JsonIgnore
    private List<SupplierOperation> operations;

    public long getOperationsCount(){
        if(operations!=null){
            return operations.size();
        }
        return 0;
    }

    public double getTotal(){
        if(operations!=null){
            double total = 0;
            for (var op: operations) {
                if (op.getDebit()!=0) {
                    total += op.getDebit();
                }else if(op.getCredit()!=0){
                    total -= op.getCredit();
                }
            }
            String formated  = String.format("%.2f",total);
            return Double.parseDouble(formated.replace(",","."));
        }else{
            return 0;
        }

    }
}
