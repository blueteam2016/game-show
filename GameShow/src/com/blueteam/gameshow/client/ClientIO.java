package com.blueteam.gameshow.client;

import java.io.EOFException;
import java.io.FileInputStream;
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
	
	public ClientIO(String pathServerFold, String pathClientFold, ClientProfile profile) {
		String identifier = profile.getIdentifier();

		try {
			ObjectOutputStream profOut = new ObjectOutputStream(new FileOutputStream(pathClientFold + ".profile_" + identifier));
			profOut.writeObject(profile);
			profOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			questIn = new ObjectInputStream(new FileInputStream(pathServerFold + ".question"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			ansOut = new ObjectOutputStream(new FileOutputStream(pathClientFold + ".answer_" + identifier));
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


}
