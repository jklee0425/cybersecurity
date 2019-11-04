package loginSessionControl;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import clientServer.*;

public class Login extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnSignup;

    public Login() {
        JPanel userInputPanel = new JPanel();
        JPanel idPanel = new JPanel();
        lbUsername = new JLabel(Helper.LABELS[0]);
        tfUsername = new JTextField(Helper.INPUT_LENGTH);
        JPanel pwPanel = new JPanel();
        lbPassword = new JLabel(Helper.LABELS[1]);
        pfPassword = new JPasswordField(Helper.INPUT_LENGTH);

        idPanel.add(lbUsername);
        idPanel.add(tfUsername);
        pwPanel.add(lbPassword);
        pwPanel.add(pfPassword);

        userInputPanel.setLayout(new BoxLayout(userInputPanel, BoxLayout.Y_AXIS));
        userInputPanel.add(idPanel);
        userInputPanel.add(pwPanel);

        JPanel btnPanel = new JPanel();
        btnLogin = new JButton("Log in");
        btnSignup = new JButton("Sign up");
        btnLogin.addActionListener(new loginListener());
        btnSignup.addActionListener(new signUpListener());

        btnPanel.add(btnLogin);
        btnPanel.add(btnSignup);

        setLayout(new BorderLayout());
        add(userInputPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.PAGE_END);

        pack();
        setTitle("ABC Airlines");
        setSize(Helper.FRAME_WIDTH, Helper.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private class loginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(isAllowed(Helper.getUsername(tfUsername))){
                new Client(8081, Helper.getUsername(tfUsername));
            }
        }
    }
    private class signUpListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //TODO
        }
    }

    /**
     * This method retrieves accessible time of the user from the database
     * and returns boolean according to the retrieved value and the current time
     * @param username ID
     * @return boolean
     */
    private boolean isAllowed(String username){
        // Retrieve allowed time for the user in the system.
        String[] accessibleTime = {"15:30", "14:40"};
        LocalTime now = LocalTime.now();
        return now.isAfter(accessibleTime[0]) && now.isBefore(accessibleTime[1]);
    }
    /**
     * Check if the pw matches the username
     * @param username  id to check from the database
     * @param pw        password to compare
     * @return return true if the password matches the username, otherwise false.
     */
    private boolean authenticate(String username, byte[] pw) {
        // TODO
        return true;
    }
    public static void main(String[] args) {
        new Login();
    }
}