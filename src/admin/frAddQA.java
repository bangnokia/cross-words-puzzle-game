/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Dau
 */
public class frAddQA extends javax.swing.JFrame {
    
    String[] qa = {"","","",""};
    String[] qa_temp = null;
    /*
    qa[0] store question
    qa[1] store answer
    qa[2] store coordinate
    qa[3] store way 0 = vertical, 1 = horizontal
    */
    int width;
    int way;
    String type = "add";
    /**
     * Creates new form frAddQA
     */
    public frAddQA(int width, int way) {
        initComponents();
        this.width = width;
        this.way = way;
        this.setLocation(100, 100);
        this.setVisible(false);
    }
    public frAddQA(int width, int way, String[] qa) {
        initComponents();
        this.width = width;
        this.way = way;
        this.setVisible(false);
        this.addQ.setText(qa[0]);
        this.addA.setText(qa[1]);
        this.addC.setText(qa[2]);
        this.type = "edit"; //edit mode        
//        panCreate44.qaList44.remove(qa); //remove qa first, add again after
        qa_temp = qa.clone();
        if (width == 4) {
            panCreate44.qaList44.remove(qa);
            panCreate44.fillAnswer44();
        } else if (width == 6) {
            panCreate66.qaList66.remove(qa);
            panCreate66.fillAnswer66();
        }
        
    }

    private frAddQA() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private boolean checkCross(String[] qa, ArrayList<JTextField> cw) { //check validate cross of answer
        String[] coor = null;
        coor = qa[2].split(",");
        int coorX = Integer.parseInt(coor[0]);
        int coorY = Integer.parseInt(coor[1]);
        int posStart, posEnd;
        
        if (qa[3].equals("0")) {            
            posStart = coorX*width + coorY;
            posEnd = posStart + (qa[1].length()-1)*width;
            //loop
            for (int i = posStart, j=0; i<= posEnd; i+=width, j++) {
                if (!cw.get(i).getText().equals("") && !cw.get(i).getText().equals(String.valueOf(qa[1].charAt(j)))) {
                    System.out.println(cw.get(i).getText() +" "+ qa[1].charAt(j));
                    JOptionPane.showMessageDialog(rootPane, "character break: "+qa[1].charAt(j) + " - "+ cw.get(i).getText() +" at "+i/width+", "+i%width);
                    return false;
                }
            }
        } else {
            posStart = coorX*width + coorY;
            posEnd = posStart + qa[1].length()-1;
            for (int i = posStart, j=0; i<= posEnd; i++, j++) {
                if (!cw.get(i).getText().equals("") && !cw.get(i).getText().equals(String.valueOf(qa[1].charAt(j)))) {
                    System.out.println(cw.get(i).getText() +" "+ qa[1].charAt(j));
                    JOptionPane.showMessageDialog(rootPane, "character break: "+qa[1].charAt(j) + " - "+ cw.get(i).getText() +" at "+i/width+", "+i%width);
                    return false;
                }
            }
        }
        return true;
    }
            
    public int checkQA() {
        //validate length
        if (qa[1].length() == 0 || qa[0].length() == 0 || qa[2].length() == 0 ) {
            JOptionPane.showMessageDialog(null, "fill all blank");
            return 0;
        }
        //check duplicate answer
        for (String[] s : panCreate44.qaList44) {
            if (qa[1].equals(s[1])) {
                JOptionPane.showMessageDialog(rootPane, "Duplicate answer!");
                return 0;
            }
        }
        //validate coordinate
        if (!Pattern.matches("\\d,\\d", qa[2])) {
            JOptionPane.showMessageDialog(rootPane, "Coordinate wrong format!");
            return 0;
        }
        String[] coor = null;
        coor = qa[2].split(",");
        int coorX = Integer.parseInt(coor[0]);
        int coorY = Integer.parseInt(coor[1]);
        if (coorX < 0 || coorY < 0 || coorX >= width || coorY >= width) {
            JOptionPane.showMessageDialog(null, "corrdinate error!");
            return 0;
        }
        //validate answer length
        if (qa[1].length() > width ) {
            JOptionPane.showMessageDialog(null, "answer too long!");
            return 0;
        }
        //validate answer length + way
        if (way == 0) {
            if (qa[1].length() + coorX > width) {
                JOptionPane.showMessageDialog(rootPane, "answer too long");
                return 0;
            }
        } else {
            if (qa[1].length() + coorY > width) {
                JOptionPane.showMessageDialog(rootPane, "answer too long");
                return 0;
            }
        }
        
        //validate answer and coordinate
        if (width == 4) {
            if (!checkCross(qa, panCreate44.cw44)) {
                return 0;
            }
        } else if (width == 6) {
            if (!checkCross(qa, panCreate66.cw66)) {
                return 0;
            }
        }
                
        //add qa to qalist
        if (width ==4 ) {
            panCreate44.qaList44.add(qa);
            panCreate44.vTable();
            panCreate44.hTable();
            panCreate44.fillAnswer44();
        } else if (width ==6) {
            panCreate66.qaList66.add(qa);
            panCreate66.vTable();
            panCreate66.hTable();
            panCreate66.fillAnswer66();
        }
        
        return 1;
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        addQ = new javax.swing.JTextField();
        addA = new javax.swing.JTextField();
        addC = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("question");

        jLabel2.setText("answer");

        jLabel3.setText("coordinate");

        jButton1.setText("save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("example (x,y) = (1,2)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addComponent(addQ, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(addC, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(addA, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(addA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(addC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (type.equals("edit")) {
            if (width == 4) {
                panCreate44.qaList44.add(qa_temp);
                panCreate44.fillAnswer44();
            } else if(width ==6) {
                panCreate66.qaList66.add(qa_temp);
                panCreate66.fillAnswer66();
            }
        }
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String q = addQ.getText();
        String a = addA.getText();
        String c = addC.getText();     
        qa[0] = q; qa[1] = a; qa[2] = c; qa[3] =  String.valueOf(way);

        if (checkQA() == 1) this.setVisible(false);
//        else {
//            if (type.equals("edit")) {
//                if (width == 4) {
//                    panCreate44.qaList44.add(qa_temp);
//                    panCreate44.fillAnswer44();
//                }   
//            }
//        }
        
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
            java.util.logging.Logger.getLogger(frAddQA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frAddQA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frAddQA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frAddQA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frAddQA().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addA;
    private javax.swing.JTextField addC;
    private javax.swing.JTextField addQ;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
