package controller;

import helper.ToScene;
import helper.runQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Customer;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

public class ModifyCustomerController {

    /**TextField of customer id*/
    @FXML
    public TextField cusIDText;
    /**TextField to mod name*/
    @FXML
    private TextField modName;

    /**TextField to mod address*/
    @FXML
    private TextField modAddress;

    /**TextField to mod postal code*/
    @FXML
    private TextField modPostalCode;

    /**TextField to mod phone number*/
    @FXML
    private TextField modPhoneNumber;

    /**ComboBox to pick a country*/
    @FXML
    private ComboBox countryComboBox;

    /**ComboBox to mod division*/
    @FXML
    private ComboBox divisionsComboBox;

    Customer customer = new Customer(0, null, null, null, null, null, null, null, null, 0);

    /**Allows you to carry over the customer information
     * from the customer you selected
     * @param selectedCustomer the customer you selected from MainScreenController*/
    @FXML
    public void setCustomer(Customer selectedCustomer){
        cusIDText.setText(String.valueOf(selectedCustomer.getCustomerId()));
        countryComboBox.setItems(runQuery.populateCountryByName());

        modName.setText(selectedCustomer.getCustomerName());
        modAddress.setText(selectedCustomer.getCustomerAddress());
        modPostalCode.setText(selectedCustomer.getCustomerPostalCode());
        modPhoneNumber.setText(selectedCustomer.getCustomerPhoneNumber());

        if(countryComboBox.getSelectionModel().isEmpty()) {
            int divisionId = selectedCustomer.getCustomerDivisionId();
            countryComboBox.getSelectionModel().select(runQuery.getCustomerCountryName(divisionId));
            if(countryComboBox.getSelectionModel().isEmpty()){
                divisionsComboBox.setItems(null);
            }
            else {
                for(int i = 0; i <= countryComboBox.getSelectionModel().getSelectedIndex(); i++){
                    if((countryComboBox.getSelectionModel().getSelectedIndex() == i)){
                        divisionsComboBox.setItems(runQuery.populateDivisionsByName(i + 1));
                        divisionsComboBox.getSelectionModel().select(runQuery.getCustomerDivisionName(divisionId));
                    }
                }
            }
        }
        customer = selectedCustomer;
    }

    /**Sets the division comboBox to the correct data
     * depending on the country you've selected.
     * @param actionEvent Country comboBox selection is made
     * */
    @FXML
    public void setCountriesComboBox(ActionEvent actionEvent) {
        if(countryComboBox.getSelectionModel().isEmpty()){
            divisionsComboBox.setItems(null);
        }
        else {
            divisionsComboBox.setItems(null);
            divisionsComboBox.getSelectionModel().select(null);
            for(int i = 0; i <= countryComboBox.getSelectionModel().getSelectedIndex(); i++){
                if((countryComboBox.getSelectionModel().getSelectedIndex() == i)){
                    divisionsComboBox.setItems(runQuery.populateDivisionsByName(i + 1));
                }
            }
        }
    }

    /**Saves and updates the customer to allCustomers
     * and to the database
     * @param actionEvent save button is clicked
     * @throws IOException from FXMLLoader*/
    @FXML
    public void saveModCustomerButton(ActionEvent actionEvent) throws IOException {
        boolean exit = false;
        if(modName.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Name field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(modAddress.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Address field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(modPostalCode.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Postal Code field must be filled.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        else if(modPhoneNumber.getText().isEmpty()){
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
                int customerId = customer.getCustomerId();
                String customerName = modName.getText();
                String customerAddress = modAddress.getText();
                String postalCode = modPostalCode.getText();
                String customerPhone = modPhoneNumber.getText();
                Timestamp dateCreated = customer.getCustomerDateCreated();
                String createdBy = customer.getCustomerCreatedBy();
                Timestamp lastUpdated = new Timestamp(System.currentTimeMillis());
                String updatedBy = LoginScreenController.getCurrentUser();
                int divisionId = runQuery.getDivisionId((String) divisionsComboBox.getValue());
                Customer.deleteCustomer(customerId);
                runQuery.updateCustomer(customerId, customerName, customerAddress, postalCode, customerPhone, dateCreated, createdBy, lastUpdated, updatedBy, divisionId);
                Customer cus = new Customer(customerId, customerName, customerAddress, postalCode, customerPhone, dateCreated, createdBy, lastUpdated, updatedBy, divisionId);
                Customer.addCustomer(cus);
                exit = true;
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "WARNING: Customer not updated!");
                alert.setTitle("Error Dialog");
                alert.showAndWait();
                e.printStackTrace();
                System.out.println("Couldn't add customer");
            }
        }
        if(exit) {
            ToScene.toMain(actionEvent);
        }
    }

    /**Cancels creating a new customer
     * @param actionEvent cancel button is clicked
     * @throws IOException from FXMLLoader*/
    @FXML
    public void cancelModCustomerButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancel updating customer?");
        alert.setTitle("Error Dialog");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            ToScene.toMain(actionEvent);
        }
    }
}
