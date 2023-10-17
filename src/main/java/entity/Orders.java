package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Orders {
    private String orderID;
    private String employeeID;
    private String productID;
    private String customerName;
    private String customerEmail;
    private String orderDate;
    private String paymentMethod;
    private String description;
    private int qty;
    private String catagory;
    private String size;
    private double sellingPrice;
    private double profit;
    private double discount;
    private double total;
    private double balance;
    private double cash;

}
