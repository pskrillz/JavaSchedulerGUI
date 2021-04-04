package Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import models.Appointment;
import sample.AppMethodsSingleton;
import sample.DbConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AppDaoImpl implements AppDao<Appointment>{

    // Global parameters.
    PreparedStatement prepStatment = null;
    ResultSet resultSet = null;
    Connection connection = null;


    public AppDaoImpl() {
    }

    /**
     * getConnection()
     * Returns an instance of a database connection for
     * writing queries.
     * @return
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        connection = DbConnectionFactory.getInstance().getConnection();
        return connection;
    }


    /**
     * Retrieves the appointment database access object instance
     * to main full encapsulation.
     * @return
     */
    public static AppDaoImpl getInstance() {
        AppDaoImpl appDao = new AppDaoImpl();
        return appDao;
    }

    /**
     * getAllApps()
     * Used by the MainUiController to retrieve a list
     * of all appointments in the DB.
     * @return
     */
    @Override
    public ObservableList<Appointment> getAllApps() {
        ObservableList<Appointment> allApps = FXCollections.observableArrayList();
        try {
            String query = "SELECT Appointment_ID, TITLE, DESCRIPTION, LOCATION, TYPE, " +
                    "convert_tz(START, \"+00:00\", ?), convert_tz(END, \"+00:00\", ?), " +
                    "CUSTOMER_ID, CONTACT_ID, USER_ID " +
                    "FROM WJ07tms.appointments " +
                    "ORDER BY START;";
            Connection con = getConnection();
            prepStatment = con.prepareStatement(query);
            prepStatment.setString(1, AppMethodsSingleton.getLocalTimezoneOffset());
            prepStatment.setString(2, AppMethodsSingleton.getLocalTimezoneOffset());
            resultSet = prepStatment.executeQuery();

            // loop to make observable list
            while(resultSet.next()) {

                Appointment currApp = new Appointment(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7), resultSet.getInt(8), resultSet.getInt(9), resultSet.getInt(10));

                allApps.add(currApp);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return allApps;
    }

    @Override
    public ObservableList<Appointment> getAppListByContact(int contactId) {
        ObservableList<Appointment> allApps = FXCollections.observableArrayList();
        try {
            String query = "SELECT Appointment_ID, TITLE, DESCRIPTION, LOCATION, TYPE, " +
                    "convert_tz(START, \"+00:00\", ?), convert_tz(END, \"+00:00\", ?), " +
                    "CUSTOMER_ID, CONTACT_ID, USER_ID " +
                    "FROM WJ07tms.appointments " +
                    "WHERE CONTACT_ID = "  + contactId +
                    " ORDER BY START;";
            Connection con = getConnection();
            prepStatment = con.prepareStatement(query);
            prepStatment.setString(1, AppMethodsSingleton.getLocalTimezoneOffset());
            prepStatment.setString(2, AppMethodsSingleton.getLocalTimezoneOffset());
            resultSet = prepStatment.executeQuery();

            // loop to make observable list
            while(resultSet.next()) {

                Appointment currApp = new Appointment(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7), resultSet.getInt(8), resultSet.getInt(9), resultSet.getInt(10));

                allApps.add(currApp);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return allApps;
    }




    /**
     * addApp()
     * Constructs and executes the sql query to
     * add an appointment to the DB.
     * @param app
     */
    @Override
    public void addApp(Appointment app) {
        if(checkAppOverlap(app) == true){
            AppMethodsSingleton.generateAlert(Alert.AlertType.ERROR, "Appointment overlaps with another appointment!");
            return;
        }
        try {
            connection = getConnection();
            String query = "insert into WJ07tms.appointments (TITLE, DESCRIPTION, LOCATION, TYPE, START, END, CUSTOMER_ID, CONTACT_ID) \n" +
                    "VALUES (?, ?, ?, ?, convert_tz(?, ?, \"+00:00\"), convert_tz(?, ?, \"+00:00\"), ?, ?);";
            prepStatment = connection.prepareStatement(query);

            // Object Params
            prepStatment.setString(1, app.getAppTitle());
            prepStatment.setString(2, app.getAppDesc());
            prepStatment.setString(3, app.getAppLocation());
            prepStatment.setString(4, app.getAppType());
            prepStatment.setString(5, app.getAppStartLocalString());
            prepStatment.setString(6, AppMethodsSingleton.getLocalTimezoneOffset());
            prepStatment.setString(7, app.getAppEndLocalString());
            prepStatment.setString(8, AppMethodsSingleton.getLocalTimezoneOffset());
            prepStatment.setInt(9, app.getAppCustId());
            prepStatment.setInt(10, app.getAppContactId());
            prepStatment.executeUpdate();
            System.out.println("Appointment " + "\"" + app.getAppTitle() + "\"" +  " Added Successfully!");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * updateApp()
     * Constructs the sql query using an appointment object
     * to update the matching appointment in the db.
     * @param app
     * @param appId
     */
    @Override
    public void updateApp(Appointment app, int appId) {
     try {
            connection = getConnection();
            prepStatment = connection.prepareStatement(
                    "update WJ07tms.appointments set TITLE = ?, DESCRIPTION = ?, LOCATION = ?, TYPE= ?, " +
                            "START = convert_tz(?, ?, \"+00:00\"), END = convert_tz(?, ?, \"+00:00\"), CUSTOMER_ID = ?, USER_ID = ?, CONTACT_ID = ?\n" +
                            "WHERE APPOINTMENT_ID = ?;");
            prepStatment.setString(1, app.getAppTitle());
            prepStatment.setString(2, app.getAppDesc());
            prepStatment.setString(3,app.getAppLocation());
            prepStatment.setString(4, app.getAppType());
            prepStatment.setString(5, app.getAppStartLocalString());
            prepStatment.setString(6, AppMethodsSingleton.getLocalTimezoneOffset());
            prepStatment.setString(7, app.getAppEndLocalString());
            prepStatment.setString(8, AppMethodsSingleton.getLocalTimezoneOffset());
            prepStatment.setInt(9, app.getAppCustId());
            prepStatment.setInt(10, app.getAppUserId());
            prepStatment.setInt(11, app.getAppContactId());
            prepStatment.setInt(12, appId);
            prepStatment.executeUpdate();
            System.out.println("Appointment ID #" + Integer.toString(appId) +
                    " Updated Successfully");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * getAppsByWeek()
     * Used by the MainUiController's filter button
     * to retrieve the appointments that are in the specified week range.
     * @param weekRange String
     * @return ObservableList<Appointment>
     */
    public ObservableList<Appointment> getAppsByWeek(String weekRange){
        ObservableList<Appointment> filteredApps = FXCollections.observableArrayList();
        String beginWeek = LocalDate.parse(weekRange.substring(0, 8), DateTimeFormatter.ofPattern("MM-dd-yy")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endWeek = LocalDate.parse(weekRange.substring(11, 19), DateTimeFormatter.ofPattern("MM-dd-yy")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        try {
            String query = "SELECT Appointment_ID, TITLE, DESCRIPTION, LOCATION, TYPE, convert_tz(START, \"+00:00\", ?), convert_tz(END, \"+00:00\", ?)," +
                    " CUSTOMER_ID, CONTACT_ID, USER_ID FROM WJ07tms.appointments " +
                    "WHERE Start between date( ? ) and date( ? );";
            Connection con = getConnection();
            prepStatment = con.prepareStatement(query);
            prepStatment.setString(1, AppMethodsSingleton.getLocalTimezoneOffset());
            prepStatment.setString(2, AppMethodsSingleton.getLocalTimezoneOffset());
            prepStatment.setString(3, beginWeek);
            prepStatment.setString(4, endWeek);
            resultSet = prepStatment.executeQuery();

            System.out.println("after query executed");
            // loop to make observable list
            while(resultSet.next()) {

                Appointment currApp = new Appointment(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7), resultSet.getInt(8),
                        resultSet.getInt(9), resultSet.getInt(10));

                System.out.println(currApp);
                filteredApps.add(currApp);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return filteredApps;

    }


    /**
     * deleteApp()
     * Used by the deleteApp() method of the MainUiController
     * Takes an appointment object and uses it's id to delete it
     * from the db.
     * @param app
     */
    @Override
    public void deleteApp(Appointment app) {
        try {
            connection = getConnection();
            String sql = "delete from WJ07tms.appointments where Appointment_ID = ?";
            prepStatment = connection.prepareStatement(sql);
            prepStatment.setInt(1, app.getAppId());
            prepStatment.executeUpdate();
            System.out.println("Appointment " + app.getAppTitle() + "ID # " + app.getAppId()  +  " deleted Successfully");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }


    /**
     *
     */
    public ObservableList<String> getTypesList(){
        ObservableList<String> types = FXCollections.observableArrayList();
        try {
            connection = getConnection();
            String sql = "SELECT distinct type FROM WJ07tms.appointments";
            prepStatment = connection.prepareStatement(sql);
            resultSet = prepStatment.executeQuery();
            while (resultSet.next()){
                types.add(resultSet.getString(1));
            }
        } catch(SQLException e ){
            e.printStackTrace();
        }
    return types;
    }

    /**
     * checkAppOverlap()
     * Used by addApp()
     * Uses the start and end times of the proposed appointment object parameter
     * to check the db and see if there are any existing appointments with start and end
     * times that overlap.
     * Returns true if overlaps are detected and false if there are no conflicts.
     * @param app
     * @return boolean
     */
    public boolean checkAppOverlap(Appointment app){
        boolean overlap = false;
        try {
            ObservableList<String> results = FXCollections.observableArrayList();
            connection = getConnection();
            String query = "SELECT * FROM WJ07tms.appointments\n" +
                    " WHERE ( \n" +
                    " (start between convert_tz(str_to_date( ? , \"%Y-%m-%d %T\"), ?, \"+00:00\")  and convert_tz(str_to_date( ? , \"%Y-%m-%d %T\"), ?, \"+00:00\")  ) \n" +
                    " or (end between convert_tz(str_to_date( ? , \"%Y-%m-%d %T\"), ?, \"+00:00\") and convert_tz(str_to_date( ? , \"%Y-%m-%d %T\"), ?, \"+00:00\")  )\n" +
                    " ) AND CUSTOMER_ID = ?;";
            prepStatment = connection.prepareStatement(query);

            // Query Params
            prepStatment.setString(1, app.getAppStartLocalString());
            prepStatment.setString(2, AppMethodsSingleton.getLocalTimezoneOffset());
            prepStatment.setString(3, app.getAppEndLocalString());
            prepStatment.setString(4, AppMethodsSingleton.getLocalTimezoneOffset());;
            prepStatment.setString(5, app.getAppStartLocalString());
            prepStatment.setString(6, AppMethodsSingleton.getLocalTimezoneOffset());
            prepStatment.setString(7, app.getAppEndLocalString());
            prepStatment.setString(8, AppMethodsSingleton.getLocalTimezoneOffset());;
            prepStatment.setInt(9, app.getAppCustId());
            resultSet = prepStatment.executeQuery();

            while(resultSet.next()){
                results.add("something");
            }
            if(results.isEmpty()){
                System.out.println("return false");
                overlap = false;
                return false;
            }
            if(!results.isEmpty()){
                System.out.println("return true");
                overlap = true;
                return overlap;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();

        }
        return overlap;
    }



    /**
     * Closes the mysql server connection
     */
    public void closeConnection(){
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


}
