package com.blueteam.gameshow.client;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.ClientProfile;
import com.blueteam.gameshow.data.OldDataException;
import com.blueteam.gameshow.data.Question;

public class ClientIO {
	private String questionPath;
	private long questionModTime;
	private String answerPath;
	private long answerModTime;
	private ObjectInputStream questIn;
	private ObjectOutputStream ansOut;
	
	public ClientIO(String pathServerFold, String pathClientFold, ClientProfile profile) throws OldDataException{
		String identifier = profile.getIdentifier();

		try {
			String path = pathClientFold + ".profile_" + identifier;
			Files.deleteIfExists(Paths.get(path));
			ObjectOutputStream profOut = new ObjectOutputStream(new FileOutputStream(path));
			profOut.writeObject(profile);
			profOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			questIn = new ObjectInputStream(new FileInputStream(pathServerFold + ".question"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			//TODO: error handling
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			String path = pathClientFold + ".answer_" + identifier;
			Files.deleteIfExists(Paths.get(path));
			ansOut = new ObjectOutputStream(new FileOutputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Question getQuestion() {
		Question qIn = null;
		try {
			qIn = (Question)questIn.readObject();
		} catch (EOFException e) {
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return qIn;
	}
	
	public void sendAnswer(Answer answer) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
