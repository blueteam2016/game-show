package com.blueteam.gameshow.client;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.blueteam.gameshow.data.ClientProfile;

public class RegistrationScreen extends JPanel{

	private static final long serialVersionUID = -4547016510843530603L;
	private JLabel NameLabel, teamNameLabel, ServerOutputLabel, ClienOutputLabel;
	private JButton servFoldBrowser, clientFoldBrowser;
	private JButton Register;
	private JTextField Name, teamName, servFoldText, clientFoldText;
	private String clientName, clientTeamName, folderLoc, servFoldLoc, clientFoldLoc;
	private ClientWindow clientWindow;
	private JFileChooser folderChooser;
	private JLabel infoPrompt;

	public RegistrationScreen(ClientWindow newclientWindow){
		clientName = "";
		clientTeamName = "";
		folderLoc = "";
		servFoldLoc = "";
		clientFoldLoc = "";
		infoPrompt = new JLabel("");
		clientWindow = newclientWindow;
		this.setLayout(new GridLayout(0,3,10,10));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 5));

		JLabel BlankSpace = new JLabel("");
		NameLabel = new JLabel("Name: ");
		this.add(NameLabel);
		Name = new JTextField("");
		Name.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {}
			public void insertUpdate(DocumentEvent e) {
				checkCompletion();
			}
			public void removeUpdate(DocumentEvent e) {}
		});
		this.add(Name);
		this.add(BlankSpace);

		JLabel BlankSpace2 = new JLabel("");
		teamNameLabel = new JLabel("Team Name:");
		this.add(teamNameLabel);
		teamName = new JTextField("");
		this.add(teamName);
		teamName.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {}
			public void insertUpdate(DocumentEvent e) {
				checkCompletion();
			}
			public void removeUpdate(DocumentEvent e) {}
		});
		this.add(BlankSpace2);

		ServerOutputLabel = new JLabel("Server Output Folder");
		this.add(ServerOutputLabel);
		servFoldBrowser = new JButton("Browse");
		servFoldBrowser.addActionListener(new ServerButton());
		this.add(servFoldBrowser);
		servFoldText = new JTextField("");
		this.add(servFoldText);

		ClienOutputLabel = new JLabel("Client Output Folder");
		this.add(ClienOutputLabel);
		clientFoldBrowser = new JButton("Browse");
		clientFoldBrowser.addActionListener(new ClientButton());
		this.add(clientFoldBrowser);
		clientFoldText = new JTextField("");
		this.add(clientFoldText);

		this.add(infoPrompt);
		JLabel BlankSpace4 = new JLabel("");
		this.add(BlankSpace4);
		Register = new JButton("Register");
		Register.addActionListener(new Register());
		this.add(Register);
		Register.setEnabled(false);
		
	}

	private String fileChooser() {
		folderLoc = "";
		folderChooser = new JFileChooser();

		int returnVal = folderChooser.showOpenDialog(folderChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			folderLoc = folderChooser.getSelectedFile().getAbsolutePath();
		}
		return(folderLoc);

	}

	private void checkCompletion(){
		clientName = Name.getText();
		clientTeamName = teamName.getText();
		if (clientName.equals("") || clientTeamName.equals("") || servFoldLoc.equals("") || clientFoldLoc.equals("")){
			Register.setEnabled(false);
		}else{
			Register.setEnabled(true);
		}
	}

	class ServerButton implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String eventName = event.getActionCommand();
			if (eventName.equals("Browse")) {
				System.out.println("Browse for server output folder");
			}

			servFoldLoc = fileChooser();
			servFoldText.setText(servFoldLoc);
			System.out.println("serverfolderloc: " + servFoldLoc);
			checkCompletion();
		}
	}
	
	class ClientButton implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String eventName = event.getActionCommand();
			if (eventName.equals("Browse")) {
				System.out.println("Browse for client output folder");
			}

			clientFoldLoc = fileChooser();
			clientFoldText.setText(clientFoldLoc);
			System.out.println("clientFoldloc: " + clientFoldLoc);
			checkCompletion();
		}

	}
	
	class Register implements ActionListener{

		public void actionPerformed(ActionEvent event){
			clientName = Name.getText();
			clientTeamName = teamName.getText();
			clientWindow.register(servFoldLoc, clientFoldLoc, new ClientProfile(clientName, clientTeamName));
		}
	}
}