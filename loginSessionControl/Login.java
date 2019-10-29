package loginSessionControl;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Login extends JFrame {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnSignup;

    public Login() {
        final int FRAME_WIDTH = 700;
        final int FRAME_HEIGHT = 200;
        final String[] labels = { "Username: ", "Password: " };
        final JLable title = new JLabel("Welcome to the Chat Room");
        
        JPanel userInputPanel = new JPanel();
        lbUsername = new JLabel(labels[0]);
        tfUsername = new JTextField(20);
        lbPassword = new JLabel(labels[1]);
        pfPassword = new JPasswordField(20);

        userInputPanel.setLayout(new BoxLayout(userInputPanel, BoxLayout.Y_AXIS));
        userInputPanel.add(lbUsername);
        userInputPanel.add(tfUsername);
        userInputPanel.add(lbPassword);
        userInputPanel.add(pfPassword);

        JPanel btnPanel = new JPanel();
        btnLogin = new JButton("Log in");
        btnSignup = new JButton("Sign up");
        btnLogin.addActionListener(new loginListener());
        btnSignup.addActionListener(new signUpListener());

        btnPanel.add(btnLogin);
        btnPanel.add(btnSignup);

        setLayout(new BorderLayout());
        add(title);
        add(userInputPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.PAGE_END);

        pack();
        setTitle("Chat Room");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public String getUsername() {
        return tfUsername.getText().trim();
    }

    public String getPassword() {
        return new String(pfPassword.getPassword());
    }

    public boolean authenticate(String username, String pw) {
        // TODO
        return true;
    }

    private class loginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //TODO
        }
    }
    private class signUpListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //TODO
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}