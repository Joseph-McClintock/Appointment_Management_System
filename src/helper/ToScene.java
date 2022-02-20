package helper;

import controller.AddAppointmentController;
import controller.ModifyAppointmentController;
import controller.ModifyCustomerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;

public class ToScene {

    /**Function to take you to the main screen
     * @param event when button clicked
     * @throws IOException from FXMLLoader*/
    public static void toMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ToScene.class.getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Management System");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**Function to take you to the add customer screen
     * @param event when button clicked
     * @throws IOException from FXMLLoader*/
    public static void toAddCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ToScene.class.getResource("/view/AddCustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Add customer");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**Function to take you to the modify customer screen
     * @param event when button clicked
     * @param selectedCustomer the selected customer to take
     * @throws IOException from FXMLLoader*/
    public static void toModifyCustomer(ActionEvent event, Customer selectedCustomer) throws IOException {
        FXMLLoader loader = new FXMLLoader(ToScene.class.getResource("/View/ModifyCustomerScreen.fxml"));
        Parent root = loader.load();
        ModifyCustomerController modifyCustomerController = loader.getController();
        modifyCustomerController.setCustomer(selectedCustomer);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Modify customer");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**Function to take you to the add appointment screen
     * @param event when button clicked
     * @param selectedCustomer the selected customer to take
     * @throws IOException from FXMLLoader*/
    public static void toAddAppointment(ActionEvent event, Customer selectedCustomer) throws IOException {
        FXMLLoader loader = new FXMLLoader(ToScene.class.getResource("/View/AddAppointmentScreen.fxml"));
        Parent root = loader.load();
        AddAppointmentController addAppointmentController = loader.getController();
        addAppointmentController.setCustomer(selectedCustomer);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Add appointment");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**Function to take you to the modify appointment screen
     * @param event when button clicked
     * @param selectedAppointment the selected appointment to take
     * @throws IOException from FXMLLoader*/
    public static void toModifyAppointment(ActionEvent event, Appointment selectedAppointment) throws IOException {
        FXMLLoader loader = new FXMLLoader(ToScene.class.getResource("/View/ModifyAppointmentScreen.fxml"));
        Parent root = loader.load();
        ModifyAppointmentController modifyAppointmentController = loader.getController();
        modifyAppointmentController.setAppointment(selectedAppointment);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Modify appointment");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**Function to take to the reports screen
     * @param event when button is clicked
     * @throws IOException from FXMLLoader*/
    public static void toReportsScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ToScene.class.getResource("/view/ReportsScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Reports");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
