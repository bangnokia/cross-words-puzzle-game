/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import com.sun.media.sound.DLSModulator;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import lib.dbConnect;

/**
 *
 * @author Dau
 */
public class puzzle { //use to progress any method
    Connection con = null;
            
    public puzzle() {
        try {
            con = dbConnect.create();            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(puzzle.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    //save puzzle to database
    public void savePuzzle(String pzName, List<String[]> qaList, int type, int status) {
        //create puzzle in db        
        try {
            String query = "INSERT INTO game(gamename, typeid, status) VALUES ("
                + " '"+pzName+"', " + type +", "+ status
                + ")";
            Statement st;   
            st = con.createStatement();
            int rowAffted = st.executeUpdate(query);
            if (rowAffted >0) {
                //System.out.println("ok");
            }
            ResultSet rs = st.executeQuery("SELECT MAX(gameid) FROM game");
            rs.next();
            int gameID = Integer.valueOf(rs.getString(1));
            //System.out.println(gameID);
            
            //create to set quesno automaticaly
            ArrayList quesNo = new ArrayList();
            //set default value for 0 index
            quesNo.add("start");
            for(String[] q: qaList) {
                quesNo.add(q[2]);                
            }
            //create query
            String iQuery = "INSERT INTO gamecontents(gameid, quesno, direction, startposition, quescontent, quesanswer) VALUES ";
            String iQuery2 = null;
            for(String[] q : qaList) {
               iQuery2 = "("+gameID+", "+quesNo.indexOf(q[2])+", " +q[3]+", '"+q[2] +"','"+q[0]+"','"+q[1]+"'" + ")";
               iQuery = iQuery + iQuery2 +",";
            }
            iQuery = iQuery.substring(0, iQuery.length()-1);
            //System.out.println(iQuery);
            int r = st.executeUpdate(iQuery);
            if (r > 0) {
                JOptionPane.showMessageDialog(null, "Success!");
                frManager fm = new frManager();
                fm.toPanMain();
            } else {
                JOptionPane.showMessageDialog(null, "Fail!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(puzzle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //get id of puzzle just inserted to insert question and answer
        
    }
}
