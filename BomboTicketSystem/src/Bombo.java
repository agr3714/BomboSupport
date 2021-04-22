import javax.swing.*;

/**
 * Name: Audrey Rovero
 * Main program for Bombo Ticket Support.
 * Uses Java Swing, JDBC, and Java Mail
 */

public class Bombo {

    public static JFrame frame;

    Bombo() {

        frame = new JFrame("Bombo Support");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        JPanel cLogin = Login.newCLogin();

        frame.add(cLogin);
        frame.setVisible(true);

    }

    public static void main(String args[]) {

        new Bombo();

    }
}