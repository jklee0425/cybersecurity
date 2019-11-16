package loginSessionControl;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.ChatRoom;

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
        btnLogin.addActionListener(new loginListener());

        btnPanel.add(btnLogin);

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
                new ChatRoom(8081, Helper.getUsername(tfUsername));
            }
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
        String[] accessibleTime = { "15:30", "14:40" }; // example
        LocalTime now = LocalTime.now();
        return now.isAfter(LocalTime.parse(accessibleTime[0])) && 
            now.isBefore(LocalTime.parse(accessibleTime[1]));
    }
    /**
     * Check if the pw matches the username
     * @param username  id to check from the database
     * @param pw        password to compare
     * @return return true if the password matches the username, otherwise false.
     */
    private boolean authenticate(String username, byte[] pw) {
        // TODO; Example
        String sql = "SELECT pwHash FROM users WHERE username=" + username;
        return true;//sql.execute().eqauls(pw);
    }
    public static void main(String[] args) {
        new Login();
    }
}