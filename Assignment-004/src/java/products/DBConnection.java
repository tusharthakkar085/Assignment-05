/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package products;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author c0653602
 */
class DBConnection {
//    public  java.sql.Connection getConnection() {
//        java.sql.Connection conn = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException ex) {
//            System.err.println("JDBC Driver Not Found: " + ex.getMessage());
//        }
//
//        try {
//            String jdbc = "jdbc:mysql://ipro.lambton.on.ca/inventory";
//            conn = DriverManager.getConnection(jdbc, "products", "products");
//        } catch (SQLException ex) {
//            System.err.println("Failed to Connect: " + ex.getMessage());
//        }
//        return conn;
//    }
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("JDBC Driver Not Found: " + ex.getMessage());
        }

        try {
            String jdbc = "jdbc:mysql://127.0.0.1/c0653602";
            conn = DriverManager.getConnection(jdbc, "root", "");
        } catch (SQLException ex) {
            System.err.println("Failed to Connect: " + ex.getMessage());
        }
        return conn;
    }
}
