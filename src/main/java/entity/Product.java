package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String productID;
    private String productCatagory;
    private String productName;
    private String productDiscription;
    private String productSize;
}
