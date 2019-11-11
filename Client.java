package clientServer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

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
	private int prime, generator, randNum, id, key;
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
		msgArea.append("Sending key\n");
		try {
			toServer.writeInt((int) (Math.pow(generator, randNum) % prime));
			toServer.flush();
			msgArea.append("Key sent\n");
			id = fromServer.readInt();
			key = (int) (Math.pow(fromServer.readInt(), randNum) % prime);
			msgArea.append("Received key from server: " + key + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createMenuBar() {
		menuBar = new JMenuBar();
		JMenu menuExit = new JMenu("Exit Chat Room");
		menuExit.addActionListener(new exitListener());

		menuBar.add(menuExit);
		setJMenuBar(menuBar);
	}
	public Client(int port, String host) {
		buildGUI();
		try {
			Random gen = new Random();
			this.address = InetAddress.getLocalHost();
			this.socket = new Socket(address.getLoopbackAddress(), port);
			this.fromServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			this.toServer = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			this.host = host;
	      	this.randNum = gen.nextInt(9) + 1;
			this.prime = 13;
			this.generator = 6; 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		diffieHellman();
		Thread read = new Thread(new Runnable() {
			@Override
			public void run() {
				String tmp;
				while(true) {
					try {
						tmp = fromServer.readUTF();
						msgArea.append(AES.decrypt(tmp, key).trim() + '\n');
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
			try {
				toServer.writeInt(id);
				String msg = id + ">" + msgField.getText().trim();
				toServer.writeUTF(AES.encrypt(msg,key));
				toServer.flush();
				msgField.setText("");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class exitListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				toServer.writeUTF(host + " has left the chat room." + '\n');
				toServer.flush();
				dispose();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Client(8081, "User");
	}
}