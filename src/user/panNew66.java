/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import lib.dbConnect;
import org.jdesktop.xswingx.PromptSupport;
import static user.frMain.userMainPan;

/**
 *
 * @author Dau
 */
public class panNew66 extends javax.swing.JPanel {

    int width = 6;
    int gameID;
    int playTime;
    
    countTime ct;
    
    Connection con = null;
    ArrayList<JTextField> newCW66 = new ArrayList<>();
    ArrayList<String> rawAnswerCW66 = new ArrayList<>();
    ArrayList<String> userAnswerCW66 = new ArrayList<>();
    List<String[]>  newQAList66 = new ArrayList<>();
    /**
     * Creates new form panNew66
     */
    public panNew66() {
        initComponents();
        try {
            con = dbConnect.create();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(panNew44.class.getName()).log(Level.SEVERE, null, ex);
        }
        //set crossword puzzle jtext field
        newCW66.add(jTextField1);newCW66.add(jTextField2);newCW66.add(jTextField3);
        newCW66.add(jTextField4);newCW66.add(jTextField5);newCW66.add(jTextField6);
        newCW66.add(jTextField7);newCW66.add(jTextField8);newCW66.add(jTextField9);
        newCW66.add(jTextField10);newCW66.add(jTextField11);newCW66.add(jTextField12);
        newCW66.add(jTextField13);newCW66.add(jTextField14);newCW66.add(jTextField15);
        newCW66.add(jTextField16);newCW66.add(jTextField17);newCW66.add(jTextField18);
        newCW66.add(jTextField19);newCW66.add(jTextField20);newCW66.add(jTextField21);
        newCW66.add(jTextField22);newCW66.add(jTextField23);newCW66.add(jTextField24);
        newCW66.add(jTextField25);newCW66.add(jTextField26);newCW66.add(jTextField27);
        newCW66.add(jTextField28);newCW66.add(jTextField29);newCW66.add(jTextField30);
        newCW66.add(jTextField31);newCW66.add(jTextField32);newCW66.add(jTextField33);
        newCW66.add(jTextField34);newCW66.add(jTextField35);newCW66.add(jTextField36); 
        
        //set limit charactor per cell
        for (JTextField j : newCW66) {
            j.setDocument(new characterLimit(1));
        }
        
        //generate default border
        Border blackBorder =  BorderFactory.createLineBorder(Color.BLACK);
        for (int i =0; i< width*width; i++) {
            newCW66.get(i).setBorder(blackBorder);
        }
        
        //call generation method
        genQAList();
        genRawAnswerCW66();
        genValuePuzzle();
        genQATable();
//        ct = new countTime(playTime, time);
//        ct.start();
    }
    
    void genQAList() {
        try {
            Statement st = con.createStatement();
            //get random game id
            String q = "SELECT TOP 1 gameid, gamename FROM game WHERE typeid=6 ORDER BY NEWID()";
            ResultSet rs = st.executeQuery(q);
            if (rs.next()) {
                gameID = Integer.valueOf(rs.getString("gameid"));
                //set name puzzle
                pzName.setText(rs.getString("gamename"));
                //let do withi this gameID
                q = "SELECT quescontent, quesanswer, startposition, direction, quesno FROM gamecontents WHERE gameid = "+gameID;
                rs = st.executeQuery(q);
                //gen a string[]
                
                //0 question content
                //1 question anser
                //2 startposition (x,y)
                //3 direction
                //4 question number
                while (rs.next()){
                    String[] qa = {"", "", "", "", ""};
                    qa[0] = rs.getString("quescontent");
                    qa[1] = rs.getString("quesanswer");
                    qa[2] = rs.getString("startposition");
                    qa[3] = rs.getString("direction");
                    qa[4] = rs.getString("quesno");
                    newQAList66.add(qa);
                }
                //start cout time
                ct = new countTime(playTime, time);
                ct.start();
            } else {
                JOptionPane.showMessageDialog(pzName, "no game available"); 
                this.setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(panNew44.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void genRawAnswerCW66() {
        //gen blank list first
        for(int i=0; i<width*width; i++) {
            rawAnswerCW66.add("");
        }
        for (String[] qa : newQAList66) {
            String[] coor = null;
            coor = qa[2].split(",");
            int coorX = Integer.parseInt(coor[0]);
            int coorY = Integer.parseInt(coor[1]);
            //vertical
            if (qa[3].equals("0")) {                
                int start = coorX*width + coorY;
                for (int i = start, j=0; i<=start + width*(qa[1].length()-1); i=i+width, j++) {
                    rawAnswerCW66.set(i, String.valueOf(qa[1].charAt(j)));
                }
            }
            //horizontal
            else {
                int start = coorX*width + coorY;
                for (int i = start, j=0; i<qa[1].length()+start; i++, j++) {
                    rawAnswerCW66.set(i, String.valueOf(qa[1].charAt(j)));
                }
            }
        }
    }
    
    //gen * and value for puzzle table
    void genValuePuzzle() {
        //gen from rawAnswerList
        int pos = 0;
        for(String s : rawAnswerCW66) {
            if (s.equals("")) {
                newCW66.get(pos).setText("*");
                newCW66.get(pos).setEnabled(false);
                newCW66.get(pos).setBackground(Color.GRAY);
            }            
            pos++;
        }
        
        //fill quesno into puzzle
        for (String[] qa : newQAList66) {
            String[] coor = null;
            coor = qa[2].split(",");
            int coorX = Integer.parseInt(coor[0]);
            int coorY = Integer.parseInt(coor[1]);
            pos = coorX*width + coorY;
            //newCW44.get(pos).setText(qa[4]);
            PromptSupport.setPrompt(qa[4], newCW66.get(pos));
        }
    }
    
    //generate vertial table
    void genQATable() {
        vTable.removeColumn(vTable.getColumnModel().getColumn(3));
        vTable.getColumnModel().getColumn(0).setPreferredWidth(300);
        //vTable.getColumnModel().getColumn(2).setPreferredWidth(5);
        
        hTable.removeColumn(hTable.getColumnModel().getColumn(3));
        hTable.getColumnModel().getColumn(0).setPreferredWidth(300);
        
        DefaultTableModel vdtm = (DefaultTableModel) vTable.getModel();
        DefaultTableModel hdtm = (DefaultTableModel) hTable.getModel();
        vdtm.setRowCount(0);
        hdtm.setRowCount(0);
        for(String[] qa : newQAList66) {
            if (qa[3].endsWith("0")) { //vertical
                Object[] row = {qa[0], qa[1].length(), qa[4], qa[2]};
                vdtm.addRow(row);
            } else {
                Object[] row = {qa[0], qa[1].length(), qa[4], qa[2]};
                hdtm.addRow(row);
            }
        }                
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jTextField24 = new javax.swing.JTextField();
        jTextField25 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jTextField30 = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jTextField32 = new javax.swing.JTextField();
        jTextField33 = new javax.swing.JTextField();
        jTextField34 = new javax.swing.JTextField();
        jTextField35 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        hTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        vTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pzName = new javax.swing.JLabel();

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField10.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField11.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField12.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField13.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField14.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField15.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField16.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField17.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField18.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField19.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField20.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField21.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField22.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField22.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField23.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField24.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField25.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField25.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField26.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField26.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField27.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField28.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField28.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField29.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField29.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField30.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField30.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField31.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField31.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField32.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField33.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField33.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField34.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField35.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField35.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField36.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField36.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jButton1.setText("submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("i'm quit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("vertical");

        hTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "question", "answer length", "No", "position"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        hTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        hTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        hTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                hTableMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(hTable);

        jLabel2.setText("horizontal");

        vTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "question", "answer length", "No", "position"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        vTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        vTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        vTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                vTableMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(vTable);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("time:");

        time.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        time.setText("jLabel4");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("s");

        pzName.setText("puzzle name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(time)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(69, 69, 69)
                                .addComponent(jButton1)
                                .addGap(26, 26, 26)
                                .addComponent(jButton2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(pzName))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel3)
                    .addComponent(time)
                    .addComponent(jLabel5))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pzName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void hTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hTableMouseClicked
        // TODO add your handling code here:
        //high light border answer
        Border redBorder = BorderFactory.createLineBorder(Color.red);
        Border blackBorder =  BorderFactory.createLineBorder(Color.BLACK);
        for (int i =0; i< width*width; i++) {
            newCW66.get(i).setBorder(blackBorder);
        }
        int r = hTable.getSelectedRow();
        int l = (int) hTable.getValueAt(r, 1);
        String pos = (String) hTable.getModel().getValueAt(r, 3);
        String[] coor =  pos.split(",");
        int x = Integer.valueOf(coor[0]);
        int y = Integer.valueOf(coor[1]);
        int start = x*width + y;
        for (int i = start, j=0; i<l+start; i++, j++) {
            newCW66.get(i).setBorder(redBorder);
        }

    }//GEN-LAST:event_hTableMouseClicked

    private void hTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hTableMousePressed

    }//GEN-LAST:event_hTableMousePressed

    private void vTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vTableMouseClicked
        // TODO add your handling code here:
        //high light border corlor of answer
        Border redBorder = BorderFactory.createLineBorder(Color.red);
        Border blackBorder =  BorderFactory.createLineBorder(Color.BLACK);
        for (int i =0; i< width*width; i++) {
            newCW66.get(i).setBorder(blackBorder);
        }
        int r = vTable.getSelectedRow();
        int l = (int) vTable.getValueAt(r, 1);
        String pos = (String) vTable.getModel().getValueAt(r, 3);
        String[] coor =  pos.split(",");
        int x = Integer.valueOf(coor[0]);
        int y = Integer.valueOf(coor[1]);
        int start = x*width + y;
        
        for (int i = start, j=0; i<=start + width*(l-1); i=i+width, j++) {
            newCW66.get(i).setBorder(redBorder);
        }
    }//GEN-LAST:event_vTableMouseClicked

    private void vTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vTableMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_vTableMousePressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ct.stop();
        JOptionPane.showMessageDialog(null, "thank you, you let try another time!");        
        userMainPan.removeAll();
        userMainPan.updateUI();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ct.stop(); //stop time counter
        //genarate user valua from cw.
        boolean isRight = true;
        int pos=0;
        for(int i=0; i<width*width; i++) {
            userAnswerCW66.add("");
        }
        for(JTextField jtf : newCW66) {
            if(jtf.getText().equals("*")) {
                pos++;
                continue;
            }
            userAnswerCW66.set(pos, jtf.getText());
            pos++;
        }
        //compare answer
        for(int i = 0; i<width*width; i++) {
            if(!userAnswerCW66.get(i).equalsIgnoreCase(rawAnswerCW66.get(i))) {
                isRight = false;
                break;
            }
        }
        //remove panel game
        userMainPan.removeAll();
        userMainPan.updateUI();
        playTime = Integer.valueOf(time.getText());
        frmSubmit sm = new frmSubmit(isRight, playTime, gameID, width, rawAnswerCW66);
        sm.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable hTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JLabel pzName;
    private javax.swing.JLabel time;
    private javax.swing.JTable vTable;
    // End of variables declaration//GEN-END:variables
}
