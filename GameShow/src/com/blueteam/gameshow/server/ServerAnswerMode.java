package com.blueteam.gameshow.server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ServerAnswerMode extends JPanel implements ActionListener{

	private JLabel question;
	private ArrayList<JLabel> answers;
	private JLabel explanation;
	private JButton moveOn;
	
	
	public ServerAnswerMode(Game g, ServerGameScreen s){
question = new JLabel(g.getQuiz().getCurrentQuestion().getQuestionText());
		
		explanation = newJLabel("Explanation: " + g.getQuiz().getCurrentQuestion().getExplanation());
		
		answers = new ArrayList<JLabel>();
		
		for(int x = 0; x< g.getQuiz().getCurrentQuestion().getAnswers().size(); x++){
			if(g.getQuiz().getCurrentQuestion().getAnswer(x).isCorrect())
				answers.add(new JLabel((char)(65+x) + ") " + g.getQuiz().getCurrentQuestion().getAnswer(x)));	
		}
		
		moveOn = new JButton("Continue");
		moveOn.addActionListener(this);
		
		add(question);
		for(int y = 0; y< answers.size(); y++){
			
			add(answers.get(y));
			
		}
		
		add(explanation);
		
		
		add(moveOn);
		
		
	}


	public void actionPerformed(ActionEvent arg0) {
		
		goToResultMode();
		
	}
	
}
