package com.blueteam.gameshow.server;


import java.util.ArrayList;

import javax.swing.table.*;

import com.blueteam.gameshow.data.Roster;
import com.blueteam.gameshow.data.Team;


public class ResultTableModel extends AbstractTableModel{
	private Roster rost;
	private ArrayList<Team> teams;

	public ResultTableModel(Roster r){
		rost = r;
		teams = new ArrayList<Team>();
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
		teams.clear();
		
		for(int k=0; k<rost.numTeams(); k++){
			int maxPerc = 0;
			for(int i=1; i<rost.numTeams(); i++){
				if(rost.getTeam(i).getPercentage()>rost.getTeam(maxPerc).getPercentage() && 
						rost.getTeam(i).getPercentage()<teams.get(teams.size()-1).getPercentage()){
					maxPerc = i;
				}
			}
			teams.add(rost.getTeam(maxPerc));
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0){
			return teams.get(rowIndex).getName();
		}else{
			return teams.get(rowIndex).getPercentage();
		}
	}
}

