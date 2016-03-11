package com.blueteam.gameshow.server;
import javax.swing.JPanel;


public class ServerGameScreen extends JPanel{
	private ResultMode result;
	private ServerQuestionMode question;
	private ServerAnswerMode answer;
	
	private JPanel currentMode;
	
	Game game;
	
	ServerGameScreen(Game g){
		result = new ResultMode(g, this);
		question = new ServerQuestionMode(g, this);
		answer = new ServerAnswerMode(g, this);
		
		game = g;
		
		goToQuestionMode();
	}
	
	public void goToAnswerMode(){
		currentMode = answer;
	}
	public void goToQuestionMode(){
		//maybe call start/end Question in Roster
		currentMode = question;
		game.getQuiz().nextQuestion();
		question.startTimer();
		
	}
	public void goToResultMode(){
		if(game.getQuiz().isLastQuestion()){
			result.lastQuestion();
		}
		currentMode = result;
		result.update();
	}
	
	public JPanel getCurrentMode(){
		return currentMode;
		
	}

}
