package Dao;

import javafx.collections.ObservableList;


public interface AppDao<Appointment> {
//    public models.Appointment getApp(Integer id);

    public ObservableList<Appointment> getAllApps();

    public void addApp(Appointment app);

//    public void updateApp(Appointment app, int appId);
//
    public void deleteApp(Appointment app);

}
