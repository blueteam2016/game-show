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
import com.blueteam.gameshow.data.Question;

public class ClientIO {
	private ObjectInputStream questIn;
	private ObjectOutputStream ansOut;
	
	public ClientIO(String pathServerFold, String pathClientFold, ClientProfile profile) {
		String identifier = profile.getIdentifier();

		try {
			String path = pathClientFold + ".profile_" + identifier;
			Files.deleteIfExists(Paths.get(path));
			FileOutputStream fOut = new FileOutputStream(path);
			ObjectOutputStream profOut = new ObjectOutputStream(fOut);
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
			FileOutputStream fOut = new FileOutputStream(path);
			ansOut = new ObjectOutputStream(fOut);
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
			ansOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
