package clientServer;

import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends JFrame{
	
	private JTextArea msgArea;
	private DataOutputStream toClient;
	private DataInputStream fromClient;
	private static ArrayList<ClientHandler> clients;
	private int prime, generator, randNum;
	private static ArrayList<Integer> keys;
	
	public void buildGUI() {
		final int FRAME_WIDTH = 700;
		final int FRAME_HEIGHT = 300;
		msgArea = new JTextArea();
		msgArea.setEditable(false);
		setLayout(new BorderLayout());
		add(new JScrollPane(msgArea), BorderLayout.CENTER);
		setTitle("Chatroom");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void diffieHellman(){
		try {
			int tmp = (int) (Math.pow(this.fromClient.readInt(), randNum) % prime);
			msgArea.append("Key for user " + keys.size() + ": " + tmp + '\n');
			keys.add(tmp);
			toClient.writeInt(keys.size() - 1);
			toClient.writeInt((int) (Math.pow(generator, randNum) % prime));
			toClient.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Server(int port) {
		Random gen = new Random();
		this.keys = new ArrayList<>();
		this.randNum = gen.nextInt(9) + 1;
		this.prime = 13;
		this.generator = 6;
		buildGUI();
		try (ServerSocket server = new ServerSocket(port)){
			msgArea.append("Server started \n");
			clients = new ArrayList<ClientHandler>();
			msgArea.append("Waiting for clients... \n");
			Socket client;
			while(true) {
				client = server.accept();
				msgArea.append("Client accepted at port: " + port + '\n');
				this.fromClient = new DataInputStream(new BufferedInputStream(client.getInputStream()));
				this.toClient = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
				ClientHandler handler = new ClientHandler(client, fromClient, toClient);
				msgArea.append("Waiting for key\n");
				diffieHellman();
				msgArea.append("Key received\n");
				clients.add(handler);
				Thread t = new Thread(handler);
				t.start();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Server(8081);
	}
	
	class ClientHandler implements Runnable{
		
		private DataInputStream in;
		private DataOutputStream out;
		Socket socket;
		
		public ClientHandler(Socket socket, DataInputStream in, DataOutputStream out) {
			this.in = in;
			this.out = out;
			this.socket = socket;
		}
		
		@Override
		public void run() {
			String msg;
			int id;
			while(true) {
				try {
					id = in.readInt();
					msg = in.readUTF();
					msgArea.append("Got msg from " + id + ": " + msg + '\n');
					msg = AES.decrypt(msg, keys.get(id));
					
					msgArea.append(msg.trim() + '\n');
						for(int i = clients.size()-1; i >= 0; i--) {
							//TODO - fix if more then one client, only senders msg is decrypted
							msgArea.append("Sending msg to user " + i + " with key " + keys.get(i) + '\n');
							clients.get(i).out.writeUTF(AES.encrypt(msg, keys.get(i)));
							clients.get(i).out.flush();
						}
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			}
		}
		
	}

}
