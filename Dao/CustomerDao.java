package Dao;

import javafx.collections.ObservableList;

public interface CustomerDao<Customer> {

   public Customer getCustomer(Integer id);

   public ObservableList<Customer> getAllCustomers();

   public void addCustomer(Customer customer);

   public void updateCustomer(Customer customer, int divId);

   public void deleteCustomer(Customer customer);
}