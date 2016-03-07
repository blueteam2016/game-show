package com.blueteam.gameshow.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Question;

public class ClientIO {
	private ObjectInputStream questIn;
	private ObjectOutputStream ansOut;
	
	public ClientIO(String pathToFolder, ClientProfile profile) {
		String sysName = null;
		
		try {
			sysName = InetAddress.getLocalHost().getHostName(); //apparently the only way to get the machine hostname in Java
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		try {
			ObjectOutputStream profOut = new ObjectOutputStream(new FileOutputStream(pathToFolder + ".profile_" + sysName));
			profOut.writeObject(profile);
			profOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			questIn = new ObjectInputStream(new FileInputStream(pathToFolder + ".question"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			ansOut = new ObjectOutputStream(new FileOutputStream(pathToFolder + ".answer_" + sysName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public Question getQuestion() {
		Question qIn = null;
		try {
			qIn = (Question)questIn.readObject();
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
