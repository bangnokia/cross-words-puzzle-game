/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import admin.panCreate44;
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
import javax.swing.JTextArea;
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
public class panNew44 extends javax.swing.JPanel {
    
    
    int width = 4;
    int gameID;
    int playTime;
    
    countTime ct;
    
    Connection con = null;
    ArrayList<JTextField> newCW44 = new ArrayList<>();
    ArrayList<String> rawAnswerCW44 = new ArrayList<>();
    ArrayList<String> userAnswerCW44 = new ArrayList<>();
    List<String[]>  newQAList44 = new ArrayList<>();
    /**
     * Creates new form panNew44
     */
    public panNew44() {
        initComponents();
        try {
            con = dbConnect.create();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(panNew44.class.getName()).log(Level.SEVERE, null, ex);
        }
        //gen the table
        newCW44.add(jTextField1);
        newCW44.add(jTextField2);
        newCW44.add(jTextField3);
        newCW44.add(jTextField4);
        newCW44.add(jTextField5);
        newCW44.add(jTextField6);
        newCW44.add(jTextField7);
        newCW44.add(jTextField8);
        newCW44.add(jTextField9);
        newCW44.add(jTextField10);
        newCW44.add(jTextField11);
        newCW44.add(jTextField12);
        newCW44.add(jTextField13);
        newCW44.add(jTextField14);
        newCW44.add(jTextField15);
        newCW44.add(jTextField16);
        
        //set limit charactor per cell
        for (JTextField j : newCW44) {
            j.setDocument(new characterLimit(1));
        }
        
        
        //generate default border
        Border blackBorder =  BorderFactory.createLineBorder(Color.BLACK);
        for (int i =0; i< width*width; i++) {
            newCW44.get(i).setBorder(blackBorder);
        }
        
        //call generation method
        genQAList();
        genRawAnswerCW44();
        genValuePuzzle();
        genQATable();        
    }
    
    void genQAList() {
        try {
            Statement st = con.createStatement();
            //get random game id
            String q = "SELECT TOP 1 gameid, gamename FROM game WHERE typeid=4 ORDER BY NEWID()";
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
                    newQAList44.add(qa);
                }
                //start cout time
                ct = new countTime(playTime, time);
                ct.start();
            } else {
                JOptionPane.showMessageDialog(jButton1, "no game available"); 
                this.setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(panNew44.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void genRawAnswerCW44() {
        //gen blank list first
        for(int i=0; i<16; i++) {
            rawAnswerCW44.add("");
        }
        for (String[] qa : newQAList44) {
            String[] coor = null;
            coor = qa[2].split(",");
            int coorX = Integer.parseInt(coor[0]);
            int coorY = Integer.parseInt(coor[1]);
            //vertical
            if (qa[3].equals("0")) {                
                int start = coorX*4 + coorY;
                for (int i = start, j=0; i<=start + 4*(qa[1].length()-1); i=i+4, j++) {
                    rawAnswerCW44.set(i, String.valueOf(qa[1].charAt(j)));
                }
            }
            //horizontal
            else {
                int start = coorX*4 + coorY;
                for (int i = start, j=0; i<qa[1].length()+start; i++, j++) {
                    rawAnswerCW44.set(i, String.valueOf(qa[1].charAt(j)));
                }
            }
        }
    }
    
    //gen * and value for puzzle table
    void genValuePuzzle() {
        //gen from rawAnswerList
        int pos = 0;
        for(String s : rawAnswerCW44) {
            if (s.equals("")) {
                newCW44.get(pos).setText("*");
                newCW44.get(pos).setEnabled(false);
                newCW44.get(pos).setBackground(Color.GRAY);
            }            
            pos++;
        }
        
        //fill quesno into puzzle
        for (String[] qa : newQAList44) {
            String[] coor = null;
            coor = qa[2].split(",");
            int coorX = Integer.parseInt(coor[0]);
            int coorY = Integer.parseInt(coor[1]);
            pos = coorX*width + coorY;
            //newCW44.get(pos).setText(qa[4]);
            PromptSupport.setPrompt(qa[4], newCW44.get(pos));
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
        for(String[] qa : newQAList44) {
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        vTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        hTable = new javax.swing.JTable();
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("time:");

        time.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        time.setText("0");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("s");

        jLabel4.setText("vertical");

        jLabel5.setText("horizontal");

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
        jScrollPane3.setViewportView(vTable);

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
        hTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        hTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        hTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(hTable);

        pzName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(time)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel3))
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jButton1)
                    .addComponent(pzName))
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4))
                    .addComponent(pzName))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(time)
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

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
            userAnswerCW44.add("");
        }
        for(JTextField jtf : newCW44) {
            if(jtf.getText().equals("*")) {
                pos++;
                continue;
            }
            userAnswerCW44.set(pos, jtf.getText());
            pos++;
        }
        //compare answer
        for(int i = 0; i<width*width; i++) {
            if(!userAnswerCW44.get(i).equalsIgnoreCase(rawAnswerCW44.get(i))) {
                isRight = false;
                break;
            }
        }
        //remove panel game
        userMainPan.removeAll();
        userMainPan.updateUI();
        playTime = Integer.valueOf(time.getText());
        frmSubmit sm = new frmSubmit(isRight, playTime, gameID, width, rawAnswerCW44);
        sm.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void vTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vTableMousePressed
      
    }//GEN-LAST:event_vTableMousePressed

    private void vTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vTableMouseClicked
        // TODO add your handling code here:
        //high light border corlor of answer
        Border redBorder = BorderFactory.createLineBorder(Color.red);
        Border blackBorder =  BorderFactory.createLineBorder(Color.BLACK);
        for (int i =0; i< width*width; i++) {
            newCW44.get(i).setBorder(blackBorder);
        }
        int r = vTable.getSelectedRow();
        int l = (int) vTable.getValueAt(r, 1);
        String pos = (String) vTable.getModel().getValueAt(r, 3);
        String[] coor =  pos.split(",");
        int x = Integer.valueOf(coor[0]);
        int y = Integer.valueOf(coor[1]);
        int start = x*width + y;
        
        for (int i = start, j=0; i<=start + width*(l-1); i=i+width, j++) {
            newCW44.get(i).setBorder(redBorder);
        }
        
    }//GEN-LAST:event_vTableMouseClicked

    private void hTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hTableMouseClicked
        //high light border answer
        Border redBorder = BorderFactory.createLineBorder(Color.red);
        Border blackBorder =  BorderFactory.createLineBorder(Color.BLACK);
        for (int i =0; i< width*width; i++) {
            newCW44.get(i).setBorder(blackBorder);
        }
        int r = hTable.getSelectedRow();
        int l = (int) hTable.getValueAt(r, 1);
        String pos = (String) hTable.getModel().getValueAt(r, 3);
        String[] coor =  pos.split(",");
        int x = Integer.valueOf(coor[0]);
        int y = Integer.valueOf(coor[1]);
        int start = x*width + y;
        for (int i = start, j=0; i<l+start; i++, j++) {
            newCW44.get(i).setBorder(redBorder);
        }
    }//GEN-LAST:event_hTableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable hTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
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
