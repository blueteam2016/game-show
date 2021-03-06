package com.blueteam.gameshow.data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import com.blueteam.gameshow.server.ServerGameScreen;

public class Roster implements ActionListener{
	private ArrayList<Team> teams;
	private Timer scanTime;
	private ServerGameScreen servGame;
	
	public Roster(){
		teams = new ArrayList<Team>();
		scanTime = new Timer(100, this);
	}
	
	public void addServGame(ServerGameScreen sgs){
		servGame = sgs;
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
	
	public ArrayList<Team> getTeams(){
		return teams;
	}
	
	public int numTeams(){
		return teams.size();
	}
	
	public void addPlayer(String teamName, Player p){
		boolean found = false;
		checkRepeat(p);
		for(int t=0; t<teams.size() && !found; t++){
			if(teams.get(t).getName().equals(teamName)){
				found = true;
				teams.get(t).addMember(p);
			}
		}
		if(!found){
			addTeam(teamName);
			getTeam(teamName).addMember(p);
		}
	}
	
	private void checkRepeat(Player p){
		//checks if player has already registers(overrides if necessary)
		boolean found = false;
		for(int t=0; t<teams.size() && !found; t++){
			if(teams.get(t).checkRepeat(p)){
				found = true;
			}
		}
	}
	
	private void addTeam(String teamName){
		//adds team to correct alphabetical location	
		boolean found = false;
		for(int i=0; i<teams.size() && !found;i++){
			if(teamName.compareTo(teams.get(i).getName())<0){
				found = true;
				teams.add(i, new Team(teamName, this));
			}
		}
		if(!found){
			teams.add(new Team(teamName, this));
		}
	}

	public void unregisterTeam(Team t){
		teams.remove(t);
	}
	
	public void startQuestion(){
		for(int i = 0; i < teams.size(); i++){
			teams.get(i).resetQuestionScore();
		}
		scanTime.start();
	}
	
	private void endQuestion(){
		//servGame.goToAnswerMode();
		//servGame.forwardToAnswerMode();
		servGame.getServerQuestionMode().allResponded();
		endQuestionScan();
	}
	
	public void endQuestionScan(){
		scanTime.stop();
	}
	
	public void calculateScores(int pointVal){
		//adds to score so only call this once per question
		for(Team team : teams){
			team.calculateScore(pointVal);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		boolean allResponded = true;
		
		for(Team team : teams){
			team.update();
			if(!team.hasEveryoneResponded()){
				allResponded = false;
			}
		}
		if(allResponded){
			endQuestion();
		}
	}
	
	public boolean isFound(String identifier) {
		for (Team team : teams)
			if (team.isFound(identifier))
				return true;
		return false;
	}

	public void reset() {
		for (Team team : teams)
			team.reset();
	}
	
}
