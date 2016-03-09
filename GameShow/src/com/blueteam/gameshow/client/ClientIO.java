package com.blueteam.gameshow.client;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.ClientProfile;
import com.blueteam.gameshow.data.Question;

public class ClientIO {
	private ObjectInputStream questIn;
	private ObjectOutputStream ansOut;
	
	private void truncate(FileOutputStream out) {
		try {
			out.getChannel().truncate(0);
			out.getChannel().force(true);
			out.getChannel().lock();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ClientIO(String pathServerFold, String pathClientFold, ClientProfile profile) {
		String identifier = profile.getIdentifier();

		try {
			FileOutputStream fOut = new FileOutputStream(pathClientFold + ".profile_" + identifier);
			truncate(fOut);
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
			FileOutputStream fOut = new FileOutputStream(pathClientFold + ".answer_" + identifier);
			truncate(fOut);
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
