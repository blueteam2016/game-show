package com.blueteam.gameshow.server;

import javax.swing.table.*;

import com.blueteam.gameshow.data.Roster;
import com.blueteam.gameshow.data.Team;


public class ScoreboardTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 8720799175739592306L;
	Roster rost;
	private Team[] teams;
	
	public ScoreboardTableModel(Roster r){
		rost = r;
		teams = new Team[rost.numTeams()];
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
	
	public void sort(){
		teams = new Team[rost.numTeams()];
		for(int i=0; i<teams.length; i++){
			teams[i] = rost.getTeam(i);
		}
		
		for (int i = 0; i < teams.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < teams.length; j++){
                if (teams[j].getScore()>teams[index].getScore())
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
			return Math.round(teams[rowIndex].getScore());
		}
	}
}
