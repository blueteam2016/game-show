package com.blueteam.gameshow.server;


import javax.swing.*;
import javax.swing.table.*;

import com.blueteam.gameshow.data.Player;
import com.blueteam.gameshow.data.Roster;



public class RosterTableModel extends AbstractTableModel{
	private Roster rost;
	private boolean registrationOpen;
	
	public RosterTableModel(Roster r){
		rost = r;
	}
	
	public String getColumnName(int col){
		if(col ==0){
			return "Player Name";
		}else{
			return "Team Name";
		}
	}
	
	public int getRowCount() {
		int numPlayers = 0;
		for(int t=0; t<rost.numTeams(); t++){
			numPlayers+=rost.getTeam(t).numPlayers();
		}
		return numPlayers;
	}

	public int getColumnCount() {
		return 2;
	}

	public String getValueAt(int rowIndex, int columnIndex) {
		for(int t=0; t<rost.numTeams(); t++){
			for(int p = 0; p<rost.getTeam(t).numPlayers(); p++){
				if(rowIndex==0){
					if(columnIndex == 0){
						return rost.getTeam(t).getPlayer(p).getName();
					}else{
						return rost.getTeam(t).getName();
					}
				}else{
					rowIndex--;
				}
			}
		}
		int t = rost.numTeams()-1;
		if(columnIndex == 0){
			return rost.getTeam(t).getPlayer(rost.getTeam(t).numPlayers()-1).getName();
		}else{
			return rost.getTeam(t).getName();
		}
	}
	
	public Object getStudentAt(int rowIndex){
		for(int t=0; t<rost.numTeams(); t++){
			for(int p = 0; p<rost.getTeam(t).numPlayers(); p++){
				if(rowIndex==0){
					return rost.getTeam(t).getPlayer(p);
				}else{
					rowIndex--;
				}
			}
		}
		int t = rost.numTeams()-1;
		return rost.getTeam(t).getPlayer(rost.getTeam(t).numPlayers()-1);
	}
	
	public void openRegistration(){
		registrationOpen = true;
		while(registrationOpen){
			//look for registrations DANIEL!!!!!!!!!!
		}
	}
	
	public void closeRegistration(){
		registrationOpen = false;
	}
	
	public void addMember(Player p, String teamName){
		rost.getTeam(teamName).addMember(p);
	}
}
