package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * JavaFX initialization;
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        primaryStage.setTitle("uSchedule");
        primaryStage.setScene(new Scene(root, 300, 370));
        primaryStage.show();
    }


    public static void main(String[] args) {
        /**
         * Run a test query to make sure the DbConnectionFactory is working.
         */
        DbConnectionFactory.fireTestQuery();
        launch(args);

    }
}


