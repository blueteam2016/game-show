package com.blueteam.gameshow.server;

import javax.swing.*; 

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.blueteam.gameshow.data.Profile;

public class ProfileScreen extends JPanel{

	private static final long serialVersionUID = 85730199279428197L;
	private JLabel defTimeLabel, servFoldLabel, clientFoldLabel, defQValLabel, qFileLabel, sec, point;
	private JButton qFileBrowser, servFoldBrowser, clientFoldBrowser, confirmButton;
	private JTextArea qFileText, serverFoldText, clientFoldText;
	private JSpinner spinnerDefTime, spinnerDefVal;
	private ServerWindow serverWindowParameter;
	private Profile prof;
	private Game game;

	public ProfileScreen(Game g, ServerWindow sw) {
		game = g;
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		prof = g.getProfile();
		serverWindowParameter = sw;
		
		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 0;
		constr.gridwidth = 1;
		constr.gridheight = 1;
		constr.anchor = GridBagConstraints.WEST;
		constr.insets = new Insets(0,0,10,10);
        constr.weightx = 1.0;
        constr.fill = GridBagConstraints.HORIZONTAL;

		defTimeLabel = new JLabel("Default Time");
		add(defTimeLabel, constr);
		game.getProfile();
		spinnerDefTime = new JSpinner(new SpinnerNumberModel(Profile.getDefaultTime(), 1, 999, 1));
		spinnerDefTime.addChangeListener(new setDefaultTime());
		((JSpinner.NumberEditor) spinnerDefTime.getEditor()).getTextField().setFont(((JSpinner.NumberEditor) spinnerDefTime.getEditor()).getTextField().getFont().deriveFont(24f));
		constr.gridx = 1;
		add(spinnerDefTime, constr);
		sec = new JLabel("seconds");
		constr.gridx = 2;
		add(sec, constr);	

		
		servFoldLabel = new JLabel("Server Output Folder");
		constr.gridx = 0;
		constr.gridy = 1;
		add(servFoldLabel, constr);
		servFoldBrowser = new JButton("Browse");
		servFoldBrowser.addActionListener(new ServerButton());
		constr.gridx = 1;
		add(servFoldBrowser, constr);
		serverFoldText = new JTextArea(prof.getServerFolderLoc());
		serverFoldText.setLineWrap(true);
		serverFoldText.setWrapStyleWord(false);
		serverFoldText.setEditable(false);
		constr.gridx = 2;
		add(serverFoldText, constr);

		clientFoldLabel = new JLabel("Client Output Folder");
		constr.gridx = 0;
		constr.gridy = 2;
		add(clientFoldLabel, constr);
		clientFoldBrowser = new JButton("Browse");
		clientFoldBrowser.addActionListener(new ClientButton());
		constr.gridx = 1;
		add(clientFoldBrowser, constr);
		clientFoldText = new JTextArea(prof.getClientFolderLoc());
		clientFoldText.setLineWrap(true);
		clientFoldText.setWrapStyleWord(false);
		clientFoldText.setEditable(false);
		constr.gridx = 2;
		add(clientFoldText, constr);

		defQValLabel = new JLabel("Default Question Value");
		constr.gridx = 0;
		constr.gridy = 3;
		add(defQValLabel, constr);
		game.getProfile();
		spinnerDefVal = new JSpinner(new SpinnerNumberModel(Profile.getDefaultValue(), 1, 999, 1));
		spinnerDefVal.addChangeListener(new setDefaultValue());
		((JSpinner.NumberEditor) spinnerDefVal.getEditor()).getTextField().setFont(((JSpinner.NumberEditor) spinnerDefVal.getEditor()).getTextField().getFont().deriveFont(24f));
		constr.gridx = 1;
		add(spinnerDefVal, constr);
		point = new JLabel("points");
		constr.gridx = 2;
		add(point, constr);

		qFileLabel = new JLabel("Question File");
		constr.gridx = 0;
		constr.gridy = 4;
		add(qFileLabel, constr);
		qFileBrowser = new JButton("Browse");
		qFileBrowser.addActionListener(new QuestionButton());
		constr.gridx = 1;
		add(qFileBrowser, constr);
		qFileText = new JTextArea(Profile.getQuestionFileLoc());
		qFileText.setLineWrap(true);
		qFileText.setWrapStyleWord(false);
		qFileText.setEditable(false);
		constr.gridx = 2;
		add(qFileText, constr);
		
		confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new confirmButtonPressed());
		constr.gridx = 1;
		constr.gridy = 5;
		add(confirmButton, constr);
	}
	
	public String getName(){
		return "Profile";
	}
	
	public String fileChooser(String title) {

		String fileLoc = "";
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setCurrentDirectory(new java.io.File("."));
	    fileChooser.setDialogTitle(title);
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int returnVal = fileChooser.showOpenDialog(fileChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			fileLoc = fileChooser.getSelectedFile().getAbsolutePath();
		}

		return fileLoc;
	}
	
	private String folderChooser(String title) {
		String folderLoc = "";
		JFileChooser folderChooser = new JFileChooser();
	
		folderChooser.setCurrentDirectory(new java.io.File("."));
	    folderChooser.setDialogTitle(title);
	    folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnVal = folderChooser.showOpenDialog(folderChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			folderLoc = folderChooser.getSelectedFile().getAbsolutePath() + File.separator;
		}
		return folderLoc;

	}

	class ServerButton implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String serverLoc = folderChooser("Server Directory");
			if (!serverLoc.equals("")) {
				serverFoldText.setText(serverLoc);
				prof.setServerFolderLoc(serverFoldText.getText());
			}
		}
	}

	class ClientButton implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String clientLoc = folderChooser("Client Directory");
			if (!clientLoc.equals("")) {
				clientFoldText.setText(clientLoc);
				prof.setClientFolderLoc(clientFoldText.getText());
			}
		}
	}
	
	class QuestionButton implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String questLoc = fileChooser("Question File");
			if (!questLoc.equals("")) {
				qFileText.setText(questLoc);
				prof.setQuestionFileLoc(qFileText.getText());
			}
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
				JOptionPane.showMessageDialog(null, "Please enter all of the required information.");
			}else {
				if (!Files.exists(Paths.get(prof.getClientFolderLoc()))) {
					JOptionPane.showMessageDialog(null, "Client path invalid!");
				} else if(!Files.exists(Paths.get(prof.getServerFolderLoc()))) {
					JOptionPane.showMessageDialog(null, "Server path invalid!");
				} else if (!game.openIO()){
					JOptionPane.showMessageDialog(null, "Server failed to initialize!");
				}else{
					serverWindowParameter.enableTabs();
				}
			}
		}

	} 
}