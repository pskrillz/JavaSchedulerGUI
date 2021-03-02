package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Locale;


public class MainController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML private Label regionName;

    @FXML public void initialize(){
    getCurrentLangZone();
    }


    private Locale renderLanguage;


    public void getCurrentLangZone(){


       var locA = new java.util.Locale("en-us");
       var locB = new java.util.Locale("fr-ca");

        if (Locale.getDefault() == locA)
            renderLanguage = locA;

        if(Locale.getDefault() == locB){
            renderLanguage = locB;
        }

       var language = locB.getDisplayLanguage();
       regionName.setText(language);

    }


}
