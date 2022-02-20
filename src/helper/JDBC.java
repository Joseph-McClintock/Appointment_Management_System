package helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBC {

    /**Protocol name*/
    private static final String protocol = "jdbc";
    /**vendor name*/
    private static final String vendor = ":mysql:";
    /**Location of db*/
    private static final String location = "//localhost/";
    /**Database name*/
    private static final String databaseName = "client_schedule";
    /**JDBC url value*/
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    /**Driver reference*/
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    /**Database username*/
    private static final String userName = "sqlUser";
    /**Database password*/
    private static String password = "Passw0rd!";
    public static Connection connection;

    /**Function to open the connection with the database
     * @return connection the connection to the database*/
    public Connection openConnection(){
        try {
            Class.forName(driver); // Locate driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference connection object
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return connection;
    }

    /**Function to close the connection with the database*/
    public static void closeConnection(){
        try{
            connection.close();
            System.out.println("Connection closed");
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
