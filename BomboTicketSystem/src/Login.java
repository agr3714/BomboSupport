import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Name: Audrey Rovero
 * File: Login.java
 * Description: Handles creating the login screen for the application.
 * Both clients and employees can sign into their accounts and new accounts can
 * be created if needed.
 */

public class Login implements ActionListener {

    private static JPanel login;
    private static JLabel userLabel, passwordLabel;
    private static JTextField userNameText;
    private static JPasswordField passwordText;
    private static JButton submit, signup;
    private static String[] types = { "client", "employee" };
    private static JComboBox<String> typeList;

    public static JPanel newCLogin() {

        login = new JPanel(new GridLayout(3, 1));
        placeComponents(login);

        return login;
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Username
        userLabel = new JLabel();
        userLabel.setText("User name :");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userNameText = new JTextField();
        userNameText.setBounds(100, 20, 165, 25);
        panel.add(userNameText);

        // Password
        passwordLabel = new JLabel();
        passwordLabel.setText("Password :");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        // User type
        typeList = new JComboBox<>(types);
        typeList.setSelectedIndex(1);
        typeList.setBounds(10, 80, 80, 25);
        panel.add(typeList);

        // Submit
        submit = new JButton("login");
        submit.setBounds(10, 120, 120, 25);
        // action for login
        submit.addActionListener(new Login());

        panel.add(submit);

        // Message
        signup = new JButton("sign up");
        signup.setBounds(140, 120, 120, 25);
        signup.addActionListener(new Login());
        panel.add(signup);

    }

    @Override
    /**
     * Method: actionPerformed
     * Description: Handles logging in an existing client or employee
     * First determines what type of user the account is and then checks
     * to make sure that the user exists in the database using DBConnection.
     * 
     * If the signup button is clicked, the sign up screen will replace the login screen,
     */
    public void actionPerformed(ActionEvent e) {
        String user = userNameText.getText();
        String pswd = String.valueOf(passwordText.getPassword());
        String type = (String) typeList.getSelectedItem();
        int i_type = 2;

        if (e.getSource() == submit) {
            if (type.equals("client")) {
                i_type = 1;
            }
            if (i_type == 1) {
                try {
                    DBConnection db = new DBConnection();
                    int user_id = db.checkClient(user, pswd, String.valueOf(i_type));
                    if (user_id != 0) {
                        JPanel client = Client.newClient(user, user_id);
                        Bombo.frame.remove(login);
                        Bombo.frame.setTitle("Ticket Submission");
                        Bombo.frame.add(client);
                        Bombo.frame.setVisible(true);
                        // JOptionPane.showMessageDialog(submit, "Successfully logged in.");

                    } else {
                        // unable to login
                        JOptionPane.showMessageDialog(submit, "Wrong Username or Password.");

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            } else {
                // employee
                try {
                    DBConnection db = new DBConnection();
                    int user_id = db.checkEmployee(user, pswd, String.valueOf(i_type));
                    if (user_id != 0) {
                        // go to ticket view panel for employees
                        JPanel emp = Employee.newEmployee(user_id);
                        Bombo.frame.remove(login);
                        Bombo.frame.setBounds(100, 100, 800, 800);
                        Bombo.frame.setTitle("View Tickets");
                        Bombo.frame.add(emp);
                        Bombo.frame.setVisible(true);

                    } else {
                        // unable to login
                        JOptionPane.showMessageDialog(submit, "Wrong Username or Password.");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
            }

        } else if (e.getSource() == signup) {
            // sign up new user
            JPanel newAcct = Signup.newSignup();
            Bombo.frame.remove(login);
            Bombo.frame.setTitle("Create New Account");
            Bombo.frame.add(newAcct);
            Bombo.frame.setVisible(true);

        }
    }
}
