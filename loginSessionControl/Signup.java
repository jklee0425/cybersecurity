package loginSessionControl;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import clientServer.*;


public class Login extends JFrame {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirm;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JLabel lbConfirm;
    private JButton btnSignup;
    private JButton btnCancel;

    public Login() {
        JPanel userInputPanel = new JPanel();
        JPanel idPanel = new JPanel();
        lbUsername = new JLabel(Helper.LABELS[0]);
        tfUsername = new JTextField(Helper.INPUT_LENGTH);
        JPanel pwPanel = new JPanel();
        lbPassword = new JLabel(Helper.LABELS[1]);
        pfPassword = new JPasswordField(Helper.INPUT_LENGTH);
        JPanel cfPanel = new JPanel();
        lbConfirm = new JLabel(Helper.LABELS[2]);
        pfConfirm = new JPasswordField(Helper.INPUT_LENGTH);

        idPanel.add(lbUsername);
        idPanel.add(tfUsername);
        pwPanel.add(lbPassword);
        pwPanel.add(pfPassword);
        cfPanel.add(lbConfirm);
        cfPanel.add(pfConfirm);

        userInputPanel.setLayout(new BoxLayout(userInputPanel, BoxLayout.Y_AXIS));
        userInputPanel.add(idPanel);
        userInputPanel.add(pwPanel);
        userInputPanel.add(pfConfirm);

        JPanel btnPanel = new JPanel();
        btnSignup = new JButton("Sign up");
        btnSignup = new JButton("Cancel");

        btnSignup.addActionListener(new signUpListener());
        btnLogin.addActionListener(new cancelListener());

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
    private class signUpListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(!isTaken(Helper.getUsername(this)) && Helper.pwMatch(this)){
                // TODO
            }
        }
    }
    
    private class cancelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
    public static void main(String[] args) {
        new Login();
    }
}