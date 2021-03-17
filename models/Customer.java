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


    public Customer(      SimpleStringProperty cName,
                          SimpleStringProperty cAddr,
                          SimpleStringProperty cZip,
                          SimpleStringProperty cPhone,
                          SimpleIntegerProperty cDivId) {
        this.cName = cName;
        this.cAddr = cAddr;
        this.cZip = cZip;
        this.cPhone = cPhone;
        this.cDivId = cDivId;
    }


    public Customer( SimpleIntegerProperty cId,
                    SimpleStringProperty cName,
                    SimpleStringProperty cAddr,
                    SimpleStringProperty cZip,
                    SimpleStringProperty cPhone,
                    SimpleIntegerProperty cDivId) {
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
//    public Customer(/*SimpleIntegerProperty cId,*/ SimpleStringProperty cName,
//                    SimpleStringProperty cAddr, SimpleStringProperty cZip,
//                    SimpleStringProperty cPhone, SimpleIntegerProperty cDivId) {
////        this.cId = cId;
//        this.cName = cName;
//        this.cAddr = cAddr;
//        this.cZip = cZip;
//        this.cPhone = cPhone;
//        this.cDivId = cDivId;
//    }

    /** Previous constructor
     *
     *
     * Prev Props:**
     *     private String cName;
     *     private String cAddr;
     *     private String cZip;
     *     private String cPhone;
     *     private Integer cDivId;
     *     ********
        public Customer(String cName,
                    String cAddr, String cZip,
                    String cPhone, Integer cDivId) {
//        this.cId = cId;
        this.cName = cName;
        this.cAddr = cAddr;
        this.cZip = cZip;
        this.cPhone = cPhone;
        this.cDivId = cDivId;
    }
    */

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

    /****
     * these were the previous string methods
     * @return


    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcAddr() {
        return cAddr;
    }

    public void setcAddr(String cAddr) {
        this.cAddr = cAddr;
    }

    public String getcZip() {
        return cZip;
    }

    public void setcZip(String cZip) {
        this.cZip = cZip;
    }

    public String getcPhone() {
        return cPhone;
    }

    public void setcPhone(String cPhone) {
        this.cPhone = cPhone;
    }

    public Integer getcDivId() {
        return cDivId;
    }

    public void setcDivId(Integer cDivId) {
        this.cDivId = cDivId;
    }
*/

    /**
     * Simple properties getters and setters.
     */

    /*
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

     */


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