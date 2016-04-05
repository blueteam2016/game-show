package com.blueteam.gameshow.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;

import com.blueteam.gameshow.data.Roster;

public class ScoreboardScreen extends JPanel{

	private static final long serialVersionUID = -1295052599543379410L;
	private ScoreboardTableModel model;
	private JTable table;
	private Roster rost;
	

	int rowSize = 30;
	
	public ScoreboardScreen(Game g){
		rost = g.getRoster();
		model = new ScoreboardTableModel(rost);
		table = new JTable(model);
		table.setSize(new Dimension(200,200));
		table.setTableHeader(new JTableHeader(table.getColumnModel()) {

			private static final long serialVersionUID = -2390893737995062836L;

			@Override public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = rowSize/3;
				return d;
			}
		});
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				table.clearSelection();
			}
		});
		
		setLayout(new BorderLayout());
		add(table, BorderLayout.CENTER);
		add(table.getTableHeader(), BorderLayout.NORTH);
		
		addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e){
				resizeText();
			}
		});
	}
	
	public void update(){
		model.sort();
		table.revalidate();
		resizeText();
		repaint();
	}
	
	private int getStringWidth(Font f){
		int width = 0;
		
		for(int i=0; i<rost.numTeams(); i++){
			String text = rost.getTeam(i).getName();      		
			int stringWidth = table.getFontMetrics(f).stringWidth(text);
			if(stringWidth>width){
				width = stringWidth;
			}
			text = (String) (model.getValueAt(i, 1) + "");      		
			stringWidth = table.getFontMetrics(f).stringWidth(text);
			if(stringWidth>width){
				width = stringWidth;
			}
		}
		
		if(width<1){
			width = 1;
		}
		
		return (int) (width * 1.25);
	}
	
	private void resizeText(){
		int rowCount = model.getRowCount();
		if(rowCount< 1){
			rowCount = 1;
		}
		rowSize = (int) ((double)table.getHeight()/(double)rowCount);
		int tableRowSize = rowSize;
		if (rowSize < 1)
			rowSize = 1;
		if(rost.numTeams() < 2){
			table.setRowHeight((rowSize + 2)/3);
			tableRowSize = (rowSize + 2)/3;
		}else{
			table.setRowHeight(rowSize);
		}
		
		Font font = table.getFont();	
		double widthRatio = ((double)table.getWidth()/2) / ((double)getStringWidth(font));
		int newFontSize = (int)(font.getSize() * widthRatio);
		newFontSize = Math.min(newFontSize, tableRowSize - 5);
		font=new Font(font.getName(), Font.PLAIN, newFontSize);
		if (font.getSize()<12)
			font=new Font(font.getName(), Font.PLAIN, 12);
		table.setFont(font);
		
		int stringWidth = table.getFontMetrics(font).stringWidth("Team Name");
		widthRatio = ((double)table.getWidth()/2) / ((double)stringWidth*1.25);
		newFontSize = (int)(font.getSize() * widthRatio);
		newFontSize = Math.min(newFontSize, rowSize/3 - 5);
		font=new Font(font.getName(), Font.PLAIN, newFontSize);
		table.getTableHeader().setFont(font);

	}
}
