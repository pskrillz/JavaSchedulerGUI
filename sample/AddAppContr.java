package sample;

import Dao.AppDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Appointment;
import models.Contact;
import models.Country;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class AddAppContr {

    /**
     * FXML elements of the add app form;
     */
    @FXML TextField appIdF;
    @FXML TextField appTitleF;
    @FXML TextField appTypeF;
    @FXML TextField appDescF;
    @FXML ComboBox<Country> appLocCB;
    @FXML ComboBox<Contact> appContactCB;
    @FXML DatePicker appDateF;
    @FXML private Spinner appStartHF;
    @FXML private Spinner appStartMF;
    @FXML private Spinner appStart12F;
    @FXML private Spinner appEndHF;
    @FXML private Spinner appEndMF;
    @FXML private Spinner appEnd12F;
    @FXML TextField appCustIdF;
    @FXML TextField appUserIdF;
    @FXML Button appAddBtn;


    /**
     * initialize()
     * Initializes the UI for the add app form;
     */
    @FXML
    private void initialize(){
        setLocationDrop();
        setContactDrop();
        initSpinners();
    }

    /**
     * setLocationDrop()
     * sets the location combo box value to the add app form;
     */
    public void setLocationDrop(){
        appLocCB.setItems(Dao.CustomerDaoImpl.getInstance().getNeededCountries());
    }

    /**
     * setContactDrop()
     * Sets the contact combo box values to the add app form
     */
    public void setContactDrop(){
        appContactCB.setItems(Contact.getAllContacts());
    }

    /**
     * initSpinners()
     * Sets the spinner values to the add app form
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

    /**
     * addApp()
     * Adds appointment to the DB.
     */
    public void addApp(){
        String title = appTitleF.getText();
        String type = appTypeF.getText();
        String desc = appDescF.getText();
        String location = appLocCB.getSelectionModel().getSelectedItem().getCountryName();
        int contactId = appContactCB.getSelectionModel().getSelectedItem().getConID();
        // for date
        String appDate = appDateF.getValue().toString();

        String startHours = appStartHF.getValue().toString();
        if(appStart12F.getValue().toString() == "PM"){
            startHours = Integer.toString(Integer.parseInt(startHours) + 12);
        }

        String startMinutes = appStartMF.getValue().toString();


        String appStartDateTimeString = appDate + " " + startHours + ":" + startMinutes + ":00";

        LocalDateTime appStartLocal = LocalDateTime.parse(appStartDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ZonedDateTime appstartLocalZoned = ZonedDateTime.of(appStartLocal, ZoneId.of(String.valueOf(ZoneId.systemDefault())));



        /**
         * Validation check to see if local appointment time is within business hours and not on weekends.
         */
        if (AppMethodsSingleton.businessHoursChecker(appstartLocalZoned) == false ){
            return;
        }


        String endHours = appEndHF.getValue().toString();
        if(appEnd12F.getValue().toString() == "PM"){
            endHours = Integer.toString(Integer.parseInt(endHours) + 12);
        }



        String endMinutes = appEndMF.getValue().toString();


        String appEndDateTimeString = appDate + " " + endHours + ":" + endMinutes + ":00";


        int appCustId = Integer.parseInt(appCustIdF.getText());
        int appUserId = Integer.parseInt(appCustIdF.getText());



       Appointment newApp = new Appointment( title, desc, location, type, appStartDateTimeString, appEndDateTimeString,
               appCustId, contactId, appUserId);
       AppDaoImpl.getInstance().addApp(newApp);



        //close window
        sample.AppMethodsSingleton.closeWindow(appAddBtn);
    }


//
//    public void getAllAppTimes(){
//        ObservableList<LocalTime> appStarts = FXCollections.observableArrayList();
//
//        for(Appointment app : Dao.AppDaoImpl.getInstance().getAllApps()){
//
//        }
//    }






}
