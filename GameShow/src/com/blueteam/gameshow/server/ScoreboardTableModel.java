package com.blueteam.gameshow.server;

import javax.swing.table.*;


public class ScoreboardTableModel extends AbstractTableModel{
	Roster rost;
	
	public ScoreboardTableModel(Roster r){
		rost = r;
	}
	
	public int getColumnCount() {
		return 2;
	}
	
	public String getColumnName(int col){
		if(col==0){
			return "Team Name";
		}else{
			return "Score";
		}
	}

	public int getRowCount() {
		return rost.numTeams();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0){
			return rost.getTeam(rowIndex).getName();
		}else{
			return rost.getTeam(rowIndex).getScore();
		}
	}
}
