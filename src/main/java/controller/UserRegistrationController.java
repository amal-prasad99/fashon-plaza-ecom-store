package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserRegistrationController implements Initializable {

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
    private JFXComboBox<String> cmbUserType;

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
    private Label lblUserID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        generateID();
        loadType();
    }

    private void loadType(){
        ObservableList<String> type = FXCollections.observableArrayList("Admin","Employee");
        cmbUserType.getItems().addAll(type);
    }

    private void generateID(){

        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1"
            );

            if(resultSet.next()){
                int userID = Integer.parseInt(resultSet.getString(1).split("[E]")[1]);
                userID++;
                lblUserID.setText(String.format("E%03d",userID));
            }else {
                lblUserID.setText("E001");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddUserOnAction(ActionEvent event) {

        User user = new User(
                lblUserID.getText(),
                cmbUserType.getValue().toString(),
                txtUserEmail.getText(),
                txtUserPassword.getText()
        );


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
