package com.blueteam.gameshow.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.blueteam.gameshow.data.*;


public class Game {
	private Roster roster;
	private Quiz quiz;
	private Profile profile;
	private String questionPath;
	private boolean IOOpened;
	private boolean running;
	
	public Game(){
		profile = new Profile();
		roster = new Roster();
		IOOpened = false;
		running = false;
	}
	
	private void reset() {
		clearQuestion();
		roster.reset();
	}
	
	public void createQuiz() throws Exception{
		reset();
		running = true;
		try {
			quiz = new Quiz(Profile.getQuestionFileLoc());
		} catch(Exception e) {
			running = false;
			throw e;
		}
	}
	
	public void createRoster(ServerGameScreen sgs){
		roster.addServGame(sgs);
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
	
	public boolean isRunning() {
		return running;
	}
	
	public void endGame() {
		running = false;
	}
	
	public boolean openIO() {
		questionPath = profile.getServerFolderLoc() + ".question";
		try {
			Files.deleteIfExists(Paths.get(questionPath));
			Files.createFile(Paths.get(questionPath));
		} catch (IOException e) {
			return false;
		}
		IOOpened = true;
		return true;
	}
	
	public boolean isIOOpen() {
		return IOOpened;
	}
	
	private void truncate(FileOutputStream fOut) throws IOException {
		FileChannel outChan = fOut.getChannel();
		outChan.truncate(0);
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
	
	public void clearQuestion() {
		try {
			FileOutputStream fOut = new FileOutputStream(questionPath, true);
			FileLock fLock = fOut.getChannel().lock();
			truncate(fOut);
			fLock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy() {
		try {
			String clientPath = profile.getClientFolderLoc();
			String serverPath = profile.getServerFolderLoc();
			if (clientPath != null) {
				File folder = new File(clientPath);
				File[] ansFiles = folder.listFiles(new FilenameFilter() {
					@Override
					public boolean accept(final File dir, final String name) {
						return name.contains(".answer_");
					}
				});
				if (ansFiles != null) {
					for (File file : ansFiles) {
						file.delete();	
					}
				}
				File[] profFiles = folder.listFiles(new FilenameFilter() {
					@Override
					public boolean accept(final File dir, final String name) {
						return name.contains(".profile_");
					}
				});
				if (profFiles != null) {
					for (File file : profFiles) {
						file.delete();	
					}
				}
			}
			if (questionPath != null)
				Files.deleteIfExists(Paths.get(questionPath));
			if (serverPath != null)
				Files.exists(Paths.get(serverPath + ".registration"));
		} catch (IOException e) {}
	}
	
}
