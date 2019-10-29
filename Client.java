package clientServer;

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
import SignInDialog;
import sun.tools.jstat.SymbolResolutionClosure;

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
		//TODO - exchange keys
	}
	
	public String encrypt() {
		//TODO - encrypt data
		return null;
	}
	
	public String decrypt() {
		//TODO - decrypt data
		return null;
	}
	public void createMenuBar() {
		menuBar = new JMenuBar();
		JMenu newUserButton;
		JMenu loginButton;
		JMenu logoutButton;

		newUserButton = new JMenu("Registration");
		loginButton = new JMenu("Sign In");
		logoutButton = new JMenu("Sign Out");

		newUserButton.addActionListener(new newUser());
		loginButton.addActionListener(new signIn());
		logoutButton.addActionListener(new signOut());

		menuBar.add(newUserButton);
		menuBar.add(loginButton);
		menuBar.add(logoutButton);

		setJMenuBar(menuBar);
	}
	public Client(int port) {
		buildGUI();
		try {
			this.address = InetAddress.getLocalHost();
			this.socket = new Socket(address.getLoopbackAddress(), port);
			this.fromServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			this.toServer = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			this.host = address.getHostName();
		}
		catch(Exception e) {
			msgArea.append(e.toString() + '\n');
			e.printStackTrace(System.err);
		}
	}
	

	private class msgListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String msg = host + ">" + msgField.getText().trim();
			try {
				toServer.writeUTF(msg + '\n');
				toServer.flush();
				msg = fromServer.readUTF();
				msgArea.append(msg);
				msgField.setText("");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private class newUser implements ActionListener{

	}
	private class signIn implements ActionListener{
		public void actionPerformed(ActionEvent e){
			SingInDialog signIn = new SignInDialog(this);
			signIn.setVisible(true);
			if(signIn.isSucceeded()){
				host = signIn.getUserName();
			}
		}
	}
	private class signOut implements ActionListener{

	}

	public static void main(String[] args) {
		new Client(8081);
	}

}
