package sample;

import java.sql.*;

class MysqlCon {

    /**
     * String dbServer, dbDriver, dbName, username, password, connStringUrl, testQuery:
     * Replaceable constants to configure a connection to a mysql database server.
     */
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


    /**
     * establishConnection()
     * Creates the connection with the database
     * and includes test a test query to check.
     */
    public static void establishConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con= DriverManager.getConnection(connStringUrl, username, password);

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(testQuery);

            while(rs.next()) {
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            }
                 // con.close();
         }
        catch(Exception e){ System.out.println(e);}
    }






}