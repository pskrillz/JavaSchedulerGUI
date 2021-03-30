package sample;

import Dao.AppDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Appointment;
import models.Contact;
import models.Country;


public class AddAppContr {

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



    @FXML
    private void initialize(){
        setLocationDrop();
        setContactDrop();
        initSpinners();
    }

    public void setLocationDrop(){
        appLocCB.setItems(Dao.CustomerDaoImpl.getInstance().getNeededCountries());
    }

    public void setContactDrop(){
        appContactCB.setItems(Contact.getAllContacts());
    }

    private void initSpinners() {
        ObservableList<String> str = FXCollections.observableArrayList();
        str.add("AM");
        str.add("PM");

        appStartHF.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12)
        );
        appStartMF.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60)
        );
        appStart12F.setValueFactory(
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(str)
        );

        appEndHF.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12)
        );
        appEndMF.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60)
        );
        appEnd12F.setValueFactory(
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(str)
        );

    }

    public void addApp(){
        String title = appTitleF.getText();
        String type = appTypeF.getText();
        String desc = appDescF.getText();
        String location = appLocCB.getSelectionModel().getSelectedItem().getCountryName();
        System.out.println(location);
        int contactId = appContactCB.getSelectionModel().getSelectedItem().getConID();
        System.out.println(contactId);

        // for date
        String appDate = appDateF.getValue().toString();
        System.out.println(appDate);

        String startHours = appStartHF.getValue().toString();
        if(appStart12F.getValue().toString() == "PM"){
            startHours = Integer.toString(Integer.parseInt(startHours) + 12);
        }
        if (Integer.parseInt(startHours)  < 10) {
            startHours = "0" + startHours;
        }


        String startMinutes = appStartMF.getValue().toString();
        if (Integer.parseInt(startMinutes)  < 10) {
            startMinutes = "0" + startMinutes;
        }

        String appStartDateTimeString = appDate + " " + startHours + ":" + startMinutes + ":00";
        System.out.println(appStartDateTimeString);

        String endHours = appEndHF.getValue().toString();
        if(appEnd12F.getValue().toString() == "PM"){
            endHours = Integer.toString(Integer.parseInt(endHours) + 12);
        }
        if (Integer.parseInt(endHours)  < 10) {
            endHours = "0" + endHours;
        }


        String endMinutes = appEndMF.getValue().toString();
        if (Integer.parseInt(endMinutes)  < 10) {
            endMinutes = "0" + endMinutes;
        }

        String appEndDateTimeString = appDate + " " + endHours + ":" + endMinutes + ":00";
        System.out.println(appEndDateTimeString);

        int appCustId = Integer.parseInt(appCustIdF.getText());
        int appUserId = Integer.parseInt(appCustIdF.getText());



       Appointment newApp = new Appointment( title, desc, location, type, appStartDateTimeString, appEndDateTimeString,
               appCustId, contactId, appUserId);
       AppDaoImpl.getInstance().addApp(newApp);

        //close window
        sample.AppMethodsSingleton.closeWindow(appAddBtn);
    }








}
