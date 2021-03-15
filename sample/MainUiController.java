package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Customer;

public class MainUiController {


    /**
     * All the FXML elements for the Customers Tab
     */


    @FXML private TableView<Customer> customerTableView;
    @FXML private TableColumn<Customer, Integer> custIdCol;
    @FXML private TableColumn<Customer, String> custNameCol;
    @FXML private TableColumn<Customer, String> custPhoneCol;
    @FXML private TableColumn<Customer, String> custAddrCol;
    @FXML private TableColumn<Customer, String> custCountryCol;
    @FXML private TableColumn<Customer, String> custZipCol;
    @FXML private TableColumn<Customer, String> custDivCol;
   // @FXML private TableColumn<Customer, (Date?)> custDateCol;


    @FXML private TextField custIdF;
    @FXML private TextField custNameF;
    @FXML private TextField custAddrF;
    @FXML private TextField custZipF;
    @FXML private TextField custPhoneF;
    @FXML private TextField custSearchF;
    @FXML private ComboBox custCountryDrop; // needs the objects in here though
    @FXML private ComboBox custDivDrop; // make with object
    @FXML private Button custSearchBtn;
    @FXML private Button custDeleteBtn;
    @FXML private Button custAddBtn;
    @FXML private Button custUpdateBtn;

    @FXML
    private void initialize(){

    }
    /*
    End Customers Tab
     */












    public void openAddCust() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddNewCustomer.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("uSchedule Main UI");
        stage.setScene(new Scene(root));
        stage.show();
    }
}