import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Name: AudreyRovero
 * File: TicketModel
 * Description: TicketModel is used for creating the viewable ticket table for employess.
 * It extends AbstractTableModel to easily convert ticket information for the JTable used in
 * Employee.java
 */

public class TicketModel extends AbstractTableModel {
    private static final int ticket_col = 0;
    private static final int status_col = 1;
    private static final int comment_col = 2;
    private static final int help_col = 3;
    private static final int client_col = 4;
    private static final int email_col = 5;
    private static final int emp_col = 6;

    private String[] columnNames = { "Ticket ID", "Status", "Employee Comment", "Ticket Issue", "Client Username",
            "Client Email", "Assigned Employee" };

    private List<Ticket> tickets;

    public TicketModel(List<Ticket> ts) {
        tickets = ts;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return tickets.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        Ticket temp = tickets.get(row);
        switch (col) {
        case ticket_col:
            return temp.getId();
        case status_col:
            return temp.getStatus_type();
        case comment_col:
            return temp.getCommment();
        case help_col:
            return temp.getHelp_text();
        case client_col:
            return temp.getUsername();
        case email_col:
            return temp.getClient_email();
        case emp_col:
            return temp.getEmp_username();
        default:
            return temp.getId();
        }
    }

    @Override
    public String getColumnName(int c) {
        return columnNames[c];
    }

}
