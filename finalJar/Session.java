package finalJar;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Session extends JFrame implements ListSelectionListener, ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JList list;
    private DefaultListModel chatRoomList;
    private JLabel lbUserInfo;
    private JButton btnJoin;
    private JButton btnAccess;
    private JButton btnLogOut;
    private String username, branch, role;
    private final int CHATROOM_PORT = 8081;
    
    public Session(String username, int roleID, String branch) {
        this.username = username;
        this.role = AccessControl.getRoleName(roleID);
        this.branch = branch;
        JPanel userInfoPn = new JPanel();
        lbUserInfo = new JLabel("Logged in as " + username);
        btnLogOut = new JButton("Sign Out");
        btnLogOut.addActionListener(this);
        userInfoPn.setLayout(new BorderLayout());
        userInfoPn.add(lbUserInfo, BorderLayout.LINE_START);
        userInfoPn.add(btnLogOut, BorderLayout.LINE_END);
        chatRoomList = new DefaultListModel();
        for (int i = 8081; i < 8091; i++) {
            chatRoomList.addElement("Chatroom port: " + i);
        }
        list = new JList(chatRoomList);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        JPanel btnPn = new JPanel();
        btnJoin = new JButton("Join selected Chatroom");
        btnJoin.addActionListener(this);
        btnAccess = new JButton("Access File System");
        btnAccess.addActionListener(this);
        btnPn.setLayout(new BorderLayout());
        btnPn.add(btnJoin, BorderLayout.WEST);
        btnPn.add(btnAccess, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(userInfoPn, BorderLayout.PAGE_START);
        add(listScrollPane, BorderLayout.CENTER);
        add(btnPn, BorderLayout.PAGE_END);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Session("user", 0, "");
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnJoin){
            int index = list.getSelectedIndex();
            int size = chatRoomList.getSize();
            if (size == 0) { // There is no chatroom, disable the join button.
                btnJoin.setEnabled(false);
            } else { //Select an index.
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
                new ChatRoom(index + CHATROOM_PORT, username);
            }
        } else if (e.getSource() == btnAccess) {
            new ABCFileSystem(branch, role);
        } else if (e.getSource() == btnLogOut) {
            System.exit(0);
        }
    }
}