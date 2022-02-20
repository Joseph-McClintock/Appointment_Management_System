package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

public class Customer {

    /**List of all the Customers*/
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**Customer id*/
    private int id;
    /**Customer name*/
    private String name;
    /**Customer address*/
    private String address;
    /**Customer postal code*/
    private String postalCode;
    /**Customer phone number*/
    private String phoneNumber;
    /**Customer date created*/
    private Timestamp dateCreated;
    /**Customer created by*/
    private String createdBy;
    /**Customer last updated*/
    private Timestamp lastUpdated;
    /**Customer updated by*/
    private String updatedBy;
    /**Customer division id*/
    private int divisionId;

    /**Constructor for customer
     * @param id customer id
     * @param name the name of the customer
     * @param address customer address
     * @param postalCode the customer postal code
     * @param phoneNumber the phone number
     * @param dateCreated the date the customer was created
     * @param createdBy who the customer was created by
     * @param lastUpdated last time the customer was updated
     * @param updatedBy who updated it last
     * @param divisionId the division id of the customer*/
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, Timestamp dateCreated, String createdBy, Timestamp lastUpdated, String updatedBy, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.updatedBy = updatedBy;
        this.divisionId = divisionId;
    }

    /**Deletes all the customers in allCustomers*/
    public static void deleteAllCustomers(){
        allCustomers.removeAll();
    }

    /** Lists all the customers
     * @return allCustomers in Customer
     * */
    public static ObservableList<Customer> getAllCustomers(){
        return allCustomers;
    }

    /** Adds a customer to allCustomers
     * @param newCustomer the customer that's added
     * */
    public static void addCustomer(Customer newCustomer){
        allCustomers.add(newCustomer);
    }

    /**Uses one lambda to remove a customer
     * if the inputted id matches any customer ids
     * Deletes a customer from allCustomers
     * @param id the id of the customer that's deleted
     * */
    public static void deleteCustomer(int id){
        allCustomers.removeIf(customer -> customer.getCustomerId() == id);
    }

    public int getCustomerId(){
        return id;
    }
    public String getCustomerName(){
        return name;
    }
    public String getCustomerAddress(){
        return address;
    }
    public String getCustomerPostalCode(){
        return postalCode;
    }
    public String getCustomerPhoneNumber(){
        return phoneNumber;
    }
    public Timestamp getCustomerDateCreated(){
        return dateCreated;
    }
    public String getCustomerCreatedBy(){
        return createdBy;
    }
    public Timestamp getCustomerLastUpdated(){
        return lastUpdated;
    }
    public String getCustomerUpdatedBy(){
        return updatedBy;
    }
    public int getCustomerDivisionId(){
        return divisionId;
    }

}
