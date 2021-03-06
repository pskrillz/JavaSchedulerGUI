package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DbConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryCount {

    private SimpleStringProperty categoryTitle;
    private SimpleIntegerProperty categoryCount;

    // db vars
    private static Connection connection;
    private static PreparedStatement prepStatment;
    private static ResultSet resultSet;

    public CategoryCount(SimpleStringProperty categoryTitle,
                         SimpleIntegerProperty categoryCount) {
        this.categoryTitle = categoryTitle;
        this.categoryCount = categoryCount;
    }

    public String getCategoryTitle() {
        return categoryTitle.get();
    }

    public SimpleStringProperty categoryTitleProperty() {
        return categoryTitle;
    }

    public int getCategoryCount() {
        return categoryCount.get();
    }

    public SimpleIntegerProperty categoryCountProperty() {
        return categoryCount;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle.set(categoryTitle);
    }

    public void setCategoryCount(int categoryCount) {
        this.categoryCount.set(categoryCount);
    }


    /**
     * DB methods
     */

    private static Connection getConnection() throws SQLException {
        Connection con = DbConnectionFactory.getInstance().getConnection();
        return con;
    }

    public static void closeConnection(){
        try {
            if (prepStatment != null)
                prepStatment.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * getSqlString()
     * Used by the reports tab's category stats table.
     * Takes a string parameter and returns the sql query string based on that.
     * @param queryType
     * @return
     */
    public static String getSqlString(String queryType) {
        String sqlQuery = "";
        if (queryType == "Month") {
            sqlQuery = "SELECT DATE_FORMAT(start, \"%M\") \"Month\"," +
                    "count(start) \"Total\"\n" +
                    "from WJ07tms.appointments\n" +
                    "group by 1;";
        } else if (queryType == "Type") {
            sqlQuery = "SELECT type," +
                    "count(type) \"Total\"\n" +
                    "from WJ07tms.appointments\n" +
                    "group by 1;";
        } else if (queryType == "filterType"){
            sqlQuery = "SELECT count(*) FROM WJ07tms.appointments" +
                    "where type = ?";
        } else if (queryType == "filterMonth"){
            sqlQuery = "SELECT count(*) FROM WJ07tms.appointments" +
                    "where DATE_FORMAT(start, \"%M\") = ?";
        } else if (queryType == "filterBoth"){
            sqlQuery = "SELECT count(*) FROM WJ07tms.appointments" +
                    "where DATE_FORMAT(start, \"%M\") = ? " +
                    "and type = ?";
        }
        return sqlQuery;
    }

    /**
     * getSqlString()
     * Overloaded method to get all appointment count
     * with empty parameter.
     * @return
     */
    public static String getSqlString(){
        System.out.println("Total number of appointments");
            String sqlQuery = "select count(*) from WJ07tms.appointments ";
            return sqlQuery;
        }

    /**
     * Returns the total number of appointments from db
     * @return String count
     */
    public static String getTotalApps() {
        String count = "";
        try {
            connection = getConnection();
            prepStatment = connection.prepareStatement(getSqlString());
            resultSet = prepStatment.executeQuery();

            while(resultSet.next()) {
               count = resultSet.getString(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return count;
    }

    /**
     * getTotalApps()
     * Overloaded to include the result restriction based on filters.
     * @param where String
     * @return String count
     */
    public static String getTotalApps(String where) {
        String count = "";
        try {
            connection = getConnection();
            prepStatment = connection.prepareStatement(getSqlString() + where);
            resultSet = prepStatment.executeQuery();

            while(resultSet.next()) {
                count = resultSet.getString(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return count;
    }




    public static ObservableList<CategoryCount> getStats(String sql) {
        ObservableList<CategoryCount> categoryList = FXCollections.observableArrayList();
        try {
            connection = getConnection();
            prepStatment = connection.prepareStatement(sql);
            resultSet = prepStatment.executeQuery();

            while(resultSet.next()) {
                SimpleStringProperty name = new SimpleStringProperty(resultSet.getString(1));
                SimpleIntegerProperty count = new SimpleIntegerProperty(resultSet.getInt(2));

                CategoryCount newItem = new CategoryCount(name, count);
                categoryList.add(newItem);
            }


        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return categoryList;
    }

//
//    public static String getFilterMonthCount(String sql, String month) {
//        ObservableList<CategoryCount> categoryList = FXCollections.observableArrayList();
//        try {
//            connection = getConnection();
//            prepStatment = connection.prepareStatement(sql);
//            resultSet = prepStatment.executeQuery();
//            while(resultSet.next()) {
//                SimpleStringProperty name = new SimpleStringProperty(resultSet.getString(1));
//                SimpleIntegerProperty count = new SimpleIntegerProperty(resultSet.getInt(2));
//                CategoryCount newItem = new CategoryCount(name, count);
//                categoryList.add(newItem);
//            }
//        } catch (SQLException e){
//            e.printStackTrace();
//        } finally {
//            closeConnection();
//        }
//
//    }
//
//    public static String getFilterTypeCount(String sql, String type) {
//        ObservableList<CategoryCount> categoryList = FXCollections.observableArrayList();
//        try {
//            connection = getConnection();
//            prepStatment = connection.prepareStatement(sql);
//            resultSet = prepStatment.executeQuery();
//            while(resultSet.next()) {
//                SimpleStringProperty name = new SimpleStringProperty(resultSet.getString(1));
//                SimpleIntegerProperty count = new SimpleIntegerProperty(resultSet.getInt(2));
//                CategoryCount newItem = new CategoryCount(name, count);
//                categoryList.add(newItem);
//            }
//        } catch (SQLException e){
//            e.printStackTrace();
//        } finally {
//            closeConnection();
//        }
//
//    }
//
//    public static String getFilteredbyBothCount(String sql) {
//        ObservableList<CategoryCount> categoryList = FXCollections.observableArrayList();
//        try {
//            connection = getConnection();
//            prepStatment = connection.prepareStatement(sql);
//            resultSet = prepStatment.executeQuery();
//            while(resultSet.next()) {
//                SimpleStringProperty name = new SimpleStringProperty(resultSet.getString(1));
//                SimpleIntegerProperty count = new SimpleIntegerProperty(resultSet.getInt(2));
//                CategoryCount newItem = new CategoryCount(name, count);
//                categoryList.add(newItem);
//            }
//        } catch (SQLException e){
//            e.printStackTrace();
//        } finally {
//            closeConnection();
//        }
//
//    }


}
