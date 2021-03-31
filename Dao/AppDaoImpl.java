package Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;
import sample.AppMethodsSingleton;
import sample.DbConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppDaoImpl implements AppDao<Appointment>{

    PreparedStatement prepStatment = null;
    ResultSet resultSet = null;
    Connection connection = null;


    // why keep closing connection?
    private Connection getConnection() throws SQLException {
        connection = DbConnectionFactory.getInstance().getConnection();
        return connection;
    }

//    @Override
//    public Appointment getApp(Integer id) {
//        return null;
//    }


    public static AppDaoImpl getInstance() {
        AppDaoImpl appDao = new AppDaoImpl();
        return appDao;
    }

    @Override
    public ObservableList<Appointment> getAllApps() {
        ObservableList<Appointment> allApps = FXCollections.observableArrayList();
        try {
            String query = "SELECT Appointment_ID, TITLE, DESCRIPTION, LOCATION, TYPE, convert_tz(START, \"+00:00\", ?), convert_tz(END, \"+00:00\", ?), CUSTOMER_ID, CONTACT_ID, USER_ID FROM WJ07tms.appointments;";
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
    public void addApp(Appointment app) {

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
