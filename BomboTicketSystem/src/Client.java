import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Name: Audrey Rovero
 * File: Client.java
 * Description: Client.java handles the creation of the client view
 * of the application. The client is able to create a ticket that will be
 * created in the ticket database.
 */
public class Client implements ActionListener {
    private static JPanel panel;
    private static JTextArea help_text;
    private static JButton submit;
    private static JLabel newTicket;
    private static int id;


    
    public static JPanel newClient(String username, int user_id) {
        id = user_id;
        panel = new JPanel();
        panel.setLayout(null);

        //Label for ticket creation
        newTicket = new JLabel("Create Help Ticket");
        newTicket.setBounds(95, 0, 155, 35);
        panel.add(newTicket);
        
        help_text = new JTextArea();
        help_text.setBounds(25, 30, 275, 90);
        panel.add(help_text);

        //Submit button
        submit = new JButton("submit");
        submit.setBounds(110, 125, 100, 25);
        submit.addActionListener(new Client());
        
        panel.add(submit);

        return panel;
    }

    @Override
    /**
     * Handles the submission of a new ticket by creating
     * the ticket in the database by using DBConnection.
     * A JOptionPane will pop up to indicate if the creation was
     * successful or not.
     */
    public void actionPerformed(ActionEvent e){
        //Submit ticket 
        String help_txt = help_text.getText();
        int u_id = id;

        try{
            DBConnection db = new DBConnection();
            db.createTicket(u_id, help_txt);
            JOptionPane.showMessageDialog(submit, "Ticket Successfully Submitted.");


        } catch( Exception ex){
            JOptionPane.showMessageDialog(submit, "Ticket Failed to Submit.");
            ex.printStackTrace();

        }

    }
   
}
