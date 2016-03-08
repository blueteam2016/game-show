package com.blueteam.gameshow.server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.blueteam.gameshow.data.Answer;


public class ServerAnswerMode extends JPanel implements ActionListener{

	private JLabel question;
	private ArrayList<JLabel> answerLabel;
	private JLabel explanation;
	private JButton moveOn;
	
	
	public ServerAnswerMode(Game g, ServerGameScreen s){
		
		question = new JLabel(g.getQuiz().getCurrentQuestion().getText());
		
		explanation = new JLabel("Explanation: " + g.getQuiz().getCurrentQuestion().getExplanationText());
		
		answerLabel = new ArrayList<JLabel>();
		
		Answer[] answers = g.getQuiz().getCurrentQuestion().getAnswers();
		
		for(int x = 0; x< answers.length; x++){
			if(answers[x].isCorrect())
				answerLabel.add(new JLabel((char)(65+x) + ") " + answers[x].getText()));	
		}
		
		moveOn = new JButton("Continue");
		moveOn.addActionListener(this);
		
		add(question);
		for(int y = 0; y< answerLabel.size(); y++){
			
			add(answerLabel.get(y));
			
		}
		
		add(explanation);
		
		
		add(moveOn);
		
		
	}


	public void actionPerformed(ActionEvent arg0) {
		
		goToResultMode();
		
	}
	
}
