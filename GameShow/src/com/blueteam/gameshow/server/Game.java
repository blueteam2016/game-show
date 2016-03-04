package com.blueteam.gameshow.server;


public class Game {
	private Roster roster;
	private Quiz quiz;
	private Profile profile;
	
	Game(){
		roster = new Roster();
		quiz = new Quiz();
		profile = new Profile();
	}
	public Roster getRoster(){
		return roster;
	}
	public Profile getProfile(){
		return profile;
	}
	public Quiz getQuiz(){
		if(profile.isComplete){
			return quiz;
		}else{
			return null;
		}	
	}
	public void sendQuestion(){
		//Daniel
	}
	
	
	
}
