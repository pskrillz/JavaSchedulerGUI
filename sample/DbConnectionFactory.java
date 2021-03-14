package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnectionFactory {

    /**
     * String dbServer, dbDriver, dbName, username, password, connStringUrl, testQuery:
     * Replaceable constants to configure a connection to a mysql database server.
     */
    private static final String
            driverClassName = "com.mysql.cj.jdbc.Driver";
    private static final String
            dbServer = "wgudb.ucertify.com/";
            // replace with "localhost:3306/" for local database instance.
    private static final String
            dbDriver = "jdbc:mysql://";
    private static final String
            dbName = "WJ07tms";
    private static final String
            username= "U07tms";
    private static final String
            password="53689129199";

    private static final String
            connStringUrl= dbDriver + dbServer + dbName;
    private static final String
            testQuery = "select * from WJ07tms.appointments;";


    private static DbConnectionFactory connectionFactory = null;

    /**
     * establishConnection()
     * Creates the connection with the database
     * and includes test a test query to check.
     */

    private void ConnectionFactory() {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(connStringUrl, username, password);
        return conn;
    }

    public static DbConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new DbConnectionFactory();
        }
        return connectionFactory;
    }


    public static void fireTestQuery(){
        try{
           // Class.forName(driverClassName);
            ResultSet rs = getInstance().getConnection().createStatement().executeQuery(testQuery);
            while(rs.next()) {
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            }
                 // con.close();
         }
        catch(Exception e){
            System.out.println(e);
        }
    }






}