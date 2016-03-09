package com.blueteam.gameshow.client;
import javax.swing.JPanel;

import com.blueteam.gameshow.data.Question;


public class ClientQuestionScreen {
private ClientQuestionMode cQuestion;
private ClientAnswerMode cAnswer;
private ClientIO clientIO;
private Question currentQuestion;
private JPanel noQuestionAvailable;
private JPanel notRegistered;
private JPanel currentMode;

public ClientQuestionScreen(ClientWindow clientWindow){
	clientIO = clientWindow.getClientIO();
	cQuestion = new ClientQuestionMode(clientIO, this);
	cAnswer = new ClientAnswerMode(this);
	noQuestionAvailable = new JPanel();
	notRegistered = new JPanel();
	currentMode = noQuestionAvailable;
	currentQuestion = null;
}

public void goToAnswerMode() {
	currentMode = cAnswer;
}

public void goToQuestionMode() {
	currentMode = cQuestion; 
}

public void goToNotRegistered() {
	currentMode = notRegistered;
}

public void goToNoQuestion() {
	currentMode = noQuestionAvailable;
}

public Question getQuestion() {
	return currentQuestion;
}

public JPanel getCurrentMode() {
	return currentMode;
}



}
