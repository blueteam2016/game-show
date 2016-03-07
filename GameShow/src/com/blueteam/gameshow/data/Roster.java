package com.blueteam.gameshow.data;
import java.util.ArrayList;

public class Roster {
	private ArrayList<Team> teams;

	private boolean questionRunning;
	
	public Roster(){
		teams = new ArrayList<Team>();
		questionRunning = false;
	}
	
	public Team getTeam(int t){
		return teams.get(t);
	}
	
	public Team getTeam(String teamName){
		for(int i=0; i<teams.size(); i++){
			if(teams.get(i).getName().equals(teamName)){
				return teams.get(i);
			}
		}
		return null;
	}
	
	public int numTeams(){
		return teams.size();
	}
	

	
	public void startQuestion(){
		boolean allResponded;
		
		questionRunning = true;
		while(questionRunning){
			allResponded = true;
			for(int i=0; i<teams.size(); i++){
				if(!teams.get(i).hasEveryoneResponded()){
					allResponded = false;
				}
			}
			if(allResponded){
				endQuestion();
			}
		}
	}
	
	public void endQuestion(){
		questionRunning = false;
		for(int i=0; i<teams.size(); i++){
			teams.get(i).calculateScore();
		}
	}
}
