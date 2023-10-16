package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Inventory {
    private String inventoryID;
    private String productID;
    private String supplierID;
    private int qtyHand;
    private int qtySupplied;
    private double sellingPrice;
    private double buyingPrice;

}
