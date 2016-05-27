/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import lib.dbConnect;

/**
 *
 * @author Dau
 */
public class frmSubmit extends javax.swing.JFrame {
    Connection con = null;
    boolean isRight;
    int playTime;
    int gameID;
    String name;
    int width;
    ArrayList<String> result;
    /**
     * Creates new form frmSubmit
     */
    public frmSubmit(boolean isRight, int playTime, int gameID, int width, ArrayList<String> result) {
        initComponents();
        this.setVisible(false);
        this.setLocationRelativeTo(null);
        this.isRight = isRight;
        this.playTime = playTime;
        this.gameID = gameID;
        this.width = width;
        this.result = result;
        
        try {
            con = dbConnect.create();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmSubmit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private frmSubmit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        userName = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("enter you name");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("enter your name");

        jButton1.setText("submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButton1)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        name = userName.getText();
        if (name.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "please enter your name!");            
        } else {
            if (isRight == false) {
                JOptionPane.showMessageDialog(rootPane, "Unfortunatly, your answer is wrong!");
                frMain.userMainPan.removeAll();
                frMain.userMainPan.updateUI();                                
                this.setVisible(false);
            } else {
                try {
                    Statement st = con.createStatement();
                    String query = "INSERT INTO scores(gameid, playertime, playername) VALUES "
                            + "(" +gameID+ ", " +playTime+ ", '" +name+ "')";
                    int r = st.executeUpdate(query);
                    if (r>0) {
                        JOptionPane.showMessageDialog(rootPane, "congratulation, you are right! time: "+playTime);                        
                        frMain.userMainPan.removeAll();
                        frMain.userMainPan.updateUI();                                
                        this.setVisible(false);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(frmSubmit.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (width == 4) {
                showResult44 rs = new showResult44(result);  
                rs.setVisible(true);
            } else if (width == 6) {
                showResult66 rs = new showResult66(result);
                rs.setVisible(true);
            }
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmSubmit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmSubmit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmSubmit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmSubmit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmSubmit().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField userName;
    // End of variables declaration//GEN-END:variables
}