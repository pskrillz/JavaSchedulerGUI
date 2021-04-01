package Dao;

import javafx.collections.ObservableList;


public interface AppDao<Appointment> {

    public ObservableList<models.Appointment> getAppListByContact(int contactId);

    public ObservableList<Appointment> getAllApps();

    public void addApp(Appointment app);

    public void updateApp(Appointment app, int appId);

    public void deleteApp(Appointment app);

}
