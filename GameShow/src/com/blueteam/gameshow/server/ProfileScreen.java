package com.blueteam.gameshow.server;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.blueteam.gameshow.data.Profile;

public class ProfileScreen extends JPanel{

	private static final long serialVersionUID = 85730199279428197L;
	private JLabel defTimeLabel, servFoldLabel, clientFoldLabel, defQValLabel, qFileLabel, sec, point;
	private JButton qFileBrowser, servFoldBrowser, clientFoldBrowser, confirmButton;
	private JTextArea qFileText, serverFoldText, clientFoldText;
	private JSpinner spinnerDefTime, spinnerDefVal;
	private ServerWindow serverWindow;
	private Profile prof;
	private Game game;
	private boolean IOpathsChanged;

	public ProfileScreen(Game g, ServerWindow sw) {
		game = g;
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		Font font = new Font(this.getFont().getName(), Font.PLAIN, 16);

		prof = g.getProfile();
		serverWindow = sw;
		
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
		defTimeLabel.setFont(font);
		add(defTimeLabel, constr);
		game.getProfile();
		spinnerDefTime = new JSpinner(new SpinnerNumberModel(Profile.getDefaultTime(), 1, 999, 1));
		spinnerDefTime.addChangeListener(new setDefaultTime());
		((JSpinner.NumberEditor) spinnerDefTime.getEditor()).getTextField().setFont(((JSpinner.NumberEditor) spinnerDefTime.getEditor()).getTextField().getFont().deriveFont(24f));
		constr.gridx = 1;
		add(spinnerDefTime, constr);
		sec = new JLabel("seconds");
		sec.setFont(font);
		constr.gridx = 2;
		add(sec, constr);	

		
		servFoldLabel = new JLabel("Server Output Folder");
		servFoldLabel.setFont(font);
		constr.gridx = 0;
		constr.gridy = 1;
		add(servFoldLabel, constr);
		servFoldBrowser = new JButton("Browse");
		servFoldBrowser.setFont(font);
		servFoldBrowser.addActionListener(new ServerButton());
		constr.gridx = 1;
		add(servFoldBrowser, constr);
		serverFoldText = new JTextArea(prof.getServerFolderLoc());
		serverFoldText.setLineWrap(true);
		serverFoldText.setWrapStyleWord(false);
		serverFoldText.setEditable(false);
		serverFoldText.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
		serverFoldText.setBackground(getBackground());
		constr.gridx = 2;
		add(serverFoldText, constr);

		clientFoldLabel = new JLabel("Client Output Folder");
		clientFoldLabel.setFont(font);
		constr.gridx = 0;
		constr.gridy = 2;
		add(clientFoldLabel, constr);
		clientFoldBrowser = new JButton("Browse");
		clientFoldBrowser.setFont(font);
		clientFoldBrowser.addActionListener(new ClientButton());
		constr.gridx = 1;
		add(clientFoldBrowser, constr);
		clientFoldText = new JTextArea(prof.getClientFolderLoc());
		clientFoldText.setLineWrap(true);
		clientFoldText.setWrapStyleWord(false);
		clientFoldText.setEditable(false);
		clientFoldText.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
		clientFoldText.setBackground(getBackground());
		constr.gridx = 2;
		add(clientFoldText, constr);

		defQValLabel = new JLabel("Default Question Value");
		defQValLabel.setFont(font);
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
		point.setFont(font);
		constr.gridx = 2;
		add(point, constr);

		qFileLabel = new JLabel("Question File");
		qFileLabel.setFont(font);
		constr.gridx = 0;
		constr.gridy = 4;
		add(qFileLabel, constr);
		qFileBrowser = new JButton("Browse");
		qFileBrowser.setFont(font);
		qFileBrowser.addActionListener(new QuestionButton());
		constr.gridx = 1;
		add(qFileBrowser, constr);
		qFileText = new JTextArea(Profile.getQuestionFileLoc());
		qFileText.setLineWrap(true);
		qFileText.setWrapStyleWord(false);
		qFileText.setEditable(false);
		qFileText.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
		qFileText.setBackground(getBackground());
		constr.gridx = 2;
		add(qFileText, constr);
		
		confirmButton = new JButton("Confirm");
		confirmButton.setFont(font);
		confirmButton.addActionListener(new confirmButtonPressed());
		constr.gridx = 1;
		constr.gridy = 5;
		add(confirmButton, constr);
		IOpathsChanged = false;
	}
	
	public String getName(){
		return "Profile";
	}
	
	public String fileChooser(String title, String description, String[] extensions) {

		String fileLoc = "";
		JFileChooser fileChooser = new JFileChooser(new java.io.File("."));
	    fileChooser.setDialogTitle(title);
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    if (extensions != null) {
	    	FileNameExtensionFilter extfilter = new FileNameExtensionFilter(description, extensions);
	    	fileChooser.setFileFilter(extfilter);
	    }
		int returnVal = fileChooser.showOpenDialog(fileChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			fileLoc = fileChooser.getSelectedFile().getAbsolutePath();
		}

		return fileLoc;
	}
	
	private String folderChooser(String title) {
		String folderLoc = "";
		JFileChooser folderChooser = new JFileChooser(new java.io.File("."));
		
	    folderChooser.setDialogTitle(title);
	    folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnVal = folderChooser.showOpenDialog(folderChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			folderLoc = folderChooser.getSelectedFile().getAbsolutePath() + File.separator;
		}
		return folderLoc;

	}

	private class ServerButton implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String serverLoc = folderChooser("Server Directory");
			if (!serverLoc.equals("")) {
				IOpathsChanged = true;
				serverFoldText.setText(serverLoc);
				prof.setServerFolderLoc(serverFoldText.getText());
				serverWindow.update();
			}
		}
	}

	private class ClientButton implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String clientLoc = folderChooser("Client Directory");
			if (!clientLoc.equals("")) {
				IOpathsChanged = true;
				clientFoldText.setText(clientLoc);
				prof.setClientFolderLoc(clientFoldText.getText());
				serverWindow.update();
			}
		}
	}
	
	private class QuestionButton implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String questLoc = fileChooser("Question File", "XML files (*.xml)", new String[]{"xml"});
			if (!questLoc.equals("")) {
				qFileText.setText(questLoc);
				prof.setQuestionFileLoc(qFileText.getText());
				serverWindow.update();
			}
		}
	}
	
	private class setDefaultTime implements ChangeListener {
		public void stateChanged(ChangeEvent arg0) {
			prof.setDefaultTime((Integer) spinnerDefTime.getValue());
		}
	}
	
	private class setDefaultValue implements ChangeListener {
		public void stateChanged(ChangeEvent arg0) {
			prof.setDefaultValue((Integer) spinnerDefVal.getValue());
		}
	}
	
	private class confirmButtonPressed implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {			
			prof.setDefaultTime((int)spinnerDefTime.getValue());
			prof.setDefaultValue((int)spinnerDefVal.getValue());
			
			if (!prof.isComplete()) {
				JOptionPane.showMessageDialog(null, "Please enter all of the required information.");
				return;
			} else if (!Files.exists(Paths.get(prof.getClientFolderLoc()))) {
				JOptionPane.showMessageDialog(null, "Client path invalid!");
				return;
			} else if(!Files.exists(Paths.get(prof.getServerFolderLoc()))) {
				JOptionPane.showMessageDialog(null, "Server path invalid!");
				return;
			} else if (game.isRunning() && !new NewGamePopUp().getChoice()) {
				return;
			} else if ((!game.isIOOpen() || IOpathsChanged) && !game.openIO()) {
				JOptionPane.showMessageDialog(null, "Server failed to initialize!");
				return;
			} else {
				IOpathsChanged = false;
				serverWindow.enableTabs();
			}
			
		}
	}
	
	private class NewGamePopUp extends PopUp {
		
		@Override
		protected String messageText() {
			return "<html>There is already a game running. Are you sure you wish to start a new game?</html>";
		}

		@Override
		protected void yes() {
			choice = true;
			dispose();
		}

		@Override
		protected void no() {
			choice = false;
			dispose();
		}
		
		@Override
		public void windowActivated(WindowEvent e) {}
		@Override
		public void windowClosed(WindowEvent e) {}
		@Override
		public void windowClosing(WindowEvent e) {}
		@Override
		public void windowDeactivated(WindowEvent e) {}
		@Override
		public void windowDeiconified(WindowEvent e) {}
		@Override
		public void windowIconified(WindowEvent e) {}
		@Override
		public void windowOpened(WindowEvent e) {}
		
	}
	
}