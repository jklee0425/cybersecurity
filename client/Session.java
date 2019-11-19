

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Session extends JFrame implements ListSelectionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JList list;
    private DefaultListModel chatRoomList;
    private JLabel lbUserInfo;
    private JButton btnJoin;
    private JButton btnCreate;
    private JButton btnAccess;
    private JButton btnLogOut;
    private String username;

    
    public Session(String username) {
        this.username = username;
        JPanel userInfoPn = new JPanel();
        lbUserInfo = new JLabel("Logged in as " + username);
        btnLogOut = new JButton("Sign Out");
        btnLogOut.addActionListener(new logOutListener());
        userInfoPn.setLayout(new BorderLayout());
        userInfoPn.add(lbUserInfo, BorderLayout.LINE_START);
        userInfoPn.add(btnLogOut, BorderLayout.LINE_END);
        chatRoomList = new DefaultListModel();
        for (int i = 1; i < 4; i++) {
            chatRoomList.addElement("username" + i + "'s chatroom");
        }
        list = new JList(chatRoomList);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        JPanel btnPn = new JPanel();
        btnJoin = new JButton("Join selected Chatroom");
        btnJoin.addActionListener(new joinListener());
        btnCreate = new JButton("Create Chatroom");
        btnCreate.addActionListener(new createListener());
        btnAccess = new JButton("Access File System");
        btnAccess.addActionListener(new accessListener());
        btnPn.setLayout(new BorderLayout());
        btnPn.add(btnJoin, BorderLayout.WEST);
        btnPn.add(btnCreate, BorderLayout.CENTER);
        btnPn.add(btnAccess, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(userInfoPn, BorderLayout.PAGE_START);
        add(listScrollPane, BorderLayout.CENTER);
        add(btnPn, BorderLayout.PAGE_END);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setVisible(true);
    }

    /**
     * Join an existing chat room.
     */
    private class joinListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            int size = chatRoomList.getSize();
 
            if (size == 0) { // There is no chatroom, disable the join button.
                btnJoin.setEnabled(false);
 
            } else { //Select an index.
                // TODO 
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
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

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
 
            if (list.getSelectedIndex() == -1) {
            //No selection, disable join button.
                btnJoin.setEnabled(false);
 
            } else {
            //Selection, enable the fire button.
                btnJoin.setEnabled(true);
            }
        }
    }
}