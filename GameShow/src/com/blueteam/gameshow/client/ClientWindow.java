package com.blueteam.gameshow.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	private boolean maximized;

	public ClientWindow() {
		
		try {
			if (!OSIdentifier.isUnix()) {
				UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
			} else {
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			}
		} catch (Exception ex) {}
		
		maximized = false;
		
		tabs = new JTabbedPane();
		content = new JPanel();
		content.setLayout( new BorderLayout() );
		frame = new JFrame("GameShow Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowStateListener(new WindowStateListener() {
			   public void windowStateChanged(WindowEvent e) {
				   if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH){
				      maximized = true;
				   } else {
					   maximized = false;
				   }
			   }
		});
		
		rScreen = new RegistrationScreen(this);
		cqScreen = new ClientQuestionScreen(this);
		
		tabs.addTab("Registration", rScreen);
		tabs.addTab("Question", cqScreen);
		tabs.setEnabledAt(tabs.indexOfTab("Question"), false);
		
		content.add(tabs);
		frame.setContentPane(content);
		frame.setSize(450, 250);
		frame.setMinimumSize(new Dimension(450, 350));
		frame.setLocationRelativeTo(null);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/blueteam/gameshow/assets/Trophy.png")));
		frame.setVisible(true);
	}
	
	public void register(String pathServerFold, String  pathClientFold, ClientProfile profile){		
		try {
			clientIO = new ClientIO(pathServerFold, pathClientFold, profile);
		} catch (IOException e) {
			clientIO = null;
			JOptionPane.showMessageDialog(null, "Failed to write client profile! Please check your permissions!", "Permissions Error", JOptionPane.ERROR_MESSAGE);
		}
		if (clientIO != null) {
			cqScreen.register();
			tabs.setEnabledAt(tabs.indexOfTab("Question"), true);
			tabs.setSelectedIndex(tabs.indexOfTab("Question"));
		}
	}
	
	public void update(){
		if (!maximized) {
			Dimension currentSize = frame.getSize();
			Dimension preferredSize = frame.getPreferredSize();
			Dimension newSize = new Dimension();
			newSize.setSize(currentSize.getWidth(), preferredSize.getHeight());
			frame.setSize(newSize);
		}
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
		tabs.setEnabledAt(tabs.indexOfTab("Question"), false);
	}
}
