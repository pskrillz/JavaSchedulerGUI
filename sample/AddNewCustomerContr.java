package sample;

import Dao.CustomerDaoImpl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Country;
import models.Customer;
import models.Division;

public class AddNewCustomerContr {
 //   @FXML private TextField custIdF;
    @FXML private TextField custNameF;
    @FXML private TextField custAddrF;
    @FXML private TextField custZipF;
    @FXML private TextField custPhoneF;
    @FXML private ComboBox<Country> custCountryDrop; // needs the objects in here though
    @FXML private ComboBox<Division> custDivDrop; // make with object
    @FXML private Button submitBtn;
   @FXML
   private void initialize(){
      setCountriesDrop();
   }




    public void addCustomer(){
    SimpleStringProperty name = new SimpleStringProperty(custNameF.getText());
    SimpleStringProperty address = new SimpleStringProperty(custAddrF.getText());
    SimpleStringProperty zip = new SimpleStringProperty(custZipF.getText());
    SimpleStringProperty phone = new SimpleStringProperty(custPhoneF.getText());
  //  int divisionId = (int) custDivDrop.getValue();

       SimpleIntegerProperty divId = new SimpleIntegerProperty(663);

       // need to not have it be hard-coded by getting the div id combo boxes done
        Customer newCustomer = new Customer(name, address, zip, phone, divId);
        CustomerDaoImpl.getInstance().addCustomer(newCustomer);

        //close window
       sample.AppMethodsSingleton.closeWindow(submitBtn);
    }


   public void setCountriesDrop(){
      custCountryDrop.getItems().setAll(Dao.CustomerDaoImpl.getInstance().getNeededCountries());
   }

   public void onCountrySelected(){
      Country selCountry = custCountryDrop.getSelectionModel().getSelectedItem();
      custDivDrop.setDisable(false);
      custDivDrop.setItems(Dao.CustomerDaoImpl.getInstance().getSelCountryDivs(selCountry));
   }


}
