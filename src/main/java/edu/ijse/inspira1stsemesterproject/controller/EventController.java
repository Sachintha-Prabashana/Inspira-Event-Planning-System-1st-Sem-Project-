package edu.ijse.inspira1stsemesterproject.controller;

import com.jfoenix.controls.JFXComboBox;
import edu.ijse.inspira1stsemesterproject.dto.EventDto;
import edu.ijse.inspira1stsemesterproject.dto.tm.EventTM;
import edu.ijse.inspira1stsemesterproject.model.EventModel;
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
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class EventController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<EventTM, Double> clmBudget;

    @FXML
    private TableColumn<EventTM, String> clmDescription;

    @FXML
    private TableColumn<EventTM, String> clmEventId;

    @FXML
    private TableColumn<EventTM, String> clmEventName;

    @FXML
    private TableColumn<EventTM, String> clmEventType;

    @FXML
    private TableColumn<EventTM, Time> clmTime;

    @FXML
    private TableColumn<EventTM, String> clmVenue;

    @FXML
    private TableColumn<EventTM, Date> clmDate;

    @FXML
    private JFXComboBox<String> cmbEventType;

    @FXML
    private Label lblBudget;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblEventId;

    @FXML
    private Label lblEventIdData;

    @FXML
    private Label lblEventName;

    @FXML
    private Label lblEventType;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblVenue;

    @FXML
    private TableView<EventTM> tblEvent;

    @FXML
    private TextField txtBudget;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtEventName;

    @FXML
    private TextField txtTime;

    @FXML
    private TextField txtVenue;

    private final EventModel eventModel = new EventModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        cmbEventType.getItems().addAll("Wedding","Birthday","Conference","Workshop","Product Launch");

    }

    private void setCellValues() {
        clmEventId.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        clmEventType.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        clmEventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clmBudget.setCellValueFactory(new PropertyValueFactory<>("budget"));
        clmVenue.setCellValueFactory(new PropertyValueFactory<>("venue"));
        clmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        try {
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        try{
            String nextEventId = eventModel.getNextEventId();
            lblEventIdData.setText(nextEventId);
        }catch(Exception e){
            e.printStackTrace();
        }
//        LocalDate today = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String formattedDate = today.format(formatter);

        txtEventName.setText("");
        txtDescription.setText("");
        txtBudget.setText("");
        txtVenue.setText("");
        txtDate.setText("");

        // Get the current time and format it as a Time object
//        LocalTime localTime = LocalTime.now();
//        Time currentTime = Time.valueOf(localTime); // Convert LocalTime to Time
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//        String formattedTime = localTime.format(timeFormatter);
        txtTime.setText("");

        btnSave.setDisable(false);
        btnSearch.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);


    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<EventDto> eventDtos = eventModel.getAllCustomers();
        ObservableList<EventTM> eventTms = FXCollections.observableArrayList();

            for(EventDto eventDto : eventDtos){
            // String formattedDate = dateFormat.format(customerDto.getRegistrationDate());

            EventTM eventTM = new EventTM(
                    eventDto.getEventId(),
                    eventDto.getEventType(),
                    eventDto.getEventName(),
                    eventDto.getDescription(),
                    eventDto.getBudget(),
                    eventDto.getVenue(),
                    eventDto.getDate(),
                    eventDto.getTime()
            );
            eventTms.add(eventTM);
        }
        tblEvent.setItems(eventTms);
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String eventId = lblEventIdData.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this event?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = eventModel.deleteEvent(eventId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Event deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Event...!").show();
            }
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        EventDto eventDto = getFieldvalues();

        try{
            boolean isSaved = eventModel.saveEvent(eventDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Event saved successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Event.").show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String eventId = lblEventIdData.getText();

        try{
            EventDto eventDto = eventModel.searchEvent(eventId);

            if(eventDto != null){
                lblEventIdData.setText(eventDto.getEventId());
                cmbEventType.setAccessibleText(eventDto.getEventType());
                txtEventName.setText(eventDto.getEventName());
                txtDescription.setText(eventDto.getDescription());
                txtBudget.setText(eventDto.getBudget().toString());
                txtVenue.setText(eventDto.getVenue());
                txtDate.setText(eventDto.getDate().toString());
                txtTime.setText(eventDto.getTime().toString());
            }
        }catch (Exception e){

        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    private EventDto getFieldvalues() {
        String eventId = lblEventIdData.getText();
        String eventType = cmbEventType.getSelectionModel().getSelectedItem();
        String eventName = txtEventName.getText();
        String description = txtDescription.getText();
        Double budget = Double.valueOf(txtBudget.getText());
        String venue = txtVenue.getText();
//        LocalDate localEventDate = LocalDate.parse(txtDate.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        LocalTime localEventTime = LocalTime.parse(txtTime.getText(), DateTimeFormatter.ofPattern("HH:mm:ss"));
//
//        Date eventDate = Date.valueOf(localEventDate);
//        Time eventTime = Time.valueOf(localEventTime);
        Date eventDate = Date.valueOf(txtDate.getText());
        Time eventTime = Time.valueOf(txtTime.getText());

        return new EventDto(eventId, eventType, eventName, description, budget, venue, eventDate, eventTime);
    }

    public void tblEventOnClick(MouseEvent mouseEvent) {
        EventTM selectedItem = tblEvent.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblEventIdData.setText(selectedItem.getEventId());
            cmbEventType.setValue(selectedItem.getEventType());
            txtEventName.setText(selectedItem.getEventName());
            txtDescription.setText(selectedItem.getDescription());
            txtBudget.setText(String.valueOf(selectedItem.getBudget()));
            txtVenue.setText(selectedItem.getVenue());
            txtDate.setText(String.valueOf(selectedItem.getDate()));
            txtTime.setText(String.valueOf(selectedItem.getTime()));

            btnSave.setDisable(true);
            btnSearch.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
}
