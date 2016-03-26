package com.blueteam.gameshow.client;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.blueteam.gameshow.data.Question;


public class ClientQuestionScreen extends JPanel{

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
	private JPanel cards;
	private Question currentQuestion;
	private String currentMode;
	
	
	public ClientQuestionScreen(ClientWindow cWindow) {
		clientWindow = cWindow;
		
		cQuestion = new ClientQuestionMode(this);
		cAnswer = new ClientAnswerMode(this);
		//cAnswer.add(new JLabel("Answer"));
		noQuestionAvailable = new JPanel();
		noQuestionAvailable.add(new JLabel("No Question"));
		notRegistered = new JPanel();
		notRegistered.add(new JLabel("Not Registered"));
		
		cards = new JPanel(new CardLayout());
		
		cards.add(cQuestion, QUESTIONMODE);
		cards.add(cAnswer, ANSWERMODE);
		cards.add(noQuestionAvailable, NOQUESTIONMODE);
		cards.add(notRegistered, NOTREGISTEREDMODE);
		this.add(cards);
	}

	public void goToAnswerMode() {
		cAnswer.update();
		CardLayout cl = (CardLayout)cards.getLayout();
		currentMode = ANSWERMODE;
		cl.show(cards, currentMode);
	}

	public void goToQuestionMode() {
		CardLayout cl = (CardLayout)cards.getLayout();
		currentMode = QUESTIONMODE;
		cl.show(cards, currentMode);
	}

	public void goToNotRegistered() {
		CardLayout cl = (CardLayout)cards.getLayout();
		currentMode = NOTREGISTEREDMODE;
		cl.show(cards, currentMode);
	}

	public void goToNoQuestion() {
		CardLayout cl = (CardLayout)cards.getLayout();
		currentMode = NOQUESTIONMODE;
		cl.show(cards, currentMode);
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
		goToNoQuestion();
		reset();
		cQuestion.register();
		
		clientQMThread = new Thread(cQuestion, "ClientQuestionThread"); // required for continuous updating to receive question from server
		clientQMThread.start();
	}
	
	public void reset() {
		goToNoQuestion();
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
