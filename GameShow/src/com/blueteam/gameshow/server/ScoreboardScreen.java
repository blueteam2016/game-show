package com.blueteam.gameshow.server;

import javax.swing.*;

public class ScoreboardScreen extends JPanel{
	private ScoreboardTableModel model;
	private JTable table;
	
	public ScoreboardScreen(Game g){
		model = new ScoreboardTableModel(g.getRoster());
		table = new JTable(model);
		
		add(table.getTableHeader());
		add(table);
	}
	
	public void update(){
		table.revalidate();
		repaint();
	}
}
