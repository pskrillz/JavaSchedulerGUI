package Dao;

import javafx.collections.ObservableList;

interface CustomerDao<Customer> {

   public models.Customer getCustomers(Integer id);

    public ObservableList<Customer> getAllCustomers();

    public void saveCustomer(String name);

   public void updateCustomer(Customer customer, String[] params);

   public void deleteCustomer(Customer customer);
}