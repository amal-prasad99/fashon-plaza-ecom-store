package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeRegistrationController {
    @FXML
    private AnchorPane EmployeeRegistrationPane;

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
    private JFXComboBox<?> cmbEmployeeID;

    @FXML
    private JFXComboBox<?> cmbTitle;

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

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnPrintOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

}
