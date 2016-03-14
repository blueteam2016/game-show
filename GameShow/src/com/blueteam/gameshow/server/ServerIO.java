package com.blueteam.gameshow.server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Profile;
import com.blueteam.gameshow.data.Question;

public class ServerIO {
	private String answerPath;
	private long answerModTime;
	private String questionPath;
	private String profilePath;
	
	public ServerIO(String pathToFolder) {
		String identifier = null;
		try {
			identifier = InetAddress.getLocalHost().getHostName(); //apparently the only way to get the machine hostname in Java
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		questionPath = pathToFolder + ".question";
		answerPath = pathToFolder + ".answer_" + identifier;
		profilePath = pathToFolder + ".profile_" + identifier;
		try {
			Files.deleteIfExists(Paths.get(questionPath));
			Files.createFile(Paths.get(questionPath));
			if (Files.exists(Paths.get(answerPath)))
				answerModTime = Files.getLastModifiedTime(Paths.get(answerPath)).toMillis();
			else
				answerModTime = 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void truncate(FileOutputStream fOut) throws IOException {
		FileChannel outChan = fOut.getChannel();
		outChan.truncate(0);
		outChan.close();
		fOut.close();
	}
	
	public Answer getAnswer() {
		try {
			if (Files.exists(Paths.get(answerPath))) {
				long lastModified = Files.getLastModifiedTime(Paths.get(answerPath)).toMillis();
				while(answerModTime == lastModified) {
					lastModified = Files.getLastModifiedTime(Paths.get(answerPath)).toMillis();
				}
				answerModTime = lastModified;
			} else {
				while(!Files.exists(Paths.get(profilePath)));
			}
			FileInputStream fIn = new FileInputStream(answerPath);
			FileLock fLock = fIn.getChannel().lock();
			ObjectInputStream ansIn = new ObjectInputStream(fIn);
			Answer aIn = (Answer)ansIn.readObject();
			fLock.close();
			ansIn.close();
			return aIn;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Profile getProfile() {
		try {
			while(!Files.exists(Paths.get(profilePath)));
			FileInputStream fIn = new FileInputStream(profilePath);
			FileLock fLock = fIn.getChannel().lock();
			ObjectInputStream profIn = new ObjectInputStream(fIn);
			Profile pIn = (Profile)profIn.readObject();
			fLock.close();
			profIn.close();
			return pIn;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void sendQuestion(Question question) {
		try {
			FileOutputStream fOut = new FileOutputStream(questionPath, true);
			FileLock fLock = fOut.getChannel().lock();
			truncate(fOut);
			ObjectOutputStream questOut = new ObjectOutputStream(fOut);
			questOut.writeObject(question);
			fLock.close();
			questOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy() {
		try {
			Files.deleteIfExists(Paths.get(answerPath));
			Files.deleteIfExists(Paths.get(questionPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
