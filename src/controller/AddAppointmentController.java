package controller;

import helper.ToScene;
import helper.runQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AddAppointmentController {
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
    public TextField locationTextField;
    /**Appointment title text field*/
    @FXML
    private TextField addAppointmentTitle;
    /**Appointment description text field*/
    @FXML
    private TextField addAppointmentDesc;
    /**Appointment contact combo box*/
    @FXML
    private ComboBox addAppointmentContact;
    /**Appointment type text field*/
    @FXML
    private TextField addAppointmentType;
    /**Appointment start date picker*/
    @FXML
    private DatePicker addAppointmentStartDate;
    /**Appointment start time combo box*/
    @FXML
    private ComboBox addAppointmentStartTime;
    /**Appointment end time combo box*/
    @FXML
    private ComboBox addAppointmentEndTime;

    private int customerId = 0;
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

    /**Allows you to carry over the customer_id
     * from the customer you selected
     * @param selectedCustomer the customer you selected from MainScreenController*/
    @FXML
    public void setCustomer(Customer selectedCustomer){
        appointmentIDText.setText(String.valueOf(runQuery.lastAppointmentId()));
        customerIdText.setText(String.valueOf(selectedCustomer.getCustomerId()));
        customerNameText.setText(selectedCustomer.getCustomerName());
        userIdText.setText(String.valueOf(LoginScreenController.getCurrentUserId()));
        customerId = selectedCustomer.getCustomerId();
        addAppointmentContact.setItems(runQuery.populateContacts());

        addAppointmentStartTime.setItems(appointmentStartTimes);
        addAppointmentEndTime.setItems(appointmentEndTimes);
    }

    /**Saves the new appointment to allAppointments
     * and also saves the appointment to the database
     * @param event Save button is clicked
     * @throws IOException from FXMLLoader
     * */
    @FXML
    public void saveAppointment(ActionEvent event) throws IOException {
        boolean exit = false;

        if(addAppointmentTitle.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The title field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(addAppointmentDesc.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The description field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(addAppointmentType.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The type field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(locationTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The location field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(addAppointmentContact.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "A contact must be selected.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(addAppointmentStartDate.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No start date is chosen, you must pick one.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(addAppointmentStartTime.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No start time is chosen, you must pick one.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else {
            try {

                int appointmentId = runQuery.lastAppointmentId();
                String title = addAppointmentTitle.getText();
                String desc = addAppointmentDesc.getText();
                String location = locationTextField.getText();
                String type = addAppointmentType.getText();

                //THE TIME THE APPOINTMENT BEGINS
                String startTime = (String) addAppointmentStartTime.getSelectionModel().getSelectedItem();
                String startDate = addAppointmentStartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalTime ltStartTime = LocalTime.parse(startTime);
                LocalDate ldStartDate = LocalDate.parse(startDate);
                LocalDateTime ltdStart = LocalDateTime.of(ldStartDate, ltStartTime);
                ZonedDateTime zdtStart = ltdStart.atZone( estZoneId );
                ZonedDateTime zdtStartUTC = zdtStart.withZoneSameInstant(ZoneId.of(String.valueOf(userZoneId)));
                Timestamp startTimestamp = Timestamp.valueOf(zdtStart.toLocalDateTime());

                //THE TIME THE APPOINTMENT ENDS
                String endTime = (String) addAppointmentEndTime.getSelectionModel().getSelectedItem();
                String endDate = addAppointmentStartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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

                ldStartDate = addAppointmentStartDate.getValue();
                trueValidEnd = trueValidEnd.withDayOfMonth(ldStartDate.getDayOfMonth());

                ZonedDateTime userTimeStart = ltdStart.atZone( userZoneId );
                ZonedDateTime userTimeEnd = ltdEnd.atZone( userZoneId );

                if(userTimeStart.isBefore(trueValidStart) && userTimeEnd.isAfter(trueValidEnd)){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment time is outside of business hours!");
                    alert.setTitle("Error Dialog");
                    Optional<ButtonType> result = alert.showAndWait();
                    return;
                }

                Timestamp dateCreated = new Timestamp(System.currentTimeMillis());
                String createdBy = LoginScreenController.getCurrentUser();

                int cusId = customerId;
                int currentUserId = LoginScreenController.getCurrentUserId();
                int contactId = addAppointmentContact.getSelectionModel().getSelectedIndex() + 1;

                //Appointment Time validation
                ZonedDateTime checkValidTimeStart = userTimeStart.withZoneSameInstant(ZoneId.of(String.valueOf(utcZoneId)));
                Timestamp validTime = Timestamp.valueOf(checkValidTimeStart.toLocalDateTime());
                if(runQuery.appointmentTimeValidation(validTime, customerId)){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment time is overlapping with another appointment!");
                    alert.setTitle("Error Dialog");
                    Optional<ButtonType> result = alert.showAndWait();
                    return;
                }

                runQuery.addAppointment(appointmentId, title, desc, location, type, startTimestamp, endTimestamp, dateCreated, createdBy, null, null, cusId, currentUserId, contactId);
                Appointment app = new Appointment(appointmentId, title, desc, location, type, startTimestamp, endTimestamp, dateCreated, createdBy, null, null, cusId, currentUserId, contactId);
                Appointment.addAppointment(app);
                exit = true;


            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "WARNING: Appointment not added!");
                alert.setTitle("Error Dialog");
                alert.showAndWait();
                e.printStackTrace();
                System.out.println("Couldn't add appointment");
            }
        }
        if(exit) {
            ToScene.toMain(event);
        }
    }

    /**Cancels add an appointment
     * @param event Cancel button is clicked
     * @throws IOException from FXMLLoader
     * */
    @FXML
    public void cancelAppointment(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all the data you entered! Do you want to continue?");
        alert.setTitle("Error Dialog");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ToScene.toMain(event);
        }
    }

    /**Sets the appointment end time depending
     * on what start time is selected.
     * @param event StartTime comboBox selection is made
     * */
    @FXML
    public void startTimeSetEndTimeComboBox(ActionEvent event){
        if(addAppointmentStartTime.getSelectionModel().isEmpty()){
            addAppointmentEndTime.setItems(null);
        }
        else {
            addAppointmentEndTime.setItems(appointmentEndTimes);

            for(int i = 0; i <= addAppointmentStartTime.getSelectionModel().getSelectedIndex(); i++){
                if(addAppointmentStartTime.getSelectionModel().getSelectedIndex() == 23) {
                    addAppointmentEndTime.getSelectionModel().select(0);
                } else {
                    addAppointmentEndTime.getSelectionModel().select(i + 1);
                }
            }
        }
    }
}
