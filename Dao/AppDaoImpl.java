package Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;
import sample.DbConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppDaoImpl implements AppDao{

    PreparedStatement prepStatment = null;
    ResultSet resultSet = null;
    Connection con = null;

    // why keep closing connection?
    private void getConnection() throws SQLException {
        con = DbConnectionFactory.getInstance().getConnection();
    }

    @Override
    public Appointment getApp(Integer id) {
        return null;
    }

    @Override
    public AppDaoImpl getInstance() {
        AppDaoImpl appDao = new AppDaoImpl();
        return appDao;
    }

    @Override
    public ObservableList getAllApps() {
        ObservableList<Appointment> allApps = FXCollections.observableArrayList();
        try {
            String query = "select * from WJ07TMS.appointments";
            prepStatment = con.prepareStatement(query);
            resultSet = prepStatment.executeQuery();

            // loop to make observable list
            while(resultSet.next()) {

                Appointment currApp = new Appointment(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7), resultSet.getInt(12), resultSet.getInt(14));

                allApps.add(currApp);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            return allApps;
        }

    }


    @Override
    public void addApp(Object app) {

    }

    @Override
    public void updateApp(Object app, int appId) {

    }

    @Override
    public void deleteApp(Object app) {

    }



}
