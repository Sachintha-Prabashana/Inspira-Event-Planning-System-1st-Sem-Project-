package edu.ijse.inspira1stsemesterproject.controller;

import com.jfoenix.controls.JFXComboBox;
import edu.ijse.inspira1stsemesterproject.dto.tm.ServiceTM;
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
import java.util.ResourceBundle;

public class ServiceController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<ServiceTM, Double> clmPrice;

    @FXML
    private TableColumn<ServiceTM, String> clmServiceId;

    @FXML
    private TableColumn<ServiceTM, String> clmServiceType;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

    }

    private void setCellValues() {
        clmServiceId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        clmServiceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        clmPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
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

    void getFieldValues(){
        String serviceId = lblServiceIdData.getText();
        String serviceType = cmbServiceType.getSelectionModel().getSelectedItem();
        String price = txtPrice.getText();


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }
}
