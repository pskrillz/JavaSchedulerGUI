package Dao;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Country;
import models.Customer;
import sample.DbConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImpl implements CustomerDao<Customer>{
    /**
     * Connection connection, PreparedStatement preparedStatement, ResultSet resultSet:
     * Initializing JDBC objects for future use.
     */
    Connection connection = null;
    PreparedStatement prepStatment = null;
    ResultSet resultSet = null;

    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    public CustomerDaoImpl(){

    }

    public static CustomerDaoImpl getInstance(){
        CustomerDaoImpl custDatabase = new CustomerDaoImpl();
        return custDatabase;
    }

    public Connection getConnection() throws SQLException {
        Connection con = DbConnectionFactory.getInstance().getConnection();
        return con;
    }

    private String custId ="";

    private final String db = "WJ07tms.customers";
    private final String commaSpace = ", ";

    private final String querySelectAll = "select * from " + db;
    private final String querySelectSpecific = "select * from " + db + " where CUSTOMER_ID=?";
    private final String queryAddCust = "insert into " + db +
        "(CUSTOMER_NAME, PHONE, ADDRESS, POSTAL_CODE,  DIVISION_ID) values(?,?,?,?,?)";
    private final String queryUpdateCust = "update " + db + "where CUSTOMER_ID=" + custId +
            "set ";
    private final String queryDelete = "delete from " + db + " where CUSTOMER_ID=?";






    @Override
    public Customer getCustomer(Integer id) {
        try {
            connection = getConnection();
            prepStatment = connection.prepareStatement(querySelectSpecific);
            prepStatment.setInt(1, id);
            resultSet = prepStatment.executeQuery();
            System.out.println(resultSet);
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return (Customer) resultSet; // will casting work here?
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        try {
            connection = getConnection();
            prepStatment = connection.prepareStatement(querySelectAll);
            resultSet = prepStatment.executeQuery();

            // loop to make observable list
            while(resultSet.next()) {
                SimpleIntegerProperty id = new SimpleIntegerProperty(resultSet.getInt(1));
                SimpleStringProperty name = new SimpleStringProperty(resultSet.getString(2));
                SimpleStringProperty addr = new SimpleStringProperty(resultSet.getString(3)) ;
                SimpleStringProperty zip = new SimpleStringProperty(resultSet.getString(4)) ;
                SimpleStringProperty phone =  new SimpleStringProperty(resultSet.getString(5));
                SimpleIntegerProperty divId = new SimpleIntegerProperty(resultSet.getInt(10));


             Customer currCust = new Customer(id, name, addr, zip, phone, divId);

              allCustomers.add(currCust);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return allCustomers;

    }


    /**
     * void closeConnection()
     * Closes connection after query is executed and
     * checks for errors.
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

    /**
     * addCustomer(Customer customer)
     * Prepares and executes a statement using the customer object argument
     * and closes connection afterwards.
     * @param customer
     */
    @Override
    public void addCustomer(Customer customer) {
        try {
            connection = getConnection();
            prepStatment = connection.prepareStatement(queryAddCust);
            prepStatment.setString(1, customer.getcName());
            prepStatment.setString(2, customer.getcPhone());
            prepStatment.setString(3, customer.getcAddr());
            prepStatment.setString(4, customer.getcZip());
            prepStatment.setInt(5, customer.getcDivId());
            prepStatment.executeUpdate();
            System.out.println("Customer " + customer.getcName() +  " Added Successfully");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void updateCustomer(Customer customer, String[] params) {

    }

    @Override
    public void deleteCustomer(Customer customer) {
        try {
            connection = getConnection();
            prepStatment = connection.prepareStatement(queryDelete);
            prepStatment.setInt(1, customer.getcId());
            prepStatment.executeUpdate();
            System.out.println("Customer " + customer.getcName() +  " deleted Successfully");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public ObservableList<Country> getAllCountries() {
        try {
            connection = getConnection();
            prepStatment = connection.prepareStatement("SELECT * FROM WJ07tms.countries;");
            resultSet = prepStatment.executeQuery();

            // loop to make observable list
            while(resultSet.next()) {
                SimpleIntegerProperty id = new SimpleIntegerProperty(resultSet.getInt(1));
                SimpleStringProperty name = new SimpleStringProperty(resultSet.getString(2));
                Country currCountry = new Country(id, name);
                allCountries.add(currCountry);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            // re-use the Customer DAO
            closeConnection();
        }

        return allCountries;

    }



}
