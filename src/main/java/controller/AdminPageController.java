package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {

    @FXML
    private AnchorPane AdminPane;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnItem;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnOrd;

    @FXML
    private JFXButton btnOrderDetails;

    @FXML
    private JFXButton btnReturnSales;

    @FXML
    private JFXButton btnSalesReport;

    @FXML
    private JFXButton btnSupplier;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void btnEmployeeOnActon(ActionEvent event) {
        Stage stage = (Stage) AdminPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeRegistration.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnItemOnActon(ActionEvent event) {
        Stage stage = (Stage) AdminPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ProductRegistration.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {
        Stage stage = (Stage) AdminPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LogInPage.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnOrdOnActon(ActionEvent event) {
        Stage stage = (Stage) AdminPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceOrder.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnOrderDetailsOnActon(ActionEvent event) {
        Stage stage = (Stage) AdminPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OrderDetails.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnReturnSalesOnActon(ActionEvent event) {
        Stage stage = (Stage) AdminPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/SalesReturn.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSalesReportOnActon(ActionEvent event) {

    }

    @FXML
    void btnSupplierOnActon(ActionEvent event) {
        Stage stage = (Stage) AdminPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/SupplierRegistration.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void btnInventoryOnActon(ActionEvent actionEvent) {
        Stage stage = (Stage) AdminPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/InventoryManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
