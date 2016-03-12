package com.blueteam.gameshow.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.blueteam.gameshow.data.ClientProfile;

public class ClientWindow{
	
	private JTabbedPane tabs;
	private JPanel content;
	private JFrame frame;
	
	private ClientIO clientIO;
	private RegistrationScreen rScreen;
	private ClientQuestionScreen cqScreen;
	
	private String danielString;
	private JTextField danielField;

	public ClientWindow() {	
		
		tabs = new JTabbedPane();
		content = new JPanel();
		content.setLayout( new BorderLayout() );
		frame = new JFrame("GameShow Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		rScreen = new RegistrationScreen(this);
		cqScreen = new ClientQuestionScreen(this);
		
		tabs.addTab("Registration", rScreen);
		tabs.addTab("Question", cqScreen);
		tabs.setEnabled(false);
		
		displayDaniel(); //create extra tab with spot to enter daniel confirmation info
						//must comment out the setEnabled line to be able to get to this
		
		content.add(tabs);
		frame.setContentPane(content);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	private void displayDaniel(){
		danielField = new JTextField(10);
		danielField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				danielString = danielField.getText();
				System.out.println("!!!!!!");
			}
		});
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("danielString: "));
		panel.add(danielField);
		tabs.addTab("Daniel", panel);
	}
	
	public void register(String pathServFold, String  pathClientFold, ClientProfile profile) {
		clientIO = new ClientIO(pathServFold, pathClientFold, profile);
		cqScreen.register();
		tabs.setEnabled(true);
	}
	
	public ClientIO getClientIO() {
		return clientIO;
	}
	
	public static void main(String args[]) {
		new ClientWindow();
	}
}
