package controller;

import helper.runQuery;
import helper.ToScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    /**TextField of customer id*/
    @FXML
    private TextField addCusID;

    /**ComboBox to pick a country*/
    @FXML
    private ComboBox countryComboBox;

    /**ComboBox to add division*/
    @FXML
    private ComboBox divisionsComboBox;

    /**TextField to add name*/
    @FXML
    private TextField addName;

    /**TextField to add address*/
    @FXML
    private TextField addAddress;

    /**TextField to add postal code*/
    @FXML
    private TextField addPostalCode;

    /**TextField to add phone number*/
    @FXML
    private TextField addPhoneNumber;

    /**Sets the country comboBox to the countries
     * in the database.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryComboBox.setItems(runQuery.populateCountryByName());
        addCusID.setText(String.valueOf(runQuery.lastCustomerId()));

    }

    /**Sets the division comboBox to the correct data
     * depending on the country you've selected.
     * @param event Country comboBox selection is made
     * */
    @FXML
    public void setCountriesComboBox(ActionEvent event){
        if(countryComboBox.getSelectionModel().isEmpty()){
            divisionsComboBox.setItems(null);
        }
        else {
            for(int i = 0; i <= countryComboBox.getSelectionModel().getSelectedIndex(); i++){
                if((countryComboBox.getSelectionModel().getSelectedIndex() == i)){
                    divisionsComboBox.setItems(runQuery.populateDivisionsByName(i + 1));
                }
            }
        }
    }
    /**Saves and adds a new customer to allCustomers
     * and to the database
     * @param event save button is clicked
     * @throws IOException from FXMLLoader*/
    @FXML
    public void saveAddCustomerButton(ActionEvent event) throws IOException {
        boolean exit = false;
        if(addName.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Name field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(addAddress.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Address field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(addPostalCode.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Postal Code field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(addPhoneNumber.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Phone Number field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(countryComboBox.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No country is chosen.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(divisionsComboBox.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No division is chosen");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else {
            try {
                int customerId = runQuery.lastCustomerId();
                String customerName = addName.getText();
                String customerAddress = addAddress.getText();
                String postalCode = addPostalCode.getText();
                String customerPhone = addPhoneNumber.getText();
                Timestamp dateCreated = new Timestamp(System.currentTimeMillis());
                String createdBy = LoginScreenController.getCurrentUser();
                int divisionId = runQuery.getDivisionId((String) divisionsComboBox.getValue());
                runQuery.addCustomer(customerId, customerName, customerAddress, postalCode, customerPhone, dateCreated, createdBy, null, null, divisionId);
                Customer cus = new Customer(customerId, customerName, customerAddress, postalCode, customerPhone, dateCreated, createdBy, null, null, divisionId);
                Customer.addCustomer(cus);
                exit = true;
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "WARNING: Customer not added!");
                alert.setTitle("Error Dialog");
                alert.showAndWait();
                e.printStackTrace();
                System.out.println("Couldn't add customer");
            }
        }
        if(exit) {
            ToScene.toMain(event);
        }
    }

    /**Cancels creating a new customer
     * @param event cancel button is clicked
     * @throws IOException from FXMLLoader*/
    @FXML
    public void cancelAddCustomerButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all the data you entered! Do you want to continue?");
        alert.setTitle("Error Dialog");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            ToScene.toMain(event);
        }
    }
}
