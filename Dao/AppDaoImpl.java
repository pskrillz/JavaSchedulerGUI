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
            String query = "SELECT Appointment_ID, TITLE, DESCRIPTION, LOCATION, TYPE, convert_tz(START, \"+00:00\", ?), convert_tz(END, \"+00:00\", ?), CUSTOMER_ID, CONTACT_ID FROM WJ07tms.appointments;";
            Connection con = getConnection();
            prepStatment = con.prepareStatement(query);
            prepStatment.setString(1, AppMethodsSingleton.getLocalTimezoneOffset());
            prepStatment.setString(2, AppMethodsSingleton.getLocalTimezoneOffset());
            resultSet = prepStatment.executeQuery();

            // loop to make observable list
            while(resultSet.next()) {

                Appointment currApp = new Appointment(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7), resultSet.getInt(8), resultSet.getInt(9));

                allApps.add(currApp);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return allApps;
    }

//    @Override
//    public void addApp(Appointment app) {
//
//    }
//
//    @Override
//    public void updateApp(Appointment app, int appId) {
//
//    }
//
//
//
//    @Override
//    public void deleteApp(Object app) {
//
//    }

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
