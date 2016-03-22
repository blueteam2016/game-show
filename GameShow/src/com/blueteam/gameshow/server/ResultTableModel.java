package com.blueteam.gameshow.server;


import java.util.ArrayList;

import javax.swing.table.*;

import com.blueteam.gameshow.data.Roster;
import com.blueteam.gameshow.data.Team;


public class ResultTableModel extends AbstractTableModel{

	private static final long serialVersionUID = -5708497032334136324L;
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
		for(int i=0; i<teams.length; i++){
			teams[i] = rost.getTeam(i);
		}
		
		for (int i = 0; i < teams.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < teams.length; j++){
                if (teams[j].getPercentage()>teams[index].getPercentage())
                    index = j;
            }
            Team smallerNumber = teams[index]; 
            teams[index] = teams[i];
            teams[i] = smallerNumber;
        }
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		sort();
		if(columnIndex == 0){
			return teams[rowIndex].getName();
		}else{
			return teams[rowIndex].getPercentage()*100 + "%";
		}
	}
}

