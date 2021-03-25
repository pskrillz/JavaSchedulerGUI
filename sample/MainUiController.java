package sample;

import Dao.CustomerDaoImpl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Country;
import models.Customer;
import models.Division;

import java.sql.SQLException;

public class MainUiController {


    /**
     * All the FXML elements for the Customers Tab
     */


    @FXML private TableView<Customer> customerTableView;
    @FXML private TableColumn<Customer, Integer> custIdCol;
    @FXML private TableColumn<Customer, String> custNameCol;
    @FXML private TableColumn<Customer, String> custPhoneCol;
    @FXML private TableColumn<Customer, String> custAddrCol;
    @FXML private TableColumn<Customer, String> custCountryCol;
    @FXML private TableColumn<Customer, String> custZipCol;
    @FXML private TableColumn<Customer, String> custDivCol;
   // @FXML private TableColumn<Customer, (Date?)> custDateCol;


    @FXML private TextField custIdF;
    @FXML private TextField custNameF;
    @FXML private TextField custAddrF;
    @FXML private TextField custZipF;
    @FXML private TextField custPhoneF;
    @FXML private TextField custSearchF;
    @FXML private ComboBox<Country> custCountryDrop;
    @FXML private ComboBox<Division> custDivDrop;
    @FXML private Button custSearchBtn;
    @FXML private Button custDeleteBtn;
    @FXML private Button custAddBtn;
    @FXML private Button custUpdateBtn;

    Customer selCustomer;
    CustomerDaoImpl customerDao = Dao.CustomerDaoImpl.getInstance();

    /**
     * Important method to get everything set up
     */
    @FXML
    private void initialize(){
    setCustomerTableView();
    setCountriesDrop();
    Dao.CustomerDaoImpl.getInstance().getNeededCountries();

        customerTableView.setOnMouseClicked((MouseEvent event) -> {
            selCustomer = customerTableView.getSelectionModel().getSelectedItem();
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if (selCustomer != null) {
                    // modify and delete buttons are only enabled if an item is selected
                    custDeleteBtn.setDisable(false);
                    custUpdateBtn.setDisable(false);

                }

            }
        });
    }

    public void deleteCustomer(){
        Customer selectedCust = customerTableView.getSelectionModel().getSelectedItem();
        Dao.CustomerDaoImpl.getInstance().deleteCustomer(selectedCust);
        setCustomerTableView();
        sample.AppMethodsSingleton.generateAlert(Alert.AlertType.INFORMATION, "Customer " + selectedCust.getcName() + " deleted successfully!" );
    }

    public void openAddCust() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddNewCustomer.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("uSchedule Main UI");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding(event -> setCustomerTableView()); // reset table after adding

    }


    public void setCustomerTableView(){
       // resetCustomerTable();

        custIdCol.setCellValueFactory(new PropertyValueFactory<>("cId"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("cName"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("cPhone"));
        custAddrCol.setCellValueFactory(new PropertyValueFactory<>("cAddr"));
        custZipCol.setCellValueFactory(new PropertyValueFactory<>("cZip"));
        custDivCol.setCellValueFactory(new PropertyValueFactory<>("cDivId"));
        customerTableView.setItems(customerDao.getAllCustomers());
    }


    public void setCountriesDrop(){
        custCountryDrop.getItems().setAll(customerDao.getNeededCountries());
    }

   // public Country selCountry;
    public void onCountrySelected(){
           Country selCountry = custCountryDrop.getSelectionModel().getSelectedItem();
            custDivDrop.setDisable(false);
            custDivDrop.setItems(Dao.CustomerDaoImpl.getInstance().getSelCountryDivs(selCountry));

    }

    @FXML
    public void fillUpdateForm() throws SQLException {


        selCustomer = customerTableView.getSelectionModel().getSelectedItem();
        System.out.println(selCustomer.getcName());
        custIdF.setText(Integer.toString(selCustomer.getcId()));
        custNameF.setText(selCustomer.getcName());
        custAddrF.setText(selCustomer.getcAddr());
        custZipF.setText(selCustomer.getcZip());
        custPhoneF.setText(selCustomer.getcPhone());
        customerDao.getSpecDivCountry(selCustomer.getcDivId());
        custCountryDrop.setValue(findCountry(customerDao.selCountryId));
        custDivDrop.setValue(findDivision(selCustomer.getcDivId()));

        // custCountryDrop.setValue();
        // custDivDrop.setValue(selCustomer.getcDivId());
    }

    public void updateCustomer(){
        // just id double check
        int id = Integer.parseInt(custIdF.getText());
        SimpleStringProperty name = new SimpleStringProperty(custNameF.getText());
        SimpleStringProperty addy = new SimpleStringProperty(custAddrF.getText());
        SimpleStringProperty zip = new SimpleStringProperty(custZipF.getText());
        SimpleStringProperty phone = new SimpleStringProperty(custPhoneF.getText());
        SimpleIntegerProperty divId = new SimpleIntegerProperty(custDivDrop.getSelectionModel().getSelectedItem().getDivisionId());

        Customer cust = new Customer(name, addy, zip, phone, divId);

        // customerDao.updateCustomer(cust, id);
        try {
            customerDao.updateCustomer(cust, id);
        } catch (RuntimeException e){
            e.printStackTrace();
        } finally{
            setCustomerTableView();
        }
    }


    /**
     * Country findCountry(int countryId)
     * Finds the right country and returns it as the object
     * @param countryId
     */
    Country matchCountry;
   Country findCountry(int countryId){
        for(Country country : customerDao.getNeededCountries()){
            if (country.getCountryId() == countryId){
                matchCountry = country;
            }
        }
        return matchCountry;
    }

    /**
     * Division findDivision(int divId)
     * Returns the division object for the drop down
     * (!) Could be rewritten for speed as its kind of slow right now.
     * @param divId
     * @return
     */
    Division findDivision(int divId){
       for(Division division: customerDao.getSelCountryDivs(matchCountry)){
           if( division.getDivisionId() == divId){
               return division;
           }
       };

       AppMethodsSingleton.generateAlert(Alert.AlertType.ERROR, "Error!");
       return null;
    }


    /*
    End Customers Tab
     */
    // ******************************
    /*
    Begin Appointments Tab
     */




}
