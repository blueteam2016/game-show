package com.blueteam.gameshow.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Player {
	private String identifier;
	private String name;
	private String answerPath;
	private long answerModTime;
	
	public Player(ClientProfile cProfile, String pathToFolder){
		name = cProfile.getPlayerName();
		identifier = cProfile.getIdentifier();
		answerPath = pathToFolder + ".answer_" + identifier;
		try {
			if (Files.exists(Paths.get(answerPath)))
				answerModTime = Files.getLastModifiedTime(Paths.get(answerPath)).toMillis();
			else
				answerModTime = 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getName(){
		return name;
	}
	
	public String getIdentifier(){
		return identifier;
	}
	
	public Answer getAnswer() {
		try {
			if (Files.exists(Paths.get(answerPath))) {
				long lastModified = Files.getLastModifiedTime(Paths.get(answerPath)).toMillis();
				if (answerModTime == lastModified) {
					return null;
				} else {
					answerModTime = lastModified;
					FileInputStream fIn = new FileInputStream(answerPath);
					FileLock fLock = fIn.getChannel().lock(0L, Long.MAX_VALUE, true);
					ObjectInputStream ansIn = new ObjectInputStream(fIn);
					Answer aIn = (Answer)ansIn.readObject();
					fLock.close();
					ansIn.close();
					return aIn;
				}
			} else {
				return null;
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
