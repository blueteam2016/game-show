package com.blueteam.gameshow.server;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class ServerWindow {
	
	private JTabbedPane tabs;
	private JPanel content;
	private JFrame frame;


	private ProfileScreen pScreen;
	private RosterScreen rosterScreen;
	private ScoreboardScreen sbScreen;
	private ServerGameScreen sgScreen;
	
	public ServerWindow(){
		
		tabs=new JTabbedPane();
		content=new JPanel();
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pScreen=new ProfileScreen(game, this);
		rosterScreen=new RosterScreen(game);
		sbScreen=new ScoreboardScreen(game);
		sgScreen=new ServerGameScreen(game);
		
		tabs.addTab("Profile", pScreen);
		tabs.addTab("Roster",rosterScreen);
		tabs.addTab("Scoreboard", sbScreen);
		tabs.addTab("Game", sgScreen);
		tabs.setEnabled(false);
		
		content.add(tabs);
		frame.setContentPane(content);
		frame.pack();
		frame.setVisible(true);
		
	}

	void enableTabs(){
		tabs.setEnabled(true);
	}

}
