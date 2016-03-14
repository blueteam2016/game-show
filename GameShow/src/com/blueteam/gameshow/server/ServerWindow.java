package com.blueteam.gameshow.server;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ServerWindow implements WindowListener, ChangeListener{
	
	private JTabbedPane tabs;
	private JPanel content;
	private JFrame frame;


	private ProfileScreen pScreen;
	private RosterScreen rosterScreen;
	private ScoreboardScreen sbScreen;
	private ServerGameScreen sgScreen;
	
	private boolean tabsEnabled;
	private Game game;
	
	public ServerWindow(){
		
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
		frame.setVisible(true);
		
	}

	public void enableTabs(){
		boolean error = false;

		try{
			game.createQuiz();
		}catch(Exception e){
			error = true;
			e.printStackTrace();
			JFrame popUp = new JFrame();
			JPanel content = new JPanel();
			content.add(new JLabel("Question file is not valid."));
		}

		if(!tabsEnabled && !error){
			tabsEnabled = true;
			rosterScreen = new RosterScreen(game);
			sbScreen = new ScoreboardScreen(game);
			sgScreen = new ServerGameScreen(game, this);


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
		if(tabsEnabled)
		{
			JTabbedPane p = (JTabbedPane) e.getSource();
			if(p.getSelectedIndex() != tabs.indexOfTab("Game")){
				sgScreen.getServerQuestionMode().stopTimer();
			}else if(sgScreen.onQuestionMode()){
				sgScreen.getServerQuestionMode().startTimer();
			}
			if(p.getSelectedIndex() != tabs.indexOfTab("Roster")){
				rosterScreen.getTableModel().closeRegistration();
			}
		}
	}

}
