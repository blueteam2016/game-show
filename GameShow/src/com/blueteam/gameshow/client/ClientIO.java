package com.blueteam.gameshow.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import data.Answer;
import data.Question;

public class ClientIO {
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ClientIO(String pathToFolder) {
		FileInputStream fIn = null;
		FileOutputStream fOut = null;
		String sysName = null;
		try {
			sysName = InetAddress.getLocalHost().getHostName(); //apparently the only way to get the machine hostname in Java
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		try {
			fIn = new FileInputStream(pathToFolder + ".question");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		};
		
		try {
			fOut = new FileOutputStream(pathToFolder + ".answer_" + sysName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			in = new ObjectInputStream(fIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out = new ObjectOutputStream(fOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public Question getQuestion() {
		Question qIn = null;
		try {
			qIn = (Question)in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return qIn;
	}
	
	public void sendAnswer(Answer answer) {
		try {
			out.writeObject(answer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
