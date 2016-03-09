package com.blueteam.gameshow.server;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Question;

public class ServerIO {
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ServerIO(String pathToFolder) {
		try {
			String path = pathToFolder + ".question";
			Files.deleteIfExists(Paths.get(path));
			FileOutputStream fOut = new FileOutputStream(path);
			out = new ObjectOutputStream(fOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void openIn(String pathToFolder) {
		String sysName = null;
		while (true) {
			try {
				sysName = InetAddress.getLocalHost().getHostName(); //apparently the only way to get the machine hostname in Java
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			try {
				in = new ObjectInputStream(new FileInputStream(pathToFolder + ".answer_" + sysName));
			} catch (FileNotFoundException e) {
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	public Answer getAnswer() {
		Answer qIn = null;
		try {
			qIn = (Answer)in.readObject();
		} catch (EOFException e) {
			return null;
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
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	
}
