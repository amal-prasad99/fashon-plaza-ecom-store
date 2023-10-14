package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import entity.Product;
import entity.tm.ProductTm;
import entity.tm.SupplierTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private JFXComboBox<String> cmbProductSize;

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

        colID.setCellValueFactory(new TreeItemPropertyValueFactory<>("productID"));
        colCatagory.setCellValueFactory(new TreeItemPropertyValueFactory<>("productCatagory"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("productName"));
        colDiscription.setCellValueFactory(new TreeItemPropertyValueFactory<>("productDiscription"));
        colSize.setCellValueFactory(new TreeItemPropertyValueFactory<>("productSize"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btnOption"));

        tblProduct.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                int selectedIndex = newValue.intValue(); // Convert the Number to int
                TreeItem<ProductTm> selectedTreeItem = tblProduct.getTreeItem(selectedIndex);
                if (selectedTreeItem != null) {
                    setData(selectedTreeItem);
                }
            }
        });

        loadCatagory();
        generateID();
        loadSize();
        loadTable();
    }

    private void setData(TreeItem<ProductTm> selectedTreeItem) {
        lblProductId.setText(selectedTreeItem.getValue().getProductID());
        txtProductName.setText(selectedTreeItem.getValue().getProductName());
        txtProductDiscription.setText(selectedTreeItem.getValue().getProductDiscription());
        cmbProductSize.setValue(selectedTreeItem.getValue().getProductSize().toString());
        cmbProductCatagory.setValue(selectedTreeItem.getValue().getProductCatagory().toString());
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

    private void loadTable(){
        ObservableList<ProductTm> tmList = FXCollections.observableArrayList();

        List<Product> list = new ArrayList<>();

        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT * FROM product"
            );

            while (resultSet.next()){
                list.add(new Product(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }

            for (Product product:list){
                JFXButton btn = new JFXButton("Delete");
                BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(227, 92, 92), null, null);
                Background background = new Background(backgroundFill);
                btn.setBackground(background);

                btn.setOnAction(actionEvent -> {
                    deleteProduct(product);
                });

                tmList.add(new ProductTm(
                        product.getProductID(),
                        product.getProductCatagory(),
                        product.getProductName(),
                        product.getProductDiscription(),
                        product.getProductSize(),
                        btn
                ));
            }

            TreeItem<ProductTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblProduct.setRoot(treeItem);
            tblProduct.setShowRoot(false);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearField(){
        generateID();
        txtProductName.setText("");
        txtProductDiscription.setText("");
        cmbProductCatagory.setValue("");
        cmbProductSize.setValue("");
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

    private void loadSize(){
        ObservableList<String> size = FXCollections.observableArrayList("Small","Medium","Large","XL","XXL","XXXL");
        cmbProductSize.getItems().addAll(size);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearField();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        ProductTm selectedProduct = tblProduct.getSelectionModel().getSelectedItem().getValue();

        if(selectedProduct!=null){
            deleteProduct(new Product(
                selectedProduct.getProductID(),
                    selectedProduct.getProductCatagory(),
                    selectedProduct.getProductName(),
                    selectedProduct.getProductDiscription(),
                    selectedProduct.getProductSize()
            ));
        }
    }

    private void deleteProduct(Product product) {

        Optional<ButtonType>buttonType = new Alert(Alert.AlertType.CONFIRMATION,
                "Do you want to delete " + product.getProductID()+" product? ",
                ButtonType.YES,ButtonType.NO).showAndWait();

        if (buttonType.get() == ButtonType.YES){

            try {
                Boolean isDeleted = CrudUtil.execute(
                        "DELETE FROM product WHERE product_id=?",
                        product.getProductID()
                );

                if (isDeleted){
                    new Alert(Alert.AlertType.INFORMATION,"Product delete success...!").show();
                    loadTable();
                    clearField();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Product delete failed...!").show();
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

        Product product = new Product(
                lblProductId.getText(),
                cmbProductCatagory.getValue().toString(),
                txtProductName.getText(),
                txtProductDiscription.getText(),
                cmbProductSize.getValue().toString()
        );

        try {
            Boolean isSaved = CrudUtil.execute(
                    "INSERT INTO product VALUES(?,?,?,?,?)",
                    product.getProductID(),
                    product.getProductCatagory(),
                    product.getProductName(),
                    product.getProductDiscription(),
                    product.getProductSize()
            );

            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Product Saved...!").show();
                clearField();
                generateID();
                loadTable();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Product Not Saved...!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Product product = new Product(
                lblProductId.getText(),
                cmbProductCatagory.getValue().toString(),
                txtProductName.getText(),
                txtProductDiscription.getText(),
                cmbProductSize.getValue().toString()
        );

        try {
            Boolean isUpdated = CrudUtil.execute(
                    "UPDATE product SET product_catagory=?, product_name=?, product_discription=?, product_size=? WHERE product_id=?",
                    product.getProductCatagory(),
                    product.getProductName(),
                    product.getProductDiscription(),
                    product.getProductSize(),
                    product.getProductID()
            );

            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Product update success... !").show();
                loadTable();
                clearField();
            }else {
                new Alert(Alert.AlertType.ERROR,"Product update not successful").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
