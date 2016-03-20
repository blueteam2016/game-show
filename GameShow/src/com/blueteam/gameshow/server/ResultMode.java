package com.blueteam.gameshow.server;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.JTextComponent;

import com.blueteam.gameshow.data.Roster;

public class ResultMode extends JPanel{
	private ResultTableModel model;
	private JTable table;
	private Roster rost;
	private JButton back;
	private JButton nextQ;
	
	public ResultMode(Game g, final ServerGameScreen s){
		rost = g.getRoster();
		model = new ResultTableModel(rost);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		
		back = new JButton("Back");
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				s.goToAnswerMode();
			}
		});
		
		nextQ = new JButton("Next Question");
		nextQ.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				s.goToQuestionMode();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(back);
		buttonPanel.add(nextQ);
		
		setLayout(new BorderLayout());
		add(table, BorderLayout.CENTER);
		add(table.getTableHeader(), BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
		
		addComponentListener(new ComponentAdapter(){
			public void ComponentResized(ComponentEvent e){
				resizeText();
			}
		});
	}
	
	public void lastQuestion(){
		nextQ.setEnabled(false);
	}
	
	public void update(){
		model.sort();
		resizeText();
		table.revalidate();
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
		}
		return width;
	}
	
	private void resizeText(){
		int height = getHeight();
		int rowSize = (int) ((double)height/model.getRowCount());

		Font font = table.getFont();
		double widthRatio = ((double)table.getWidth()/2) / ((double)getStringWidth(font));
		int newFontSize = (int)(font.getSize() * widthRatio);
		int componentHeight = table.getRowHeight();
		int fontSizeToUse = Math.min(newFontSize, componentHeight);
		font=new Font(font.getName(), Font.PLAIN, fontSizeToUse);
		if (font.getSize()<12)
			font=new Font(font.getName(), Font.PLAIN, 12);
		table.setFont(font);
		
	}
	
	
}
