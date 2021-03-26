package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DbConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Contact {
    private static Connection connection;
    private static PreparedStatement prepStatment;
    private static ResultSet resultSet;
    private SimpleIntegerProperty conID;
    private SimpleStringProperty conName;
    private SimpleStringProperty conEmail;

    /**
     * contact constructor
     * @param conID
     * @param conName
     * @param conEmail
     */
    public Contact(SimpleIntegerProperty conID, SimpleStringProperty conName, SimpleStringProperty conEmail) {
        this.conID = conID;
        this.conName = conName;
        this.conEmail = conEmail;
    }

    /**
     * Contact getters
     */
    public int getConID() {
        return conID.get();
    }

    public SimpleIntegerProperty conIDProperty() {
        return conID;
    }

    public String getConName() {
        return conName.get();
    }

    public SimpleStringProperty conNameProperty() {
        return conName;
    }

    public String getConEmail() {
        return conEmail.get();
    }

    public SimpleStringProperty conEmailProperty() {
        return conEmail;
    }

    /**
     * Contact setters
     */
    public void setConID(int conID) {
        this.conID.set(conID);
    }

    public void setConName(String conName) {
        this.conName.set(conName);
    }

    public void setConEmail(String conEmail) {
        this.conEmail.set(conEmail);
    }



    /**
     * Database objects
     */

    //Connection connection = null;
  //  PreparedStatement prepStatment = null;
   // ResultSet resultSet = null;


    /**
     * DB methods
     */

    private static Connection getConnection() throws SQLException {
        Connection con = DbConnectionFactory.getInstance().getConnection();
        return con;
    }

    public static void closeConnection(){
        try {
            if (prepStatment != null)
                prepStatment.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Contact Methods
     */

    /**
     * getAllContacts()
     * Used by the appointment tab
     * Grabs all contacts for populating the contact dropdown combo box on main UI init.
     * @return
     */
    public static ObservableList<String> getAllContacts() {
        ObservableList<String> allContacts = FXCollections.observableArrayList();
        try {
            connection = getConnection();
            prepStatment = connection.prepareStatement(
                    "select * from WJ07tms.contacts;");
            resultSet = prepStatment.executeQuery();

            while(resultSet.next()) {
                SimpleIntegerProperty id = new SimpleIntegerProperty(resultSet.getInt(1));
                SimpleStringProperty name = new SimpleStringProperty(resultSet.getString(2));
                SimpleStringProperty email = new SimpleStringProperty(resultSet.getString(3));
                Contact currContact = new Contact(id, name, email);
                allContacts.add(currContact.getConName());
            }


        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return allContacts;
    }

    public static String getSpecificContactEmail(int appContactId){
        String contactEmail = "";
        try {
            connection = getConnection();
            prepStatment = connection.prepareStatement(
                    "select Email from WJ07tms.contacts where Contact_ID = ?;");
            prepStatment.setInt(1, appContactId);
            resultSet = prepStatment.executeQuery();

            while(resultSet.next()) {
                SimpleIntegerProperty id = new SimpleIntegerProperty(resultSet.getInt(1));
                SimpleStringProperty name = new SimpleStringProperty(resultSet.getString(2));
                SimpleStringProperty email = new SimpleStringProperty(resultSet.getString(3));
                Contact currContact = new Contact(id, name, email);
                contactEmail = currContact.getConEmail();
            }

          //  contactEmail = resultSet.getString(1);


        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return contactEmail;
    }





    }
