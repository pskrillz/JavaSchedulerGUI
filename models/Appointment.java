package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private SimpleIntegerProperty appId;
    private SimpleStringProperty appTitle;
    private SimpleStringProperty appDesc;
    private SimpleStringProperty appLocation;
    private SimpleStringProperty appType;
    private LocalDateTime appStartLocal;
    private LocalDateTime appEndLocal;
    private SimpleStringProperty appDate;
    private SimpleStringProperty appStart;
    private SimpleStringProperty appEnd;
    private SimpleIntegerProperty appCustId;
    private SimpleIntegerProperty appContactId;
    private SimpleIntegerProperty appUserId;


    /**
     * Appointment Constructor
     * First constructor includes appointment ID and
     * is used for creating appointment objects from query results.
     *
     * @param appId
     * @param appTitle
     * @param appDesc
     * @param appLocation
     * @param appType
     * @param appStart
     * @param appEnd
     * @param appCustId
     * @param appContactId
     * @param appUserId
     */
    public Appointment(int appId,
                       String appTitle,
                       String appDesc,
                       String appLocation,
                       String appType,
                       String appStart,
                       String appEnd,
                       int appCustId,
                       int appContactId,
                   int appUserId
                        ) {
        this.appId = new SimpleIntegerProperty(appId);
        this.appTitle = new SimpleStringProperty(appTitle) ;
        this.appDesc = new SimpleStringProperty(appDesc) ;
        this.appLocation = new SimpleStringProperty(appLocation) ;
        this.appType = new SimpleStringProperty(appType);
        this.appStartLocal = LocalDateTime.parse(appStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.appEndLocal = LocalDateTime.parse(appEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.appDate = new SimpleStringProperty(appStartLocal.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        this.appStart = new SimpleStringProperty(appStartLocal.format(DateTimeFormatter.ofPattern("hh:mm a")));
        this.appEnd = new SimpleStringProperty(appEndLocal.format(DateTimeFormatter.ofPattern("hh:mm a")));
        this.appCustId = new SimpleIntegerProperty(appCustId) ;
        this.appContactId = new SimpleIntegerProperty(appContactId) ;
        this.appUserId = new SimpleIntegerProperty(appUserId);
    }

    /**
     * Second Appointment Constructor
     * Is used to create appointment objects before using it to
     * insert into the db where the appointment ID is automatically
     * generated.
     * @param appTitle
     * @param appDesc
     * @param appLocation
     * @param appType
     * @param appStart
     * @param appEnd
     * @param appCustId
     * @param appContactId
     * @param appUserId
     */
    public Appointment(
                       String appTitle,
                       String appDesc,
                       String appLocation,
                       String appType,
                       String appStart,
                       String appEnd,
                       int appCustId,
                       int appContactId,
                       int appUserId) {
        this.appTitle = new SimpleStringProperty(appTitle) ;
        this.appDesc = new SimpleStringProperty(appDesc) ;
        this.appLocation = new SimpleStringProperty(appLocation) ;
        this.appType = new SimpleStringProperty(appType);
        this.appStartLocal = LocalDateTime.parse(appStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.appEndLocal = LocalDateTime.parse(appEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.appDate = new SimpleStringProperty(appStartLocal.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        this.appStart = new SimpleStringProperty(appStartLocal.format(DateTimeFormatter.ofPattern("hh:mm a")));
        this.appEnd = new SimpleStringProperty(appEndLocal.format(DateTimeFormatter.ofPattern("hh:mm a")));
        this.appCustId = new SimpleIntegerProperty(appCustId) ;
        this.appContactId = new SimpleIntegerProperty(appContactId) ;
        this.appUserId = new SimpleIntegerProperty(appUserId);
    }


    /**
     * Getters and setters for appointments.
     *
     */

    public LocalDateTime getAppStartLocal() {
        return appStartLocal;
    }

    public LocalDateTime getAppEndLocal() {
        return appEndLocal;
    }

    public int getAppUserId() {
        return appUserId.get();
    }

    public SimpleIntegerProperty appUserIdProperty() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId.set(appUserId);
    }

    public String getAppDate() {
        return appDate.get();
    }

    /**
     * Returns the String form of the appointment start datetime
     * @return
     */
    public String getAppStartLocalString() {
        return appStartLocal.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setAppStartLocal(LocalDateTime appStartLocal) {
        this.appStartLocal = appStartLocal;
    }

    /**
     * Returns the String form of the appointment end datetime.
     * @return
     */
    public String getAppEndLocalString() {
        return appEndLocal.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setAppEndLocal(LocalDateTime appEndLocal) {
        this.appEndLocal = appEndLocal;
    }

    public SimpleStringProperty appDateProperty() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate.set(appDate);
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


    public SimpleStringProperty appStartProperty() {
        return appStart;
    }

    public void setAppStart(String appStart) {
        this.appStart = new SimpleStringProperty(appStart);
    }

    public String getAppStart(){
        return this.appStart.get();
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

    public String getAppEnd() {
        return appEnd.get();
    }

    public SimpleStringProperty appEndProperty(){
        return appEnd;
    }

    public void setAppEnd(SimpleStringProperty appEnd) {
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
