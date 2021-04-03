package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.time.*;
import java.time.format.DateTimeFormatter;

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
     //   System.out.println(easternOffset);
        easternOffsetString = String.valueOf(easternOffset);
        return easternOffsetString;

    }

    public static String getLocalToEasternOffset(){
        int OffsetConvert = Integer.parseInt(String.valueOf(AppMethodsSingleton.getLocalTimezoneOffset().charAt(2))) - Integer.parseInt(String.valueOf(AppMethodsSingleton.getEasternOffset().charAt(2)));
        String offsetString = "-0" + OffsetConvert + ":00";
        return offsetString;
    }

    /**
     * businessHoursChecker()
     * Takes a time standardized to local UTC and return true
     * if it its within business hours and not on weekend or else
     * provide alerts.
     * @param zonedDateTime
     * @return boolean
     */
    public static boolean businessHoursChecker(ZonedDateTime zonedDateTime){
        /**
         * Normalizes the users datetime to a localtime in UTC to check if within business hours.
         */
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime utcZoned = zonedDateTime.withZoneSameInstant(utcZone);
        String timeString = utcZoned.format(DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime timeNorm = LocalTime.parse(timeString);
        System.out.println("UTC time is " + timeNorm);

        LocalTime startBizHours = LocalTime.of(4, 00);
        LocalTime endBizHours = LocalTime.of(18, 00);

        if (zonedDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || zonedDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            AppMethodsSingleton.generateAlert(Alert.AlertType.ERROR, "Appointment cannot be on weekends!");
            return false;
        } else if(timeNorm.isAfter(startBizHours) && timeNorm.isBefore(endBizHours)){
                return true;
            } else if(timeNorm.isAfter(endBizHours)){
            AppMethodsSingleton.generateAlert(Alert.AlertType.ERROR, "Appointment is after business hours!");
                return false;
            } else if (timeNorm.isBefore(startBizHours)){
            AppMethodsSingleton.generateAlert(Alert.AlertType.ERROR, "Appointment is before business hours!");
                return false;
            }


        return false;
    }



// Not needed when only really need db for one more object class.
//    private Connection getConnection() throws SQLException {
//        Connection con = DbConnectionFactory.getInstance().getConnection();
//        return con;
//    }



}
