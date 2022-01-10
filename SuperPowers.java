package com.CS1102;
import javax.swing.JOptionPane;
public class SuperPowers {
    public static void main(String[] args) {
        String power = JOptionPane.showInputDialog("What is your super power?");
        power = power.toUpperCase();
        JOptionPane.showMessageDialog(null,power+" TO THE RESCUE!");
    }
}
