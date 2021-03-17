package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Division {
    private SimpleIntegerProperty divisionId;
    private SimpleStringProperty divisionName;
    private SimpleIntegerProperty countryId;

    public Division(SimpleIntegerProperty divisionId, SimpleStringProperty divisionName, SimpleIntegerProperty countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    // This converts the objects names to string names in the combobox
    @Override
    public String toString() {
        return  this.getDivisionName();
    }

    public int getDivisionId() {
        return divisionId.get();
    }

    public SimpleIntegerProperty divisionIdProperty() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId.set(divisionId);
    }

    public String getDivisionName() {
        return divisionName.get();
    }

    public SimpleStringProperty divisionNameProperty() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName.set(divisionName);
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
}
