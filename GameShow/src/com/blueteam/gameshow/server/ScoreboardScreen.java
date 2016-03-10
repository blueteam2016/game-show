package com.blueteam.gameshow.server;

import javax.swing.*;
import javax.swing.JTable;

public class ScoreboardScreen extends JPanel{
	private ScoreboardTableModel model;
	private JTable table;
	
	public ScoreboardScreen(Game g){
		model = new ScoreboardTableModel(g.getRoster());
		table = new JTable(model);
		
		table.getColumnModel().getColumn(0).setWidth(getWidth()/2);
		table.getColumnModel().getColumn(1).setWidth(getWidth()/2);
		
		table.setFillsViewportHeight(true);
		
		add(table.getTableHeader());
		add(table);
	}
	
	public void update(){
		table.revalidate();
		repaint();
	}
}
