package com.blueteam.gameshow.server;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.*;
import javax.swing.table.*;

import com.blueteam.gameshow.data.ClientProfile;
import com.blueteam.gameshow.data.Player;
import com.blueteam.gameshow.data.Roster;



public class RosterTableModel extends AbstractTableModel implements ActionListener {
	private static final long serialVersionUID = -1561458490688644986L;
	private Roster roster;
	private String pathToFolder;
	private Timer scanTime;
	
	public RosterTableModel(Game game){
		roster = game.getRoster();
		pathToFolder = game.getProfile().getClientFolderLoc();
		try {
			Files.deleteIfExists(Paths.get(pathToFolder + ".registration"));
		} catch (IOException e) {}
		scanTime = new Timer(100, this);
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
		for(int t=0; t<roster.numTeams(); t++){
			numPlayers+=roster.getTeam(t).numPlayers();
		}
		return numPlayers;
	}

	public int getColumnCount() {
		return 2;
	}

	public String getValueAt(int rowIndex, int columnIndex) {
		for(int t=0; t<roster.numTeams(); t++){
			for(int p = 0; p<roster.getTeam(t).numPlayers(); p++){
				if(rowIndex==0){
					if(columnIndex == 0){
						return roster.getTeam(t).getPlayer(p).getName();
					}else{
						return roster.getTeam(t).getName();
					}
				}else{
					rowIndex--;
				}
			}
		}
		int t = roster.numTeams()-1;
		if(columnIndex == 0){
			return roster.getTeam(t).getPlayer(roster.getTeam(t).numPlayers()-1).getName();
		}else{
			return roster.getTeam(t).getName();
		}
	}
	
	public Player getStudentAt(int rowIndex){
		for(int t=0; t<roster.numTeams(); t++){
			for(int p = 0; p<roster.getTeam(t).numPlayers(); p++){
				if(rowIndex==0){
					return roster.getTeam(t).getPlayer(p);
				}else{
					rowIndex--;
				}
			}
		}
		int t = roster.numTeams()-1;
		return roster.getTeam(t).getPlayer(roster.getTeam(t).numPlayers()-1);
	}
	
	public void openRegistration(){
		scanTime.start();
		try {
			Files.createFile(Paths.get(pathToFolder + ".registration"));
		} catch (IOException e) {}
	}
	
	public void closeRegistration(){
		scanTime.stop();
		try {
			Files.deleteIfExists(Paths.get(pathToFolder + ".registration"));
		} catch (IOException e) {}
	}
	
	public void addMember(Player p, String teamName){
		roster.addPlayer(teamName, p);
		fireTableDataChanged();
	}
	
	public void dataChanged(){
		fireTableDataChanged();
	}


	public void actionPerformed(ActionEvent arg0) {
		File folder = new File(pathToFolder);
		File[] ansFiles = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(final File dir, final String name) {
				return name.contains(".profile_");
			}
		});
		if (ansFiles != null) {
			for (File file : ansFiles) {
				String fileName = file.getName();
				String identifier = fileName.replaceFirst(".profile_", "");
				if (!roster.isFound(identifier)) {
					try {
						FileInputStream fIn = new FileInputStream(file);
						FileLock fLock = fIn.getChannel().lock(0L, Long.MAX_VALUE, true);
						ObjectInputStream profIn = new ObjectInputStream(fIn);
						ClientProfile pIn = (ClientProfile)profIn.readObject();
						fLock.close();
						profIn.close();
						Player newPlayer = new Player(pIn, pathToFolder);
						addMember(newPlayer, pIn.getTeamName());
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				file.delete();
			}
		}
	}
}
