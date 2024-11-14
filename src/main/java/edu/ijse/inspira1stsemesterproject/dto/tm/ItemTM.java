package edu.ijse.inspira1stsemesterproject.dto.tm;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@ToString
@AllArgsConstructor

public class ItemTM {
    private String itemId;
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private int itemQuantity;
    private String supplierId;
}
