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
		for (boolean responded : answerReceived) {
			if (!responded)
				return false;
		}
		return true;
	}
	
	public void addMember(Player newPlayer){
		/*for(int i=0; i<members.size(); i++){
			if(members.get(i).getIdentifier().equals(newPlayer.getIdentifier())){
				members.remove(i);
			}
		}*/
		
		alphaAdd(newPlayer);
		answerReceived = new boolean[members.size()];
		answerCorrect = new boolean[members.size()];
	}
	
	public boolean checkRepeat(Player p){
		//checks if player has already registers(overrides if necessary)
		boolean repeat = false;	
		for(int i=0; i<members.size()&& !repeat; i++){
			if(members.get(i).getIdentifier().equals(p.getIdentifier())){
				members.remove(i);
				repeat = true;
			}
		}
		return repeat;
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
		answerReceived = new boolean[members.size()];
		answerCorrect = new boolean[members.size()];
	}
	
	public void calculatePercentage(){
		int numCorrect = 0;
		for(int i = 0; i < members.size(); i++){
			if (answerReceived[i] && answerCorrect[i])
				numCorrect++;
		}
		latestPercentage = ((double)numCorrect)/(members.size());
	}
	
	public void calculateScore(int pointVal){
		calculatePercentage();
		score += latestPercentage*pointVal;
	}
	
	public boolean isFound(String identifier) {
		for (Player player : members)
			if (player.getIdentifier().equals(identifier))
				return true;
		return false;
	}

	public void update() {
		for (int i = 0; i < answerReceived.length; i++) {
			if (!answerReceived[i]) {
				Answer ans = members.get(i).getAnswer();
				if (ans != null) {
					answerCorrect[i] = ans.isCorrect();
					answerReceived[i] = true;
				}
			}
		}
	}

	public void resetScore() {
		answerReceived = new boolean[members.size()];
		answerCorrect = new boolean[members.size()];
	}
		
}
