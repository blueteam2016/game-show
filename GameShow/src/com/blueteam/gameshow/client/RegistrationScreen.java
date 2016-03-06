package com.blueteam.gameshow.client;
import javax.swing.*; 
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.ArrayList;
import java.awt.Graphics;
import java.io.*;
import java.awt.event.*;

import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

public class RegistrationScreen extends JPanel{

	private JLabel NameLabel, teamNameLabel, ServerOutputLabel, ClienOutputLabel, ServerOutputFolder, ClientOutputLabel;
	private JButton servFoldBrowser, clientFoldBrowser;
	private JButton Register;
	private JTextField Name, teamName, servFoldText, clientFoldText;
	private String clientName, clientTeamName, folderLoc, servFoldLoc, clientFoldLoc;
	ClientProfile profile;
	JFileChooser folderChooser;
	JLabel infoPrompt=new JLabel("");
	
	public RegistrationScreen(/*ClientProfile newprofile*/){
		//profile=newprofile;
		this.setLayout(new GridLayout(0,3,10,10));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 5));

		JLabel BlankSpace=new JLabel("");
		NameLabel = new JLabel("Name: ");
		this.add(NameLabel);
		Name=new JTextField("");
		this.add(Name);
		this.add(BlankSpace);

		JLabel BlankSpace2=new JLabel("");
		teamNameLabel = new JLabel("Team Name:");
		this.add(teamNameLabel);
		teamName=new JTextField("");
		this.add(teamName);
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
		JLabel BlankSpace4=new JLabel("");
		this.add(BlankSpace4);
		Register=new JButton("Register");
		Register.addActionListener(new Register());
		this.add(Register);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				runGUI();
			}
		});
	}

	private static void runGUI() {
		JFrame frame = new JFrame("RegistrationScreen");
		frame.add(new RegistrationScreen());

		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.pack();
		frame.setVisible(true);    
	}

	private String fileChooser() {

		folderChooser = new JFileChooser();

		int returnVal = folderChooser.showOpenDialog(folderChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			folderLoc = folderChooser.getSelectedFile().getAbsolutePath();
		}
		return(folderLoc);

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
		}
	}class ClientButton implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String eventName = event.getActionCommand();
			if (eventName.equals("Browse")) {
				System.out.println("Browse for client output folder");
			}

			clientFoldLoc = fileChooser();
			clientFoldText.setText(clientFoldLoc);
			System.out.println("clientFoldloc: " + clientFoldLoc);
		}

	}class Register implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			clientName=Name.getText();
			clientTeamName=teamName.getText();
			if (!clientName.equals("")&&!clientTeamName.equals("")&&!servFoldLoc.equals("")&&!clientFoldLoc.equals("")){
				profile.setPlayerName(clientName);
				profile.setTeamName(clientTeamName);
			}else{
				final JFrame popup=new JFrame("Error");
				JPanel panel=new JPanel();
				JLabel label=new JLabel("Please Enter all the required information");
				JButton close=new JButton("Close");
				
				panel.setLayout(new GridLayout(0,1));
				panel.add(label);
				panel.add(close);
				close.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						popup.dispose();
					}				
				});
				popup.add(panel);
				
				popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				popup.pack();
				popup.setVisible(true); 
			}
		}
	}
}