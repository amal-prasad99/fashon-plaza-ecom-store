package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import entity.Supplier;
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

public class SupplierRegistrationController implements Initializable {
    @FXML
    private AnchorPane SupplierRegistrationPane;

    @FXML
    private JFXTreeTableView<SupplierTm> tblSupplierDetails;

    @FXML
    private TreeTableColumn colCompany;

    @FXML
    private TreeTableColumn colEmail;

    @FXML
    private TreeTableColumn colID;

    @FXML
    private TreeTableColumn colName;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    private TreeTableColumn colTitle;

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
    private JFXComboBox<String> cmbSupplierTitle;

    @FXML
    private Label lblSupplierID;

    @FXML
    private JFXTextField txtSupplierCompany;

    @FXML
    private JFXTextField txtSupplierEmail;

    @FXML
    private JFXTextField txtSupplierName;

    @FXML
    private JFXTextField txtSupplierSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colID.setCellValueFactory(new TreeItemPropertyValueFactory<>("supplierID"));
        colTitle.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("supplierName"));
        colCompany.setCellValueFactory(new TreeItemPropertyValueFactory<>("supplierCompany"));
        colEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("supplierEmail"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btnOption"));

        tblSupplierDetails.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue != null){
                int selectedIndex = newValue.intValue();
                TreeItem<SupplierTm> selectedTreeItem = tblSupplierDetails.getTreeItem(selectedIndex);
                if(selectedTreeItem != null){
                    setData(selectedTreeItem);
                }
            }
        });

        generateID();
        loadTitle();
        loadTable();
    }

    private void setData(TreeItem<SupplierTm> selectedTreeItem) {
        lblSupplierID.setText(selectedTreeItem.getValue().getSupplierID());
        txtSupplierName.setText(selectedTreeItem.getValue().getSupplierName());
        txtSupplierCompany.setText(selectedTreeItem.getValue().getSupplierCompany());
        txtSupplierEmail.setText(selectedTreeItem.getValue().getSupplierEmail());
        cmbSupplierTitle.setValue(selectedTreeItem.getValue().getTitle().toString());
    }

    //======================================= Load Title
    public void loadTitle(){
        ObservableList<String> obs = FXCollections.observableArrayList("Mr.", "Mrs.");
        cmbSupplierTitle.getItems().addAll(obs);
    }

    //======================================= Generate ID
    private void generateID(){
        try {
            ResultSet resultSet = CrudUtil.execute(
                "SELECT supplier_id FROM supplier ORDER BY supplier_id DESC LIMIT 1"
            );

            if(resultSet.next()){
                int supID = Integer.parseInt(resultSet.getString(1).split("[S]")[1]);
                supID++;
                lblSupplierID.setText(String.format("S%03d",supID));
            }else {
                lblSupplierID.setText("S001");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //======================================= Load Table
    private void loadTable(){
        ObservableList<SupplierTm> tmList = FXCollections.observableArrayList();

        List<Supplier> list = new ArrayList<>();

        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT * FROM supplier"
            );

            while (resultSet.next()){
                list.add(new Supplier(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }

            for (Supplier supplier:list){
                JFXButton btn = new JFXButton("Delete");
                BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(227, 92, 92), null, null);
                Background background = new Background(backgroundFill);
                btn.setBackground(background);

                btn.setOnAction(actionEvent -> {
                        deleteSupplier(supplier);
                });

                tmList.add(new SupplierTm(
                        supplier.getSupplierID(),
                        supplier.getTitle(),
                        supplier.getSupplierName(),
                        supplier.getSupplierCompany(),
                        supplier.getSupplierEmail(),
                        btn
                ));
            }

            TreeItem<SupplierTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblSupplierDetails.setRoot(treeItem);
            tblSupplierDetails.setShowRoot(false);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //======================================= Clear Method
    private void clearField(){
        generateID();
        cmbSupplierTitle.setValue("");
        txtSupplierName.clear();
        txtSupplierCompany.clear();
        txtSupplierEmail.clear();

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        Stage stage = (Stage) SupplierRegistrationPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminPage.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearField();
    }

    //======================================= Delete Method
    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        SupplierTm selectedSupplier = tblSupplierDetails.getSelectionModel().getSelectedItem().getValue();

        if(selectedSupplier != null){
            deleteSupplier(new Supplier(
                    selectedSupplier.getSupplierID(),
                    selectedSupplier.getTitle(),
                    selectedSupplier.getSupplierName(),
                    selectedSupplier.getSupplierCompany(),
                    selectedSupplier.getSupplierEmail()
            ));
        }
    }

    private void deleteSupplier(Supplier supplier) {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION,
                "Do you want to dlete " + supplier.getSupplierID()+" supplier? ",ButtonType.YES,ButtonType.NO).showAndWait();

        if (buttonType.get() == ButtonType.YES){
            Boolean isDeleted = null;
            try {
                isDeleted = CrudUtil.execute(
                        "DELETE FROM supplier WHERE supplier_id=?",
                        supplier.getSupplierID()
                );
            } catch (SQLException |ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Supplier Deleted...!").show();
                clearField();
                loadTable();
            }else{
                new Alert(Alert.AlertType.INFORMATION,"Supplier not Deleted...!").show();
            }
        }
    }

    //======================================= Print Method
    @FXML
    void btnPrintOnAction(ActionEvent event) {

    }

    //======================================= Save Method

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        Supplier supplier = new Supplier(
                lblSupplierID.getText(),
                cmbSupplierTitle.getValue().toString(),
                txtSupplierName.getText(),
                txtSupplierCompany.getText(),
                txtSupplierEmail.getText()
        );

        try {
            Boolean isSaved = CrudUtil.execute(
                    "INSERT INTO supplier VALUES(?,?,?,?,?)",
                    supplier.getSupplierID(),
                    supplier.getTitle(),
                    supplier.getSupplierName(),
                    supplier.getSupplierCompany(),
                    supplier.getSupplierEmail()
            );

            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Supplier Saved...!").show();
                loadTable();
                clearField();
            }else{
                new Alert(Alert.AlertType.ERROR,"Supplier not Added...!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    //======================================= Update Method

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        Supplier supplier = new Supplier(
                lblSupplierID.getText(),
                cmbSupplierTitle.getValue().toString(),
                txtSupplierName.getText(),
                txtSupplierCompany.getText(),
                txtSupplierEmail.getText()
        );

        try {
            Boolean isUpdate =CrudUtil.execute(
                    "UPDATE supplier SET supplier_name=?, supplier_company=?, supplier_email=?, title=? WHERE supplier_id=?",
                    supplier.getTitle(),
                    supplier.getSupplierName(),
                    supplier.getSupplierCompany(),
                    supplier.getSupplierEmail(),
                    supplier.getSupplierID()
            );

            if(isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Supplier Updated...!").show();
                clearField();
                loadTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Supplier not Updated...!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }


}
