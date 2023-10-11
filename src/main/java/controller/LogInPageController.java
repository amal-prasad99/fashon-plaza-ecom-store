package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInPageController implements Initializable {

    @FXML
    private AnchorPane LogInPane;

    @FXML
    private JFXButton btnLogIn;

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private CheckBox chkShowPassword;

    @FXML
    private Label lblForgotPassword;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void btnLogInOnAction(ActionEvent event) {

        Stage stage = (Stage) LogInPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminPage.fxml"))));
        }catch (IOException e){
            e.printStackTrace();
        }
        stage.show();

    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        Stage stage = (Stage) LogInPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserRegistration.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void chkShowPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void lblForgotPasswordOnAction(MouseEvent event) {

    }


}
