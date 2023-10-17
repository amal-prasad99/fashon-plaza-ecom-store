package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {

    @FXML
    private AnchorPane PlaceOrderPane;

    @FXML
    private DatePicker DatePickerEmployeeDoB1;

    @FXML
    private Rectangle DateSelecterOrderPlaced;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXCheckBox chkCard;

    @FXML
    private JFXCheckBox chkCash;

    @FXML
    private JFXComboBox<String> cmbCatagory;

    @FXML
    private JFXComboBox<String> cmbEmployeeID;

    @FXML
    private JFXComboBox<String> cmbProductID;

    @FXML
    private JFXComboBox<String> cmbSellingPrice;

    @FXML
    private Label lblProfitOnSale;

    @FXML
    private Label lblInventoryID;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblDesription;

    @FXML
    private Label lblSize;

    @FXML
    private Label lblCatagory;

    @FXML
    private Label lblOrdID;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblTotal;

    @FXML
    private JFXTextField txtCache;

    @FXML
    private JFXTextField txtCustomerEmail;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private JFXTextField txtDiscription;

    @FXML
    private JFXTextField txtProfit;

    @FXML
    private JFXTextField txtQTYBuy;

    @FXML
    private JFXTextField txtQTYonHand;

    @FXML
    private JFXTextField txtSellingPrice;

    @FXML
    private JFXTextField txtSize;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        generateID();
        loadEmployeeId();
        loadProductId();
    }



    private void generateID(){
        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1"
            );

            if(resultSet.next()){
                int ordID = Integer.parseInt(resultSet.getString(1).split("[O]")[1]);
                ordID++;
                lblOrdID.setText(String.format("O%3d",ordID));
            }else {
                lblOrdID.setText("O001");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProductId(){

        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT product_id FROM product"
            );
            ObservableList<String> productIds = FXCollections.observableArrayList();

            while (resultSet.next()){
                productIds.add(resultSet.getString(1));
            }
            cmbProductID.setItems(productIds);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadEmployeeId(){

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

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        Stage stage = (Stage) PlaceOrderPane.getScene().getWindow();
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
    void btnPlaceOrderOnAction(ActionEvent event) {

    }
    public void productIDOnAction(ActionEvent actionEvent) {

        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT * FROM product WHERE product_id=?",
                    cmbProductID.getValue().toString()
            );

            if(resultSet.next()){
                lblCatagory.setText(resultSet.getString(2).toString());
                lblSize.setText(resultSet.getString(5).toString());
                lblDesription.setText(resultSet.getString(4).toString());
            } else {
                lblCatagory.setText("not found");
                lblSize.setText("not found");
                lblDesription.setText("not found");
            }



            ResultSet resultSet1 = CrudUtil.execute(
                    "SELECT * FROM inventory WHERE product_id=?",
                    cmbProductID.getValue().toString()
            );

            if(resultSet1.next()){
                lblQtyOnHand.setText(resultSet1.getString(4).toString());
            }

            ObservableList<String> sellPrice = FXCollections.observableArrayList();
            while (resultSet1.next()){
                sellPrice.add(resultSet1.getString(6).toString());
            }
            cmbSellingPrice.setItems(sellPrice);




        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sellPriceOnAction(ActionEvent actionEvent) {

    }
}
