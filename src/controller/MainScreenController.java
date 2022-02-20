package controller;

import helper.JDBC;
import helper.ToScene;
import helper.runQuery;
import javafx.animation.PauseTransition;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Appointment;
import model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class MainScreenController implements Initializable {

    /**Appointment deleted label*/
    @FXML
    private Label appointmentDeletedInfo;
    /**Customer id column*/
    @FXML
    private TableColumn customerIdColumn;
    /**Customer name column*/
    @FXML
    private TableColumn customerNameColumn;
    /**Customer address column*/
    @FXML
    private TableColumn customerAddressColumn;
    /**Customer postal code column*/
    @FXML
    private TableColumn customerPostalCodeColumn;
    /**Customer phone number column*/
    @FXML
    private TableColumn customerPhoneNumberColumn;
    /**Customer created date column*/
    @FXML
    private TableColumn customerCreatedDateColumn;
    /**Customer created by column*/
    @FXML
    private TableColumn customerCreatedByColumn;
    /**Customer last updated column*/
    @FXML
    private TableColumn customerLastUpdatedColumn;
    /**Customer updated by column*/
    @FXML
    private TableColumn customerUpdatedByColumn;
    /**Customer division id column*/
    @FXML
    private TableColumn customerDivisionIdColumn;
    /**Search customer text field*/
    @FXML
    private TextField queryCustomers;
    /**Appointment table*/
    @FXML
    private TableView appointmentTable;
    /**Appointment id column*/
    @FXML
    private TableColumn appointmentIdColumn;
    /**Appointment customer id column*/
    @FXML
    private TableColumn appointmentCusNameColumn;
    /**Appointment title column*/
    @FXML
    private TableColumn appointmentTitleColumn;
    /**Appointment description column*/
    @FXML
    private TableColumn appointmentDescColumn;
    /**Appointment location column*/
    @FXML
    private TableColumn appointmentLocationColumn;
    /**Appointment type column*/
    @FXML
    private TableColumn appointmentTypeColumn;
    /**Appointment start column*/
    @FXML
    private TableColumn appointmentStartColumn;
    /**Appointment end column*/
    @FXML
    private TableColumn appointmentEndColumn;
    /**Appointment contact id column*/
    @FXML
    private TableColumn appointmentContactColumn;
    /**Appointment current user id column*/
    @FXML
    private TableColumn appointmentUserIdColumn;
    /**Search appointments text field*/
    @FXML
    private TextField queryAppointments;
    /**Appointment soon label*/
    @FXML
    private Label appointmentInfoLabel;
    /**Customer table*/
    @FXML
    private TableView customerTable;

    /**Variable to populate allCustomers and allAppointments*/
    private static boolean firstTime = true;

    /**Function to populate allCustomers and allAppointments*/
    public void firstRun(){
        if(!firstTime){
            return;
        }
        runQuery.populateCustomerData();
        runQuery.populateAppointmentData();
        firstTime = false;
    }

    /**Uses one lambda to search though the
     * allAppointments ObservableList and finds
     * if any appointments are within the next 15
     * minutes. If an appointment is found within
     * the next 15 minutes the appointmentInfoLabel
     * is set to "Upcoming appointment in 15 minutes or less!"
     * and if no appointment is found the label
     * stays it's default value.
     * Populates allCustomers and allAppointments and
     * then populates the tables with said data*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstRun();

        customerTable.setItems(Customer.getAllCustomers());

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customerPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        customerCreatedDateColumn.setCellValueFactory(new PropertyValueFactory<>("customerDateCreated"));
        customerCreatedByColumn.setCellValueFactory(new PropertyValueFactory<>("customerCreatedBy"));
        customerLastUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("customerLastUpdated"));
        customerUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("customerUpdatedBy"));
        customerDivisionIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivisionId"));

        appointmentTable.setItems(Appointment.getAllAppointments());

        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentCusNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appointmentUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        appointmentInfoLabel.setText("No upcoming appointments");
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Timestamp timeAfter15Minutes = new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15));
        try {
            Appointment.getAllAppointments().stream()
                    .filter(a -> a.getStartTime().after(currentTimestamp))
                    .filter(a -> a.getStartTime().before(timeAfter15Minutes))
                    .forEach(a -> appointmentInfoLabel.setText("Upcoming appointment in 15 minutes or less!"));
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Error finding appointments within the next 15 minutes");
        }

    }

    /**Takes you to the add customer screen
     * @param actionEvent when add customer button is clicked
     * @throws IOException from FXMLLoader*/
    @FXML
    public void toAddCustomer(ActionEvent actionEvent) throws IOException {
        try {
            ToScene.toAddCustomer(actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading add customer");
        }
    }

    /**Takes you to the modify customer screen
     * @param actionEvent when update customer button is clicked
     * @throws IOException from FXMLLoader*/
    @FXML
    public void toUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();

        if(!(customerTable.getSelectionModel().isEmpty())){
            try {
                ToScene.toModifyCustomer(actionEvent, selectedCustomer);
            } catch (Exception e){
                e.printStackTrace();
                System.out.print("Error loading updated customer screen");
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No customer was selected to be updated");
            alert.setTitle("Warning Dialog");
            alert.showAndWait();
        }

    }

    /**Deletes the selected customer
     * @param actionEvent when delete customer button is clicked*/
    @FXML
    public void deleteCustomer(ActionEvent actionEvent) {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        if(!(customerTable.getSelectionModel().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected customer?");
            alert.setTitle("Error Dialog");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                if(runQuery.customerHasAppointments(selectedCustomer.getCustomerId())){
                    Alert cusAlert = new Alert(Alert.AlertType.CONFIRMATION, "Customer has associated appointments, please delete those appointments before deleting the customer.");
                    cusAlert.setTitle("Error Dialog");
                    Optional<ButtonType> cusResult = cusAlert.showAndWait();
                    return;
                }
                int id = selectedCustomer.getCustomerId();
                runQuery.deleteCustomer(id);
                Customer.deleteCustomer(id);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No customer was selected to be deleted");
            alert.setTitle("Warning Dialog");
            alert.showAndWait();
        }
    }

    /**Uses two lambdas to search the users by name or id.
     * The use of a lambda here allows me to not have to
     * create two extra functions in the customers class
     * and clears up some code.
     * @param actionEvent when enter is pressed customers are searched
     * */
    @FXML
    public void searchCustomers(ActionEvent actionEvent) {

        String q = queryCustomers.getText();
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            Customer.getAllCustomers().stream()
                    .filter(c -> c.getCustomerName().toLowerCase().startsWith(q))
                    .forEach(customers::add);
            customerTable.setItems(customers);

            if(customers.size() == 0) {
                int id = Integer.parseInt(q);
                Customer.getAllCustomers().stream()
                        .filter(c -> c.getCustomerId() == id)
                        .forEach(customers::add);
                customerTable.setItems(customers);

                if(customers.size() == 0){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No customers with the id " + q + " found!");
                    alert.setTitle("Warning Dialog");
                    alert.showAndWait();
                    customerTable.setItems(Customer.getAllCustomers());
                    queryCustomers.setText("");
                    }
                }
            } catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.WARNING, "No customers with the name " + q + " found!");
                alert.setTitle("Warning Dialog");
                alert.showAndWait();
                customerTable.setItems(Customer.getAllCustomers());
                queryCustomers.setText("");

        }
    }

    /**Takes you to the add appointment screen
     * @param actionEvent when add appointment button is clicked*/
    @FXML
    public void toAddAppointment(ActionEvent actionEvent) {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();

        if(!(customerTable.getSelectionModel().isEmpty())){
            try {
                ToScene.toAddAppointment(actionEvent, selectedCustomer);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No customer was selected to make an appointment for, please select a customer from the customer table then click add appointment.");
            alert.setTitle("Warning Dialog");
            alert.showAndWait();
        }
    }

    /**Takes you to the update appointment screen
     * @param actionEvent when update appointment button is clicked*/
    @FXML
    public void toUpdateAppointment(ActionEvent actionEvent) {
        Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();

        if(!(appointmentTable.getSelectionModel().isEmpty())){
            try {
                ToScene.toModifyAppointment(actionEvent, selectedAppointment);
            } catch (Exception e){
                System.out.print("Error loading updated appointment screen");
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No appointment was selected to be updated");
            alert.setTitle("Warning Dialog");
            alert.showAndWait();
        }

    }

    /**Deletes the selected appointment
     * @param actionEvent when delete appointment button is clicked*/
    @FXML
    public void deleteAppointment(ActionEvent actionEvent) {
        if(!(appointmentTable.getSelectionModel().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected appointment?");
            alert.setTitle("Error Dialog");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
                int id = selectedAppointment.getAppointmentId();
                runQuery.deleteAppointment(id);
                Appointment.deleteAppointment(id);
                appointmentDeletedInfo.setText("An appointment with the ID of: " + selectedAppointment.getAppointmentId() + " and type " + selectedAppointment.getType() + " was deleted!");
                PauseTransition pause = new PauseTransition(Duration.seconds(10));
                pause.setOnFinished(e -> appointmentDeletedInfo.setText(null));
                pause.play();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No appointment was selected to be deleted");
            alert.setTitle("Warning Dialog");
            alert.showAndWait();
        }
    }

    /**Uses one lambda to search the appointments by id.
     * The use of a lambda here allows me to not have to
     * create one extra functions in the appointments class
     * and clears up some code.
     * @param event when enter is pressed appointments are searched
     * */
    @FXML
    public void searchAppointments(ActionEvent event) {
        String q = queryAppointments.getText();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            int id = Integer.parseInt(q);
            Appointment.getAllAppointments().stream()
                    .filter(a -> a.getAppointmentId() == id)
                    .forEach(appointments::add);
            appointmentTable.setItems(appointments);

            if(appointments.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No appointments with the id " + q + " found!");
                alert.setTitle("Warning Dialog");
                alert.showAndWait();
                appointmentTable.setItems(Appointment.getAllAppointments());
                queryAppointments.setText("");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**When the radio button is selected it will
     * show all the appointments in the database
     * @param event when button is toggled*/
    @FXML
    public void showAllAppointmentsRadioButton(ActionEvent event) {
        appointmentTable.setItems(Appointment.getAllAppointments());
    }

    /**When the radio button is selected it will
     * show all the appointments in the next 7 days
     * in the database
     * @param event when button is toggled*/
    @FXML
    public void showWeeklyAppointmentsRadioButton(ActionEvent event) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Timestamp timeAfter1Week = new Timestamp(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7));
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
        for(Appointment app : Appointment.getAllAppointments()){
            if(app.getStartTime().after(currentTimestamp) && app.getEndTime().before(timeAfter1Week)){
                weeklyAppointments.add(app);
                appointmentTable.setItems(weeklyAppointments);
            }
            else {
                appointmentTable.setItems(null);
            }
        }
    }

    /**When the radio button is selected it will
     * show all the appointments in the next 30 days
     * in the database
     * @param event when button is toggled*/
    @FXML
    public void showMonthlyAppointmentsRadioButton(ActionEvent event) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Timestamp timeAfter1Month = new Timestamp(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30));
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
        for(Appointment app : Appointment.getAllAppointments()){
            if(app.getStartTime().after(currentTimestamp) && app.getEndTime().before(timeAfter1Month)){
                monthlyAppointments.add(app);
                appointmentTable.setItems(monthlyAppointments);
            }
            else {
                appointmentTable.setItems(null);
            }
        }
    }

    /**Takes you to the reports page
     * @param event when reports button is clicked*/
    @FXML
    public void toReportsScreen(ActionEvent event) {
        try {
            ToScene.toReportsScreen(event);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading reports screen");
        }
    }

    /**Quits the program and closes the database
     * connection
     * @param event when quit button is clicked*/
    @FXML
    public void onQuitButton(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to quit?");
            alert.setTitle("Close application");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
                JDBC.closeConnection();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
