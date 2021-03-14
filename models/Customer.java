package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Customer implements Serializable {
    private SimpleIntegerProperty cId;
    private SimpleStringProperty cName;
    private SimpleStringProperty cAddr;
    private SimpleStringProperty cZip;
    private SimpleStringProperty cPhone;
    private SimpleIntegerProperty cDivId;


    public Customer(/*SimpleIntegerProperty cId,*/ SimpleStringProperty cName,
                    SimpleStringProperty cAddr, SimpleStringProperty cZip,
                    SimpleStringProperty cPhone, SimpleIntegerProperty cDivId) {
//        this.cId = cId;
        this.cName = cName;
        this.cAddr = cAddr;
        this.cZip = cZip;
        this.cPhone = cPhone;
        this.cDivId = cDivId;
    }

//    public int getId() {
//        return cId.get();
//    }
//
//    public SimpleIntegerProperty cIdProperty() {
//        return cId;
//    }
//
//    public void setId(int cId) {
//        this.cId.set(cId);
//    }

    public String getName() {
        return cName.get();
    }

    public SimpleStringProperty getNameProperty() {
        return cName;
    }

    public void setName(String cName) {
        this.cName.set(cName);
    }

    public String getAddr() {
        return cAddr.get();
    }

    public SimpleStringProperty getAddrProperty() {
        return cAddr;
    }

    public void setAddr(String cAddr) {
        this.cAddr.set(cAddr);
    }

    public String getZip() {
        return cZip.get();
    }

    public SimpleStringProperty getZipProperty() {
        return cZip;
    }

    public void setZip(String cZip) {
        this.cZip.set(cZip);
    }

    public String getPhone() {
        return cPhone.get();
    }

    public SimpleStringProperty getPhoneProperty() {
        return cPhone;
    }

    public void setPhone(String cPhone) {
        this.cPhone.set(cPhone);
    }

    public int getDivId() {
        return cDivId.get();
    }

    public SimpleIntegerProperty getDivIdProperty() {
        return cDivId;
    }

    public void setDivId(int cDivId) {
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