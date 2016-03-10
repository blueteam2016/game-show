package com.blueteam.gameshow.server;
//hi helen
import javax.swing.*;
import javax.swing.JTable;

public class ScoreboardScreen extends JPanel{
	private ScoreboardTableModel model;
	private JTable table;
	
	public ScoreboardScreen(Game g){
		model = new ScoreboardTableModel(g.getRoster());
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.doLayout();
		table.setFillsViewportHeight(true);
		
		add(table.getTableHeader());
		add(table);
	}
	
	public void update(){
		table.revalidate();
		repaint();
	}
}
