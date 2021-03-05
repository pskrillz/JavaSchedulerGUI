module JavaSchedulerGUI {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires mysql.connector.java;

    opens sample;
    opens lib;
}