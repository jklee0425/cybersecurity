package loginSessionControl;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final String[] ROLES = {"Salesperson", "Warehouse"};
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JComboBox cbRole;
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
        cbRole = new JComboBox<String>(ROLES);
        cbRole.addActionListener(this);


        idPanel.add(lbUsername);
        idPanel.add(tfUsername);
        pwPanel.add(lbPassword);
        pwPanel.add(pfPassword);

        userInputPanel.setLayout(new BoxLayout(userInputPanel, BoxLayout.Y_AXIS));
        userInputPanel.add(idPanel);
        userInputPanel.add(pwPanel);
        userInputPanel.add(cbRole);
        JPanel btnPanel = new JPanel();
        btnLogin = new JButton("Log in");
        btnLogin.addActionListener(this);

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
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(src == btnLogin) {
            if(isAllowed(Helper.getUsername(tfUsername))){
                dispose();
                new client.Session(Helper.getUsername(tfUsername));
            }
        }else if(src == cbRole){
            
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
        // return now.isAfter(LocalTime.parse(accessibleTime[0])) && 
        //     now.isBefore(LocalTime.parse(accessibleTime[1]));
        return true;
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