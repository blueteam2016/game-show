package com.blueteam.gameshow.server;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ServerWindow implements WindowListener, ChangeListener{
	
	private JTabbedPane tabs;
	private JPanel content;
	private static JFrame frame;


	private ProfileScreen pScreen;
	private RosterScreen rosterScreen;
	private ScoreboardScreen sbScreen;
	private ServerGameScreen sgScreen;
	
	private boolean tabsEnabled;
	private Game game;
	
	public ServerWindow(){

		try {
			//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) { }
		
		tabsEnabled = false;
		game = new Game();
		
		tabs = new JTabbedPane();
		tabs.addChangeListener(this);
		
		content = new JPanel(new BorderLayout());
		frame = new JFrame("GameShow Server");
		frame.addWindowListener(this);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		pScreen = new ProfileScreen(game, this);
		tabs.add(pScreen, 0);
		tabs.setSelectedIndex(0);
			
		content.add(tabs, BorderLayout.CENTER);
		frame.setContentPane(content);
		frame.pack();
		frame.setSize(450,550);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

	public void enableTabs(){
		boolean error = false;

		try{
			game.createQuiz();
		}catch(Exception e){
			error = true;
			JDialog popUp = new JDialog(frame);
			JPanel content = new JPanel();
			content.add(new JLabel("The Question File is invalid"));
			popUp.setModal(true);
			popUp.setTitle("!ERROR!");
			popUp.add(content);
			popUp.pack();
			popUp.setLocationRelativeTo(frame);
			popUp.setVisible(true);
		}

		if(!tabsEnabled && !error){
			tabsEnabled = true;
			sgScreen = new ServerGameScreen(game, this);
			game.createRoster(sgScreen);
			rosterScreen = new RosterScreen(game);
			sbScreen = new ScoreboardScreen(game);

			tabs.addTab("Roster",rosterScreen);
			tabs.addTab("Scoreboard", sbScreen);
			tabs.addTab("Game", sgScreen.getCurrentMode());
		}
	}
	
	public void update(){
		int i = tabs.indexOfTab("Game");
		
		tabs.setComponentAt(i,sgScreen.getCurrentMode());
		tabs.getComponentAt(i).repaint();
	}
	
	public static void main(String args[]) {
		new ServerWindow();
	}

	public void windowActivated(WindowEvent arg0) {
	}

	public void windowClosed(WindowEvent arg0) {
		//System.out.println("WINDOW CLOSED");
		game.getProfile().saveProfile();
		game.destroy();
		System.exit(0);
	}

	public void windowClosing(WindowEvent arg0) {
	}

	public void windowDeactivated(WindowEvent arg0) {
	}

	public void windowDeiconified(WindowEvent arg0) {		
	}

	public void windowIconified(WindowEvent arg0) {	
	}

	public void windowOpened(WindowEvent arg0) {		
	}



	public void stateChanged(ChangeEvent e) {
		//System.out.println("CHANGED");
		if(tabsEnabled) {
			JTabbedPane p = (JTabbedPane) e.getSource();
			if(p.getSelectedIndex() != tabs.indexOfTab("Game")){
				sgScreen.getServerQuestionMode().stopTimer();
			} else if(sgScreen.onQuestionMode()){
				sgScreen.getServerQuestionMode().startTimer();
			} else{
				sgScreen.resizeResult();
			}
			if(p.getSelectedIndex() != tabs.indexOfTab("Roster")){
				rosterScreen.closeRegistration();
			}
			if(p.getSelectedIndex() == tabs.indexOfTab("Scoreboard")){
				sbScreen.update();
			}
		}
	}
	
	public static JFrame accessFrame() {
		return frame;
	}

}
