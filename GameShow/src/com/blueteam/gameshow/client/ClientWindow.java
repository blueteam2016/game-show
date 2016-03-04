package com.blueteam.gameshow.client;

import ClientProfile;

public class ClientWindow{
	
	ClientProfile profile;
	ClientIO clientIO;
	RegistrationScreen rScreen;
	ClientQuestionScreen cqScreen;
	String pathToFolder;

	ClientWindow(){
		profile=new ClientProfile();
		clientIO=new ClientIO(pathToFolder);
		rScreen=new RegistrationScreen(profile);
		cqScreen=new ClientQuestionScreen();
		
	}
	
	public void register(){
		
	}
	

}
