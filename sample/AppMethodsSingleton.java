package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AppMethodsSingleton {

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

//    public static void openWidow(String fxmlFile, String title) throws Exception{
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainUi.fxml"));
//        Parent root = (Parent) fxmlLoader.load();
//        Stage stage = new Stage();
//        stage.setTitle("uSchedule Main UI");
//        stage.setScene(new Scene(root));
//        stage.show();
  //  }


}
