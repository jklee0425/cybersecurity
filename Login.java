package clientServer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Login extends JFrame implements ActionListener{
	
	//@author Armaan Gill(100295504) 
	//@summary Creates a login page
	
	private static final long serialVersionUID = 1L;
	JPanel panel;
	JLabel name, pswd, port, state;
	JTextField usernameInput, portInput;
	JPasswordField passwordInput;
	JButton submit;

	public Login() {
		name = new JLabel();
		name.setText("Username: ");
		usernameInput = new JTextField();
		usernameInput.addActionListener(this);
		
		pswd = new JLabel();
		pswd.setText("Password: ");
		passwordInput = new JPasswordField();
		passwordInput.addActionListener(this);
		
		port = new JLabel();
		port.setText("Port: ");
		portInput = new JTextField();
		portInput.setText("0");
		portInput.addActionListener(this);
		
		state = new JLabel();
		state.setText("");
		
		submit = new JButton("Submit");
		submit.addActionListener(this);
		
		panel = new JPanel(new GridLayout(4, 1));
		panel.add(name);
		panel.add(usernameInput);
		panel.add(pswd);
		panel.add(passwordInput);
		panel.add(port);
		panel.add(portInput);
		panel.add(state);
		panel.add(submit);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel, BorderLayout.CENTER);
		setTitle("Login");
		setSize(300, 200);
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Login();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String username = usernameInput.getText();
		String password = String.valueOf(passwordInput.getPassword());
		int port = Integer.parseInt(portInput.getText());
		usernameInput.setText("");
		passwordInput.setText("");
		portInput.setText("");
		if(username.equals("admin") && password.equals("admin")) {
			state.setText("Welcome " + username);
			String[] selection = {"Filesystem", "Conference rooms"};
			String choice = (String) JOptionPane.showInputDialog(null, "What would you like to do", "Login choice", JOptionPane.QUESTION_MESSAGE, null, selection, "Filesystem" );
			setVisible(false);
			if(choice == "Conference rooms") {
				new Client(port, username);
			}
			else {
				
			}
		}
		else {
			username = "";
			password = "";
			port = 0;
			state.setText("Invalid user");
		}
		
	}

}
