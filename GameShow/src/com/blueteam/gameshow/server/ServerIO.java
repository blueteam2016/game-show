package com.blueteam.gameshow.server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.ClientProfile;
import com.blueteam.gameshow.data.Question;

public class ServerIO {
	
	
	private String profilePath;
	
	public ServerIO(String pathToFolder) {
		profilePath = pathToFolder + ".profile_" + identifier;

	}
	

	
	
	
	public ClientProfile getClientProfile() {
		try {
			while(!Files.exists(Paths.get(profilePath)));
			FileInputStream fIn = new FileInputStream(profilePath);
			FileLock fLock = fIn.getChannel().lock(0L, Long.MAX_VALUE, true);
			ObjectInputStream profIn = new ObjectInputStream(fIn);
			ClientProfile pIn = (ClientProfile)profIn.readObject();
			fLock.close();
			profIn.close();
			return pIn;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	

	
}
