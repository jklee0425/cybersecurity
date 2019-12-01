package finalJar;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class ChatRoom extends JFrame {
	// @summary Creates a client for a chatroom

	private JTextArea msgArea;
	private JTextField msgField;
	private JMenuBar menuBar;
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	private InetAddress address;
	private Socket socket;
	private int prime, generator, randNum, key;
	private String host;
	private boolean runnable = true;
	
	public void buildGUI() {
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
		setSize(Helper.FRAME_WIDTH, Helper.FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void diffieHellman(){
		msgArea.append("Sending key\n");
		try {
			toServer.writeInt((int) (Math.pow(generator, randNum) % prime));
			toServer.flush();
			msgArea.append("Key sent\n");
			key = (int) (Math.pow(fromServer.readInt(), randNum) % prime);
			msgArea.append("Received key from server: " + key + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createMenuBar() {
		menuBar = new JMenuBar();
		JMenu menuExit = new JMenu("Exit Chat Room");
		menuBar.add(menuExit);
	    menuExit.addMenuListener(new MenuListener() {
	    	
	    	@Override
	    	public void menuSelected(MenuEvent e) {
					try {
						runnable = false;
						msgArea.append("Exiting");
						toServer.writeUTF(AES.encrypt("ping", key));
						toServer.flush();
						sendMsg(host + " has left");
						dispose();
						socket.close();
						toServer.close();
						fromServer.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		      }

			@Override
			public void menuDeselected(MenuEvent e) {
			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}

		    });
		setJMenuBar(menuBar);
	}
	public ChatRoom(int port, String host){
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
				while(runnable == true) {
					try {
						receiveMsg();
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		read.start();
	}
	
	private void receiveMsg() throws IOException {
		String tmp = "", msg = "";
		do {
			tmp = AES.decrypt(fromServer.readUTF(), key).trim();
			if(!tmp.equals(".")) {
				msg += tmp;
			}
			else if(tmp.equals("exit")) {
				runnable = false;
				break;
			}
		}while(!tmp.equals("."));
		msgArea.append(msg.replaceAll("`", " ") + '\n');
	}
	
	private void sendMsg(String msg) {
		String tmp;
		try {
			toServer.writeUTF(AES.encrypt(String.valueOf(msg.hashCode()), key));
			for(int i = 0; i < msg.length(); i=i+15) {
				if(i + 15 < msg.length()) {
					tmp = msg.substring(i, i+15);
				}
				else {
					tmp = msg.substring(i, msg.length());
				}
				toServer.writeUTF(AES.encrypt(tmp, key));
				toServer.flush();
			}
			toServer.writeUTF(AES.encrypt(".", key));
			toServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class msgListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				toServer.writeUTF(AES.encrypt("ping", key));
				sendMsg(host + ">" + msgField.getText().trim().replaceAll(" ", "`"));
				msgField.setText("");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new ChatRoom(8081, "User");
	}
}
