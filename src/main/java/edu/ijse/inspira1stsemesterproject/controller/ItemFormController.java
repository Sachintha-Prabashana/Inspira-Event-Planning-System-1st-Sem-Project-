package edu.ijse.inspira1stsemesterproject.controller;

import com.jfoenix.controls.JFXComboBox;
import edu.ijse.inspira1stsemesterproject.dto.EmployeeDto;
import edu.ijse.inspira1stsemesterproject.dto.ItemDto;
import edu.ijse.inspira1stsemesterproject.dto.ServiceDto;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
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

    private static final String PRICE_PATTERN = "^[0-9]+(\\.[0-9]{1,2})?$";
    private static final String NAME_PATTERN = "^[A-Za-z ]+$";
    private static final String DESCRIPTION_PATTERN = "^[A-Za-z0-9 ,.-]+$";
    private static final String QTY_PATTERN = "^[1-9][0-9]*$";


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
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String itemId = lblItemIdData.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = itemModel.deleteItem(itemId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Item deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete item...!").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        ItemDto itemDto = getFieldValues();

        if(itemDto != null) {
            try {
                boolean isSaved = itemModel.saveItem(itemDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Item saved successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save item.").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        ItemDto itemDto = getFieldValues();

        if(itemDto != null) {
            try {
                boolean isUpdate = itemModel.updateItem(itemDto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Item updated successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update item.").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    private ItemDto getFieldValues() {
        String itemId = lblItemIdData.getText();
        String itemName = txtItemName.getText();
        String description = txtDescription.getText();
        double price = Double.parseDouble(txtCost.getText());
        int  quantity = Integer.parseInt((txtQuantity.getText()));
        String supplierId = cmbSupplierId.getValue();


        boolean isValidateFields = validateSupplierField();

        if(isValidateFields) {
            return new ItemDto(itemId,itemName,description,price,quantity,supplierId);
        }
        return null;
    }

    private boolean validateSupplierField() {
        boolean isValidPrice = txtCost.getText().matches(PRICE_PATTERN);
        if(!isValidPrice) {
            txtCost.setStyle(txtCost.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidQuantity = txtQuantity.getText().matches(QTY_PATTERN);
        if(!isValidQuantity) {
            txtQuantity.setStyle(txtQuantity.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidName = txtItemName.getText().matches(NAME_PATTERN);
        if(!isValidName) {
            txtItemName.setStyle(txtItemName.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidDescription = txtDescription.getText().matches(DESCRIPTION_PATTERN);
        if(!isValidDescription) {
            txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: red;");
        }

        return isValidPrice && isValidQuantity && isValidName && isValidDescription;

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
            lblSupplierInfo.setText(supplierDto.getSupplierName());
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
