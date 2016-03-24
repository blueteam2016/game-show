package com.blueteam.gameshow.server;
import javax.swing.JPanel;


public class ServerGameScreen {
	private ResultMode result;
	private ServerQuestionMode question;
	private ServerAnswerMode answer;
	private JPanel currentMode;
	private ServerWindow servWin;
	private Game game;
	private boolean firstQuestion = true;
	
	public ServerGameScreen(Game g, ServerWindow sw) {
		result = new ResultMode(g, this);
		question = new ServerQuestionMode(g, this);
		answer = new ServerAnswerMode(g, this);
		
		game = g;
		servWin = sw;
		
		result.update();
		currentMode = result;
	}

	public void forwardToAnswerMode() {
		game.getRoster().calculateScores(game.getQuiz().getCurrentQuestion().getPointValue());
	}
	
	public void goToAnswerMode() {
		game.clearQuestion();
		answer.newQuestion();
		game.getRoster().endQuestionScan();
		currentMode = answer;
		servWin.update();
	}

	public void goToQuestionMode() {
		if(firstQuestion){
			firstQuestion = false;
		}else{
			game.getQuiz().nextQuestion();
		}
			
		currentMode = question;
		question.newQuestion();
		question.startTimer();
		game.getRoster().startQuestion();
		
		servWin.update();
	}
	
	public void goToResultMode() {
		result.enableBack();
		if(game.getQuiz().isLastQuestion()) {
			result.lastQuestion();
		}
		game.getRoster().endQuestionScan();
		currentMode = result;
		result.update();
		
		servWin.update();
	}
	
	public void resizeResult(){
		result.update();
	}
	
	public JPanel getCurrentMode() {
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
