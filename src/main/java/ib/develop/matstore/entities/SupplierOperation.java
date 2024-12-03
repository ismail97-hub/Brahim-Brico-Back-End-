package ib.develop.matstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ib.develop.matstore.common.enums.OperationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SupplierOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id",nullable = false)
    @JsonIgnore
    private Supplier supplier;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType type;

    private double debit;

    private double credit;

    @OneToMany(mappedBy = "operation")
    private List<OperationItem> items;

    private String reference;
}
