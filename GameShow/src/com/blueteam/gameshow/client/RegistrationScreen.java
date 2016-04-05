package com.blueteam.gameshow.client;

import javax.swing.*; 

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.blueteam.gameshow.data.ClientProfile;

public class RegistrationScreen extends JPanel{

	private static final long serialVersionUID = -4547016510843530603L;
	private ClientWindow clientWindow;
	private JLabel nameLabel, teamNameLabel, serverOutputLabel, clientOutputLabel;
	private JButton servFoldBrowser, clientFoldBrowser;
	private JButton registerButton;
	private JTextArea name, teamName;
	private JTextArea servFoldText, clientFoldText;
	private String clientName, clientTeamName, servFoldLoc, clientFoldLoc;
	private JFileChooser folderChooser;
	public RegistrationScreen(ClientWindow newclientWindow){
		clientName = "";
		clientTeamName = "";
		servFoldLoc = "";
		clientFoldLoc = "";
		clientWindow = newclientWindow;
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		Font font = new Font(Font.DIALOG, Font.PLAIN, 16);
		
		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 0;
		constr.gridwidth = 1;
		constr.gridheight = 1;
		constr.anchor = GridBagConstraints.WEST;
		constr.insets = new Insets(0,0,10,10);
        constr.weightx = 1.0;
        constr.fill = GridBagConstraints.HORIZONTAL;
        
        DocumentFilter filter = new DocumentFilter() {
        	@Override
	        public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
                if ((fb.getDocument().getLength() + str.length()) <= 200) {
                	str = str.replaceAll("[^A-Za-z0-9 ]", "");
                    fb.insertString(offs, str, a);
                } else {
                    int spaceLeft = 200 - fb.getDocument().getLength();
                    if (spaceLeft <= 0)
                        return;

                    str = str.substring(0, spaceLeft);
                	str = str.replaceAll("[^A-Za-z0-9 ]", "");
                    fb.insertString(offs, str, a);
                }
            }

            @Override
	        public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException 
            {
                if ((fb.getDocument().getLength() + str.length() - length) <= 200)
                {
                	str = str.replaceAll("[^A-Za-z0-9 ]", "");
                    fb.replace(offs, length, str, a);
                }
                else
                {
                    int spaceLeft = 200 - fb.getDocument().getLength() + length;
                    if (spaceLeft <= 0)
                        return;
                    
                    str = str.substring(0, spaceLeft);
                	str = str.replaceAll("[^A-Za-z0-9 ]", "");
                    fb.replace(offs, length, str, a);
                }
            }
	    };
        
		nameLabel = new JLabel("Name");
		nameLabel.setFont(font);
		this.add(nameLabel, constr);
		name = new JTextArea("");
		name.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
		name.setLineWrap(true);
		name.setWrapStyleWord(true);
		AbstractDocument nameDoc = (AbstractDocument)name.getDocument();
		nameDoc.addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {}
			public void insertUpdate(DocumentEvent e) {
				clientWindow.update();
				checkCompletion();
			}
			public void removeUpdate(DocumentEvent e) {}
		});
		nameDoc.setDocumentFilter(filter);
		constr.gridx = 1;
		constr.gridwidth = 2;
		this.add(name, constr);


		teamNameLabel = new JLabel("Team Name");
		teamNameLabel.setFont(font);
		constr.gridx = 0;
		constr.gridy = 1;
		constr.gridwidth = 1;
		this.add(teamNameLabel, constr);
		teamName = new JTextArea("");
		teamName.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
		teamName.setLineWrap(true);
		teamName.setWrapStyleWord(true);
		AbstractDocument teamNameDoc = (AbstractDocument)name.getDocument();
		teamNameDoc.addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {}
			public void insertUpdate(DocumentEvent e) {
				clientWindow.update();
				checkCompletion();
			}
			public void removeUpdate(DocumentEvent e) {}
		});
		teamNameDoc.setDocumentFilter(filter);
		constr.gridx = 1;
		constr.gridwidth = 2;
		this.add(teamName, constr);

		serverOutputLabel = new JLabel("Server Output Folder");
		serverOutputLabel.setFont(font);
		constr.gridx = 0;
		constr.gridy = 2;
		constr.gridwidth = 1;
		this.add(serverOutputLabel, constr);
		servFoldBrowser = new JButton("Browse");
		servFoldBrowser.setFont(font);
		servFoldBrowser.addActionListener(new ServerButton());
		constr.gridx = 1;
		this.add(servFoldBrowser, constr);
		servFoldText = new JTextArea("");
		servFoldText.setLineWrap(true);
		servFoldText.setWrapStyleWord(false);
		servFoldText.setEditable(false);
		servFoldText.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
		constr.gridx = 2;
		this.add(servFoldText, constr);


		clientOutputLabel = new JLabel("Client Output Folder");
		clientOutputLabel.setFont(font);
		constr.gridx = 0;
		constr.gridy = 3;
		this.add(clientOutputLabel, constr);
		clientFoldBrowser = new JButton("Browse");
		clientFoldBrowser.setFont(font);
		clientFoldBrowser.addActionListener(new ClientButton());
		constr.gridx = 1;
		this.add(clientFoldBrowser, constr);
		clientFoldText = new JTextArea("");
		clientFoldText.setLineWrap(true);
		clientFoldText.setWrapStyleWord(false);
		clientFoldText.setEditable(false);
		clientFoldText.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
		constr.gridx = 2;
		this.add(clientFoldText, constr);
		
		registerButton = new JButton("Register");
		registerButton.setFont(font);
		registerButton.addActionListener(new Register());
		constr.gridx = 1;
		constr.gridy = 4;
		this.add(registerButton, constr);
		registerButton.setEnabled(false);
	}
	
	private String folderChooser(String title) {
		String folderLoc = "";
		folderChooser = new JFileChooser(new java.io.File("."));
		folderChooser.setDialogTitle(title);
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnVal = folderChooser.showOpenDialog(folderChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			folderLoc = folderChooser.getSelectedFile().getAbsolutePath() + File.separator;
		}
		return folderLoc;

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
			String newlocation = folderChooser("Server");
			if (!newlocation.equals("")){
				servFoldLoc = newlocation;
				servFoldText.setText(servFoldLoc);
				clientWindow.update();
			}
			checkCompletion();
		}
	}

	class ClientButton implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String newlocation = folderChooser("Client");
			if (!newlocation.equals("")){
				clientFoldLoc = newlocation;
				clientFoldText.setText(clientFoldLoc);
				clientWindow.update();
			}
			checkCompletion();
		}

	}

	private static String correctCaps(String phrase){
		phrase += " ";
		
		ArrayList<String> words = new ArrayList<String>();
		String correct = "";
		int oldIndex = 0;
		int spaceIndex = phrase.indexOf(' ');
		while(oldIndex<phrase.length() && spaceIndex!=-1){
			words.add(phrase.substring(oldIndex, spaceIndex));
			oldIndex = spaceIndex+1;
			spaceIndex = phrase.indexOf(' ', oldIndex);
		}
		
		
		for(int i=0; i<words.size(); i++){
			String w = words.get(i).toLowerCase();
			w = w.substring(0,1).toUpperCase() + w.substring(1);
			correct += w + " ";
		}
		
		return correct.substring(0, correct.length() - 1);	
	}

	class Register implements ActionListener{

		public void actionPerformed(ActionEvent event){
			clientName = name.getText();
			clientTeamName = correctCaps(teamName.getText());
			
			if (!Files.exists(Paths.get(servFoldLoc))) {
				JOptionPane.showMessageDialog(null, "Server path invalid!", "Bad Input Error", JOptionPane.ERROR_MESSAGE);
			} else if (!Files.exists(Paths.get(clientFoldLoc))) {
				JOptionPane.showMessageDialog(null, "Client path invalid!", "Bad Input Error", JOptionPane.ERROR_MESSAGE);
			} else if (!Files.exists(Paths.get(servFoldLoc + ".question"))) {
				JOptionPane.showMessageDialog(null, "Server not found!", "Server Error", JOptionPane.ERROR_MESSAGE);
			} else {
				clientWindow.register(servFoldLoc, clientFoldLoc, new ClientProfile(clientName, clientTeamName));
			}
		}
	}
}
