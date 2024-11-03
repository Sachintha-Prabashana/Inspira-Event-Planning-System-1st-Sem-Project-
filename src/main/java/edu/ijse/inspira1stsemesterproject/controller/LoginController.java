package edu.ijse.inspira1stsemesterproject.controller;

import edu.ijse.inspira1stsemesterproject.model.UserModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblCreateAcc;

    @FXML
    private Label lblForgotPassword;

    @FXML
    private Label lblLogin;

    @FXML
    private Label lblNotAcc;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblWelcome;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private Label lblError;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    private final UserModel userModel = new UserModel();

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        navigateToDashBoardPage();
    }

    public void navigateToDashBoardPage() {
        if(areFieldsEmpty()){
            showErrorMessage("Please fill all the required fields");
        }else {
            String password = txtPassword.getText();
            String username = txtUsername.getText();
            boolean isMatch = false;

            try{
                isMatch = userModel.validateUser(username,password);
            }catch(SQLException e){
                new Alert(Alert.AlertType.ERROR,"Throwing a SQL Exception");
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
                new Alert(Alert.AlertType.ERROR,"Throwing a ClassNotFound Exception");
                ex.printStackTrace();
            }
            if(isMatch){
                loadUI("/view/DashBoard.fxml");
            } else{
                showErrorMessage("Provided credentials are incorrect");
            }
        }
    }
    private boolean areFieldsEmpty() {
        return txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty();
    }

    @FXML
    void lblCreateAccOnClick(MouseEvent event) {

    }

    @FXML
    void lblForgotPasswordOnClick(MouseEvent event) {

    }

    @FXML
    private void loadUI(String resourse){
        //welcomePane.getChildren().clear();
        try{
            loginPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(resourse));
            load.prefWidthProperty().bind(loginPane.widthProperty());
            load.prefHeightProperty().bind(loginPane.heightProperty());
            loginPane.getChildren().add(load);
        }catch(IOException e){
            new Alert(Alert.AlertType.ERROR,"Throwing a IOException").show();
            e.printStackTrace();
        }
    }

    private void showErrorMessage(String message) {
        lblError.setText(message);
        lblError.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> lblError.setText("")
        ));
        timeline.play();
    }

}
