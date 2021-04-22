
/**
 * Name: Audrey Rovero
 * File: Ticket.java
 * Description: Ticket.java contains the Ticket object that is used to convert
 *  sql rows from the ticket table into usable ticket objects.
 */
public class Ticket {

    private int id;
    private int status_id;
    private String commment;
    private String help_text;
    private int client;
    private String client_email;
    private int employee;
    private String status_type;
    private String username;
    private String emp_username;
  
    public Ticket(int client, String help_text){
        this.client = client;
        this.help_text = help_text;
    }

    public Ticket(int id, String comment, String help_text, int client, int employee, String status_type, String client_email, String username, String e_user){
        this.id = id;
        //this.status_id = status_id;
        this.commment = comment;
        this.help_text = help_text;
        this.client = client;
        this.employee = employee;
        this.status_type = status_type;
        this.client_email = client_email;
        this.username = username;
        this.emp_username = e_user;
    }

    public int getClient() {
        return client;
    }

    public String getCommment() {
        return commment;
    }

    public String getHelp_text() {
        return help_text;
    }

    public int getStatus_id() {
        return status_id;
    }

    public int getEmployee() {
        return employee;
    }

    public int getId() {
        return id;
    }

    public String getClient_email() {
        return client_email;
    }

    public String getStatus_type() {
        return status_type;
    }

    public String getUsername() {
        return username;
    }

    public String getEmp_username() {
        return emp_username;
    }

    public void setCommment(String commment) {
        this.commment = commment;
    }

    public void setEmployee(int employee) {
        this.employee = employee;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public void setHelp_text(String help_text) {
        this.help_text = help_text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmp_username(String emp_username) {
        this.emp_username = emp_username;
    }
    

    @Override
    public String toString(){
        return String.format("Ticket [ticket_id=%s, ticket_status_id=%s, comment=%s, help_text=%s, client_id=%s, employee_id=%s]",
         id, status_id, commment, help_text, client, employee);
    }


}
