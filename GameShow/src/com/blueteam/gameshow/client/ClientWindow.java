package com.blueteam.gameshow.client;

public class ClientWindow{
	
	private ClientProfile profile;
	private ClientIO clientIO;
	private RegistrationScreen rScreen;
	private ClientQuestionScreen cqScreen;
	private String pathToFolder;

	public ClientWindow() {
		profile = new ClientProfile(pathToFolder, pathToFolder);
		clientIO = new ClientIO(pathToFolder);
		rScreen = new RegistrationScreen(profile);
		cqScreen = new ClientQuestionScreen();
		
	}
	
	public void register() {
		
	}
	

}
