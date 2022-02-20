package controller;

import helper.ToScene;
import helper.runQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Appointment;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ModifyAppointmentController {

    /**Customer id text field*/
    @FXML
    public TextField customerIdText;
    /**Customer name text field*/
    @FXML
    public TextField customerNameText;
    /**Appointment id text field*/
    @FXML
    public TextField appointmentIDText;
    /**Current user id text field*/
    @FXML
    public TextField userIdText;
    /**Appointment location text field*/
    @FXML
    public TextField modLocationTextField;
    /**Appointment title text field*/
    @FXML
    private TextField modAppointmentTitle;
    /**Appointment description text field*/
    @FXML
    private TextField modAppointmentDesc;
    /**Appointment contact combo box*/
    @FXML
    private ComboBox modAppointmentContact;
    /**Appointment type text field*/
    @FXML
    private TextField modAppointmentType;
    /**Appointment start date picker*/
    @FXML
    private DatePicker modAppointmentStartDate;
    /**Appointment start time combo box*/
    @FXML
    private ComboBox modAppointmentStartTime;
    /**Appointment end time combo box*/
    @FXML
    private ComboBox modAppointmentEndTime;

    ZoneId estZoneId = ZoneId.of( "US/Eastern" );
    ZoneId utcZoneId = ZoneId.of( "UTC" );
    ZoneId userZoneId = ZoneId.systemDefault();

    /**
     * A list of all the appointment start times
     * */
    private ObservableList<String> appointmentStartTimes = FXCollections.observableArrayList( "00:00:00", "01:00:00", "02:00:00", "03:00:00", "04:00:00", "05:00:00", "06:00:00", "07:00:00", "08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00", "21:00:00", "22:00:00", "23:00:00");
    /**
     * A list of all the appointment end times
     * */
    private ObservableList<String> appointmentEndTimes = FXCollections.observableArrayList( "23:45:00", "00:45:00", "01:45:00", "02:45:00", "03:45:00", "04:45:00", "05:45:00", "06:45:00", "07:45:00", "08:45:00", "09:45:00", "10:45:00", "11:45:00", "12:45:00", "13:45:00", "14:45:00", "15:45:00", "16:45:00", "17:45:00", "18:45:00", "19:45:00", "20:45:00", "21:45:00", "22:45:00");

    Appointment appointment = new Appointment(0,null,null,null,null,null,null,null,null,null,null,0,0,0);

    /**Allows you to carry over the appointment information
     * from the appointment you selected
     * @param selectedAppointment the appointment you selected from MainScreenController*/
    @FXML
    public void setAppointment(Appointment selectedAppointment){
        appointmentIDText.setText(String.valueOf(selectedAppointment.getAppointmentId()));
        customerIdText.setText(String.valueOf(selectedAppointment.getCustomerId()));
        customerNameText.setText(runQuery.getCustomerNameById(selectedAppointment.getCustomerId()));
        userIdText.setText(String.valueOf(LoginScreenController.getCurrentUserId()));

        modLocationTextField.setText(String.valueOf(selectedAppointment.getLocation()));
        modAppointmentContact.setItems(runQuery.populateContacts());
        modAppointmentContact.getSelectionModel().select(selectedAppointment.getContactId()-1);

        modAppointmentTitle.setText(selectedAppointment.getTitle());
        modAppointmentDesc.setText(selectedAppointment.getDescription());
        modAppointmentType.setText(selectedAppointment.getType());

        modAppointmentStartTime.setItems(appointmentStartTimes);
        modAppointmentStartDate.setValue(selectedAppointment.getStartTime().toLocalDateTime().toLocalDate());

        LocalDateTime ltdStart = selectedAppointment.getStartTime().toLocalDateTime();
        ZonedDateTime zdtStart = ltdStart.atZone( userZoneId );
        LocalTime ltStart = zdtStart.toLocalTime();
        modAppointmentStartTime.setValue(ltStart + ":00");

        LocalDateTime ltdEnd = selectedAppointment.getEndTime().toLocalDateTime();
        ZonedDateTime zdtEnd = ltdEnd.atZone( userZoneId );
        LocalTime ltEnd = zdtEnd.toLocalTime();
        modAppointmentEndTime.setValue(ltEnd + ":00");

        appointment = selectedAppointment;
    }


    /**Sets the appointment end time depending
     * on what start time is selected.
     * @param event StartTime comboBox selection is made
     * */
    @FXML
    public void startTimeSetEndTimeComboBox(ActionEvent event) {
        if(modAppointmentStartTime.getSelectionModel().isEmpty()){
            modAppointmentEndTime.setItems(null);
        }
        else {
            modAppointmentEndTime.setItems(appointmentEndTimes);

            for(int i = 0; i <= modAppointmentStartTime.getSelectionModel().getSelectedIndex(); i++){
                if(modAppointmentStartTime.getSelectionModel().getSelectedIndex() == 23) {
                    modAppointmentEndTime.getSelectionModel().select(0);
                } else {
                    modAppointmentEndTime.getSelectionModel().select(i + 1);
                }
            }
        }
    }

    /**Saves the updated appointment to allAppointments
     * and also updates the appointment to the database
     * @param event Save button is clicked
     * @throws IOException from FXMLLoader
     * */
    @FXML
    public void saveAppointment(ActionEvent event) throws IOException {
        boolean exit = false;

        if(modAppointmentTitle.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The title field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(modAppointmentDesc.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The description field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(modAppointmentType.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The type field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(modLocationTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The location field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(modAppointmentContact.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "A contact must be selected.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(modAppointmentStartDate.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No start date is chosen, you must pick one.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(modAppointmentStartTime.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No start time is chosen, you must pick one.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else {
            try {

                int appointmentId = appointment.getAppointmentId();
                String title = modAppointmentTitle.getText();
                String desc = modAppointmentDesc.getText();
                String location = modLocationTextField.getText();
                String type = modAppointmentType.getText();

                //THE TIME THE APPOINTMENT BEGINS
                String startTime = (String) modAppointmentStartTime.getSelectionModel().getSelectedItem();
                String startDate = modAppointmentStartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalTime ltStartTime = LocalTime.parse(startTime);
                LocalDate ldStartDate = LocalDate.parse(startDate);
                LocalDateTime ltdStart = LocalDateTime.of(ldStartDate, ltStartTime);
                ZonedDateTime zdtStart = ltdStart.atZone( estZoneId );
                ZonedDateTime zdtStartUTC = zdtStart.withZoneSameInstant(ZoneId.of(String.valueOf(userZoneId)));
                Timestamp startTimestamp = Timestamp.valueOf(zdtStart.toLocalDateTime());

                //THE TIME THE APPOINTMENT ENDS
                String endTime = (String) modAppointmentEndTime.getSelectionModel().getSelectedItem();
                String endDate = modAppointmentStartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalTime ltEndTime = LocalTime.parse(endTime);
                LocalDate ldEndDate = LocalDate.parse(endDate);
                LocalDateTime ltdEnd = LocalDateTime.of(ldEndDate, ltEndTime);
                ZonedDateTime zdtEnd = ltdEnd.atZone( estZoneId );
                ZonedDateTime zdtEndUTC = zdtEnd.withZoneSameInstant(ZoneId.of(String.valueOf(userZoneId)));
                Timestamp endTimestamp = Timestamp.valueOf(zdtEnd.toLocalDateTime());

                String validStartTime = "08:00:00";
                String validEndTime = "22:00:00";
                LocalTime validStart = LocalTime.parse(validStartTime);
                LocalTime validEnd = LocalTime.parse(validEndTime);
                LocalDateTime ldtValidStart = LocalDateTime.of(ldStartDate, validStart);
                LocalDateTime ldtValidEnd = LocalDateTime.of(ldEndDate, validEnd);
                ZonedDateTime zdtValidStart = ldtValidStart.atZone( estZoneId );
                ZonedDateTime zdtValidEnd = ldtValidEnd.atZone( estZoneId );
                ZonedDateTime trueValidStart = zdtValidStart.withZoneSameInstant(userZoneId);
                ZonedDateTime trueValidEnd = zdtValidEnd.withZoneSameInstant(userZoneId);

                ldStartDate = modAppointmentStartDate.getValue();
                trueValidEnd = trueValidEnd.withDayOfMonth(ldStartDate.getDayOfMonth());

                ZonedDateTime userTimeStart = ltdStart.atZone( userZoneId );
                ZonedDateTime userTimeEnd = ltdEnd.atZone( userZoneId );

                if(userTimeStart.isBefore(trueValidStart) && userTimeEnd.isAfter(trueValidEnd)){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment time is outside of business hours!");
                    alert.setTitle("Error Dialog");
                    Optional<ButtonType> result = alert.showAndWait();
                    return;
                }

                Timestamp dateCreated = appointment.getCreatedDate();
                String createdBy = appointment.getCreatedBy();

                Timestamp lastUpdated = new Timestamp(System.currentTimeMillis());
                String updatedBy = LoginScreenController.getCurrentUser();

                int cusId = appointment.getCustomerId();
                int currentUserId = appointment.getUserId();
                int contactId = modAppointmentContact.getSelectionModel().getSelectedIndex() + 1;

                //Appointment Time validation
                ZonedDateTime checkValidTimeStart = userTimeStart.withZoneSameInstant(ZoneId.of(String.valueOf(utcZoneId)));
                Timestamp validTime = Timestamp.valueOf(checkValidTimeStart.toLocalDateTime());
                if(runQuery.appointmentTimeValidation(validTime, cusId) && !(appointment.getStartTime().equals(startTimestamp))){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment time is overlapping with another appointment!");
                    alert.setTitle("Error Dialog");
                    Optional<ButtonType> result = alert.showAndWait();
                    return;
                }

                Appointment.deleteAppointment(appointmentId);
                runQuery.updateAppointment(appointmentId, title, desc, location, type, startTimestamp, endTimestamp, dateCreated, createdBy, lastUpdated, updatedBy, cusId, currentUserId, contactId);
                Appointment app = new Appointment(appointmentId, title, desc, location, type, startTimestamp, endTimestamp, dateCreated, createdBy, lastUpdated, updatedBy, cusId, currentUserId, contactId);
                Appointment.addAppointment(app);
                exit = true;


            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "WARNING: Appointment not updated!");
                alert.setTitle("Error Dialog");
                alert.showAndWait();
                e.printStackTrace();
                System.out.println("Couldn't add customer");
                e.printStackTrace();
            }
        }
        if(exit) {
            ToScene.toMain(event);
        }
    }

    /**Cancels updating the appointment
     * @param event Cancel button is clicked
     * @throws IOException from FXMLLoader
     * */
    @FXML
    public void cancelAppointment(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancel updating appointment?");
        alert.setTitle("Error Dialog");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            ToScene.toMain(event);
        }
    }
}
