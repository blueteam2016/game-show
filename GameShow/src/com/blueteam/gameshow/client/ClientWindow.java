package com.blueteam.gameshow.client;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.blueteam.gameshow.data.ClientProfile;
import com.blueteam.gameshow.data.OSIdentifier;

public class ClientWindow {
	
	private JTabbedPane tabs;
	private JPanel content;
	private JFrame frame;
	
	private ClientIO clientIO;
	private RegistrationScreen rScreen;
	private ClientQuestionScreen cqScreen;

	public ClientWindow() {
		
		try {
			if (!OSIdentifier.isUnix()) {
				UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
			} else {
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			}
		} catch (Exception ex) {}
		
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
		
		content.add(tabs);
		frame.setContentPane(content);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	public void register(String pathServFold, String  pathClientFold, ClientProfile profile) throws IOException {
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

	public void reset() {
		clientIO = null;
		tabs.setSelectedIndex(tabs.indexOfTab("Registration"));
		tabs.setEnabled(false);
	}
}
