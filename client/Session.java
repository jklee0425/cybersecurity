import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Session extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // private JButton[] btnChatrooms;
    private String[] chatroomTitles;
    private JLabel lbUserInfo;
    private JButton btnCreate;
    private JButton btnAccess;
    private JButton btnLogOut;
    private String username;

    /**
     *  Logged in as User         [Sign Out]
     *  ----------- chatrooms ---------------
     *  username1's chatroom        [JOIN]
     *  usernmae2's chatroom        [JOIN]
     *  ...
     *  -------------------------------------
     *  [CREATE CHATROOM]    [ACCESS FILESYSTEM]
     */
    public Session(String username) {
        this.username = username;
        JPanel userInfoPn = new JPanel();
        lbUserInfo = new JLabel("Logged in as " + username);
        btnLogOut = new JButton("Sign Out");
        btnLogOut.addActionListener(new logOutListener());
        userInfoPn.setLayout(new BorderLayout());
        userInfoPn.add(lbUserInfo, BorderLayout.LINE_START);
        userInfoPn.add(btnLogOut, BorderLayout.LINE_END);
        chatroomTitles = new String[4];
        for (int i = 1; i < 4; i++) {
            chatroomTitles[i - 1] = "username" + i + "'s chatroom";
        }
        JPanel chatroomPn = new JPanel();
        for(int i = 0; i < 3; i++) {
            JLabel lbTitle = new JLabel(chatroomTitles[i]);
            JButton btnJoin = new JButton("Join");
            btnJoin.addActionListener(new joinListener());
            chatroomPn.add(lbTitle);
            chatroomPn.add(btnJoin);
        }

        JPanel btnPn = new JPanel();
        btnCreate = new JButton("Create Chatroom");
        btnCreate.addActionListener(new createListener());
        btnAccess = new JButton("Access File System");
        btnAccess.addActionListener(new accessListener());
        btnPn.setLayout(new BorderLayout());
        btnPn.add(btnCreate, BorderLayout.WEST);
        btnPn.add(btnAccess, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(userInfoPn, BorderLayout.PAGE_START);
        add(chatroomPn, BorderLayout.CENTER);
        add(btnPn, BorderLayout.PAGE_END);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Join an existing chat room.
     */
    private class joinListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }
    /**
     * Create a new chat room.
     */
    private class createListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TODO
            new ChatRoom(0, username);
        }
    }
    /**
     * Access the file system
     */
    private class accessListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TODO
            new ABCFileSystem();
        }
    }
    /**
     * Terminate the session
     */
    private class logOutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TODO
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Session("user");
    }
}