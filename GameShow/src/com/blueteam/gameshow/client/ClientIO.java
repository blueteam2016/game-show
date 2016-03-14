package com.blueteam.gameshow.client;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	
	public ClientIO(String pathServerFold, String pathClientFold, ClientProfile profile) throws IOException{
		String identifier = profile.getIdentifier();
		questionPath = pathServerFold + ".question";
		answerPath = pathClientFold + ".answer_" + identifier;
		questionModTime = Files.getLastModifiedTime(Paths.get(questionPath)).toMillis();
		
		ObjectOutputStream profOut = new ObjectOutputStream(new FileOutputStream(answerPath));
		profOut.writeObject(profile);
		profOut.close();
	}
	
	private void truncate(String file) throws IOException {
		FileOutputStream fOut = new FileOutputStream(file, true);
		FileChannel outChan = fOut.getChannel();
		outChan.truncate(0);
		outChan.close();
		fOut.close();
	}

	public Question getQuestion() {
		try {
			long lastModified = Files.getLastModifiedTime(Paths.get(questionPath)).toMillis();
			while(questionModTime == lastModified) {
				lastModified = Files.getLastModifiedTime(Paths.get(questionPath)).toMillis();
			}
			questionModTime = lastModified;
			FileInputStream fIn = new FileInputStream(questionPath);
			FileLock fLock = fIn.getChannel().lock();
			ObjectInputStream questIn = new ObjectInputStream(fIn);
			Question qIn = (Question)questIn.readObject();
			fLock.close();
			questIn.close();
			return qIn;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void sendAnswer(Answer answer) {
		ObjectOutputStream ansOutnew = new ObjectOutputStream(new FileOutputStream(answerPath));
		
		
		try {
			ansOut.writeObject(answer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy() {
		try {
			Files.deleteIfExists(Paths.get(questionPath));
			Files.deleteIfExists(Paths.get(answerPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
