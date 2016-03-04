package com.blueteam.gameshow.client;


public class ClientProfile {

	String playerName;
	String teamName;
	String IP;
	boolean complete;
	
	ClientProfile(){
		//Get Aidan
	}

	public String getPlayerName(){
		return playerName;
	}
	public String getTeamName(){
		return teamName;
	}

	public void setTeamName(String t){
		teamName=t;
		if (!teamName.equals(""))
			if(!playerName.equals(""))
				complete=true;
	}
	public void setPlayerName(String n){
		playerName=n;
		if (!teamName.equals(""))
			if(!playerName.equals(""))
				complete=true;
	}

}
