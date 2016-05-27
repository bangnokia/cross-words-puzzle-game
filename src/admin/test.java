/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Dau
 */
public class test {
    public static void main(String[] args) {
        ArrayList test = new ArrayList();
        test.add("fuck");
        test.add("ahaha");
        test.add("fuck");
        for (Object a : test) {
            System.out.println(a);
            System.out.println(test.indexOf(a));
        }
    }
}
