package ib.develop.matstore.entities;


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
public class Purchase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  private String product;

  @Column(nullable = false)
  private double quantity;

  @Column(nullable = false)
  private double purchasePrice;

  @Column(nullable = false)
  private double sellingPrice;

  @Column(nullable = false)
  private LocalDateTime date;

  public double getTotal(){
      return quantity * purchasePrice;
  }
}
