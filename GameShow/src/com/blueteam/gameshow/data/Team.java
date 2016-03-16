package com.blueteam.gameshow.data;

import java.util.ArrayList;

public class Team {
	private ArrayList<Player> members;
	private double score;
	private double latestPercentage;
	private String name;
	private boolean[] answerReceived;
	private boolean[] answerCorrect;
	
	public Team(String n){
		name = n;
		members = new ArrayList<Player>();
		score = 0;
		latestPercentage = 0;
		answerReceived = new boolean[];
		answerCorrect = new boolean[];
	}
	
	public double getPercentage(){
		return latestPercentage;
	}
	
	public double getScore(){
		return score;
	}
	
	public String getName(){
		return name;
	}
	
	public Player getPlayer(int p){
		return members.get(p);
	}
	
	public int numPlayers(){
		return members.size();
	}
	
	boolean hasEveryoneResponded(){
		calculatePercentage();
		if(numResponses == members.size()){
			return true;
		}return false;
	}
	
	public void addMember(Player newPlayer){
		for(int i=0; i<members.size(); i++){
			if(members.get(i).getIdentifier().equals(newPlayer.getIdentifier())){
				members.remove(i);
			}
		}
		alphaAdd(newPlayer);
	}
	
	private void alphaAdd(Player p){
		//adds player to correct alphabetical position in list
		boolean found = false;

		for(int i=0; i<members.size() && !found;i++){
			if(p.getName().compareTo(members.get(i).getName())<0){
				found = true;
				members.add(i, p);
			}
		}

		if(!found){
			members.add(p);
		}

	}
	
	public void unregisterStudent(Player p){
		members.remove(p);
	}
	
	public void calculatePercentage(){
		int numCorrect = 0;
		numResponses = 0;
		for(int i=0; i<members.size(); i++){
			if(members.get(i).getAnswer()!=null){
				numResponses++;
				if(members.get(i).getAnswer().isCorrect()){
					numCorrect++;
				}
			}
		}
		latestPercentage = ((double)numCorrect)/(members.size());
	}
	
	public void calculateScore(){
		calculatePercentage();
		score += latestPercentage*members.size();
	}
	
	public boolean isFound(String identifier) {
		for (Player player : members)
			if (player.getIdentifier().equals(identifier))
				return true;
		return false;
	}
		
}
