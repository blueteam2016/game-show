package com.blueteam.gameshow.server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class RosterScreen extends JPanel implements TableModelListener{
	private static final long serialVersionUID = 8457546251705691933L;
	private JPanel panel;
	private JScrollPane scroll;
	private JTable table;
	private JButton unregButt, openClose;
	//private ListSelectionModel selectModel;
	private int[] selectedRows;
	private Game game;
	private RosterTableModel model;
	boolean open = false;
	
	RosterScreen(Game g){
		game = g;
		model = new RosterTableModel(game);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		table = new JTable(model);
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				if(open){
					table.clearSelection();
				}else{
					selectedRows = table.getSelectedRows();
					unregButt.setEnabled(true);
				}
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
		panel.add(unregButt);
		openClose = new JButton("Open Registration");
		openClose.setActionCommand("Open Registration");
		openClose.addActionListener(new OpenClose());
		panel.add(openClose);
	
		this.add(panel);
		
	}
	
	public RosterTableModel getTableModel(){
		return model;
	}
	
	class Unreg implements ActionListener{
		public void actionPerformed(ActionEvent event){
			for (int row : selectedRows)
				game.getRoster().getTeam(model.getValueAt(row,1)).unregisterStudent(model.getStudentAt(row));
			model.dataChanged();
			unregButt.setEnabled(false);
			table.clearSelection();		
		}
	}
	
	class OpenClose implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getActionCommand().equals("Open Registration")){
				open = true;
				model.openRegistration();
				openClose.setText("Close Registration");
				openClose.setActionCommand("Close Registration");
				unregButt.setEnabled(false);
				table.clearSelection();
				
			}
			else if(event.getActionCommand().equals("Close Registration")){	
				closeRegistration();			
			}
		}
	}
	
	public void closeRegistration(){
		open = false;
		model.closeRegistration();
		openClose.setText("Open Registration");
		openClose.setActionCommand("Open Registration");
	}
	
	public void tableChanged(TableModelEvent e){
		table.revalidate();
		repaint();
	}
	
}
