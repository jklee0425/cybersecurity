package finalJar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.graalvm.compiler.nodes.memory.Access;

import Database.AccessControl;
import clientServer.AES;
import client.Session;

public class Login extends JFrame implements ActionListener{
    private final String[] ROLES = {"Salesperson", "Warehouse"};
    private final String[] BRANCHES = {"GERMANY", "VANCOUVER", "NEW_YORK"};
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JComboBox cbRole;
    private JComboBox cbBranch;
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
        cbBranch = new JComboBox<String>(BRANCHES);
        

        idPanel.add(lbUsername);
        idPanel.add(tfUsername);
        pwPanel.add(lbPassword);
        pwPanel.add(pfPassword);

        userInputPanel.setLayout(new BoxLayout(userInputPanel, BoxLayout.Y_AXIS));
        userInputPanel.add(idPanel);
        userInputPanel.add(pwPanel);
        userInputPanel.add(cbRole);
        userInputPanel.add(cbBranch);
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
        String roleName = cbRole.getSelectedItem().toString();
        String name = Helper.getUsername(tfUsername);
        int role = Database.AccessControl.getRoleKey(roleName);
        String branch = cbBranch.getSelectedItem().toString();
        /**
         * TODO 
         * - include sanity check with Database.AccessControl.getRoleKey
         * - retrieve branch name from the userinput and pass it down to Session as a parameter
         */ 
        if(authenticate(name, Helper.getPassword(pfPassword),branch) && Database.AccessControl.isAllowed(name,branch)){
            dispose();
            AccessControl.logger(name, " has logged in.");
            new Session(name, role, branch);
        }else{
            JOptionPane.showMessageDialog(null, "Password is wrong / Accessing after hours");
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
    private boolean authenticate(String username, String password, String branch) {
        // TODO; Example
        String sql = "SELECT userName,branch FROM userInfo WHERE userName=? and userPass=? AND branch=?";
       
        try{
            Connection myConn = DriverManager.getConnection(AccessControl.accountDatabase, "sampleuser", "CodeHaze1");
            PreparedStatement prepState = (PreparedStatement) myConn.prepareStatement(sql);
            int key = AccessControl.getRoleKey(cbRole.getSelectedItem().toString().toUpperCase());
            
            prepState.setString(1, AES.encrypt(username, key));
            prepState.setString(2, AES.encrypt(password, key));
            prepState.setString(3, branch);
            ResultSet myRs = prepState.executeQuery();
            int count = 0;
            while(myRs.next()){
                //branch = myRs.getString("branch");
                count++;
            }
            if(count == 1){
                myConn.close();
                return true;
            }else{
                System.out.println("wrong password");
                return false;
            }
           
        }catch(SQLException f){
            f.printStackTrace();
            System.out.println("Wrong password");
            return false;
        }
        
        //return false;
    }
    public static void main(String[] args) {
        new Login();
    }
}
