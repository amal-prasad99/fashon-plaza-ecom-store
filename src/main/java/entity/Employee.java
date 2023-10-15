package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {
    private String employeeID;
    private String employeeTitle;
    private String employeeFirstName;
    private String employeeLastName;
    private String employeeAddress;
    private String employeeDoB;
    private String employeeEmail;
    private String employeeAccount;
    private String employeeBranch;
    private String employeeNIC;
}
