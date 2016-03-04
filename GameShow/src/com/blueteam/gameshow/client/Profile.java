package com.blueteam.gameshow.client;

public class Profile {
	private String servFoldLoc;
	private String clientFoldLoc;
	private int defVal;
	private int defTime;
	private String qFileLoc;
	private boolean foundSavedFile;

	public Profile(){
		//Get Aidan
	}

	public void saveProfile(){
		//Get Daniel
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
	
	public int getDefaultValue(){
		return defVal;
	}
	
	public int getDefaultTime(){
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
