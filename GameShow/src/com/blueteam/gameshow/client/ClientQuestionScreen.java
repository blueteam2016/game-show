package com.blueteam.gameshow.client;
import javax.swing.JPanel;

import com.blueteam.gameshow.data.Question;


public class ClientQuestionScreen {
private ClientQuestionMode cQuestion;
private ClientAnswerMode cAnswer;
private JPanel noQuestionAvailable;
private JPanel notRegistered;
private JPanel currentMode;
private Question currentQuestion;

ClientIO clientIO;

public ClientQuestionScreen(){
	
	clientIO = new ClientIO();
	cQuestion = new ClientQuestionMode(clientIO, this);
	cAnswer = new ClientAnswerMode(this);
	noQuestionAvailable = new JPanel();
	notRegistered = new JPanel();
	currentMode = new JPanel();
	//parameters: string qText, Answer[] ans, String exp, int t, intv
	currentQuestion = new Question();
	
	
}
public void goToAnswerMode(){
	currentMode= cAnswer;
}
public void goToQuestionMode(){
	currentMode = cQuestion; 
}
public void goToNotRegistered(){
	currentMode = notRegistered;
}
public void goToNoQuestion(){
	currentMode = noQuestionAvailable;
}
public Question getQuestion(){
	return currentQuestion;
}
public JPanel getCurrentMode(){
	return currentMode;
}



}
