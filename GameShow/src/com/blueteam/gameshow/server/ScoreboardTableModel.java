package com.blueteam.gameshow.server;

import java.util.ArrayList;

import javax.swing.table.*;

import com.blueteam.gameshow.data.Roster;
import com.blueteam.gameshow.data.Team;


public class ScoreboardTableModel extends AbstractTableModel{
	Roster rost;
	ArrayList<Team> teams;
	
	public ScoreboardTableModel(Roster r){
		rost = r;
		teams = new ArrayList<Team>();
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
		teams.clear();
		
		for(int k=0; k<rost.numTeams(); k++){
			int maxScore = 0;
			for(int i=1; i<rost.numTeams(); i++){
				if(rost.getTeam(i).getScore()>rost.getTeam(maxScore).getScore() && 
						rost.getTeam(i).getScore()<teams.get(teams.size()-1).getScore()){
					maxScore = i;
				}
			}
			teams.add(rost.getTeam(maxScore));
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0){
			return teams.get(rowIndex).getName();
		}else{
			return teams.get(rowIndex).getScore();
		}
	}
}
