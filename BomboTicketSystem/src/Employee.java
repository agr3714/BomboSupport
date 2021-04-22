import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Name: Audrey Rovero
 * File: Employee.java
 * Description: Employee.java handles the creation of the employee view of the
 * program. It will create a selectable table of all current tickets in the system
 * and handles the editing of tickets.
 */

public class Employee implements ActionListener {
    private static JPanel panel;
    private static JTable ticket_tbl;
    private static JScrollPane scroll;
    private static JButton submit;
    private static int id;
    private static TicketModel m;
    private static String[] ticket_status = { "In Progress", "Complete" };

    public static JPanel newEmployee(int user_id) {
        id = user_id;
        panel = new JPanel();

        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout(0, 0));
        submit = new JButton("");
        submit.setText("Update Ticket Status");
        submit.addActionListener(new Employee());
        panel.add(submit, BorderLayout.SOUTH);
    
        ticket_tbl = new JTable();
        
        // generate table
        try {
            DBConnection db = new DBConnection();
            List<Ticket> tickets = db.getAllTickets();
            m = new TicketModel(tickets);
            ticket_tbl.setModel(m);

            ticket_tbl.setRowSelectionAllowed(true);
            panel.add(ticket_tbl.getTableHeader());

            scroll = new JScrollPane(ticket_tbl);
            scroll.setViewportView(ticket_tbl);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            panel.add(scroll, BorderLayout.CENTER);
           

        } catch (Exception e) {
            e.printStackTrace();

        }

        return panel;
    }

    @Override
    /**
     * Method: actionPerformed
     * Description: handles updating ticket information. Uses two JOptionPane
     * to get status change and comment from the employee and then inserts it
     * into the database using DBConnection. After succesful insertion, an email
     * is then sent to the client using sendEmail().
     * After each update, the table updates to reflect new changes.
     */
    public void actionPerformed(ActionEvent e) {
        int selectedTicket = ticket_tbl.getSelectedRow();

        try {
            int tick_id = (int) m.getValueAt(selectedTicket, 0);
            String client_email = m.getValueAt(selectedTicket, 5).toString();

            // get new edits to ticket - comment and status
            String newStatus = (String) JOptionPane.showInputDialog(submit, "Update Ticket Status: ", "Ticket Update",
                    JOptionPane.QUESTION_MESSAGE, null, ticket_status, ticket_status[1]);
            String newComment = (String) JOptionPane.showInputDialog(submit, "Additional Comments: ");
            // update entry with new status and comment
            int status = 1; // set to complete
            if (newStatus.equals(ticket_status[1])) {
                status = 2; // in progress
            }
            try {
                DBConnection db = new DBConnection();
                db.editTicket(tick_id, id, newComment, status);
                // send email
                SendEmail emailer = new SendEmail(client_email, newStatus, newComment);
                emailer.sendOutEmail();
                JOptionPane.showMessageDialog(null, "Succesfully Updated. Email sent to user.");
                JPanel emp = Employee.newEmployee(id);
                Bombo.frame.remove(panel);
                Bombo.frame.add(emp);
                Bombo.frame.setVisible(true);

            } catch (Exception ex2) {
                JOptionPane.showMessageDialog(null, "Failed to Update Ticket");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(submit, "No Ticket Selected");
            ex.printStackTrace();

        }

    }

}
