package edu.ijse.inspira1stsemesterproject.controller;

import com.jfoenix.controls.JFXComboBox;
import edu.ijse.inspira1stsemesterproject.dto.ItemDto;
import edu.ijse.inspira1stsemesterproject.dto.SupplierDto;
import edu.ijse.inspira1stsemesterproject.dto.tm.EventTM;
import edu.ijse.inspira1stsemesterproject.dto.tm.ItemTM;
import edu.ijse.inspira1stsemesterproject.dto.tm.SupplierTM;
import edu.ijse.inspira1stsemesterproject.model.ItemModel;
import edu.ijse.inspira1stsemesterproject.model.SupplierModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<ItemTM, Double> clmCost;

    @FXML
    private TableColumn<ItemTM, String> clmDescription;

    @FXML
    private TableColumn<ItemTM, String> clmItemId;

    @FXML
    private TableColumn<ItemTM, String> clmItemName;

    @FXML
    private TableColumn<ItemTM, Integer> clmQuantity;

    @FXML
    private TableColumn<SupplierTM, String> clmSupplierId;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private Label lblCost;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblItemId;

    @FXML
    private Label lblItemIdData;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblQuantity;

    @FXML
    private Label lblSupplierId;

    @FXML
    private Label lblSupplierInfo;

    @FXML
    private Label lblSupplierName;

    @FXML
    private TableView<ItemTM> tblItem;

    @FXML
    private TextField txtCost;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtQuantity;

    private final ItemModel itemModel = new ItemModel();
    private final SupplierModel supplierModel = new SupplierModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
    }

    private void setCellValues() {
        clmItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        clmItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        clmCost.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        clmQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        clmSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        try{
            loadSupplierIds();
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void refreshPage() {
        try{
            refreshTable();
            String itemId = itemModel.getNextItemId();
            lblItemIdData.setText(itemId);
        }catch (Exception e){
            e.printStackTrace();
        }

        txtItemName.setText("");
        txtDescription.setText("");
        txtCost.setText("");
        txtQuantity.setText("");

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDto> itemDtos = itemModel.getAllItems();
        ObservableList<ItemTM> itemTMS = FXCollections.observableArrayList();

        for(ItemDto itemDto : itemDtos){
            ItemTM itemTM = new ItemTM(
                    itemDto.getItemId(),
                    itemDto.getItemName(),
                    itemDto.getItemDescription(),
                    itemDto.getItemPrice(),
                    itemDto.getItemQuantity(),
                    itemDto.getSupplierId()
            );
            itemTMS.add(itemTM);

        }
        tblItem.setItems(itemTMS);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    private void loadSupplierIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> supplierIds = supplierModel.getAllSupplierIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(supplierIds);
        cmbSupplierId.setItems(observableList);
    }

    @FXML
    void cmbSupplierOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedSupplierId = cmbSupplierId.getSelectionModel().getSelectedItem();
        SupplierDto supplierDto = supplierModel.findById(selectedSupplierId);

        // If customer found (customerDTO not null)
        if (supplierDto != null) {

            // FIll customer related labels
            lblSupplierName.setText(supplierDto.getSupplierName());
        }
    }


    public void tblItemOnClick(MouseEvent mouseEvent) {
        ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblItemIdData.setText(selectedItem.getItemId());
            txtItemName.setText(String.valueOf(selectedItem.getItemName()));
            txtDescription.setText(selectedItem.getItemDescription());
            txtCost.setText(String.valueOf(selectedItem.getItemPrice()));
            txtQuantity.setText(String.valueOf(selectedItem.getItemQuantity()));
            cmbSupplierId.setValue(selectedItem.getSupplierId());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
}
