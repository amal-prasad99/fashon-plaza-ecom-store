package entity.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierTm extends RecursiveTreeObject<SupplierTm> {
    private String supplierID;
    private String title;
    private String supplierName;
    private String supplierCompany;
    private String supplierEmail;
    private JFXButton btnOption;
}
