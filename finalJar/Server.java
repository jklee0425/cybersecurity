package finalJar;

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
	//@summary Creates a server that accepts clients for a chatroom
	
	private JTextArea msgArea;
	private DataOutputStream toClient;
	private DataInputStream fromClient;
	private static ArrayList<ClientHandler> clients;
	private int prime, generator, randNum;
	private ArrayList<Integer> keys;
	
	public void buildGUI() {
		final int FRAME_WIDTH = 700;
		final int FRAME_HEIGHT = 300;
		msgArea = new JTextArea();
		msgArea.setEditable(false);
		setLayout(new BorderLayout());
		add(new JScrollPane(msgArea), BorderLayout.CENTER);
		setTitle("Chatroom server");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void diffieHellman(){
		try {
			int tmp = (int) (Math.pow(this.fromClient.readInt(), randNum) % prime);
			msgArea.append("Key for user " + keys.size() + ": " + tmp + '\n');
			keys.add(tmp);
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
			msgArea.append("Server started\n");
			clients = new ArrayList<ClientHandler>();
			msgArea.append("Waiting for clients...\n");
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
		
		private ClientHandler(Socket socket, DataInputStream in, DataOutputStream out) {
			this.in = in;
			this.out = out;
			this.socket = socket;
		}
		
		private void closeClient(int client) {
			try {
				clients.get(client).out.writeUTF("exit");//client is still waiting for input after exiting, this closes it
				clients.get(client).out.flush();
				wait(20);
				clients.get(client).socket.close();
				clients.get(client).out.close();
				clients.get(client).in.close();
				keys.set(client, null);
				clients.set(client, null);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		private String receivedMsg(int id) {
			String tmp = "", msg = "";
				try {
					do {
						tmp = AES.decrypt(in.readUTF(),keys.get(id)).trim();
						if(!tmp.equals(".")) {
							msg += tmp;
						}
					}while(!tmp.equals("."));
				} catch (IOException e) {
					e.printStackTrace();
				}
			return msg;
		}
		
		private void sendMsg(int client, String msg) {
			try {
				for(int i = 0; i < msg.length(); i = i + 15) {
					if(i + 15 < msg.length()) {
						clients.get(client).out.writeUTF(AES.encrypt(msg.substring(i, i + 15), keys.get(client)));
					}
					else {
						clients.get(client).out.writeUTF(AES.encrypt(msg.substring(i, msg.length()), keys.get(client)));
					}
					clients.get(client).out.flush();
				}
				clients.get(client).out.writeUTF(AES.encrypt(".", keys.get(client)));
				clients.get(client).out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private int findId() {
			try {
				String ping = in.readUTF();
				for(int i = 0; i < keys.size(); i++) {
					if(AES.decrypt(ping, keys.get(i)).trim().equals("ping")) {
						return i;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return -1;
		}
		
		@Override
		public void run() {
			String msg, hash;
			boolean running = true;
			int id;
			while(running == true) {
				try {
					id = findId();
					if(id == -1) {
						msgArea.append("Key does not match\n");
					}
					else {
						hash = AES.decrypt(in.readUTF(), keys.get(id)).trim();
						msg = receivedMsg(id);
						if(hash.equals(String.valueOf(msg.hashCode()))) {
							msgArea.append(msg + '\n');
							for(int i = clients.size()-1; i >= 0; i--) {
								if(clients.get(i) != null) {
									msgArea.append("Sending msg to user " + i + '\n');
									sendMsg(i, msg);
								}
							}
							if(msg.endsWith("has left") == true) {
								msgArea.append("Closing socket\n");
								closeClient(id);
								running = false;
							}
						}
						else {
							msgArea.append("Hash mismatch, can't validate integrity\n");
							sendMsg(id, "Message_not_sent,_please_resend\n");
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			}
		}
		
	}

}
