package sample;

import Dao.AppDaoImpl;
import Dao.CustomerDaoImpl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainUiController {


    /**
     * All the FXML elements for the Customers Tab
     */


    @FXML private TableView<Customer> customerTableView;
    @FXML private TableColumn<Customer, Integer> custIdCol;
    @FXML private TableColumn<Customer, String> custNameCol;
    @FXML private TableColumn<Customer, String> custPhoneCol;
    @FXML private TableColumn<Customer, String> custAddrCol;
    @FXML private TableColumn<Customer, String> custZipCol;
    @FXML private TableColumn<Customer, String> custDivCol;


    @FXML private TextField custIdF;
    @FXML private TextField custNameF;
    @FXML private TextField custAddrF;
    @FXML private TextField custZipF;
    @FXML private TextField custPhoneF;
    @FXML private ComboBox<Country> custCountryDrop;
    @FXML private ComboBox<Division> custDivDrop;
    @FXML private Button custDeleteBtn;
    @FXML private Button custAddBtn;
    @FXML private Button custUpdateBtn;

    Customer selCustomer;
    CustomerDaoImpl customerDao = Dao.CustomerDaoImpl.getInstance();
    Appointment selApp;
   // AppDaoImpl appDao = Dao.AppDaoImpl.getInstance();

    /**
     * Important method to get everything set up
     */
    @FXML
    private void initialize(){
    setCustomerTableView(); // Sets up customers table
    setAppTable(); // Sets up appointments table
    setCountriesDrop(); // Sets up countries/location drop down (combo boxes)
    setContactF(); // Sets up contact drop down/combo box.
    initSpinners();


    customerDao.getNeededCountries();

        /**
         * Creates event handler that enables buttons only when a customer is selected
         */
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

        /**
         * Creates event handler that enables buttons only when a appointment is selected in the table
         */
        appTable.setOnMouseClicked((MouseEvent event) -> {
            selApp = appTable.getSelectionModel().getSelectedItem();
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if (selApp != null) {
                    // modify and delete buttons are only enabled if an item is selected
                    appDeleteBtn.setDisable(false);
                    appUpdateBtn.setDisable(false);

                }

            }
        });


    }

    public void deleteCustomer(){
        Customer selectedCust = customerTableView.getSelectionModel().getSelectedItem();
        customerDao.deleteCustomer(selectedCust);
        setCustomerTableView();
        sample.AppMethodsSingleton.generateAlert(Alert.AlertType.INFORMATION, "Customer " + selectedCust.getcName() + " deleted successfully!" );
    }

    public void openAddCust() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddNewCustomer.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding(event -> setCustomerTableView()); // reset table after adding

    }


    public void setCustomerTableView(){
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("cId"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("cName"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("cPhone"));
        custAddrCol.setCellValueFactory(new PropertyValueFactory<>("cAddr"));
        custZipCol.setCellValueFactory(new PropertyValueFactory<>("cZip"));
        custDivCol.setCellValueFactory(new PropertyValueFactory<>("cDivId"));
        customerTableView.setItems(customerDao.getAllCustomers());
    }

    /**
     * setCountriesDrop()
     * Used on init
     * Gets and sets the country objects to the drop down fields(combo box)
     * on both customer tab and appointment tab
     */
    public void setCountriesDrop(){
        custCountryDrop.getItems().setAll(customerDao.getNeededCountries());
        appLocF.setItems(customerDao.getNeededCountries());
    }

   // public Country selCountry;
    public void onCountrySelected(){
           Country selCountry = custCountryDrop.getSelectionModel().getSelectedItem();
            custDivDrop.setDisable(false);
            custDivDrop.setItems(Dao.CustomerDaoImpl.getInstance().getSelCountryDivs(selCountry));

    }

    @FXML
    public void fillUpdateForm() throws SQLException {

        if(customerTableView.getSelectionModel().getSelectedItem() == null){
            return;
        }

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

    public static AppDaoImpl appDao = Dao.AppDaoImpl.getInstance();

    /**
     * Appointment Table Elements
     */
    @FXML private TableView<Appointment> appTable;
    @FXML private TableColumn<Appointment, Integer> appIdC;
    @FXML private TableColumn<Appointment, String> appTitleC;
    @FXML private TableColumn<Appointment, String> appDescC;
    @FXML private TableColumn<Appointment, String> appLocC;
    @FXML private TableColumn<Appointment, String> appContactC;
    @FXML private TableColumn<Appointment, String> appTypeC;
    @FXML private TableColumn<Appointment, String> appDateC;
    @FXML private TableColumn<Appointment, String> appStartC;
    @FXML private TableColumn<Appointment, String> appEndC;
    @FXML private TableColumn<Appointment, Integer> appCustIdC;

    /**
     * Appointment Form Elements
     */
    @FXML private TextField appIdF;
    @FXML private TextField appTitleF;
    @FXML private TextField appTypeF;
    @FXML private TextField appDescF;
    @FXML private ComboBox<Country> appLocF;
    @FXML private ComboBox<Contact> appContactF;
    @FXML private DatePicker appDateF;
    @FXML private Spinner<String> appStartHF;
    @FXML private Spinner<String> appStartMF;
    @FXML private Spinner<String> appStart12F;
    @FXML private Spinner<String> appEndHF;
    @FXML private Spinner<String> appEndMF;
    @FXML private Spinner<String> appEnd12F;
    @FXML private TextField appCustIdF;
    @FXML private TextField appUserIdF;
    @FXML private Button appUpdateBtn;

    /**
     * Filter control Elements
     */
    @FXML private RadioButton appMonthR;
    @FXML private RadioButton appWeekR;
    @FXML private ComboBox<String> appFilterDrop;

    /**
     * Other Controls
     */
    @FXML private Button appDeleteBtn;
    @FXML private Button appAddBtn;


    /**
     * setAppTable()
     * Fills appointment table view with Appointment objects from the db.
     */
    @FXML
    public void setAppTable(){
        appIdC.setCellValueFactory(new PropertyValueFactory<>("appId"));
        appTitleC.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appDescC.setCellValueFactory(new PropertyValueFactory<>("appDesc"));
        appLocC.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        appTypeC.setCellValueFactory(new PropertyValueFactory<>("appType"));
        appDateC.setCellValueFactory(new PropertyValueFactory<>("appDate"));
        appStartC.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        appEndC.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        appCustIdC.setCellValueFactory(new PropertyValueFactory<>("appCustId"));
        appContactC.setCellValueFactory(new PropertyValueFactory<>("appContactId"));
        appTable.setItems(appDao.getAllApps());
    }

    @FXML
    public void setContactF(){
        appContactF.setItems(models.Contact.getAllContacts());
    }


    /**
     * openAddAppView()
     * Used by the appointment Tab
     * Opens the view to add an appointment through a pop up form
     * @throws Exception
     */
    public void openAddAppView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddAppView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding(event -> setAppTable()); // reset table after adding

    }

    public void deleteApp(){
            selApp = appTable.getSelectionModel().getSelectedItem();
            appDao.deleteApp(selApp);
            setAppTable();
            sample.AppMethodsSingleton.generateAlert(Alert.AlertType.INFORMATION,  "Appointment type: " + selApp.getAppType() + ", ID #" + selApp.getAppId()  +  " canceled successfully!" );
    }

    @FXML
    public void fillAppUpdateForm() throws SQLException {

        try {
            if(appTable.getSelectionModel().getSelectedItem() == null){
                return;
            }

            selApp = appTable.getSelectionModel().getSelectedItem();
            System.out.println(selApp.getAppTitle());
            appIdF.setText(Integer.toString(selApp.getAppId()));
            appTitleF.setText(selApp.getAppTitle());
            appTypeF.setText(selApp.getAppType());
            appDescF.setText(selApp.getAppDesc());
            appLocF.setValue(getCountry(selApp.getAppLocation()));
            appContactF.setValue(getContact(selApp.getAppContactId()));
            appDateF.setValue(LocalDate.parse(selApp.getAppDate(), DateTimeFormatter.ofPattern("MM-dd-yyyy")));
            appStartHF.getValueFactory().setValue((selApp.getAppStart().substring(0, 2)));
            appStartMF.getValueFactory().setValue((selApp.getAppStart().substring(3, 5)));
            appStart12F.getValueFactory().setValue(selApp.getAppStart().substring(6, 8));
            appEndHF.getValueFactory().setValue((selApp.getAppEnd().substring(0, 2)));
            appEndMF.getValueFactory().setValue((selApp.getAppEnd().substring(3, 5)));
            appEnd12F.getValueFactory().setValue(selApp.getAppEnd().substring(6, 8));
            appCustIdF.setText(Integer.toString(selApp.getAppCustId()));
            appUserIdF.setText(Integer.toString(selApp.getAppUserId()));
        } catch( NullPointerException e){
            e.printStackTrace();
        }


        // custCountryDrop.setValue();
        // custDivDrop.setValue(selCustomer.getcDivId());
    }

    /**
     * Country getCountry()
     * Returns the country object with the specified string title.
     * @param countryStr
     * @return
     */
    public Country getCountry(String countryStr){
        for(Country item : customerDao.getNeededCountries()){
            if(item.getCountryName().contains(countryStr)){
                return item;
            }
        }
            return null;
    }

    /**
     * Contact getContact()
     * Returns the contact object using the contact Id param.
     * @param contactId
     * @return
     */
    public Contact getContact(int contactId){
        for(Contact item : Contact.getAllContacts()){
            if(item.getConID() == contactId){
                return item;
            }
        }
        return null;
    }

    /**
     * Initializes the spinners with the values needed to track time.
     */

    private void initSpinners() {
        ObservableList<String> str = FXCollections.observableArrayList();
        str.add("AM");
        str.add("PM");

        ObservableList<String> hours = FXCollections.observableArrayList();
        hours.add("01");
        hours.add("02"); hours.add("03"); hours.add("04"); hours.add("05"); hours.add("06");
        hours.add("07"); hours.add("08"); hours.add("09"); hours.add("10"); hours.add("11");
        hours.add("12");

        ObservableList<String> minutes = FXCollections.observableArrayList();

        for (int i = 0; i <= 60; i++) {
            if (i < 10) {
                minutes.add("0" + Integer.toString(i));
            } else {
                minutes.add(Integer.toString(i));
            }
        }



        appStartHF.setValueFactory(
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(hours)
        );
        appStartMF.setValueFactory(
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(minutes)
        );
        appStart12F.setValueFactory(
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(str)
        );

        appEndHF.setValueFactory(
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(hours)
        );
        appEndMF.setValueFactory(
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(minutes)
        );
        appEnd12F.setValueFactory(
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(str)
        );

    }



}
