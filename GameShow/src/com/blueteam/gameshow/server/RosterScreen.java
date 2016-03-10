package com.blueteam.gameshow.server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RosterScreen extends JPanel{
	private JPanel panel;
	private JScrollPane scroll;
	private JTable table;
	private JButton unregButt, openClose;
	boolean regClosed;
	private ListSelectionModel selectModel;
	private int selectedRow;
	private Game game;
	private RosterTableModel model;
	RosterScreen(Game g){
		game = g;
		model = new RosterTableModel(game.getRoster());
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		table = new JTable(model);
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				selectedRow = table.getSelectedRow();
				unregButt.setEnabled(true);
			}
		});
		scroll = new JScrollPane(table);
		this.add(scroll);
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		unregButt = new JButton("Unregister");
		unregButt.setActionCommand("Unregister");
		unregButt.addActionListener(new Unreg());
		unregButt.setEnabled(false);
		regClosed = true;
		panel.add(unregButt);
		openClose = new JButton("Open Registration");
		openClose.setActionCommand("Open Registration");
		openClose.addActionListener(new OpenClose());
		panel.add(openClose);
		this.add(panel);
		
	}
	class Unreg implements ActionListener{
		public void actionPerformed(ActionEvent event){
			game.getRoster().getTeam(model.getValueAt(selectedRow,1)).unregisterStudent(model.getStudentAt(selectedRow));
			
		}
	}
	class OpenClose implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getActionCommand().equals("Open Registration")){
				model.openRegistration();
				openClose.setText("Close Registration");
				openClose.setActionCommand("Close Registration");
			}
			else if(event.getActionCommand().equals("Close Registration")){
				model.closeRegistration();
				openClose.setText("Open Registration");
				openClose.setActionCommand("Open Registration");
			}
		}
	}
	
	
}
