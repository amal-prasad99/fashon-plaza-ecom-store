package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderDetailsController {

    @FXML
    private AnchorPane OrderDetailsPane;
    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnReturn;

    @FXML
    private JFXTextField txtOrderID;

    @FXML
    void btnBackOnAction(ActionEvent event) {
        Stage stage = (Stage) OrderDetailsPane.getScene().getWindow();
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
    void btnReturnOnAction(ActionEvent event) {

    }

}
