package com.blueteam.gameshow.client;

import com.blueteam.gameshow.data.ClientProfile;

public class ClientWindow{
	
	private ClientIO clientIO;
	private RegistrationScreen rScreen;
	private ClientQuestionScreen cqScreen;

	public ClientWindow() {
		rScreen = new RegistrationScreen(this);
		cqScreen = new ClientQuestionScreen(this);
		
	}
	
	public void register(String pathServFold, String  pathClientFold, ClientProfile profile) {
		clientIO = new ClientIO(pathServFold, pathClientFold, profile);
	}
	
	public ClientIO getClientIO() {
		return clientIO;
	}
	

}
