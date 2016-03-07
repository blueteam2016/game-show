package com.blueteam.gameshow.data;


public class Player {
	private String IP;
	private String name;
	
	public Player(String n, String identifier){
		name = n;
		IP = identifier;
	}
	
	public String getName(){
		return name;
	}
	
	public String getIP(){
		return IP;
	}
	
	public Answer getAnswer(){
		return null;
		//Daniel!!!!!!!
	}
}
