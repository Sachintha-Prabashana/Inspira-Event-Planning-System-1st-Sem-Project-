package edu.ijse.inspira1stsemesterproject.controller;

import com.jfoenix.controls.JFXComboBox;
import edu.ijse.inspira1stsemesterproject.dto.UserDto;
import edu.ijse.inspira1stsemesterproject.model.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserUpdateFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbUserId;

    @FXML
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtPassoword;

    @FXML
    private TextField txtUsername;

    private final UserModel userModel = new UserModel();

    public void initialize(URL url, ResourceBundle rb) {
        txtUsername.requestFocus();

        try {
            loadUserIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        refreshPage();
    }

    private void refreshPage() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtUsername.setText("");
        txtEmail.setText("");
        txtPassoword.setText("");

        savebtn.setDisable(false);
    }

    private void loadUserIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> userIds = userModel.getAllUserIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(userIds);
        cmbUserId.setItems(observableList);
    }

    @FXML
    void cmbUserIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedUserId = cmbUserId.getSelectionModel().getSelectedItem();
        if (selectedUserId != null) {
            UserDto userDto = userModel.findById(selectedUserId);
            if (userDto != null) {
                txtFirstName.setText(userDto.getFirstName());
                txtLastName.setText(userDto.getLastName());
                txtUsername.setText(userDto.getUsername());
                txtEmail.setText(userDto.getEmail());
                txtPassoword.setText(userDto.getPassword());
            }
        }

    }

    @FXML
    void resetbtnOnAction(ActionEvent event) {
        cmbUserId.setValue(null);
        cmbUserId.setPromptText("Select user Id");

        refreshPage();

    }

    @FXML
    void savebtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String userId = String.valueOf(cmbUserId.getValue());
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassoword.getText();

        String fnamePattern = "^[A-Za-z ]+$";
        String lnamePattern = "^[A-Za-z ]+$";
        String unamePattern = "^[a-zA-Z0-9_]{5,15}$";
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";

        boolean isValidFname = firstName.matches(fnamePattern);
        boolean isValidLname = lastName.matches(lnamePattern);
        boolean isValidUname = username.matches(unamePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPassword = password.matches(passwordPattern);

        txtFirstName.setStyle(txtFirstName.getStyle() + ";-fx-border-color:  #091057;");
        txtLastName.setStyle(txtLastName.getStyle() + ";-fx-border-color:  #091057;");
        txtUsername.setStyle(txtUsername.getStyle() + ";-fx-border-color:  #091057;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color:  #091057;");
        txtPassoword.setStyle(txtPassoword.getStyle() + ";-fx-border-color:  #091057;");

        if (isValidFname && isValidLname && isValidUname && isValidEmail && isValidPassword){
            UserDto dto = new UserDto(userId, firstName, lastName, username, email, password);

            boolean isSaved = userModel.updateUser(dto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User Information saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save user information...!").show();

            }
        }

    }

}
