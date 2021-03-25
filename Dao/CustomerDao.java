package Dao;

import javafx.collections.ObservableList;

interface CustomerDao<Customer> {

   public models.Customer getCustomer(Integer id);

   public ObservableList<Customer> getAllCustomers();

   public void addCustomer(Customer customer);

   public void updateCustomer(Customer customer, int divId);

   public void deleteCustomer(Customer customer);
}