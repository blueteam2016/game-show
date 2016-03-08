package com.blueteam.gameshow.server;


import javax.swing.table.*;

import com.blueteam.gameshow.data.Roster;


public class ResultTableModel extends AbstractTableModel{
	private Roster rost;

	public ResultTableModel(Roster r){
		rost = r;
	}

	public String getColumnName(int col){
		if(col==0){
			return "Team Name";
		}else{
			return "Percentage";
		}
	}

	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		return rost.numTeams();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0){
			return rost.getTeam(rowIndex).getName();
		}else{
			return rost.getTeam(rowIndex).getPercentage();
		}
	}
}

