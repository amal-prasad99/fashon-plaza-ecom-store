package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import entity.tm.ProductTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductRegistrationController implements Initializable {
    @FXML
    private AnchorPane ProductRegistrationPane;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXTreeTableView<ProductTm> tblProduct;

    @FXML
    private TreeTableColumn colCatagory;

    @FXML
    private TreeTableColumn colDiscription;

    @FXML
    private TreeTableColumn colID;

    @FXML
    private TreeTableColumn colName;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    private TreeTableColumn colSize;

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
    private JFXComboBox<String> cmbProductCatagory;

    @FXML
    private Label lblProductId;

    @FXML
    private JFXTextField txtProductDiscription;

    @FXML
    private JFXTextField txtProductName;

    @FXML
    private JFXTextField txtProductSearch;

    @FXML
    private JFXTextField txtProductSize;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCatagory();
        generateID();
    }

    private void generateID(){
        ResultSet resultSet = null;
        try {
            resultSet = CrudUtil.execute(
                    "SELECT product_id FROM product ORDER BY product_id DESC LIMIT 1"
            );

            if(resultSet.next()){
                int proID = Integer.parseInt(resultSet.getString(1).split("[P]")[1]);
                proID++;
                lblProductId.setText(String.format("P%03d",proID));
            }else {
                lblProductId.setText("P001");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        Stage stage = (Stage) ProductRegistrationPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminPage.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCatagory(){
        ObservableList<String> catagory = FXCollections.observableArrayList("Gents","Ladies","Kids");
        cmbProductCatagory.getItems().addAll(catagory);
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

    public void btnUpdateOnAction(ActionEvent actionEvent) {

    }


}
