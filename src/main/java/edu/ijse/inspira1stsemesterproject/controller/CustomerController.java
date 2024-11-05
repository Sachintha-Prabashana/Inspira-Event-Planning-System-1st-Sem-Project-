package edu.ijse.inspira1stsemesterproject.controller;

import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.dto.tm.CustomerTM;
import edu.ijse.inspira1stsemesterproject.model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private Button btnCustomerRepo;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnGenerateRepo;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CustomerTM, String> clmCustId;

    @FXML
    private TableColumn<CustomerTM, Date> clmDate;

    @FXML
    private TableColumn<CustomerTM, String> clmEmail;

    @FXML
    private TableColumn<CustomerTM, String> clmFirstName;

    @FXML
    private TableColumn<CustomerTM, String> clmLastName;

    @FXML
    private TableColumn<CustomerTM, String> clmNic;

    @FXML
    private TableColumn<CustomerTM, String> clmTitle;

    @FXML
    private ComboBox<String> cmbCustTitle;

    @FXML
    private Label lblCustId;

    @FXML
    private Label lblCustIdData;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDateData;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblNic;

    @FXML
    private Label lblTitle;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtNic;

    private final CustomerModel customerModel = new CustomerModel();

    private static final String NAME_PATTERN = "^[A-Za-z ]+$";
    private static final String NIC_PATTERN = "^[0-9]{9}[vVxX]||[0-9]{12}$";
    private static final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        cmbCustTitle.getItems().addAll("Mr", "Ms", "Mrs", "Dr", "Prof","Miss");
    }

    private void setCellValues(){
        clmCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        clmTitle.setCellValueFactory(new PropertyValueFactory<>("customerTitle"));
        clmFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        clmLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clmNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshPage() {

        try {
            refreshTable();
            String nextCustomerID = customerModel.getNextCustomerId();
            lblCustIdData.setText(nextCustomerID);
        }catch (Exception e) {
            e.printStackTrace();
        }


        // Getting today's date
        LocalDate today = LocalDate.now();

// Formatting date as needed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);

        txtFirstName.setText("");
        txtLastName.setText("");
        txtNic.setText("");
        txtEmail.setText("");
        lblDateData.setText(formattedDate);
        //cmbCustTitle.getItems().clear();


        btnSave.setDisable(false);
        btnSearch.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnCustomerRepo.setDisable(true);
        btnGenerateRepo.setDisable(true);
        //btnOpenMailSendModel.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDto> customerDtos = customerModel.getAllCustomers();
        ObservableList<CustomerTM> customerTms = FXCollections.observableArrayList();

        for (CustomerDto customerDto : customerDtos) {
            // String formattedDate = dateFormat.format(customerDto.getRegistrationDate());

            CustomerTM customerTm = new CustomerTM(
                    customerDto.getCustomerId(),
                    customerDto.getCustomerTitle(),
                    customerDto.getFirstName(),
                    customerDto.getLastName(),
                    customerDto.getNic(),
                    customerDto.getEmail(),
                    customerDto.getRegistrationDate()
            );
            customerTms.add(customerTm);
        }
        tblCustomer.setItems(customerTms);
    }

    @FXML
    void btnCustomerRepoOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId = lblCustIdData.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = customerModel.deleteEvent(customerId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Customer...!").show();
            }
        }
    }

    @FXML
    void btnGenerateRepoOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        CustomerDto customerDto = getFieldValues();

        if(customerDto != null) {
            try {
                boolean isSaved = customerModel.saveCustomer(customerDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save customer.").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private CustomerDto getFieldValues() {
        String customerId = lblCustIdData.getText();
        String customerTitle = cmbCustTitle.getSelectionModel().getSelectedItem();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        LocalDate localEventDate = LocalDate.parse(lblDateData.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Date registrationDate = Date.valueOf(localEventDate);

        boolean isValidateFields = validateCustomerFields(firstName, lastName, nic, email);

        if(isValidateFields) {
            return new CustomerDto(customerId,customerTitle,firstName,lastName,nic,email,registrationDate);
        }
        return null;

    }

    private boolean validateCustomerFields(String firstName, String lastName, String nic, String email) {
        boolean isValidName = firstName.matches(NAME_PATTERN) && lastName.matches(NAME_PATTERN);
        boolean isValidNic = nic.matches(NIC_PATTERN);
        boolean isValidEmail = email.matches(EMAIL_PATTERN);

        if (!isValidName) {
            txtFirstName.setStyle(txtFirstName.getStyle() + ";-fx-border-color: red;");
            txtLastName.setStyle(txtLastName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        return isValidName && isValidNic && isValidEmail;
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        CustomerDto customerDto = getFieldValues();

        if(customerDto != null) {
            try{
                boolean isUpdate = customerModel.updateCustomer(customerDto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer updated...!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to update customer...!").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void btnSendMailOnAction(ActionEvent actionEvent) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select customer..!");
            return;
        }

        try{
            // Load the mail dialog from FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SendMailView.fxml"));
            Parent load = loader.load();

            SendMailController sendMailController = loader.getController();

            String email = selectedItem.getEmail();
            sendMailController.setCustomerEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send email");

            // Set window as modal
            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tblCustomerOnClick(MouseEvent event) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblCustIdData.setText(selectedItem.getCustomerId());
            cmbCustTitle.setValue(selectedItem.getCustomerTitle());
            txtFirstName.setText(selectedItem.getFirstName());
            txtLastName.setText(selectedItem.getLastName());
            txtNic.setText(selectedItem.getNic());
            txtEmail.setText(selectedItem.getEmail());
            lblDateData.setText(selectedItem.getRegistrationDate().toString());

            btnSave.setDisable(true);
            btnSearch.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnCustomerRepo.setDisable(false);
        }
    }
}
