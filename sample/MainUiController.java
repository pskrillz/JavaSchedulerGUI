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

    // Customer Table
    @FXML private TableView<Customer> customerTableView;
    @FXML private TableColumn<Customer, Integer> custIdCol;
    @FXML private TableColumn<Customer, String> custNameCol;
    @FXML private TableColumn<Customer, String> custPhoneCol;
    @FXML private TableColumn<Customer, String> custAddrCol;
    @FXML private TableColumn<Customer, String> custZipCol;
    @FXML private TableColumn<Customer, String> custDivCol;

    // Customer Update Form.
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

    // Global variables for accessing the customer Data Access Object,
    // current selected customer and current selected appointment.
    Customer selCustomer;
    CustomerDaoImpl customerDao = Dao.CustomerDaoImpl.getInstance();
    Appointment selApp;

    /**
     * Important method to instantiate UI components.
     */
    @FXML
    private void initialize(){
    setCustomerTableView(); // Sets up customers table
    setAppTable(refreshTable()); // Sets up appointments table
    setCountriesDrop(); // Sets up countries/location drop down (combo boxes)
    setContactF(); // Sets up contact drop down/combo box.
    initSpinners(); // initializes spinner values

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

    /**
     * deleteCustomer()
     * Used by the customer tab's delete button.
     * Deletes the selected customer from the db.
     */
    public void deleteCustomer(){
        Customer selectedCust = customerTableView.getSelectionModel().getSelectedItem();
        customerDao.deleteCustomer(selectedCust);
        setCustomerTableView();
        sample.AppMethodsSingleton.generateAlert(Alert.AlertType.INFORMATION, "Customer " +
                selectedCust.getcName() + " deleted successfully!" );
    }

    /**
     * openAddCust()
     * Used by customer tab's add button.
     * Opens the add customer window.
     * @throws Exception
     */
    public void openAddCust() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddNewCustomer.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding(event -> setCustomerTableView()); // reset table after adding
    }

    /**
     * setCustomerTableView()
     * Used by initialize()
     * Populates the customer table with all customers in the database.
     */
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
     * Used by initialize()
     * Gets and sets the country objects to the drop down fields(combo box)
     * on both customer tab and appointment tab
     */
    public void setCountriesDrop(){
        custCountryDrop.getItems().setAll(customerDao.getNeededCountries());
        appLocF.setItems(customerDao.getNeededCountries());
    }

    /**
     * onCountrySelected()
     * Triggered by the location combo box when a value is selected
     * and then both enables and populates the division combo box
     */
    public void onCountrySelected(){
           Country selCountry = custCountryDrop.getSelectionModel().getSelectedItem();
            custDivDrop.setDisable(false);
            custDivDrop.setItems(Dao.CustomerDaoImpl.getInstance().getSelCountryDivs(selCountry));

    }

    /**
     * fillUpdateForm()
     * Triggered by the customer tab's customer table on mouse click to populate the form
     * with attributes of the selected customer on valid selection.
     * @throws SQLException
     */
    @FXML
    public void fillUpdateForm() throws SQLException {
        // Checks that there is a valid selection before running the rest.
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

    /**
     * updateCustomer()
     * Used by the update button on the customer tab.
     * Collects form data and uses it to update the specified customer on the db.
     */
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
    @FXML private Button appFilterBtn;

    /**
     * Other Controls
     */
    @FXML private Button appDeleteBtn;
    @FXML private Button appAddBtn;


    /**
     * setAppTable()
     * Fills appointment table view with Appointment objects from the db.
     * @param appList
     * Takes an ObservableList<Appointment> to set the table up with.
     */
    @FXML
    public void setAppTable(ObservableList<Appointment> appList){
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
        appTable.setItems(appList);
    }

    /**
     * refreshTable()
     * Gets the most current list of all apps from the database.
     * Used by the setAppTable() function to set the table.
     * @return
     */
    public ObservableList<Appointment> refreshTable(){
        ObservableList<Appointment> allApps = appDao.getAllApps();
        return allApps;
    }

    /**
     * setContactF()
     * Used by initialize()
     * Sets the contact combo-box values.
     */
    @FXML
    public void setContactF(){
        appContactF.setItems(models.Contact.getAllContacts());
    }


    /**
     * openAddAppView()
     * Used by the appointment Tab's add button.
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
        stage.setOnHiding(event -> setAppTable(refreshTable())); // reset table after adding
    }

    /**
     * void updateCustomer()
     * Used by the update button.
     * Captures the data in the form and
     * constructs an appointment object that the appointment DAO uses
     * to update the appointment on the mysql server.
     */
    public void updateApp(){
        // just id double check
        int id = Integer.parseInt(appIdF.getText());
        String title = appTitleF.getText();
        String desc = appDescF.getText();
        String loc = appLocF.getSelectionModel().getSelectedItem().getCountryName();
        String type = appTypeF.getText();
        String appDate = appDateF.getValue().toString();

// Get start time values from spinners
        String startHours = appStartHF.getValue();
        if(appStart12F.getValue().toString() == "PM"){
            startHours = Integer.toString(Integer.parseInt(startHours) + 12);
        }
        String startMinutes = appStartMF.getValue();
//   *     // this is the actual start parameter
        String appStartDateTimeString = appDate + " " + startHours + ":" + startMinutes + ":00";
// Gets end time values from spinners
        String endHours = appEndHF.getValue().toString();
        if(appEnd12F.getValue().toString() == "PM"){
            endHours = Integer.toString(Integer.parseInt(endHours) + 12);
        }
        String endMinutes = appEndMF.getValue().toString();
//   *      // the actual actual end parameter
        String appEndDateTimeString = appDate + " " + endHours + ":" + endMinutes + ":00";

        int custId = Integer.parseInt(appCustIdF.getText());
        int userId = Integer.parseInt(appUserIdF.getText());
        int contactId = appContactF.getSelectionModel().getSelectedItem().getConID();

        Appointment app = new Appointment(id, title, desc, loc, type, appStartDateTimeString, appEndDateTimeString,
                custId, contactId, userId);

        try {
            appDao.updateApp(app, id);
        } catch (RuntimeException e){
            e.printStackTrace();
        } finally{
            setAppTable(refreshTable());
        }
    }

    /**
     * deleteApp()
     * Used by the delete button.
     * Deletes the selected appointment and refreshes the table
     */
    public void deleteApp(){
            selApp = appTable.getSelectionModel().getSelectedItem();
            appDao.deleteApp(selApp);
            setAppTable(refreshTable());
            sample.AppMethodsSingleton.generateAlert(Alert.AlertType.INFORMATION,  "Appointment type: " + selApp.getAppType() + ", ID #" + selApp.getAppId()  +  " canceled successfully!" );
    }

    /**
     * fillAppUpdateForm()
     * Triggered on mouse click on the appointment table,
     * If a valid appointment is selected, it populates the form with specified appointment attributes.
     * @throws SQLException
     */
    @FXML
    public void fillAppUpdateForm() throws SQLException {
        try {
            // checks to see a valid item from the table is selected before running the rest
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
     * initSpinners()
     * Used by initialize()
     * Initializes the spinners with the values needed to track time.
     */

    private void initSpinners() {
        // Creates the AM PM list
        ObservableList<String> str = FXCollections.observableArrayList();
        str.add("AM");
        str.add("PM");

        // Creates the list of hours
        ObservableList<String> hours = FXCollections.observableArrayList();
        hours.add("01");
        hours.add("02"); hours.add("03"); hours.add("04"); hours.add("05"); hours.add("06");
        hours.add("07"); hours.add("08"); hours.add("09"); hours.add("10"); hours.add("11");
        hours.add("12");

        //Creates the minutes list in string type
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


    /**
     * createMonthsList()
     * Creates and returns an array list of Month names.
     * Used by fillMonths()
     * @return ObservableList<String>
     */
    public ObservableList<String> createMonthsList(){
        ObservableList<String> months = FXCollections.observableArrayList();
        months.add("January"); months.add("February"); months.add("March");
        months.add("April"); months.add("May"); months.add("June"); months.add("July");
        months.add("August"); months.add("September"); months.add("October"); months.add("November");
        months.add("December");
        return months;
    }

    /**
     * fillMonths()
     * Triggered by the months radio button selection to set the filter combo box
     * with the months list.
     */
    public void fillMonths(){
        ObservableList<String> months = createMonthsList();
        appFilterDrop.setItems(months);
    }


    /**
     * createWeeksList()
     * Used by fillWeeks() to return the weeks array list in the desired format.
     * @return ObservableList<String>
     */
    public ObservableList<String> createWeeksList(){
        DateTimeFormatter selFormat = DateTimeFormatter.ofPattern("MM-dd-yy");
        ObservableList<String> weeks = FXCollections.observableArrayList();
        LocalDate firstWeekStart = LocalDate.of(2021, 1, 4);
        LocalDate firstWeekEnd = LocalDate.of(2021, 1, 10);
        weeks.add(firstWeekStart.format(selFormat) + " - " +
                firstWeekEnd.format(selFormat));

        for(int i = 0; i < 52; i++){
            firstWeekStart = firstWeekStart.plusDays(7);
            firstWeekEnd = firstWeekEnd.plusDays(7);
            weeks.add(firstWeekStart.format(selFormat) + " - " + firstWeekEnd.format(selFormat));
        }
        return weeks;
    }


    /**
     * fillWeeks()
     * Used by the week radio button to fill the filter combobox on selection.
     */
    public void fillWeeks(){
        ObservableList<String> weeks = createWeeksList();
        appFilterDrop.setItems(weeks);
    }

    /**
     * void checkSelection()
     * Event triggered by a change in the combobox to make sure
     * an item is selected before enabling filter button.
     */
    public void checkSelection(){
        if(!(appMonthR.isSelected()) && !(appWeekR.isSelected()) ){
            System.out.println("Not valid selection");
            appFilterDrop.setDisable(true);
            return;
        }

        if(appFilterDrop.getSelectionModel().getSelectedItem() != null) {
            appFilterBtn.setDisable(false);
        }
    }

    /**
     * setFilteredApps()
     * Parses the string data in the appointment filter combo box
     * to update the appointment table with the matching appointments.
     */
    public void setFilteredApps(){
        // Checks for valid selection before running the rest of the function
        if(appFilterDrop.getSelectionModel().getSelectedItem() == null){
            System.out.println("not valid selection");
            return;
        }

        ObservableList<Appointment> filteredApps = FXCollections.observableArrayList();
        String selectedItem = appFilterDrop.getSelectionModel().getSelectedItem();

        // Filter by Month
        if(appFilterDrop.getItems().get(1) == createMonthsList().get(1)){
            for(Appointment app : appDao.getAllApps()){
                String checkMonth = app.getAppStartLocal().format(DateTimeFormatter.ofPattern("MMMM"));
                if(checkMonth.equals(selectedItem)){
                    filteredApps.add(app);
                }
            }
            if(filteredApps.isEmpty()){
                AppMethodsSingleton.generateAlert(Alert.AlertType.INFORMATION, "There are no appointments in that month");
            } else {
                setAppTable(filteredApps);
                AppMethodsSingleton.generateAlert(Alert.AlertType.INFORMATION, "Success! " + filteredApps.size() + " appointments found." );
                return;
            }

            // Filter by Week
        } else if(appFilterDrop.getItems()  != createMonthsList()){
            filteredApps = appDao.getAppsByWeek(selectedItem);
            if(filteredApps.isEmpty() != true){
                setAppTable(filteredApps);
                AppMethodsSingleton.generateAlert(Alert.AlertType.INFORMATION, "Success! " + filteredApps.size() + " appointments found." );
                return;
            } else {
                AppMethodsSingleton.generateAlert(Alert.AlertType.INFORMATION, "There are no appointments in that week");
                return;
            }
        }
        AppMethodsSingleton.generateAlert(Alert.AlertType.ERROR, "No matching appointments found!");
        return;
        }








}
