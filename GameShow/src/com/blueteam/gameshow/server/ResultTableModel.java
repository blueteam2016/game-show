package com.blueteam.gameshow.server;


import java.util.ArrayList;

import javax.swing.table.*;

import com.blueteam.gameshow.data.Roster;
import com.blueteam.gameshow.data.Team;


public class ResultTableModel extends AbstractTableModel{
	private Roster rost;
	private Team[] teams;

	public ResultTableModel(Roster r){
		rost = r;
		teams = new Team[rost.numTeams()];
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

	public void sort(){
		teams = new Team[rost.numTeams()];
		ArrayList<Team> tempTeams = rost.getTeams();
		
		for(int k=0; k<teams.length; k++){
			int maxPerc = 0;
			for(int i=1; i<teams.length; i++){
				if(tempTeams.get(i).getPercentage()>tempTeams.get(maxPerc).getPercentage()){
					maxPerc = i;
				}
			}
			teams[k] = tempTeams.remove(maxPerc);
		}
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0){
			return teams[rowIndex].getName();
		}else{
			return teams[rowIndex].getPercentage();
		}
	}
}

