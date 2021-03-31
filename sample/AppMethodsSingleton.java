package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class AppMethodsSingleton {

    public static String zoneOffsetString;
    public static String easternOffsetString;

    /**
     * void closeWindow()
     * Closes LoginView window.
     * @param anyButton
     * Takes any button from the scene
     */
   public static void closeWindow(Button anyButton){
        Stage stage = (Stage) anyButton.getScene().getWindow();
        stage.close();
    }

    /**
     * void generateAlert()
     * Generates an alert with parameters for alert type and message
     * @param alertType
     * @param message
     */
    public static void generateAlert(Alert.AlertType alertType, String message){
        Alert err = new Alert(alertType, message, ButtonType.OK);
        err.show();
    }

    public static Alert generateAlertObject(Alert.AlertType alertType, String message){
        return new Alert(alertType, message, ButtonType.OK);
    }

    /**
     * getLocalTimezoneOffset()
     * Returns the string offset for use in sql queries to normalize it's display
     * for the local user.
     * @return
     */
    public static String getLocalTimezoneOffset(){

        LocalDateTime now = LocalDateTime.now();
        ZoneId zone = ZoneId.of(String.valueOf(ZoneId.systemDefault()));
        ZoneOffset zoneOffset = zone.getRules().getOffset(now);
      //  System.out.println(zoneOffset);
        zoneOffsetString = String.valueOf(zoneOffset);
        return zoneOffsetString;

    }

    public static String getEasternOffset(){
        LocalDateTime now = LocalDateTime.now();
        ZoneId zone = ZoneId.of("America/New_York");
        ZoneOffset easternOffset = zone.getRules().getOffset(now);
        System.out.println(easternOffset);
        easternOffsetString = String.valueOf(easternOffset);
        return easternOffsetString;

    }

// Not needed when only really need db for one more object class.
//    private Connection getConnection() throws SQLException {
//        Connection con = DbConnectionFactory.getInstance().getConnection();
//        return con;
//    }



}
