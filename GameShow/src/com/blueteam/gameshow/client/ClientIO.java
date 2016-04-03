package com.blueteam.gameshow.client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.ClientProfile;
import com.blueteam.gameshow.data.Question;

public class ClientIO {
	private String questionPath;
	private long questionModTime;
	private String answerPath;
	private String profilePath;
	
	public ClientIO(String pathServerFold, String pathClientFold, ClientProfile profile) throws IOException {
		String identifier = profile.getIdentifier();
		questionPath = pathServerFold + ".question";
		answerPath = pathClientFold + ".answer_" + identifier;
		profilePath = pathClientFold + ".profile_" + identifier;
		questionModTime = 0;
		
		FileOutputStream fOut = new FileOutputStream(profilePath);
		FileLock fLock = fOut.getChannel().lock();
		ObjectOutputStream profOut = new ObjectOutputStream(fOut);
		try {
			profOut.writeObject(profile);
		} catch (IOException e) {
			throw e;
		} finally {
			fLock.close();
			profOut.close();
		}
	}
	
	private void truncate(FileOutputStream fOut) throws IOException {
		FileChannel outChan = fOut.getChannel();
		outChan.truncate(0);
	}

	public Question getQuestion() throws IOException {
		FileLock fLock = null;
		ObjectInputStream questIn = null;
		try {
			long lastModified = Files.getLastModifiedTime(Paths.get(questionPath)).toMillis();
			if (questionModTime == lastModified) {
				return null;
			} else {
				questionModTime = lastModified;
				FileInputStream fIn = new FileInputStream(questionPath);
				fLock = fIn.getChannel().lock(0L, Long.MAX_VALUE, true);
				questIn = new ObjectInputStream(fIn);
				Question qIn = (Question)questIn.readObject();
				return qIn;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fLock != null)
				fLock.close();
			if (questIn != null)
				questIn.close();
		}
		return null;
	}
	
	public void sendAnswer(Answer answer) {
		try {
			FileOutputStream fOut = new FileOutputStream(answerPath, true);
			FileLock fLock = fOut.getChannel().lock();
			truncate(fOut);
			ObjectOutputStream ansOut = new ObjectOutputStream(fOut);
			ansOut.writeObject(answer);
			fLock.close();
			ansOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clearAnswer() {
		try {
			Files.deleteIfExists(Paths.get(answerPath));
		} catch (IOException e) {}
	}


}
