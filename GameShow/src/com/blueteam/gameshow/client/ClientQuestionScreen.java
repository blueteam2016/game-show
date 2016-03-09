package com.blueteam.gameshow.client;
import javax.swing.JPanel;

import com.blueteam.gameshow.data.Question;


public class ClientQuestionScreen extends JPanel{

	private static final long serialVersionUID = 335785158677578632L;
	private ClientQuestionMode cQuestion;
	private ClientAnswerMode cAnswer;
	private ClientIO clientIO;
	private Question currentQuestion;
	private JPanel noQuestionAvailable;
	private JPanel notRegistered;
	private JPanel currentMode;

	public ClientQuestionScreen(ClientWindow clientWindow) {
		clientIO = clientWindow.getClientIO();
		cQuestion = new ClientQuestionMode(this);
		cAnswer = new ClientAnswerMode(this);
		noQuestionAvailable = new JPanel();
		notRegistered = new JPanel();
		currentMode = noQuestionAvailable;
		
		new Thread(cQuestion, "ClientQuestionThread").start(); // required for continuous updating to receive question from server
		
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
	
	public void setQuestion(Question question) {
		currentQuestion = question;
	}

	public JPanel getCurrentMode() {
		return currentMode;
	}
	
	public ClientIO getClientIO() {
		return clientIO;
	}

}
