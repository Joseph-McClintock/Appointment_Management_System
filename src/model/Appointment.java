package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

public class Appointment {

    /**List of all the appointments*/
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**Appointment id*/
    private int appointmentId;
    /**Appointment title*/
    private String title;
    /**Appointment description*/
    private String description;
    /**Appointment location*/
    private String location;
    /**Appointment type*/
    private String type;
    /**Appointment start time*/
    private Timestamp startTime;
    /**Appointment end time*/
    private Timestamp endTime;
    /**Appointment created date*/
    private Timestamp createdDate;
    /**Appointment created by*/
    private String createdBy;
    /**Appointment last updated*/
    private Timestamp lastUpdated;
    /**Appointment updated by*/
    private String updatedBy;
    /**Appointment customerId*/
    private int customerId;
    /**Appointment userId*/
    private int userId;
    /**Appointment contactId*/
    private int contactId;

    /**Constructor for Appointment
     * @param appointmentId id
     * @param title the title
     * @param description the desc
     * @param location the location
     * @param type the type
     * @param startTime the start time
     * @param endTime the end time
     * @param createdDate the date the appointment was created
     * @param createdBy who the appointment was created by
     * @param lastUpdated last time updated
     * @param updatedBy updated by
     * @param customerId customers id
     * @param contactId contacts id
     * @param userId current user id*/
    public Appointment(int appointmentId, String title, String description, String location, String type, Timestamp startTime, Timestamp endTime, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String updatedBy, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.updatedBy = updatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**Deletes all the appointments in allAppointments*/
    public static void deleteAllAppointments(){
        allAppointments.removeAll();
    }

    /** Lists all the appointments
     * @return allAppointments in Appointment
     * */
    public static ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }

    /** Adds an appointment to allAppointments
     * @param newAppointment the appointment that's added
     * */
    public static void addAppointment(Appointment newAppointment){
        allAppointments.add(newAppointment);
    }

    /**Uses one lambda to remove an appointment
     * if the inputted id matches any appointment ids
     * Deletes an appointment from allAppointments
     * @param id the id of the appointment that's deleted
     * */
    public static void deleteAppointment(int id){
        allAppointments.removeIf(appointment -> appointment.getAppointmentId() == id);
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
