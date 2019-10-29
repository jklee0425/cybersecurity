package clientServer;

import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends JFrame{
	
	private JTextArea msgArea;
	private DataOutputStream toClient;
	private DataInputStream fromClient;
	private static ArrayList<ClientHandler> clients;
	private int privKey, num1, num2, symKey;
	
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
   
   public int primeGenerator() {
		int[] primes = {11,13,17,19,23,29,31,37,41,43,47,53,59,61,71,73,79};
		return primes[(int) (Math.random()*((primes.length)))];
	}
	
	public void diffieHellman(){
		privKey = (int)(Math.random()*((100-80)+1))+80;
		num1 = primeGenerator();
		num2 = privKey - num1; //primitive root of num1
		try {
			toClient.writeInt((int) (Math.pow(num2, privKey) % num1));
			toClient.flush();
			symKey = (int) (Math.pow(fromClient.readInt(), privKey) % num1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   
   public static String encrypt(String msg, int key) {
		String tmp = "";
		for(int i = 0; i < msg.length(); i++) {
			tmp += (char) (msg.charAt(i) + key);
		}
		return tmp;
	}
	
	public static String decrypt(String msg, int key) {
		String tmp = "";
		for(int i = 0; i < msg.length(); i++) {
			tmp += (char) (msg.charAt(i) - key);
		}
		return tmp;
	}
	
	public Server(int port) {
		buildGUI();
		try (ServerSocket server = new ServerSocket(port)){
			msgArea.append("Server started \n");
			clients = new ArrayList<ClientHandler>();
			msgArea.append("Waiting for clients... \n");
			Socket client;
			while(true){
				client = server.accept();
				msgArea.append("Client accepted at: " + port + '\n');
			
				this.fromClient = new DataInputStream(new BufferedInputStream(client.getInputStream()));
				this.toClient = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
				
				ClientHandler handler = new ClientHandler(client, fromClient, toClient);
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
			while(true) {
				try {
					msg = in.readUTF();
					msgArea.append("Got msg: " + msg);
					for(ClientHandler user : Server.clients) {
						user.out.writeUTF(msg);
						user.out.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			}
		}
		
	}


}
