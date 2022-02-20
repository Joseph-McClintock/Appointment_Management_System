package controller;

import helper.JDBC;
import helper.ToScene;
import helper.runQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    /**Username label*/
    @FXML
    private Label usernameLabel;
    /**Password label*/
    @FXML
    private Label passwordLabel;
    /**Welcome label*/
    @FXML
    private Label welcomeLabel;
    /**Login button*/
    @FXML
    private Button loginButton;
    /**Cancel button*/
    @FXML
    private Button cancelButton;
    /**Login message label*/
    @FXML
    private Label loginMessageLabel;
    /**Username text field*/
    @FXML
    private TextField usernameTextField;
    /**Password password field*/
    @FXML
    private PasswordField passwordPasswordField;
    /**Current users zoneId label*/
    @FXML
    private Label zoneIdLabel;

    private static String currentUser;
    private static int currentUserId;

    public static String getCurrentUser(){
        return currentUser;
    }

    /**Sets the resource bundle and picks the
     * correct one if it is supported
     * @param resourceBundle resource bundle being used*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zone = ZoneId.systemDefault();
        zoneIdLabel.setText(String.valueOf(zone));


        try{
            resourceBundle = ResourceBundle.getBundle("resources/Login", Locale.getDefault());
            welcomeLabel.setText(resourceBundle.getString("welcomeLabel"));
            loginButton.setText(resourceBundle.getString("loginButton"));
            cancelButton.setText(resourceBundle.getString("cancelButton"));
            usernameLabel.setText(resourceBundle.getString("usernameLabel"));
            passwordLabel.setText(resourceBundle.getString("passwordLabel"));
            usernameTextField.setPromptText(resourceBundle.getString("usernameTextField"));
            passwordPasswordField.setPromptText(resourceBundle.getString("passwordField"));


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**Public function to allow you
     * to access the current user ID anywhere
     * @return currentUserId the current user id*/
    public static int getCurrentUserId(){
        currentUserId = runQuery.getCurrentUserId(currentUser);
        return currentUserId;
    }

    /**Validates the login info and logs the
     * attempts in a .txt file
     * @param actionEvent when login is clicked
     * @throws IOException from FXMLLoader*/
    @FXML
    public void loginButtonOnAction(ActionEvent actionEvent) throws IOException {
        Writer writer = new BufferedWriter(new FileWriter("login_activity.txt", true));
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/Login", Locale.getDefault());

        if(!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank()){
            JDBC connectNow = new JDBC();
            Connection connectionDB = connectNow.openConnection();

            try {
                Statement statement = connectionDB.createStatement();
                ResultSet queryResult = statement.executeQuery(runQuery.verifyLogin(usernameTextField, passwordPasswordField));

                while(queryResult.next()){
                    if(queryResult.getInt(1) == 1){
                        currentUser = usernameTextField.getText();
                        ToScene.toMain(actionEvent);
                        writer.append("User name: " + usernameTextField.getText() + " Password: " + passwordPasswordField.getText() + " Login attempt time: " + currentTime + " Successful login\n");
                    }
                    else{
                        loginMessageLabel.setText(resourceBundle.getString("invalidLogin"));
                        writer.append("User name: " + usernameTextField.getText() + " Password: " + passwordPasswordField.getText() + " Login attempt time: " + currentTime + " Failed login\n");
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        writer.close();
    }

    /**Closes the application
     * @param actionEvent when cancel is clicked*/
    public void cancelButtonOnAction(ActionEvent actionEvent){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
