/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dau
 */
public class dbConnect {
    //create connection to database
    static public Connection create() throws ClassNotFoundException {
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://daudau;databaseName=crossword572", "lab", "123456");
        } catch(SQLException e) {
            e.getMessage();
        }        
        return con;
    }
    //destroy connection
    static public void destroy(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(dbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}
