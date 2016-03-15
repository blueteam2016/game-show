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
	
	public Game(){
		roster = new Roster();
		profile = new Profile();
		questionPath = profile.getServerFolderLoc() + ".question";
		try {
			Files.deleteIfExists(Paths.get(questionPath));
			Files.createFile(Paths.get(questionPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createQuiz() throws Exception{
		quiz = new Quiz(Profile.getQuestionFileLoc());
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
	
	public void destroy() {
		try {
			File folder = new File(profile.getClientFolderLoc());
			File[] ansFiles = folder.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(final File dir, final String name) {
					return name.matches(".answer_*");
				}
			});
			for (File file : ansFiles) {
				file.delete();	
			}
			File[] profFiles = folder.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(final File dir, final String name) {
					return name.matches(".profile_*");
				}
			});
			for (File file : profFiles) {
				file.delete();	
			}
			Files.deleteIfExists(Paths.get(questionPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
