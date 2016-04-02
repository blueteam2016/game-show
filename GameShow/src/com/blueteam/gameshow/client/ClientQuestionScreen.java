package com.blueteam.gameshow.client;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.blueteam.gameshow.data.Question;


public class ClientQuestionScreen extends JPanel {

	private static final long serialVersionUID = 335785158677578632L;
	public static final String QUESTIONMODE = "Question Mode";
	public static final String ANSWERMODE = "Answer Mode";
	public static final String NOQUESTIONMODE = "No Questions Available";
	public static final String NOTREGISTEREDMODE = "Not registered";
	private ClientWindow clientWindow;
	private ClientQuestionMode cQuestion;
	private Thread clientQMThread;
	private ClientAnswerMode cAnswer;
	private JPanel noQuestionAvailable;
	private JPanel notRegistered;
	private Question currentQuestion;
	private String currentMode;
	
	
	public ClientQuestionScreen(ClientWindow cWindow) {
		setLayout(new CardLayout());
		
		clientWindow = cWindow;
		
		cQuestion = new ClientQuestionMode(this);
		cAnswer = new ClientAnswerMode(this);
		noQuestionAvailable = new JPanel();
		noQuestionAvailable.add(new JLabel("No Question"));
		notRegistered = new JPanel();
		notRegistered.add(new JLabel("Not Registered"));
		
		add(cQuestion, QUESTIONMODE);
		add(cAnswer, ANSWERMODE);
		add(noQuestionAvailable, NOQUESTIONMODE);
		add(notRegistered, NOTREGISTEREDMODE);
		
		goToNotRegisteredMode();
	}

	public void goToAnswerMode() {
		cAnswer.newAnswer();
		CardLayout cl = (CardLayout)getLayout();
		currentMode = ANSWERMODE;
		cl.show(this, currentMode);
	}

	public void goToQuestionMode() {
		CardLayout cl = (CardLayout)getLayout();
		currentMode = QUESTIONMODE;
		cl.show(this, currentMode);
	}

	public void goToNotRegisteredMode() {
		CardLayout cl = (CardLayout)getLayout();
		currentMode = NOTREGISTEREDMODE;
		cl.show(this, currentMode);
	}

	public void goToNoQuestionMode() {
		CardLayout cl = (CardLayout)getLayout();
		currentMode = NOQUESTIONMODE;
		cl.show(this, currentMode);
		clientWindow.update();
	}

	public Question getQuestion() {
		return currentQuestion;
	}
	
	public void setQuestion(Question question) {
		currentQuestion = question;
	}

	public String getCurrentMode() {
		return currentMode;
	}
	
	public ClientWindow getClientWindow() {
		return clientWindow;
	}

	public void register() {
		reset();
		cQuestion.register();
		
		clientQMThread = new Thread(cQuestion, "ClientQuestionThread"); // required for continuous updating to receive question from server
		clientQMThread.start();
	}
	
	public void reset() {
		goToNotRegisteredMode();
		if (clientQMThread != null) {
			clientQMThread.interrupt();
			try {
				clientQMThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		clientQMThread = null;
	}

}
