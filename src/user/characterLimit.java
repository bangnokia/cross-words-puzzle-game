/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;    
/**
 *
 * @author Dau
 */
public class characterLimit extends PlainDocument{
    private int limit;

    public characterLimit(int limit) {
        this.limit = limit;
    }
    
    @Override
    public void insertString(int offset, String str, AttributeSet set) throws BadLocationException {
        if (str == null) {
            return;
        } else if((getLength() + str.length()) <= limit) {
            str = str.toUpperCase();
            super.insertString(offset, str, set);
        } else {
            JOptionPane.showMessageDialog(null, "only 1 character in 1 cell!");
        }
    }
}
