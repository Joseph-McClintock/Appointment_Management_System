package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Appointment;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;

public class runQuery {

    private static JDBC connectNow = new JDBC();
    private static Connection connectionDB = connectNow.openConnection();

    /**Verifies the login
     * @param passwordPasswordField password entered
     * @param usernameTextField username entered
     * @return userName */
    public static String verifyLogin(TextField usernameTextField, PasswordField passwordPasswordField){
        return "SELECT count(1) FROM users WHERE User_Name = '" + usernameTextField.getText() + "' and Password = '" + passwordPasswordField.getText() + "'";
    }

    /**Gets the current userID from current username
     * @param name username
     * @return currentUser user Id*/
    public static int getCurrentUserId(String name){
        int currentUser = 0;
        try{
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT User_ID FROM users WHERE User_Name = '" + name + "'");

            rs.next();
            currentUser = rs.getInt(1);
        } catch(Exception e){
            e.printStackTrace();
        }
        return currentUser;
    }

    /**Populates comboBoxes with country name
     * @return countryList list of countries*/
    public static ObservableList<String> populateCountryByName(){
        ObservableList<String> countryList = FXCollections.observableArrayList();
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Country FROM countries");

            while(rs.next()){
                countryList.add(new String(rs.getString(1)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return countryList;
    }

    /**Populates comboBoxes with division name from country id
     * @param i country id
     * @return divisionsListName list of division names*/
    public static ObservableList<String> populateDivisionsByName(int i){
        ObservableList<String> divisionsListName = FXCollections.observableArrayList();

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Division, Division_ID FROM first_level_divisions WHERE Country_ID = '" + i + "'");

            while(rs.next()){
                divisionsListName.add(new String(rs.getString(1)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionsListName;
    }

    /**Returns the last customerID
     * @return customerId last customerId + 1*/
    public static int lastCustomerId(){
        int customerId = 0;
        ArrayList<Integer> id = new ArrayList<>();
        try{
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT MAX(Customer_ID) FROM customers;");

            rs.next();
            customerId = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerId + 1;
    }

    /**Gets division id by division name
     * @param divisionName name of the division
     * @return divisionId the divisionId*/
    public static int getDivisionId(String divisionName){
        int divisionId;
        ArrayList<Integer> id = new ArrayList<>();
        try{
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Division_ID FROM first_level_divisions WHERE Division = '" + divisionName + "'");

            while(rs.next()) {
                id.add(rs.getInt(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        divisionId = id.get(id.size() - 1);
        return divisionId;

    }

    public static void addCustomer(int id, String name, String address, String postalCode, String phone, Timestamp dateCreated, String createdBy, Timestamp dateUpdated, String updatedBy, int divisionId){
        try{
            String sql = "INSERT INTO customers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, postalCode);
            ps.setString(5, phone);
            ps.setTimestamp(6, dateCreated);
            ps.setString(7, createdBy);
            ps.setTimestamp(8, dateUpdated);
            ps.setString(9, updatedBy);
            ps.setInt(10, divisionId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**GPopulates customer data*/
    public static void populateCustomerData(){

        Customer.deleteAllCustomers();
        try{
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM customers");

            while(rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                Timestamp dateCreated = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp dateUpdated = rs.getTimestamp("Last_Update");
                String updatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");
                Customer cus = new Customer(customerId, name, address, postalCode, phone, dateCreated, createdBy, dateUpdated, updatedBy, divisionId);
                Customer.addCustomer(cus);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateCustomer(int id, String name, String address, String postalCode, String phone, Timestamp dateCreated, String createdBy, Timestamp dateUpdated, String updatedBy, int divisionId){

        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setTimestamp(5, dateCreated);
            ps.setString(6, createdBy);
            ps.setTimestamp(7, dateUpdated);
            ps.setString(8, updatedBy);
            ps.setInt(9, divisionId);
            ps.setInt(10, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**Gets country id by division id
     * @param divisionId id of division
     * @return countryId the country id - 1*/
    public static int getCustomerCountryName(int divisionId){
        int countryId = 0;
        try{
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT countries.Country_ID FROM countries INNER JOIN first_level_divisions ON first_level_divisions.Country_ID = countries.Country_ID AND first_level_divisions.Division_ID = '" + divisionId + "'");
            while(rs.next()) {
                countryId = rs.getInt(1);;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryId - 1;
    }

    /**Gets division name by country id
     * @param countryId id of the country
     * @return selectedDivision the name of the division*/
    public static String getCustomerDivisionName(int countryId){
        String selectedDivision = null;
        try{
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Division FROM first_level_divisions WHERE Division_ID = '" + countryId + "'");
            while(rs.next()) {
                selectedDivision = rs.getString(1);;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return selectedDivision;
    }

    /**Deletes customer based on id
     * @param id the customerId*/
    public static void deleteCustomer(int id){
        try{
            String sql = "DELETE FROM customers WHERE Customer_ID = '" + id + "'";
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //--------------- Appointment Related Queries Below -------------

    /**Populates the appointments*/
    public static void populateAppointmentData(){

        Appointment.deleteAllAppointments();
        try{
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM appointments");

            while(rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String updatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment app = new Appointment(appointmentId, title, description, location, type, startDate, endDate, createDate, createdBy, lastUpdate, updatedBy, customerId, userId, contactId);
                Appointment.addAppointment(app);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**Gets the last appointment id
     * @return appointmentId the appointmentId + 1*/
    public static int lastAppointmentId(){
        int appointmentId = 0;
        ArrayList<Integer> id = new ArrayList<>();
        try{
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT MAX(Appointment_ID) FROM appointments");

            rs.next();
            appointmentId = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentId + 1;
    }

    /**List of contacts
     * @return contactsListName list of contacts by name*/
    public static ObservableList<String> populateContacts(){
        ObservableList<String> contactsListName = FXCollections.observableArrayList();

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Contact_Name from contacts");

            while(rs.next()){
                contactsListName.add(new String(rs.getString(1)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactsListName;
    }

    public static void addAppointment(int appointmentId, String title, String description, String location, String type, Timestamp startTime, Timestamp endTime, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String updatedBy, int customerId, int userId, int contactId){
        try{
            String sql = "INSERT INTO appointments VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setTimestamp(6, startTime);
            ps.setTimestamp(7, endTime);
            ps.setTimestamp(8, createdDate);
            ps.setString(9, createdBy);
            ps.setTimestamp(10, lastUpdated);
            ps.setString(11, updatedBy);
            ps.setInt(12, customerId);
            ps.setInt(13, userId);
            ps.setInt(14, contactId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateAppointment(int appointmentId, String title, String description, String location, String type, Timestamp startTime, Timestamp endTime, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String updatedBy, int customerId, int userId, int contactId){
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startTime);
            ps.setTimestamp(6, endTime);
            ps.setTimestamp(7, createdDate);
            ps.setString(8, createdBy);
            ps.setTimestamp(9, lastUpdated);
            ps.setString(10, updatedBy);
            ps.setInt(11, customerId);
            ps.setInt(12, userId);
            ps.setInt(13, contactId);
            ps.setInt(14, appointmentId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**Deletes appointments based on id
     * @param id the appointmentId*/
    public static void deleteAppointment(int id){
        try{
            String sql = "DELETE FROM appointments WHERE Appointment_ID = '" + id + "'";
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**Checks if any appointments are soon
     * @param currentTime the current time
     * @param timeAfter15Minutes the time after 15 minutes
     * @return soon true of false depending on if an appointment is in 15 minutes*/
    public static boolean appointmentSoon(Timestamp currentTime, Timestamp timeAfter15Minutes){
        boolean soon = false;
        try{
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Start FROM appointments WHERE Start BETWEEN '" + currentTime + "' AND '" + timeAfter15Minutes + "'");

            if(rs.next()){
                soon = true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return soon;
    }

    /**Validates that the appointment time
     * isn't overlaping with any other appointments
     * @param startTime the start time of the appointments
     * @param customerID the id of the customer
     * @return soon true or false if overlapping*/
    public static boolean appointmentTimeValidation(Timestamp startTime, int customerID){
        boolean soon = false;
        try{
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Start FROM appointments WHERE Start = '" + startTime + "' AND Customer_ID = '" + customerID + "'");
            if(rs.next()){
                soon = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return soon;
    }

    /**Gets the division id by division name
     * @param divisionName the name of the division
     * @return divisionId the id of the division*/
    public static int getDivisionIdByName(String divisionName){
        int divisionId = 0;
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Division_ID FROM first_level_divisions WHERE Division = '" + divisionName + "'");
            rs.next();
            divisionId = rs.getInt(1);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return divisionId;
    }

    /**Gets a list of appointments types
     * @return countryName the list of appointments types*/
    public static ObservableList<String> getAppointmentTypes(){
        ObservableList<String> countryName = FXCollections.observableArrayList();
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT DISTINCT Type FROM appointments");

            while(rs.next()){
                countryName.add(new String(rs.getString(1)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryName;
    }

    /**Gets a list of appointments titles
     * @return countryName the list of appointments titles*/
    public static ObservableList<String> getAppointmentTitles(){
        ObservableList<String> countryName = FXCollections.observableArrayList();
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT DISTINCT Title FROM appointments");

            while(rs.next()){
                countryName.add(new String(rs.getString(1)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryName;
    }

    /**Gets a customer name based of their id
     * @param id the customers id
     * @return customerName the customers name that is found*/
    public static String getCustomerNameById(int id){
        String customerName = null;
        try{
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Customer_Name FROM customers WHERE Customer_ID = '" + id + "'");
            rs.next();
            customerName = rs.getString(1);
        } catch(Exception e){
            e.printStackTrace();
        }
        return customerName;
    }

    /**Checks if the customer has any appointments
     * @param id the customers id
     * @return hasAppointments true or false if appointment is found*/
    public static boolean customerHasAppointments(int id){
        boolean hasAppointments = false;
        try{
            Statement statement = connectionDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM appointments WHERE Customer_ID = '" + id + "'");
            if(rs.next()){
                hasAppointments = true;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return hasAppointments;
    }
}
