package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private SimpleIntegerProperty cId;
    private SimpleStringProperty cName;
    private SimpleStringProperty cAddr;
    private SimpleStringProperty cZip;
    private SimpleStringProperty cPhone;
    private SimpleIntegerProperty cDivId;


    public Customer(SimpleIntegerProperty cId, SimpleStringProperty cName,
                    SimpleStringProperty cAddr, SimpleStringProperty cZip,
                    SimpleStringProperty cPhone, SimpleIntegerProperty cDivId) {
        this.cId = cId;
        this.cName = cName;
        this.cAddr = cAddr;
        this.cZip = cZip;
        this.cPhone = cPhone;
        this.cDivId = cDivId;
    }

    public int getcId() {
        return cId.get();
    }

    public SimpleIntegerProperty cIdProperty() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId.set(cId);
    }

    public String getcName() {
        return cName.get();
    }

    public SimpleStringProperty cNameProperty() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName.set(cName);
    }

    public String getcAddr() {
        return cAddr.get();
    }

    public SimpleStringProperty cAddrProperty() {
        return cAddr;
    }

    public void setcAddr(String cAddr) {
        this.cAddr.set(cAddr);
    }

    public String getcZip() {
        return cZip.get();
    }

    public SimpleStringProperty cZipProperty() {
        return cZip;
    }

    public void setcZip(String cZip) {
        this.cZip.set(cZip);
    }

    public String getcPhone() {
        return cPhone.get();
    }

    public SimpleStringProperty cPhoneProperty() {
        return cPhone;
    }

    public void setcPhone(String cPhone) {
        this.cPhone.set(cPhone);
    }

    public int getcDivId() {
        return cDivId.get();
    }

    public SimpleIntegerProperty cDivIdProperty() {
        return cDivId;
    }

    public void setcDivId(int cDivId) {
        this.cDivId.set(cDivId);
    }
}


/*
Customer_ID int(10) AI PK
Customer_Name varchar(50)
Address varchar(100)
Postal_Code varchar(50)
Phone varchar(50)
Create_Date datetime
Created_By varchar(50)
Last_Update timestamp
Last_Updated_By varchar(50)
Division_ID int(10)
 */