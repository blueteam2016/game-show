package com.blueteam.gameshow.server;
import java.awt.CardLayout;

import javax.swing.JPanel;


public class ServerGameScreen extends JPanel {
	
	private static final long serialVersionUID = -2603169182746777682L;
	public static final String QUESTIONMODE = "Question Mode";
	public static final String ANSWERMODE = "Answer Mode";
	public static final String RESULTMODE = "Result Mode";
	private ServerResultMode result;
	private ServerQuestionMode question;
	private ServerAnswerMode answer;
	private ServerWindow servWin;
	private Game game;
	private boolean firstQuestion = true;
	private String currentMode;
	
	public ServerGameScreen(Game g, ServerWindow sw) {
		setLayout(new CardLayout());
		
		game = g;
		servWin = sw;
	}
	
	public void startGame() {
		result = new ServerResultMode(game, this);
		question = new ServerQuestionMode(game, this);
		answer = new ServerAnswerMode(game, this);
		add(result, RESULTMODE);
		add(question, QUESTIONMODE);
		add(answer, ANSWERMODE);
		result.update();
		goToResultMode();
	}

	public void forwardToAnswerMode() {
		game.getRoster().calculateScores(game.getQuiz().getCurrentQuestion().getPointValue());
	}
	
	public void goToAnswerMode() {
		game.getRoster().endQuestionScan();
		game.clearQuestion();
		answer.newAnswer();
		CardLayout cl = (CardLayout)getLayout();
		currentMode = ANSWERMODE;
		cl.show(this, currentMode);
		servWin.update();
	}

	public void goToQuestionMode() {
		if(firstQuestion){
			firstQuestion = false;
		}else{
			game.getQuiz().nextQuestion();
		}
		question.newQuestion();
		question.startTimer();
		game.getRoster().startQuestion();
		CardLayout cl = (CardLayout)getLayout();
		currentMode = QUESTIONMODE;
		cl.show(this, currentMode);
		servWin.update();
	}

	public void goToResultMode() {
		result.enableBack();
		if(game.getQuiz().isLastQuestion()) {
			result.lastQuestion();
		}
		game.getRoster().endQuestionScan();
		CardLayout cl = (CardLayout)getLayout();
		currentMode = RESULTMODE;
		cl.show(this, currentMode);
		result.update();
		servWin.update();
	}
	
	public void resizeResult(){
		result.update();
	}
	
	public String getCurrentMode() {
		return currentMode;
	}
	
	public ServerQuestionMode getServerQuestionMode() {
		return question;
	}
	
	public boolean onQuestionMode() {
		if(currentMode.equals(question)) {
			return true;
		}return false;
	}

}
