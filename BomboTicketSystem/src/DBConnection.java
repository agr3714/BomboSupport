
import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Name: Audrey Rovero
 * File: DBConnection.java
 * Description: Handles connecting to the database and performing any mysql statements.
 */

public class DBConnection {

    private Connection myConn;

    public DBConnection() throws Exception {
        Properties prop = new Properties();
        String fileName = "db.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            InputStream input = loader.getResourceAsStream(fileName);
            prop.load(input);
            // prop.load(new FileInputStream("db.properties"));

        } catch (IOException i) {
            i.printStackTrace();
        }

        String user = prop.getProperty("user");
        String password = prop.getProperty("password");
        String dburl = prop.getProperty("dburl");

        // connect to db
        myConn = DriverManager.getConnection(dburl, user, password);

        System.out.println("DB connection successful to: " + dburl);

    }

    /**
     * Method: checkClient
     * Description: checkClient makes sure that the given client exists in the database.
     * If the client does exist, their user id will be returned, otherwise 0 will be returned
     * to indicate that the client does not exist.
     * @param user
     * @param pswd
     * @param type
     * @return
     * @throws Exception
     */
    public int checkClient(String user, String pswd, String type) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rsSet = null;

        int user_id = 0;
        try {
            stmt = myConn.prepareStatement(
                    "Select user_id, email, username, password, user_type from user where username=? and password =? and user_type =?");
            stmt.setString(1, user);
            stmt.setString(2, pswd);
            stmt.setString(3, String.valueOf(type));

            rsSet = stmt.executeQuery();
            if (rsSet.next()) {
                user_id = rsSet.getInt("user_id");
                
                return user_id;

            } else {
                // unable to find client
                return user_id;

            }

        } finally {
            close(stmt, rsSet);
        }

    }

    /**
     * Method: checkEmployee
     * Description: checkEmployee makes sure that the given employee exists in the database.
     * If the employee does exist, their user id will be returned, otherwise 0 will be returned
     * to indicate that the client does not exist.
     * @param user
     * @param pswd
     * @param type
     * @return
     * @throws Exception
     */
    public int checkEmployee(String user, String pswd, String type) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rsSet = null;

        int user_id = 0;

        try {
            stmt = myConn.prepareStatement(
                    "Select user_id, username, password, user_type from user where username=? and password =? and user_type =?");
            stmt.setString(1, user);
            stmt.setString(2, pswd);
            stmt.setString(3, String.valueOf(type));

            rsSet = stmt.executeQuery();
            if (rsSet.next()) {
                user_id = rsSet.getInt("user_id");

                return user_id;

            } else {
                // unable to find employee
                return user_id;
            }

        } finally {
            close(stmt, rsSet);
        }

    }

    /**
     * Method: createTicket
     * Description: createTicket creates a new ticket with the given information.
     * @param user_id - id of the client creating the ticket
     * @param help_text - the text of the ticket
     * @throws Exception
     */
    public void createTicket(int user_id, String help_text) throws Exception {
        Statement stmt = myConn.createStatement();
        ResultSet rsSet = null;

        try {
            // create new entry in ticket table
            stmt.executeUpdate(
                    "INSERT INTO ticket(help_text, client_id) VALUES ('" + help_text + "', " + "'" + user_id + "')");
        } finally {
            close(stmt, rsSet);
        }
    }

    /**
     * Method: createUser
     * Description: createUser creates a new user entry in the database. 
     * @param username - username of the new user
     * @param pswd - password of the new user
     * @param email - email of the new user
     * @param type - user type of the new user, either employee or client
     * @throws Exception
     */
    public void createUser(String username, String pswd, String email, int type) throws Exception {
        Statement stmt = myConn.createStatement();
        ResultSet rsSet = null;
        try {
            // create new user
            stmt.executeUpdate("insert into user(username, password, email, user_type) values ('" + username + "', '"
                    + pswd + "', '" + email + "', " + type + ")");
        } finally {
            close(stmt, rsSet);
        }

    }

    /**
     * Method: editTicket
     * Description: editTicket updates a given ticket in the database with the given information.
     * @param ticket_id - id of the ticket to be updated
     * @param employee_id - id of employee that edited ticket
     * @param comment - comment made by employee
     * @param status_id - status of the ticket as set by the employee
     * @throws Exception
     */
    public void editTicket(int ticket_id, int employee_id, String comment, int status_id) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rsSet = null;

        try {
            stmt = myConn.prepareStatement(
                    "update ticket set comment = ?, ticket_status_id = ?, employee_id = ? where ticket_id = ?");
            stmt.setString(1, comment);
            stmt.setInt(2, status_id);
            stmt.setInt(3, employee_id);
            stmt.setInt(4, ticket_id);

            stmt.executeUpdate();
            close(stmt, rsSet);
        } catch (SQLException e) {
            throw e;
        }

    }

    /**
     * Method: getAllTickets()
     * Description: getAllTickets returns all the tickets along with information about the
     * client and employee so that the information can be displayed.
     * @return
     * @throws Exception
     */
    public List<Ticket> getAllTickets() throws Exception {
        List<Ticket> list = new ArrayList<>();

        Statement stmt = null;
        ResultSet rsSet = null;

        try {
            stmt = myConn.createStatement();
            // rsSet = stmt.executeQuery("select * from ticket");
            rsSet = stmt.executeQuery("select t.ticket_id, t.comment, t.help_text, t.client_id, t.employee_id, "
                    + "s.status, u.email, u.username, e.username as username2 from ticket as t left join ticket_status as s on t.ticket_status_id = s.ticket_status_id left join user as u on t.client_id = u.user_id"
                    + " left join user as e on e.user_id = t.employee_id");

            while (rsSet.next()) {
                Ticket tempTicket = convertRowToTicket(rsSet);
                list.add(tempTicket);
            }

            return list;
        } finally {
            close(stmt, rsSet);
        }

    }

    /**
     * Method: convertRowToTicket
     * Description: converts a given row from the ticket table into a ticket object
     * @param rsSet
     * @return
     * @throws SQLException
     */
    private Ticket convertRowToTicket(ResultSet rsSet) throws SQLException {
        int t_id = rsSet.getInt("ticket_id");
        // int t_status = rsSet.getInt("ticket_status_id");
        String comm = rsSet.getString("comment");
        String desc = rsSet.getString("help_text");
        int client_id = rsSet.getInt("client_id");
        int employee_id = rsSet.getInt("employee_id");
        String str_status = rsSet.getString("status");
        String email = rsSet.getString("email");
        String username = rsSet.getString("username");
        String employee_user = rsSet.getString("username2");

        Ticket tempTicket = new Ticket(t_id, comm, desc, client_id, employee_id, str_status, email, username,
                employee_user);
        System.out.println(tempTicket);
        return tempTicket;
    }

    private static void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {

        if (rs != null) {
            rs.close();
        }

        if (stmt != null) {

        }

        if (conn != null) {
            conn.close();
        }
    }

    private void close(Statement stmt, ResultSet rs) throws SQLException {
        close(null, stmt, rs);
    }

}
