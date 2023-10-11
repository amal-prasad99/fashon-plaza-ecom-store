package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UserRegistrationController {

    @FXML
    private AnchorPane UserRegistrationPane;

    @FXML
    private JFXButton btnAddUser;

    @FXML
    private JFXButton btnCancle;

    @FXML
    private JFXButton btnCheck;

    @FXML
    private JFXButton btnSendOTP;

    @FXML
    private CheckBox chkShowAdminPassword;

    @FXML
    private CheckBox chkShowUserPassword;

    @FXML
    private JFXComboBox<?> cmbUserType;

    @FXML
    private JFXTextField txtAdminName;

    @FXML
    private JFXTextField txtAdminPassword;

    @FXML
    private JFXTextField txtOTP;

    @FXML
    private JFXTextField txtUserConformPassword;

    @FXML
    private JFXTextField txtUserEmail;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXTextField txtUserName31;

    @FXML
    private Label txtUserPassword;

    @FXML
    void btnAddUserOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancleOnAction(ActionEvent event) {
        Stage stage = (Stage) UserRegistrationPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LogInPage.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnCheckOnAction(ActionEvent event) {

    }

    @FXML
    void btnSendOTPOnAction(ActionEvent event) {

    }

    @FXML
    void chkShowAdminPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void chkShowUserPasswordOnAction(ActionEvent event) {

    }

}
