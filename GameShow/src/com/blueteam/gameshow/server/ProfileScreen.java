package com.blueteam.gameshow.server;

import javax.swing.*; 

import java.awt.*;
import java.awt.event.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.blueteam.gameshow.data.Profile;

public class ProfileScreen extends JPanel{

	private static final long serialVersionUID = 85730199279428197L;
	private JLabel defTimeLabel, servFoldLabel, clientFoldLabel, defQValLabel, qFileLabel, sec, point;
	private JButton qFileBrowser, servFoldBrowser, clientFoldBrowser, confirmButton;
	private JTextArea qFileText, servFoldText, clientFoldText;
	private JSpinner spinnerDefTime, spinnerDefVal;
	private JFileChooser folderChooser;
	private ServerWindow serverWindowParameter;
	private Profile prof;
	private Game game;

	public ProfileScreen(Game g, ServerWindow sw) {
		game = g;
		setLayout(new GridLayout(6,3,10,10));

		prof = g.getProfile();
		serverWindowParameter = sw;

		defTimeLabel = new JLabel("Default Time");
		add(defTimeLabel);
		spinnerDefTime = new JSpinner(new SpinnerNumberModel(30, 1, 999, 1));
		spinnerDefTime.addChangeListener(new setDefaultTime());
		((JSpinner.NumberEditor) spinnerDefTime.getEditor()).getTextField().setFont(((JSpinner.NumberEditor) spinnerDefTime.getEditor()).getTextField().getFont().deriveFont(64f));
		add(spinnerDefTime);
		sec = new JLabel("seconds");
		add(sec);	

		servFoldLabel = new JLabel("Server Output Folder");
		add(servFoldLabel);
		servFoldBrowser = new JButton("Browse");
		servFoldBrowser.addActionListener(new ServerButton());
		add(servFoldBrowser);
		servFoldText = new JTextArea();
		servFoldText.setLineWrap(true);
		servFoldText.setWrapStyleWord(false);
		servFoldText.setEditable(false);
		add(servFoldText);

		clientFoldLabel = new JLabel("Client Output Folder");
		add(clientFoldLabel);
		clientFoldBrowser = new JButton("Browse");
		clientFoldBrowser.addActionListener(new ClientButton());
		add(clientFoldBrowser);
		clientFoldText = new JTextArea();
		clientFoldText.setLineWrap(true);
		clientFoldText.setWrapStyleWord(false);
		clientFoldText.setEditable(false);
		add(clientFoldText);

		defQValLabel = new JLabel("Default Question Value");
		add(defQValLabel);
		spinnerDefVal = new JSpinner(new SpinnerNumberModel(10, 1, 999, 1));
		spinnerDefVal.addChangeListener(new setDefaultValue());
		add(spinnerDefVal);
		point = new JLabel("points");
		add(point);

		qFileLabel = new JLabel("Question File");
		add(qFileLabel);
		qFileBrowser = new JButton("Browse");
		qFileBrowser.addActionListener(new QuestionButton());
		add(qFileBrowser);
		qFileText = new JTextArea();
		qFileText.setLineWrap(true);
		qFileText.setWrapStyleWord(false);
		qFileText.setEditable(false);
		add(qFileText);

		add(new JLabel());

		confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new confirmButtonPressed());
		add(confirmButton);

		if(prof.isComplete()){
			spinnerDefVal.getModel().setValue((Integer)Profile.getDefaultValue());
			spinnerDefTime.getModel().setValue((Integer)Profile.getDefaultTime());
			servFoldText.setText(prof.getServerFolderLoc());
			clientFoldText.setText(prof.getClientFolderLoc());
			qFileText.setText(Profile.getQuestionFileLoc());
		}
	}
	
	public String getName(){
		return "Profile";
	}
	
	public String fileChooser(String title) {

		String folderLoc = "";
		folderChooser = new JFileChooser();
		
		folderChooser.setCurrentDirectory(new java.io.File("."));
	    folderChooser.setDialogTitle(title);
	    folderChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int returnVal = folderChooser.showOpenDialog(folderChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			folderLoc = folderChooser.getSelectedFile().getAbsolutePath();
		}

		return(folderLoc);
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

	class ServerButton implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			servFoldText.setText(folderChooser("Server Directory"));
			prof.setServerFolderLoc(servFoldText.getText());
		}
	}

	class ClientButton implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			clientFoldText.setText(folderChooser("Client Directory"));
			prof.setClientFolderLoc(clientFoldText.getText());
		}
	}
	
	class QuestionButton implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			qFileText.setText(fileChooser("Question File"));
			prof.setQuestionFileLoc(qFileText.getText());
		}
	}
	
	class setDefaultTime implements ChangeListener {
		public void stateChanged(ChangeEvent arg0) {
			prof.setDefaultTime((Integer) spinnerDefTime.getValue());
		}
	}
	
	class setDefaultValue implements ChangeListener {
		public void stateChanged(ChangeEvent arg0) {
			prof.setDefaultValue((Integer) spinnerDefVal.getValue());
		}
	}
	
	class confirmButtonPressed implements ActionListener {
		
		
		public void actionPerformed(ActionEvent event) {			
			prof.setDefaultTime((int)spinnerDefTime.getValue());
			prof.setDefaultValue((int)spinnerDefVal.getValue());
			
			if (!prof.isComplete()){
				JOptionPane.showMessageDialog(null, "Enter all of the required information.");
			}else if (!game.openIO()){
				JOptionPane.showMessageDialog(null, "Server path invalid!");
			}else{
				serverWindowParameter.enableTabs();
			}
		}

	} 
}