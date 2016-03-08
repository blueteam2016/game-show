package com.blueteam.gameshow.client;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class ClientProfile implements Serializable{
	private static final long serialVersionUID = -6940285608262784919L;
	private String playerName;
	private String teamName;
	private String identifier;
	
	public ClientProfile(String playerName, String teamName) {
		this.playerName = playerName;
		this.teamName = teamName;
		this.identifier = null;
		
		try {
			this.identifier = InetAddress.getLocalHost().getHostName(); //apparently the only way to get the machine hostname in Java
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	}

	public String getPlayerName() {
		return playerName;
	}
	public String getTeamName() {
		return teamName;
	}
	
	public String getIdentifier() {
		return identifier;
	}

}
