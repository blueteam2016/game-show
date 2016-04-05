package com.blueteam.gameshow.server;

import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import com.blueteam.gameshow.data.*;


public class Game {
	private Roster roster;
	private Quiz quiz;
	private Profile profile;
	private String activeServerPath;
	private String activeClientPath;
	private String questionPath;
	private boolean IOOpened;
	private boolean running;
	private boolean doResetScore;
	
	public Game(){
		profile = new Profile();
		roster = new Roster();
		activeServerPath = null;
		activeClientPath = null;
		questionPath = null;
		IOOpened = false;
		running = false;
		doResetScore = false;
	}
	
	public void scheduleScoreReset() {
		doResetScore = true;
	}
	
	private void reset() {
		clearQuestion();
		if (doResetScore) {
			roster.reset();
			doResetScore = false;
		}
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
	
	private File[] getExistingIOFiles(String serverPath, String clientPath) throws IOException{
		ArrayList<File> out = new ArrayList<File>();
		File folder = new File(clientPath);
		ArrayList<File> ansFiles = new ArrayList<File>(Arrays.asList(folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(final File dir, final String name) {
				return name.contains(".answer_");
			}
		})));
		if (ansFiles != null) {
			out.addAll(ansFiles);
		}
		ArrayList<File> profFiles = new ArrayList<File>(Arrays.asList(folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(final File dir, final String name) {
				return name.contains(".profile_");
			}
		})));
		if (profFiles != null) {
			out.addAll(profFiles);
		}
		if (Files.exists(Paths.get(serverPath + ".question")))
			out.add(new File(serverPath + ".question"));
		return out.toArray(new File[0]);
	}
	
	public boolean openIO() {
		
		String newServerPath = profile.getServerFolderLoc();
		String newClientPath = profile.getClientFolderLoc();
		
		File[] files = null;
		try {
			files = getExistingIOFiles(newServerPath, newClientPath);
		} catch (AccessDeniedException e) {
			JOptionPane.showMessageDialog(null, "You do not have access!", "Permissions Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (IOException e) {}
		
		if (files != null && files.length > 0) {
			ExistingFilesPopUp popUp = new ExistingFilesPopUp(files, newServerPath, newClientPath);
			if (!popUp.getChoice()) {
				JOptionPane.showMessageDialog(null, "Please delete files or change directory before play.", "IO Error", JOptionPane.ERROR_MESSAGE);
				return false;
			} else if(!popUp.wasDeleteSuccessful()) {
				JOptionPane.showMessageDialog(null, "Failed to delete files. Please check your permissions or change directories.", "File Deletion Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		try {
			Files.createFile(Paths.get(newServerPath + ".question"));
		} catch (IOException e) {
			return false;
		}
		if (IOOpened) {
			closeIO();
		}
		activeServerPath = newServerPath;
		activeClientPath = newClientPath;
		questionPath = activeServerPath + ".question";
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
	
	public void closeIO() {
		if (IOOpened) {
			File[] files = null;
			try {
				files = getExistingIOFiles(activeServerPath, activeClientPath);
			} catch (IOException e) {}
			for (File file : files)
				file.delete();
		}
		IOOpened = false;
	}
	
	private class ExistingFilesPopUp { //doesn't extend PopUp because this constructor MUST be called before PopUp's constructor
		private boolean deleteSuccessful;
		private PopUp p;

		public ExistingFilesPopUp(final File[] files, final String newServerPath, final String newClientPath) {
			
			deleteSuccessful = false;
			p = new PopUp() {
				@Override
				protected String messageText() {
					return "<html>Existing IO files have been found. They must be deleted before play. Would you like to delete these files now?</html>";
				}

				@Override
				protected void yes() {
					choice = true;
					File[] checkFiles = null;
					for (File file : files)
						file.delete();
					try {
						checkFiles = getExistingIOFiles(newServerPath, newClientPath);
					} catch (IOException e) {}
					if (checkFiles == null || checkFiles.length == 0)
						deleteSuccessful = true;
					dispose();
				}

				@Override
				protected void no() {
					choice = false;
					dispose();
				}
				
				@Override
				public void windowActivated(WindowEvent e) {}
				@Override
				public void windowClosed(WindowEvent e) {}
				@Override
				public void windowClosing(WindowEvent e) {
					choice = false;
				}
				@Override
				public void windowDeactivated(WindowEvent e) {}
				@Override
				public void windowDeiconified(WindowEvent e) {}
				@Override
				public void windowIconified(WindowEvent e) {}
				@Override
				public void windowOpened(WindowEvent e) {}
			};
		}
		
		public boolean wasDeleteSuccessful() {
			return deleteSuccessful;
		}
		
		public boolean getChoice() {
			return p.getChoice();
		}
		
	}
	
}
