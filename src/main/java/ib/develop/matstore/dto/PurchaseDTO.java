package ib.develop.matstore.dto;

import ib.develop.matstore.common.enums.MeasureUnit;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseDTO {
    private String label;

    @Enumerated(EnumType.STRING)
    private MeasureUnit measureUnit;

    private double purchasePrice;

    private double sellingPrice;

    private double quantity;
}
