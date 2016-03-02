package com.blueteam.gameshow.server;

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

public class ServerIO {
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ServerIO(String pathToFolder) {
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(pathToFolder + ".question");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			out = new ObjectOutputStream(fOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void openIn(String pathToFolder) {
		FileInputStream fIn = null;
		String sysName = null;
		try {
			sysName = InetAddress.getLocalHost().getHostName(); //apparently the only way to get the machine hostname in Java
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		try {
			fIn = new FileInputStream(pathToFolder + ".answer_" + sysName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		};
		try {
			in = new ObjectInputStream(fIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Answer getAnswer() {
		Answer qIn = null;
		try {
			qIn = (Answer)in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return qIn;
	}
	
	public void sendQuestion(Question question) {
		try {
			out.writeObject(question);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
