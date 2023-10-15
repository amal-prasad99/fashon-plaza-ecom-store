package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import entity.Employee;
import entity.tm.EmployeeTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeRegistrationController implements Initializable {
    @FXML
    private AnchorPane EmployeeRegistrationPane;

    @FXML
    private TreeTableColumn<?, ?> colAccount;

    @FXML
    private TreeTableColumn<?, ?> colAddress;

    @FXML
    private TreeTableColumn<?, ?> colBranch;

    @FXML
    private TreeTableColumn<?, ?> colDOB;

    @FXML
    private TreeTableColumn<?, ?> colEmail;

    @FXML
    private TreeTableColumn<?, ?> colEmployeeID;

    @FXML
    private TreeTableColumn<?, ?> colFirstName;

    @FXML
    private TreeTableColumn<?, ?> colLastName;

    @FXML
    private TreeTableColumn<?, ?> colNIC;

    @FXML
    private TreeTableColumn<?, ?> colTitle;

    @FXML
    private JFXTreeTableView<EmployeeTm> tblEmplyee;

    @FXML
    private DatePicker DatePickerEmployeeDoB;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnPrint;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXComboBox<String> cmbEmployeeID;

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private JFXTextField txtEmployeeAccountNo;

    @FXML
    private JFXTextField txtEmployeeAddress;

    @FXML
    private JFXTextField txtEmployeeBranchName;

    @FXML
    private JFXTextField txtEmployeeEmail;

    @FXML
    private JFXTextField txtEmployeeFirstName;

    @FXML
    private JFXTextField txtEmployeeLastName;

    @FXML
    private JFXTextField txtEmployeeNIC;

    @FXML
    private JFXTextField txtEmployeeSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colEmployeeID.setCellValueFactory(new TreeItemPropertyValueFactory<>("employeeID"));
        colTitle.setCellValueFactory(new TreeItemPropertyValueFactory<>("employeeTitle"));
        colFirstName.setCellValueFactory(new TreeItemPropertyValueFactory<>("employeeFirstName"));
        colLastName.setCellValueFactory(new TreeItemPropertyValueFactory<>("employeeLastName"));
        colAddress.setCellValueFactory(new TreeItemPropertyValueFactory<>("employeeAddress"));
        colDOB.setCellValueFactory(new TreeItemPropertyValueFactory<>("employeeDoB"));
        colEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("employeeEmail"));
        colAccount.setCellValueFactory(new TreeItemPropertyValueFactory<>("employeeAccount"));
        colBranch.setCellValueFactory(new TreeItemPropertyValueFactory<>("employeeBranch"));
        colNIC.setCellValueFactory(new TreeItemPropertyValueFactory<>("employeeNIC"));

        tblEmplyee.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldValue, newValue)->{
            if(newValue!=null){
                int selectedIndex = newValue.intValue();
                TreeItem<EmployeeTm> selectedTreeItem = tblEmplyee.getTreeItem(selectedIndex);
                if(selectedTreeItem!=null){
                    setData(selectedTreeItem);
                }
            }
        });


        loadTitle();
        loadUserId();
        loadTable();
    }

    private void setData(TreeItem<EmployeeTm> selectedTreeItem) {
        cmbEmployeeID.setValue(selectedTreeItem.getValue().getEmployeeID().toString());
        cmbTitle.setValue(selectedTreeItem.getValue().getEmployeeTitle().toString());
        txtEmployeeFirstName.setText(selectedTreeItem.getValue().getEmployeeFirstName());
        txtEmployeeLastName.setText(selectedTreeItem.getValue().getEmployeeLastName());
        txtEmployeeAddress.setText(selectedTreeItem.getValue().getEmployeeAddress());
//        DateTimeFormatter.ofPattern(selectedTreeItem.getValue().getEmployeeDoB().toString());
        txtEmployeeEmail.setText(selectedTreeItem.getValue().getEmployeeEmail());
        txtEmployeeAccountNo.setText(selectedTreeItem.getValue().getEmployeeAccount());
        txtEmployeeBranchName.setText(selectedTreeItem.getValue().getEmployeeBranch());
        txtEmployeeNIC.setText(selectedTreeItem.getValue().getEmployeeNIC());
        DatePickerEmployeeDoB.setValue(LocalDate.parse(selectedTreeItem.getValue().getEmployeeDoB(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

    }

    private void loadUserId(){

        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT user_id FROM user"
            );

            ObservableList<String> userIds = FXCollections.observableArrayList();
            while (resultSet.next()){
                userIds.add(resultSet.getString(1));
            }
            cmbEmployeeID.setItems(userIds);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadTable(){
        ObservableList<EmployeeTm> tmList = FXCollections.observableArrayList();
        List<Employee> list = new ArrayList<>();

        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT * FROM employee"
            );

            while (resultSet.next()){
                list.add(new Employee(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getString(10)
                ));
            }

            for (Employee employee:list){
                tmList.add(new EmployeeTm(
                        employee.getEmployeeID(),
                        employee.getEmployeeTitle(),
                        employee.getEmployeeFirstName(),
                        employee.getEmployeeLastName(),
                        employee.getEmployeeAddress(),
                        employee.getEmployeeDoB(),
                        employee.getEmployeeEmail(),
                        employee.getEmployeeAccount(),
                        employee.getEmployeeBranch(),
                        employee.getEmployeeNIC()
                ));
            }
            TreeItem<EmployeeTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblEmplyee.setRoot(treeItem);
            tblEmplyee.setShowRoot(false);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void clearFields(){
        cmbEmployeeID.setValue("");
        cmbTitle.setValue("");
        txtEmployeeFirstName.clear();
        txtEmployeeLastName.clear();
        txtEmployeeAddress.clear();
        txtEmployeeEmail.clear();
        txtEmployeeAccountNo.clear();
        txtEmployeeBranchName.clear();
        txtEmployeeNIC.clear();
        DatePickerEmployeeDoB.getEditor().clear();

    }


    private void loadTitle(){
        ObservableList<String> title = FXCollections.observableArrayList("Mr","Mrs");
        cmbTitle.getItems().addAll(title);
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        Stage stage = (Stage) EmployeeRegistrationPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminPage.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this Employee",
                ButtonType.YES, ButtonType.NO).showAndWait();
        if(buttonType.get() == ButtonType.YES){

            try {
                Boolean isDeletedEmp = CrudUtil.execute(
                        "DELETE FROM employee WHERE employee_id=?",
                        cmbEmployeeID.getValue().toString()
                );

                Boolean isDeletedUsr = CrudUtil.execute(
                        "DELETE FROM user WHERE user_id=?",
                        cmbEmployeeID.getValue().toString()
                );

                if (isDeletedEmp && isDeletedUsr){
                    new Alert(Alert.AlertType.INFORMATION,"Employee Deleted...!").show();
                    clearFields();
                    loadTable();
                }else {
                    new Alert(Alert.AlertType.INFORMATION,"Customer not Deleted...!").show();
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnPrintOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        Employee employee = new Employee(
                cmbEmployeeID.getValue().toString(),
                cmbTitle.getValue().toString(),
                txtEmployeeFirstName.getText(),
                txtEmployeeLastName.getText(),
                txtEmployeeAddress.getText(),
                DatePickerEmployeeDoB.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                txtEmployeeEmail.getText(),
                txtEmployeeAccountNo.getText(),
                txtEmployeeBranchName.getText(),
                txtEmployeeNIC.getText()
        );

        try {
            Boolean isSaved = CrudUtil.execute(
                    "INSERT INTO employee VALUES(?,?,?,?,?,?,?,?,?,?)",
                    employee.getEmployeeID(),
                    employee.getEmployeeTitle(),
                    employee.getEmployeeFirstName(),
                    employee.getEmployeeLastName(),
                    employee.getEmployeeAddress(),
                    employee.getEmployeeDoB(),
                    employee.getEmployeeEmail(),
                    employee.getEmployeeAccount(),
                    employee.getEmployeeBranch(),
                    employee.getEmployeeNIC()
            );

            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Employee successfully Saved...!").show();
                clearFields();
                loadTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Employee not Saved...!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        Employee employee = new Employee(
                cmbEmployeeID.getValue().toString(),
                cmbTitle.getValue().toString(),
                txtEmployeeFirstName.getText(),
                txtEmployeeLastName.getText(),
                txtEmployeeAddress.getText(),
                DatePickerEmployeeDoB.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                txtEmployeeEmail.getText(),
                txtEmployeeAccountNo.getText(),
                txtEmployeeBranchName.getText(),
                txtEmployeeNIC.getText()
        );

        try {
            Boolean isUpdated = CrudUtil.execute(
                    "UPDATE employee SET employee_title=?, employee_first_name=?, employee_last_name =?, employee_address=?, employee_dob=?, employee_email=?, employee_account=?, employee_branch=?, employee_nic =? WHERE employee_id=?",
                    employee.getEmployeeTitle(),
                    employee.getEmployeeFirstName(),
                    employee.getEmployeeLastName(),
                    employee.getEmployeeAddress(),
                    employee.getEmployeeDoB(),
                    employee.getEmployeeEmail(),
                    employee.getEmployeeAccount(),
                    employee.getEmployeeBranch(),
                    employee.getEmployeeNIC(),
                    employee.getEmployeeID()
            );

            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Employee update successful...!").show();
                loadTable();
                clearFields();
            }else {
                new Alert(Alert.AlertType.ERROR,"Employee update not successful..!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
