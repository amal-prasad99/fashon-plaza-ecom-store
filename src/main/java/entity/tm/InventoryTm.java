package entity.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryTm extends RecursiveTreeObject<InventoryTm> {
    private String inventoryID;
    private String productID;
    private String supplierID;
    private int qtyHand;
    private int qtySupplied;
    private double sellingPrice;
    private double buyingPrice;
    private JFXButton btnOption;
}
