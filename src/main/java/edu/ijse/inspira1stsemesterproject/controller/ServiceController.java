package edu.ijse.inspira1stsemesterproject.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ServiceController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> clmPrice;

    @FXML
    private TableColumn<?, ?> clmServiceId;

    @FXML
    private TableColumn<?, ?> clmServiceType;

    @FXML
    private JFXComboBox<String> cmbServiceType;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblServiceId;

    @FXML
    private Label lblServiceIdData;

    @FXML
    private Label lblServiceType;

    @FXML
    private TableView<?> tblService;

    @FXML
    private TextField txtPrice;




    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    void getFieldValues(){
        String serviceId = lblServiceIdData.getText();
        String serviceType = cmbServiceType.getSelectionModel().getSelectedItem();
        String price = txtPrice.getText();


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}
