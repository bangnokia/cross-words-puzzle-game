/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class countTime extends Thread{
    int playTime;
    JLabel time;
    @Override
        public void run() {
            while (true) {
                try {
                    playTime++;
                    System.out.println(playTime);
                    time.setText(String.valueOf(playTime));
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
        }
    public countTime(int playTime, JLabel time) {
        this.playTime = playTime;
        this.time = time;              
    }
        public countTime() {
            
        }
    
}
