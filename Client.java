package clientServer;

import loginSessionControl.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame{
	
	private JTextArea msgArea;
	private JTextField msgField;
	private JMenuBar menuBar;
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	private InetAddress address;
	private Socket socket;
	private int pubKey1, pubKey2, privKey, symKey;
	private String host;
	
	public void buildGUI() {
		final int FRAME_WIDTH = 700;
		final int FRAME_HEIGHT = 200;
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Message: ");

		msgField = new JTextField("");
		msgField.addActionListener(new msgListener());
		
		panel.setLayout(new BorderLayout());
		panel.add(label, BorderLayout.WEST);
		panel.add(msgField, BorderLayout.CENTER);
		
		msgArea = new JTextArea();
		msgArea.setEditable(false);
		setLayout(new BorderLayout());
		add(panel, BorderLayout.SOUTH);
		add(new JScrollPane(msgArea), BorderLayout.CENTER);
		createMenuBar();

		setTitle("Chatroom client");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void diffieHellman(){
      		privKey = (int)(Math.random()*((100-10)+1))+10;
		num1 = (int)(Math.random()*((100-10)+1))+10;
		num2 = privKey - num1; //primitive root of num1
		try {
			toServer.writeInt((int) (Math.pow(num2, privKey) % num1));
			toServer.flush();
			symKey = (int) (Math.pow(fromServer.readInt(), privKey) % num1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String encrypt(String msg, int key) {
		String tmp = "";
		for(int i = 0; i < msg.length(); i++) {
			tmp += (char) (msg.charAt(i) + key);
		}
		return tmp;
	}
	
	public String decrypt(String msg, int key) {
		String tmp = "";
		for(int i = 0; i < msg.length(); i++) {
			tmp += (char) (msg.charAt(i) - key);
		}
		return tmp;
	}
	public void createMenuBar() {
		menuBar = new JMenuBar();
		JMenu logoutButton;
		logoutButton = new JMenu("Sign Out");
		logoutButton.addActionListener(new signOut());

		menuBar.add(logoutButton);
		setJMenuBar(menuBar);
	}
	public Client(int port, String host) {
		buildGUI();
		
		System.out.println(this.toString());
		try {
			this.address = InetAddress.getLocalHost();
			this.socket = new Socket(address.getLoopbackAddress(), port);
			this.fromServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			this.toServer = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			this.host = host;
		}
		catch(Exception e) {
			msgArea.append(e.toString() + '\n');
			e.printStackTrace(System.err);
		}
		Thread read = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						msgArea.append(fromServer.readUTF());
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		read.start();
	}
	

	private class msgListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String msg = host + ">" + msgField.getText().trim();
			try {
				toServer.writeUTF(msg + '\n');
				toServer.flush();
				msgField.setText("");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private class signOut implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				toServer.writeUTF(host + " has left the chat room." + '\n');
				toServer.flush();
				dispose();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Client(8081, "User");
	}
}
