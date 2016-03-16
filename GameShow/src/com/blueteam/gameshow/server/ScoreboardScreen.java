package com.blueteam.gameshow.server;

import java.awt.BorderLayout;

import javax.swing.*;

public class ScoreboardScreen extends JPanel{
	private ScoreboardTableModel model;
	private JTable table;
	
	public ScoreboardScreen(Game g){
		setLayout(new BorderLayout());
		model = new ScoreboardTableModel(g.getRoster());
		table = new JTable(model);
		
		table.getColumnModel().getColumn(0).setWidth(getWidth()/2);
		table.getColumnModel().getColumn(1).setWidth(getWidth()/2);
		
		table.setFillsViewportHeight(true);
		
		add(table.getTableHeader(), BorderLayout.NORTH);
		add(table, BorderLayout.CENTER);
	}
	
	public void update(){
		model.sort();
		table.revalidate();
		repaint();
	}
}
