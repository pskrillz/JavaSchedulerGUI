package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Customer;

public class AddNewCustomerContr {
 //   @FXML private TextField custIdF;
    @FXML private TextField custNameF;
    @FXML private TextField custAddrF;
    @FXML private TextField custZipF;
    @FXML private TextField custPhoneF;
    @FXML private TextField custSearchF;
    @FXML private ComboBox custCountryDrop; // needs the objects in here though
    @FXML private ComboBox custDivDrop; // make with object
    @FXML private Button custSearchBtn;
    @FXML private Button custDeleteBtn;
    @FXML private Button custAddBtn;
    @FXML private Button custUpdateBtn;



    public void addCustomer(){
    String name = custNameF.getText();
    String address = custAddrF.getText();
    String zip = custZipF.getText();
    String phone = custPhoneF.getText();
    int divisionId = (int) custDivDrop.getValue();

        Customer newCustomer = new Customer(name, address, zip, phone, divisionId);

    }


}
