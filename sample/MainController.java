package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class MainController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML private Label regionNameLabel;

    /**
     * initialize()
     * sets the UI to it's default state.
     */
    @FXML public void initialize(){
    setRegionNameLabel();


    }

    // this sets the region name from the Locale Class
    private String regionName = sample.LocaleInfo.getDefaultLocale();


    /**
     * setRegionNameLabel()
     * This sets the region name to the label.
     */
    public void setRegionNameLabel(){
        regionNameLabel.setText(regionName);
    }



}
