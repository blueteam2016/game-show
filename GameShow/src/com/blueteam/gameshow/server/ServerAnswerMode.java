package com.blueteam.gameshow.server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.blueteam.gameshow.data.Answer;


public class ServerAnswerMode extends JPanel implements ActionListener{

	private JLabel question;
	private ArrayList<JLabel> answerLabel;
	private JLabel explanation;
	private JButton moveOn;
	private ServerGameScreen SGS;
	private Game game;
	
	public ServerAnswerMode(Game g, ServerGameScreen s){	
		SGS = s;
		game = g;
		
		moveOn = new JButton("Continue");
		moveOn.addActionListener(this);
		
		
		newQuestion();
	}
	
	public void newQuestion(){
		removeAll();

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		question = new JLabel(game.getQuiz().getCurrentQuestion().getText());
		
		
		String explanationString = game.getQuiz().getCurrentQuestion().getExplanationText();
		if(explanationString != null)
			explanation = new JLabel("Explanation: " + game.getQuiz().getCurrentQuestion().getExplanationText());
		
		answerLabel = new ArrayList<JLabel>();
		
		Answer[] answers = game.getQuiz().getCurrentQuestion().getAnswers();
		
		for(int x = 0; x< answers.length; x++){
			if(answers[x].isCorrect())
				answerLabel.add(new JLabel((char)(65+x) + ") " + answers[x].getText()));	
		}
		
		add(question);
		for(int y = 0; y< answerLabel.size(); y++){
			
			add(answerLabel.get(y));
			
		}
		
		if(explanationString != null)
			add(explanation);
		
		add(moveOn);
	}


	public void actionPerformed(ActionEvent arg0) {
		SGS.goToResultMode();
	}
	
}
