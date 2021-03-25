package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private SimpleIntegerProperty appId;
    private SimpleStringProperty appTitle;
    private SimpleStringProperty appDesc;
    private SimpleStringProperty appLocation;
    private SimpleStringProperty appType;
    private ZonedDateTime appStart;
    private ZonedDateTime appEnd;
    private SimpleIntegerProperty appCustId;
    private SimpleIntegerProperty appContactId;


    public Appointment(int appId,
                       String appTitle,
                       String appDesc,
                       String appLocation,
                       String appType,
                       String appStart,
                       String appEnd,
                       int appCustId,
                       int appContactId) {
        this.appId = new SimpleIntegerProperty(appId);
        this.appTitle = new SimpleStringProperty(appTitle) ;
        this.appDesc = new SimpleStringProperty(appDesc) ;
        this.appLocation = new SimpleStringProperty(appLocation) ;
        this.appType = new SimpleStringProperty(appType);
        this.appStart = ZonedDateTime.of(LocalDateTime.parse(appStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ZoneId.of("UTC+0"));
        this.appEnd = ZonedDateTime.of(LocalDateTime.parse(appEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ZoneId.of("UTC+0"));
        this.appCustId = new SimpleIntegerProperty(appCustId) ;
        this.appContactId = new SimpleIntegerProperty(appContactId) ;
    }

    // Overloaded without ID for when creating locally
    public Appointment(
                       String appTitle,
                       String appDesc,
                       String appLocation,
                       String appType,
                       String appStart,
                       String appEnd,
                       int appCustId,
                       int appContactId) {
        this.appTitle = new SimpleStringProperty(appTitle) ;
        this.appDesc = new SimpleStringProperty(appDesc) ;
        this.appLocation = new SimpleStringProperty(appLocation) ;
        this.appType = new SimpleStringProperty(appType);
        this.appStart = ZonedDateTime.of(LocalDateTime.parse(appStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ZoneId.of("UTC+0"));
        this.appEnd = ZonedDateTime.of(LocalDateTime.parse(appEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ZoneId.of("UTC+0"));
        this.appCustId = new SimpleIntegerProperty(appCustId) ;
        this.appContactId = new SimpleIntegerProperty(appContactId) ;
    }


    public int getAppId() {
        return appId.get();
    }

    public SimpleIntegerProperty appIdProperty() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId.set(appId);
    }

    public String getAppTitle() {
        return appTitle.get();
    }

    public SimpleStringProperty appTitleProperty() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle.set(appTitle);
    }

    public String getAppDesc() {
        return appDesc.get();
    }

    public SimpleStringProperty appDescProperty() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc.set(appDesc);
    }

    public String getAppLocation() {
        return appLocation.get();
    }

    public SimpleStringProperty appLocationProperty() {
        return appLocation;
    }

    public void setAppLocation(String appLocation) {
        this.appLocation.set(appLocation);
    }

    public ZonedDateTime getAppStart() {
        return appStart;
    }

    public void setAppStart(ZonedDateTime appStart) {
        this.appStart = appStart;
    }

    public ZonedDateTime getAppEnd() {
        return appEnd;
    }

    public void setAppEnd(ZonedDateTime appEnd) {
        this.appEnd = appEnd;
    }

    public int getAppCustId() {
        return appCustId.get();
    }

    public SimpleIntegerProperty appCustIdProperty() {
        return appCustId;
    }

    public void setAppCustId(int appCustId) {
        this.appCustId.set(appCustId);
    }

    public int getAppContactId() {
        return appContactId.get();
    }

    public SimpleIntegerProperty appContactIdProperty() {
        return appContactId;
    }

    public void setAppContactId(int appContactId) {
        this.appContactId.set(appContactId);
    }

    public String getAppType() {
        return appType.get();
    }

    public SimpleStringProperty appTypeProperty() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType.set(appType);
    }
}
