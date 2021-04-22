import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Name: Audrey Rovero
 * File: Signup.java
 * Description: Handles creating the signup screen for the application.
 * Both clients and employees can create accounts. After succesful account creation,
 * the user is redirected back to the login screen.
 */
public class Signup implements ActionListener {
    private static JPanel signup;
    private static JLabel userLabel, passwordLabel, emailLabel;
    private static JTextField userNameText, emailText;
    private static JPasswordField passwordText;
    private static JButton submit;
    private static String[] types = { "client", "employee" };
    private static JComboBox<String> typeList;

    public static JPanel newSignup() {

        signup = new JPanel(new GridLayout(3, 1));
        placeComponents(signup);

        return signup;
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

        // Email
        emailLabel = new JLabel();
        emailLabel.setText("Email :");
        emailLabel.setBounds(10, 75, 80, 25);
        panel.add(emailLabel);

        emailText = new JTextField();
        emailText.setBounds(100, 75, 165, 25);
        panel.add(emailText);

        // User type
        typeList = new JComboBox<>(types);
        typeList.setSelectedIndex(1);
        typeList.setBounds(10, 120, 120, 25);
        panel.add(typeList);

        // Submit
        submit = new JButton("create account");
        submit.setBounds(140, 120, 120, 25);
        // action for login
        submit.addActionListener(new Signup());

        panel.add(submit);

    }

    @Override
    /**
     * method: actionPerformed
     * Description: handles submitting a new account
     * and uses DBConnection to create a new user entry
     * in the user table. If account creation is succesful,
     * the user will be brought back to the login screen.
     */
    public void actionPerformed(ActionEvent e) {
        String user = userNameText.getText();
        String pswd = String.valueOf(passwordText.getPassword());
        String email = emailText.getText();

        if (!user.equals("") || !pswd.equals("")) {
            String type = (String) typeList.getSelectedItem();
            int i_type = 2;

            if (type.equals("client")) {
                i_type = 1;
            }

            try {
                DBConnection db = new DBConnection();
                db.createUser(user, pswd, email, i_type);
                JOptionPane.showMessageDialog(submit, "Succesfully Signed up.");
                // go back to login
                JPanel cLogin = Login.newCLogin();
                Bombo.frame.remove(signup);
                Bombo.frame.add(cLogin);
                Bombo.frame.setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(submit, "Username alrady in use");

            }

        } else {
            JOptionPane.showMessageDialog(submit, "Need to enter Username and Password");

        }

    }

}
