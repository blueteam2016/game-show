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

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Question;

public class ServerIO {
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private void truncate(FileOutputStream out) {
		try {
			out.getChannel().truncate(0);
			out.getChannel().force(true);
			out.getChannel().lock();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ServerIO(String pathToFolder) {
		try {
			FileOutputStream fOut = new FileOutputStream(pathToFolder + ".question");
			truncate(fOut);
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
