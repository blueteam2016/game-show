package com.blueteam.gameshow.server;
import javax.swing.JPanel;


public class ServerGameScreen{
	private ResultMode result;
	private ServerQuestionMode question;
	private ServerAnswerMode answer;
	
	private JPanel currentMode;
	
	private ServerWindow servWin;
	
	Game game;
	
	ServerGameScreen(Game g, ServerWindow sw){
		result = new ResultMode(g, this);
		question = new ServerQuestionMode(g, this);
		answer = new ServerAnswerMode(g, this);
		
		game = g;
		servWin = sw;
		
		currentMode = question;
	}

	public void goToAnswerMode(){
		answer.newQuestion();
		
		currentMode = answer;
		servWin.update();
	}
	public void goToQuestionMode(){
		//maybe call start/end Question in Roster
		currentMode = question;
		game.getQuiz().nextQuestion();
		
		question.newQuestion();
		question.startTimer();
		
		servWin.update();
		
	}
	public void goToResultMode(){
		if(game.getQuiz().isLastQuestion()){
			result.lastQuestion();
		}
		currentMode = result;
		result.update();
		
		servWin.update();
	}
	
	public JPanel getCurrentMode(){
		return currentMode;
	}

}
