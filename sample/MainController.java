package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;


public class MainController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label titleLabel;
    @FXML private Label locationTagLabel;
    @FXML private Label locationNameLabel;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Button loginButton;

    /**
     * initialize()
     * sets the UI to it's default state.
     */
    @FXML
    public void initialize(){
        translateText();

    }

    /**
     * String locationName:
     *  Sets the location name from the Locale Class.
      */

    private String locationName = sample.LocaleInfo.getDefaultLocale();

    /**
     * ResourceBundle langBundle:
     * Sets the specific resourceBundle based off end-user's system's region settings.
     */
    private ResourceBundle langBundle = LocaleInfo.getResourceBundle();

    /**
     * void translateText()
     * Sets all the text in the view to the specified language.
     */
    public void translateText(){
        titleLabel.setText(langBundle.getString("Title"));
        locationTagLabel.setText(langBundle.getString("LocationTag"));
        locationNameLabel.setText(langBundle.getString("LocationName"));
        usernameLabel.setText(langBundle.getString("Username"));
        passwordLabel.setText(langBundle.getString("Password"));
        loginButton.setText(langBundle.getString("Login"));

    }



}
