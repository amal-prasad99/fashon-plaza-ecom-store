package entity.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductTm extends RecursiveTreeObject<ProductTm> {
    private String productID;
    private String productCatagory;
    private String productName;
    private String productDiscription;
    private String productSize;
    private JFXButton btnOption;
}
