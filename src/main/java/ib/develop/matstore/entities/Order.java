package ib.develop.matstore.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "order")
    private List<Item> items = new ArrayList<>();

    @Column(nullable = false)
    String clientName;

    @Column(nullable = false)
    String clientPhone;

    @Column(nullable = false)
    LocalDateTime date;

    public double getTotal(){
        double total = 0;
        for (var item: items) {
            total += item.getTotal();
        }
        return total;
    }

    public String getReference(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return date.format(format)+"/"+id;
    }

}
