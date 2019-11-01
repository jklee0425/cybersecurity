

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import clientServer.*;


public class Signup extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirm;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JLabel lbConfirm;
    private JButton btnSignup;
    private JButton btnCancel;

    public Signup() {
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
        btnCancel = new JButton("Cancel");

        btnSignup.addActionListener(new signUpListener());
        btnCancel.addActionListener(new cancelListener());

        btnPanel.add(btnSignup);
        btnPanel.add(btnCancel);

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
            if(!isTaken(Helper.getUsername(tfUsername)) 
                && Helper.pwMatch(pfPassword, pfConfirm)){
                // TODO
            }
        }
    }
    /**
     * Check if the given username is already in the database
     * @param username  username to check from the database
     * @return return true if the name exists in the database, otherwise false.
     */
    public static boolean isTaken(String username) {
        // TODO
        return true;
    }
    /**
     * 
     */
    private class cancelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}