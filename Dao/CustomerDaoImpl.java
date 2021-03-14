package Dao;

import javafx.collections.ObservableList;
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

    public CustomerDaoImpl(){

    }

    private Connection getConnection() throws SQLException {
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
    private final String queryDelete = "delete from " + db + " where CUSTOMER_ID=";






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
        return (Customer) resultSet;
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        return null;
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

    }
}
