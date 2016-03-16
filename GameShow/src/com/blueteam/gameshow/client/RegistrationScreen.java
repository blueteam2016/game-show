package com.blueteam.gameshow.client;

import javax.swing.*; 

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.blueteam.gameshow.data.ClientProfile;

public class RegistrationScreen extends JPanel{

	private static final long serialVersionUID = -4547016510843530603L;
	private ClientWindow clientWindow;
	private JLabel nameLabel, teamNameLabel, serverOutputLabel, clientOutputLabel;
	private JButton servFoldBrowser, clientFoldBrowser;
	private JButton registerButton;
	private JTextField name, teamName, servFoldText, clientFoldText;
	private String clientName, clientTeamName, servFoldLoc, clientFoldLoc;
	private JFileChooser folderChooser;
	private JLabel infoPrompt;

	public RegistrationScreen(ClientWindow newclientWindow){
		clientName = "";
		clientTeamName = "";
		servFoldLoc = "";
		clientFoldLoc = "";
		infoPrompt = new JLabel("");
		clientWindow = newclientWindow;
		this.setLayout(new GridLayout(0,3,10,10));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 5));

		
		JLabel BlankSpace = new JLabel("");
		nameLabel = new JLabel("Name: ");
		this.add(nameLabel);
		name = new JTextField("");
		name.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {}
			public void insertUpdate(DocumentEvent e) {
				checkCompletion();
			}
			public void removeUpdate(DocumentEvent e) {}
		});
		this.add(name);
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

		serverOutputLabel = new JLabel("Server Output Folder");
		this.add(serverOutputLabel);
		servFoldBrowser = new JButton("Browse");
		servFoldBrowser.addActionListener(new ServerButton());
		this.add(servFoldBrowser);
		servFoldText = new JTextField("");
		servFoldText.setEditable(false);
		this.add(servFoldText);

		
		clientOutputLabel = new JLabel("Client Output Folder");
		this.add(clientOutputLabel);
		clientFoldBrowser = new JButton("Browse");
		clientFoldBrowser.addActionListener(new ClientButton());
		this.add(clientFoldBrowser);
		clientFoldText = new JTextField("");
		clientFoldText.setEditable(false);
		this.add(clientFoldText);

		
		this.add(infoPrompt);
		JLabel BlankSpace4 = new JLabel("");
		this.add(BlankSpace4);
		registerButton = new JButton("Register");
		registerButton.addActionListener(new Register());
		this.add(registerButton);
		registerButton.setEnabled(false);
		
	} 

	private String folderChooser(String title) {
		String folderLoc = "";
		folderChooser = new JFileChooser();
	
		folderChooser.setCurrentDirectory(new java.io.File("."));
	    folderChooser.setDialogTitle(title);
	    folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnVal = folderChooser.showOpenDialog(folderChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			folderLoc = folderChooser.getSelectedFile().getAbsolutePath() + "/";
		}
		return(folderLoc);

	}

	private void checkCompletion(){
		clientName = name.getText();
		clientTeamName = teamName.getText();
		if (clientName.equals("") || clientTeamName.equals("") || servFoldLoc.equals("") || clientFoldLoc.equals("")){
			registerButton.setEnabled(false);
		}else{
			registerButton.setEnabled(true);
		}
	}

	class ServerButton implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String eventName = event.getActionCommand();
			if (eventName.equals("Browse")) {
			}
			String newlocation=folderChooser("Server");
			if (!newlocation.equals("")){
				servFoldLoc = newlocation;
				servFoldText.setText(servFoldLoc);
			}
			checkCompletion();
		}
	}
	
	class ClientButton implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String eventName = event.getActionCommand();
			if (eventName.equals("Browse")) {
			}
			String newlocation=folderChooser("Client");
			if (!newlocation.equals("")){
				clientFoldLoc = newlocation;
				clientFoldText.setText(clientFoldLoc);
			}
			checkCompletion();
		}

	}
	
	private String correctCaps(String phrase){
		ArrayList<String> words = new ArrayList<String>();
		String correct = "";
		int oldIndex = 0;
		int spaceIndex = phrase.indexOf(' ');
		while(spaceIndex<phrase.length() && oldIndex<phrase.length() && spaceIndex!=-1){
			words.add(phrase.substring(oldIndex, spaceIndex));
			oldIndex = spaceIndex+1;
			spaceIndex = phrase.indexOf(' ', oldIndex);
		}
		
		
		for(int i=0; i<words.size(); i++){
			String w = words.get(i).toLowerCase();
			w = w.substring(0,1).toUpperCase() + w.substring(1);
			correct += w;
		}
		
		return correct;	
	}
	
	class Register implements ActionListener{

		public void actionPerformed(ActionEvent event){
			clientName = correctCaps(name.getText());
			clientTeamName = correctCaps(teamName.getText());
			try {
				clientWindow.register(servFoldLoc, clientFoldLoc, new ClientProfile(clientName, clientTeamName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}