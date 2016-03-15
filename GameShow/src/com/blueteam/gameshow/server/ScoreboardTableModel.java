package com.blueteam.gameshow.server;

import java.util.ArrayList;

import javax.swing.table.*;

import com.blueteam.gameshow.data.Roster;
import com.blueteam.gameshow.data.Team;


public class ScoreboardTableModel extends AbstractTableModel{
	Roster rost;
	Team[] teams;
	
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
		ArrayList<Team> tempTeams = rost.getTeams();
		
		for(int k=0; k<teams.length; k++){
			int maxScore = 0;
			for(int i=1; i<teams.length; i++){
				if(tempTeams.get(i).getScore()>tempTeams.get(maxScore).getScore()){
					maxScore = i;
				}
			}
			teams[k] = tempTeams.remove(maxScore);
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0){
			return teams[rowIndex].getName();
		}else{
			return teams[rowIndex].getScore();
		}
	}
}
