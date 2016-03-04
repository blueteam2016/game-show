package com.blueteam.gameshow.server;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.blueteam.gameshow.client.Profile;
import com.blueteam.gameshow.data.Question;
import com.blueteam.gameshow.data.Quiz;


public class Game {
	private Roster roster;
	private Quiz quiz;
	private Profile profile;
	private ObjectOutputStream out;
	
	public Game(){
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
		if(profile.isComplete()){
			return quiz;
		}else{
			return null;
		}	
	}
	
	public void () {
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(pathToFolder + ".question");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			out = new ObjectOutputStream(fOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendQuestion(Question question) {
		try {
			out.writeObject(question);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
}
