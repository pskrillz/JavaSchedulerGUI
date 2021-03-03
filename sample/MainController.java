package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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


    /**
     * void checkLogin()
     * Checks for test username and password
     * and generate appropriate error messages.
     */
    public void checkLogin(){
        String un = usernameField.getText();
        String pw = passwordField.getText();
        String correct = "test";

       if ( !un.equals(correct) && !pw.equals(correct)) {
           System.out.println(un + pw);
           generateError(langBundle.getString("ErrorBoth"));
           return;
        }else if (!un.equals(correct)){
            generateError(langBundle.getString("ErrorUn"));
            return;
        } else if (!pw.equals(correct)){
           generateError(langBundle.getString("ErrorPw"));
           return;
        } else if (pw.equals(correct) && un.equals(correct)){
                closeWindow();
                return;
       } else {
           generateError(langBundle.getString("ErrorUnknown"));
           return;
       }

    }

    /**
     * void closeWindow()
     * Closes LoginView window.
     */
    public void closeWindow(){
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }

    /**
     * void generateError()
     * @param message
     * Generates an error message with a custom message.
     */
    public void generateError(String message){
        Alert err = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        err.show();
    }

}
