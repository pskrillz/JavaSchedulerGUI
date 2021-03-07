package Dao;

import javafx.collections.ObservableList;
import models.Customer;

public class CustomerDaoImpl implements CustomerDao<Customer>{

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
