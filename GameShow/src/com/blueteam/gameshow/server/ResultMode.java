package com.blueteam.gameshow.server;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

import com.blueteam.gameshow.data.Roster;

public class ResultMode extends JPanel{
	private ResultTableModel model;
	private JTable table;
	private JButton back;
	private JButton nextQ;
	
	public ResultMode(Game g, final ServerGameScreen s){
		model = new ResultTableModel(g.getRoster());
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
	}
	
	public void lastQuestion(){
		nextQ.setEnabled(false);
	}
	
	public void update(){
		table.revalidate();
		repaint();
	}
	
	
	
}
