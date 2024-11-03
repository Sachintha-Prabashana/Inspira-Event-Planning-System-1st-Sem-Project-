package edu.ijse.inspira1stsemesterproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashBoardController {

    @FXML
    private AnchorPane bodyPane;

    @FXML
    private Button btnBooking;

    @FXML
    private Button btnBookingService;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnEmployee;

    @FXML
    private Button btnEvent;

    @FXML
    private Button btnEventSupplier;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnItem;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnService;

    @FXML
    private Button btnSupplier;

    @FXML
    private Label lblHome;

    @FXML
    void btnBookingOnAction(ActionEvent event) {

    }

    @FXML
    void btnBookingServiceOnAction(ActionEvent event) {

    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        loadUI("/view/CustomerForm.fxml");
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {

    }

    @FXML
    void btnEventOnAction(ActionEvent event) {
        loadUI("/view/EventForm.fxml");
    }

    @FXML
    void btnEventSupplierOnAction(ActionEvent event) {

    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {

    }

    @FXML
    void btnItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {

    }

    @FXML
    void btnServiceOnAction(ActionEvent event) {

    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) {

    }

    private void loadUI(String resource) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(resource));
            bodyPane.getChildren().setAll(root);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Throwing a IOException").show();
        }
    }

}
