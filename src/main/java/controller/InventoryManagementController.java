package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import entity.Inventory;
import entity.tm.InventoryTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.CrudUtil;

import javax.sql.rowset.CachedRowSet;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class InventoryManagementController implements Initializable {
    @FXML
    private AnchorPane InventoryManagementPane;

    @FXML
    private TreeTableColumn colInventoryID;

    @FXML
    private TreeTableColumn colBuyingPrice;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    private TreeTableColumn colProductID;

    @FXML
    private TreeTableColumn colQtyOnHand;

    @FXML
    private TreeTableColumn colQtySupplied;

    @FXML
    private TreeTableColumn colSellPrice;

    @FXML
    private TreeTableColumn colSupplierID;

    @FXML
    private JFXTreeTableView<InventoryTm> tblInventory;

    @FXML
    private Label lblInventoryID;

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
    private JFXComboBox<String> cmbProductID;

    @FXML
    private JFXComboBox<String> cmbSupplierID;

    @FXML
    private JFXTextField txtBuyingPrice;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtQtySupplied;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXTextField txtSellingPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colInventoryID.setCellValueFactory(new TreeItemPropertyValueFactory<>("inventoryID"));
        colProductID.setCellValueFactory(new TreeItemPropertyValueFactory<>("productID"));
        colSupplierID.setCellValueFactory(new TreeItemPropertyValueFactory<>("supplierID"));
        colQtyOnHand.setCellValueFactory(new TreeItemPropertyValueFactory<>("qtyHand"));
        colQtySupplied.setCellValueFactory(new TreeItemPropertyValueFactory<>("qtySupplied"));
        colSellPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("sellingPrice"));
        colBuyingPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("buyingPrice"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btnOption"));

        tblInventory.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldValue, newValue)->{
            if (newValue!=null){
                int selectedIndex = newValue.intValue();
                TreeItem<InventoryTm> selectedTreeItem = tblInventory.getTreeItem(selectedIndex);

                if(selectedTreeItem != null){
                    setData(selectedTreeItem);
                }
            }
        });


        loadProductId();
        loadSupplierID();
        generateInventoryID();
        loadTable();
    }

    private void setData(TreeItem<InventoryTm> selectedTreeItem) {
        lblInventoryID.setText(selectedTreeItem.getValue().getInventoryID());
        cmbProductID.setValue(selectedTreeItem.getValue().getProductID().toString());
        cmbSupplierID.setValue(selectedTreeItem.getValue().getSupplierID().toString());
        txtQtyOnHand.setText(String.valueOf(selectedTreeItem.getValue().getQtyHand()));
        txtQtySupplied.setText(String.valueOf(selectedTreeItem.getValue().getQtySupplied()));
        txtSellingPrice.setText(String.valueOf(selectedTreeItem.getValue().getSellingPrice()));
        txtBuyingPrice.setText(String.valueOf(selectedTreeItem.getValue().getBuyingPrice()));
    }

    private void clearFields(){
        generateInventoryID();
        cmbSupplierID.setValue("");
        cmbProductID.setValue("");
        txtQtyOnHand.clear();
        txtQtySupplied.clear();
        txtSellingPrice.clear();
        txtBuyingPrice.clear();
    }

    private void loadTable(){

        ObservableList<InventoryTm> tmList = FXCollections.observableArrayList();
        List<Inventory> list = new ArrayList<>();

        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT * FROM inventory"
            );

            while (resultSet.next()){
                list.add(new Inventory(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getDouble(6),
                        resultSet.getDouble(7)
                ));
            }

            for (Inventory inventory:list){

                JFXButton btn = new JFXButton("Delete");
                BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(227, 92, 92), null, null);
                Background background = new Background(backgroundFill);
                btn.setBackground(background);

                btn.setOnAction(actionEvent -> {
                    deleteInventoryRecord(inventory);
                });

                tmList.add(new InventoryTm(
                        inventory.getInventoryID(),
                        inventory.getProductID(),
                        inventory.getSupplierID(),
                        inventory.getQtyHand(),
                        inventory.getQtySupplied(),
                        inventory.getSellingPrice(),
                        inventory.getBuyingPrice(),
                        btn
                ));
            }
            TreeItem<InventoryTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblInventory.setRoot(treeItem);
            tblInventory.setShowRoot(false);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateInventoryID(){

        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT inventory_id FROM inventory ORDER BY inventory_id DESC LIMIT 1"
            );

            if (resultSet.next()){
                int inventoryId = Integer.parseInt(resultSet.getString(1).split("[I]")[1]);
                inventoryId++;
                lblInventoryID.setText(String.format("I%03d",inventoryId));
            }else {
                lblInventoryID.setText("I001");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        Stage stage = (Stage) InventoryManagementPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminPage.fxml"))));
        } catch (IOException e) {
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

    private void loadSupplierID(){

        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT supplier_id FROM supplier"
            );

            ObservableList<String> supplierIDs = FXCollections.observableArrayList();

            while (resultSet.next()){
                supplierIDs.add(resultSet.getString(1));
            }

            cmbSupplierID.setItems(supplierIDs);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        InventoryTm selectedInventory = tblInventory.getSelectionModel().getSelectedItem().getValue();

        if(selectedInventory!=null){
            deleteInventoryRecord(new Inventory(
                    selectedInventory.getInventoryID(),
                    selectedInventory.getProductID(),
                    selectedInventory.getSupplierID(),
                    selectedInventory.getQtyHand(),
                    selectedInventory.getQtySupplied(),
                    selectedInventory.getSellingPrice(),
                    selectedInventory.getBuyingPrice()
            ));
        }

    }

    private void deleteInventoryRecord(Inventory inventory) {

        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION,
                "Do you want to delete this record? ",ButtonType.YES,ButtonType.NO).showAndWait();
        if (buttonType.get()==ButtonType.YES){

            try {
                Boolean isDeleted = CrudUtil.execute(
                        "DELETE FROM inventory WHERE inventory_id=?",
                        inventory.getInventoryID()
                );

                if (isDeleted){
                    new Alert(Alert.AlertType.INFORMATION,"Inventory Record deleted...!").show();
                    clearFields();
                    loadTable();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Inventory Record not deleted...!").show();
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

        int qtyOnHandUpdate = Integer.parseInt(txtQtyOnHand.getText())+Integer.parseInt(txtQtySupplied.getText());

        Inventory inventory = new Inventory(
                lblInventoryID.getText(),
                cmbProductID.getValue().toString(),
                cmbSupplierID.getValue().toString(),
                qtyOnHandUpdate,
                Integer.parseInt(txtQtySupplied.getText()),
                Double.parseDouble(txtSellingPrice.getText()),
                Double.parseDouble(txtBuyingPrice.getText())
                );

        try {
            Boolean isSaved = CrudUtil.execute(
                    "INSERT INTO inventory VALUES(?,?,?,?,?,?,?)",
                    inventory.getInventoryID(),
                    inventory.getProductID(),
                    inventory.getSupplierID(),
                    inventory.getQtyHand(),
                    inventory.getQtySupplied(),
                    inventory.getSellingPrice(),
                    inventory.getBuyingPrice()
            );

            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Inventory Item saved...!").show();
                clearFields();
                loadTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Inventory Item not saved...!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    public void cmbProductIDOnAction(ActionEvent actionEvent) {

        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT qty_supplied FROM inventory WHERE product_id=?",
                    cmbProductID.getValue().toString()
            );

        int totalQtyOnHand=0;

        while (resultSet.next()){
            int qtyOnProduct = resultSet.getInt("qty_supplied");
            totalQtyOnHand +=qtyOnProduct;
        }

        txtQtyOnHand.setText(String.valueOf(totalQtyOnHand));

        ResultSet resultSet1 = CrudUtil.execute(
//                "UPDATE qty_hand FROM inventory WHERE product_id=?",
                "UPDATE inventory SET qty_hand = "+totalQtyOnHand+" WHERE product_id = ?",
                cmbProductID.getValue().toString()
        );
        loadTable();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
