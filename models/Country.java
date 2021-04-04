package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Country {

    private SimpleIntegerProperty countryId;
    private SimpleStringProperty countryName;

    Connection connection = null;
    PreparedStatement prepStatment = null;
    ResultSet resultSet = null;

    public Country(int countryId, String countryName) {
        this.countryId = new SimpleIntegerProperty(countryId);
        this.countryName = new SimpleStringProperty(countryName);
    }

    // This converts the objects names to string names in the combobox
    @Override
    public String toString() {
        return  this.getCountryName();
    }

    public int getCountryId() {
        return countryId.get();
    }

    public SimpleIntegerProperty countryIdProperty() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId.set(countryId);
    }

    public String getCountryName() {
        return countryName.get();
    }

    public SimpleStringProperty countryNameProperty() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName.set(countryName);
    }
}
