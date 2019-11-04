import java.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Session extends JFrame {

    private JBtutton[] btnChatrooms;
    private String[] chatroomTitles;
    private JLabel lbUserInfo;
    private JButton btnCreate;
    private JButton btnAccess;
    private JButton btnLogOut;

    public Session(String username) {
        // TODO : Layout
        JPanel userInfoPn = new JPanel();
        userInfo = new JLabel("Logged in as " + username);
        btnLogOut = new JButton("Sign Out");
        btnLogOut.addActionListener(new logOutListener());
        userInfoPn.add(lbUserInfo);
        userInfoPn.add(btnLogOut);

        /**
         *  idea
         *  ----------- chatrooms ---------------
         *  username1's chatroom        JOIN(btn)
         *  usernmae2's chatroom        JOIN(btn)
         *  ...
         *  -------------------------------------
         *  
         */
        JPanel chatroomPn = new JPanel();
        for(int i = 0; i < btnChatrooms.length(); i++){
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
        btnPn.add(btnCreate);
        btnPn.add(btnAccess);

        // TODO
    }

    /**
     * Join an existing chat room.
     */
    private class joinListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TODO
            new ChatRoom(getLocalPort(), username);
        }
    }
    /**
     * Create a new chat room.
     */
    private class createListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TODO
            new ChatRoom(0000, username);
        }
    }
    /**
     * Access the file system
     */
    private class accessListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TODO
            new FileSystem(username);
        }
    }
    /**
     * Terminate the session
     */
    private class logOutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TODO
            system.exit();
        }
    }
}