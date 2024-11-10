package edu.ijse.inspira1stsemesterproject.controller;

import edu.ijse.inspira1stsemesterproject.dto.EventDto;
import edu.ijse.inspira1stsemesterproject.dto.SupplierDto;
import edu.ijse.inspira1stsemesterproject.dto.tm.EventTM;
import edu.ijse.inspira1stsemesterproject.dto.tm.SupplierTM;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<SupplierTM, String> clmEmail;

    @FXML
    private TableColumn<SupplierTM, String> clmSupplierId;

    @FXML
    private TableColumn<SupplierTM, String> clmSupplierName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TableView<SupplierTM> tblSupplier;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblSupplierId;


    @FXML
    private Label lblSupplierIdData;

    SupplierModel supplierModel = new SupplierModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellvalues();
    }

    private void setCellvalues() {
        clmSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        clmSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try{
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void refreshPage() {
        try{
            refreshTable();
            String supplierId = supplierModel.getNextSupplierId();
            lblSupplierIdData.setText(supplierId);

        }catch (Exception e){
            e.printStackTrace();
        }

        txtSupplierName.setText("");
        txtEmail.setText("");

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDto> supplierDtos = supplierModel.getAllSuppliers();
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (SupplierDto supplierDto : supplierDtos) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDto.getSupplierId(),
                    supplierDto.getSupplierName(),
                    supplierDto.getEmail()
            );
            supplierTMS.add(supplierTM);
        }
        tblSupplier.setItems(supplierTMS);


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


}
