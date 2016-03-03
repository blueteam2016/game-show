package com.blueteam.gameshow.server;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ServerWindow {
	
	JTabbedPane tabs;
	JPanel content;
	JFrame frame;


	ProfileScreen pScreen;
	RosterScreen rosterScreen;
	ScoreboardScreen sbScreen;
	ServerGameScreen sgScreen;
	
	ServerWindow(){
		
		tabs=new JTabbedPane();
		content=new JPanel();
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pScreen=new ProfileScreen(game, this);
		rosterScreen=new RosterScreen(game);
		sbScreen=new ScoreboardScreen(game);
		sgScreen=new ServerGameScreen(game);
		
	}

	void enableTabs(){
		tabs.addTab("Profile", pScreen);
		tabs.addTab("Roster",rosterScreen);
		tabs.addTab("Scoreboard", sbScreen);
		tabs.addTab("Game", sgScreen);
		
		content.add(tabs);
		frame.setContentPane(content);
		frame.pack();
		frame.setVisible(true);
	}

}
