package com.blueteam.gameshow.client;

import javax.xml.parsers.*;

import org.w3c.dom.*;

public class Profile {
	private String servFoldLoc;
	private String clientFoldLoc;
	private static int defVal;
	private static int defTime;
	private static String qFileLoc;
	private boolean foundSavedFile;
	
	
	public Profile(){
		try{
			Document profileSave;
			DocumentBuilderFactory profileFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder profileBuilder = profileFactory.newDocumentBuilder();
			profileSave = profileBuilder.parse("fileLocation");
			Element root = profileSave.getDocumentElement();
			servFoldLoc = ((Element)root.getElementsByTagName("serverLoc").item(0)).getTextContent();
			clientFoldLoc = ((Element)root.getElementsByTagName("clientLoc").item(0)).getTextContent();
			qFileLoc = ((Element)root.getElementsByTagName("questionLoc").item(0)).getTextContent();
			defTime = Integer.parseInt(((Element)root.getElementsByTagName("timeDefault").item(0)).getTextContent());
			defVal = Integer.parseInt(((Element)root.getElementsByTagName("valueDefault").item(0)).getTextContent());
		}catch(Exception e){e.printStackTrace();};
	}

	public void saveProfile(){
		//Get Aidan
	}
	
	public String getServerFolderLoc(){
		return servFoldLoc;
	}
	
	public String getClientFolderLoc(){
		return clientFoldLoc;
	}
	
	public static String getQuestionFileLoc(){
		return qFileLoc;
	}
	
	public static int getDefaultValue(){
		return defVal;
	}
	
	public static int getDefaultTime(){
		return defTime;
	}
	
	public boolean savedFileFound(){
		return foundSavedFile;
	}

	public void setServerFolderLoc(String loc){
		servFoldLoc=loc;
	}
	
	public void setClientFolderLoc(String loc){
		clientFoldLoc=loc;
	}
	
	public void setQuestionFileLoc(String loc){
		qFileLoc=loc;
	}
	
	public void setDefaultValue(int v){
		defVal=v;
	}
	
	public void setDefaultTime(int t) {
		defTime=t;
	}
	
	public boolean isComplete(){
		if (!servFoldLoc.equals(""))
			if (!clientFoldLoc.equals(""))
				if (!qFileLoc.equals(""))
					if (defVal!=0)
						if(defTime!=0)
							return true;
		return false;
	}

}
