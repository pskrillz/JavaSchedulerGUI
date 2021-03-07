package Dao;

import javafx.collections.ObservableList;
import models.Customer;

public class CustomerDaoImpl implements CustomerDao<Customer>{
    private String custId ="";


    private final String db = "WJ07tms.customers";
    private final String commaSpace = ", ";


    private final String querySelectAll = "select * from " + db;
    private final String querySelectSpecific = "select * from " + db + " where CUSTOMER_ID=";
    private final String queryAddCust = "insert into " + db +
        "(CUSTOMER_ID, CUSTOMER_NAME, ADDRESS, POSTAL_CODE, PHONE, DIVISION_ID) values(";
    private final String queryUpdateCust = "update " + db + "where CUSTOMER_ID=" + custId +
            "set ";
    private final String queryDelete = "delete from " + db + " where CUSTOMER_ID=";






    @Override
    public Customer getCustomers(Integer id) {
        return null;
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public void saveCustomer(String name) {

    }

    @Override
    public void updateCustomer(Customer customer, String[] params) {

    }

    @Override
    public void deleteCustomer(Customer customer) {

    }
}
