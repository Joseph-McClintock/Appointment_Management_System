package controller;

import helper.ToScene;
import helper.runQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    /**Appointment by month combo box*/
    @FXML
    public ComboBox monthComboBox;
    /**Appointment by type combo box*/
    @FXML
    public ComboBox typeComboBox;
    /**Appointment by contact combo box*/
    @FXML
    public ComboBox contactComboBox;
    /**Appointment by title combo box*/
    @FXML
    public ComboBox TitleComboBox;
    /**Table view of appointments by month*/
    @FXML
    public TableView appointmentTableByMonth;
    @FXML
    public TableColumn monthIdColumn;
    @FXML
    public TableColumn monthCusNameColumn;
    @FXML
    public TableColumn monthTitleColumn;
    @FXML
    public TableColumn monthDescColumn;
    @FXML
    public TableColumn monthLocationColumn;
    @FXML
    public TableColumn monthTypeColumn;
    @FXML
    public TableColumn monthStartColumn;
    @FXML
    public TableColumn monthEndColumn;
    @FXML
    public TableColumn monthContactColumn;
    @FXML
    public TableColumn monthUserIdColumn;
    /**Table view of appointments by type*/
    @FXML
    public TableView appointmentTableByType;
    @FXML
    public TableColumn typeIdColumn;
    @FXML
    public TableColumn typeCusNameColumn;
    @FXML
    public TableColumn typeTitleColumn;
    @FXML
    public TableColumn typeDescColumn;
    @FXML
    public TableColumn typeLocationColumn;
    @FXML
    public TableColumn typeTypeColumn;
    @FXML
    public TableColumn typeStartColumn;
    @FXML
    public TableColumn typeEndColumn;
    @FXML
    public TableColumn typeContactColumn;
    @FXML
    public TableColumn typeUserIdColumn;
    /**Table view of appointments by contact*/
    @FXML
    public TableView appointmentTableByContact;
    @FXML
    public TableColumn contactIdColumn;
    @FXML
    public TableColumn contactCusNameColumn;
    @FXML
    public TableColumn contactTitleColumn;
    @FXML
    public TableColumn contactDescColumn;
    @FXML
    public TableColumn contactLocationColumn;
    @FXML
    public TableColumn contactTypeColumn;
    @FXML
    public TableColumn contactStartColumn;
    @FXML
    public TableColumn contactEndColumn;
    @FXML
    public TableColumn contactContactColumn;
    @FXML
    public TableColumn contactUserIdColumn;
    /**Table view of appointments by title*/
    @FXML
    public TableView appointmentTableByTitle;
    @FXML
    public TableColumn TitleIdColumn;
    @FXML
    public TableColumn TitleCusNameColumn;
    @FXML
    public TableColumn TitleTitleColumn;
    @FXML
    public TableColumn TitleDescColumn;
    @FXML
    public TableColumn TitleLocationColumn;
    @FXML
    public TableColumn TitleTypeColumn;
    @FXML
    public TableColumn TitleStartColumn;
    @FXML
    public TableColumn TitleEndColumn;
    @FXML
    public TableColumn TitleContactColumn;
    @FXML
    public TableColumn TitleUserIdColumn;

    private ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    /**Sets all the tables to their correct values*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        monthComboBox.setItems(months);
        typeComboBox.setItems(runQuery.getAppointmentTypes());
        contactComboBox.setItems(runQuery.populateContacts());
        TitleComboBox.setItems(runQuery.getAppointmentTitles());

        monthIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        monthCusNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        monthTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        monthDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        monthLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        monthTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthStartColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        monthEndColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        monthContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        monthUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        typeIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        typeCusNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        typeTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeStartColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        typeEndColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        typeContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        contactCusNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        contactTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        contactDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactStartColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        contactEndColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        contactContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        contactUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TitleIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        TitleCusNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        TitleTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TitleDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        TitleLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        TitleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        TitleStartColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TitleEndColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        TitleContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        TitleUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }

    /**Takes the month selected in the comboBox and
     * filters to only show that month
     * @param event when comboBox is selected*/
    @FXML
    public void setAppointmentsByMonth(ActionEvent event) {
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
        int monthName = monthComboBox.getSelectionModel().getSelectedIndex();
        for(Appointment app : Appointment.getAllAppointments()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(app.getStartTime().getTime()));
            int appMonth = cal.get(Calendar.MONTH);
            if(monthName == appMonth){
                monthlyAppointments.add(app);
            }
        }
        appointmentTableByMonth.setItems(monthlyAppointments);
    }

    /**Takes the type selected in the comboBox and
     * filters to only show that type
     * @param event when comboBox is selected*/
    @FXML
    public void setAppointmentsByType(ActionEvent event) {
        ObservableList<Appointment> typeAppointments = FXCollections.observableArrayList();
        String typeName = (String) typeComboBox.getSelectionModel().getSelectedItem();
        for(Appointment app : Appointment.getAllAppointments()){
            if(typeName.equals(app.getType())){
                typeAppointments.add(app);
            }
        }
        appointmentTableByType.setItems(typeAppointments);
    }

    /**Takes the contact selected in the comboBox and
     * filters to only show that contact
     * @param event when comboBox is selected*/
    @FXML
    public void setAppointmentByContact(ActionEvent event) {
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        int contactId = contactComboBox.getSelectionModel().getSelectedIndex() + 1;
        for(Appointment app : Appointment.getAllAppointments()){
            if(contactId == app.getContactId()){
                contactAppointments.add(app);
            }
        }
        appointmentTableByContact.setItems(contactAppointments);
    }

    /**Takes the title selected in the comboBox and
     * filters to only show that title
     * @param event when comboBox is selected*/
    @FXML
    public void setAppointmentByTitle(ActionEvent event) {
        ObservableList<Appointment> titleAppointments = FXCollections.observableArrayList();
        String titleName = (String) TitleComboBox.getSelectionModel().getSelectedItem();
        for(Appointment app : Appointment.getAllAppointments()){
            if(titleName.equals(app.getTitle())){
                titleAppointments.add(app);
            }
        }
        appointmentTableByTitle.setItems(titleAppointments);
    }

    /**Takes you back to the main screen
     * @param event when button is clicked*/
    @FXML
    public void toMainScreen(ActionEvent event) {
        try{
            ToScene.toMain(event);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
