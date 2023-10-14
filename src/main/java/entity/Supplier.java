package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Supplier {

    private String supplierID;
    private String title;
    private String supplierName;
    private String supplierCompany;
    private String supplierEmail;


}
