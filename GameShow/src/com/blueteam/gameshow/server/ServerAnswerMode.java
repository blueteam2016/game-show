package com.blueteam.gameshow.server;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import com.blueteam.gameshow.data.Answer;


public class ServerAnswerMode extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JLabel question;
	private ArrayList<JLabel> answerLabel;
	private JLabel explanation;
	private JButton moveOn;
	private ServerGameScreen SGS;
	private Game game;

	private ArrayList<JLabel> allLabels;

	public ServerAnswerMode(Game g, ServerGameScreen s){	
		SGS = s;
		game = g;
		
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		moveOn = new JButton("Continue");
		moveOn.addActionListener(this);

		newQuestion();
	}
	
	public void newQuestion(){		
		//adds question
		question = new JLabel("<html>" + game.getQuiz().getCurrentQuestion().getText() + "</html>");
		
		//adds correct answer(s)
		answerLabel = new ArrayList<JLabel>();
		Answer[] answers = game.getQuiz().getCurrentQuestion().getAnswers();
		for(int x = 0; x< answers.length; x++){
			if(answers[x].isCorrect()) {
				JLabel answer = new JLabel("<html>" + (char)(65+x) + ") " + answers[x].getText() + "</html>");
				answerLabel.add(answer);
				answer.setAlignmentX(Component.LEFT_ALIGNMENT);
			}
		}
		
		//add explanation
		String explanationString = game.getQuiz().getCurrentQuestion().getExplanationText();
		if(explanationString != null){
			explanation = new JLabel("<html>Explanation: " + explanationString + "</html>");
			explanation.setAlignmentX(Component.LEFT_ALIGNMENT);
		}
		
		setUpGUI();
	}
	
	private void setUpGUI() {
		// organizes components in visually appealing manner
		
		removeAll();
		
		// Sets layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JPanel answerInfo = new JPanel();
		answerInfo.setLayout(new BoxLayout(answerInfo, BoxLayout.PAGE_AXIS));
		answerInfo.add(question);
		answerInfo.add(Box.createRigidArea(new Dimension(0, 15)));
		for (int i = 0; i < answerLabel.size(); i++) {
			answerInfo.add(answerLabel.get(i));
			answerInfo.add(Box.createRigidArea(new Dimension(0, 5)));
		}
		answerInfo.add(Box.createRigidArea(new Dimension(0, 10)));
		answerInfo.add(explanation);
		answerInfo.setAlignmentX(CENTER_ALIGNMENT);
		add(answerInfo);
		add(Box.createRigidArea(new Dimension(0, 15)));
		
		//add button
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(moveOn);
		buttonPanel.setAlignmentX(JButton.CENTER_ALIGNMENT);
		add(buttonPanel);
	}

	public void actionPerformed(ActionEvent arg0) {
		SGS.goToResultMode();
	}
	
}
