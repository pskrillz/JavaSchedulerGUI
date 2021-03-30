package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Contact;
import models.Country;


public class AddAppContr {

    @FXML TextField appIdF;
    @FXML TextField appTitleF;
    @FXML TextField appTypeF;
    @FXML TextField appDescF;
    @FXML ComboBox<Country> appLocCB;
    @FXML ComboBox<Contact> appContactCB;


    @FXML
    private void initialize(){
        setLocationDrop();
        setContactDrop();
    }

    public void setLocationDrop(){
        appLocCB.setItems(Dao.CustomerDaoImpl.getInstance().getNeededCountries());
    }

    public void setContactDrop(){
        appContactCB.setItems(Contact.getAllContacts());
    }







}
