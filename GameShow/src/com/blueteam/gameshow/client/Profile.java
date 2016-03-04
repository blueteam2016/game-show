package com.blueteam.gameshow.client;

public class Profile {
	private String servFoldLoc;
	private String clientFoldLoc;
	private static int defVal;
	private static int defTime;
	private String qFileLoc;
	private boolean foundSavedFile;
	
	
	public Profile(){
		//Get Aidan
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
	
	public String getQuestionFileLoc(){
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
