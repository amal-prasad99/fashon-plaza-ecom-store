package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class PlaceOrderController {

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
    private JFXComboBox<?> cmbCatagory;

    @FXML
    private JFXComboBox<?> cmbEmployeeID;

    @FXML
    private JFXComboBox<?> cmbProductID;

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

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

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

}
