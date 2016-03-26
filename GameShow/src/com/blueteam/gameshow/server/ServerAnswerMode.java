package com.blueteam.gameshow.server;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import com.blueteam.gameshow.data.Answer;


public class ServerAnswerMode extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JLabel questionLabel;
	private ArrayList<JLabel> answerLabels;
	private JLabel explanation;
	private JButton moveOn;
	private ServerGameScreen SGS;
	private Game game;

	public ServerAnswerMode(Game g, ServerGameScreen s){	
		SGS = s;
		game = g;
		
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		moveOn = new JButton("Continue");
		moveOn.addActionListener(this);

		newAnswer();
	}
	
	public void newAnswer(){		
		//adds question
		questionLabel = new JLabel("<html><span style='font-size:16px'>" + game.getQuiz().getCurrentQuestion().getText() + "</span></html>");
		
		//adds correct answer(s)
		answerLabels = new ArrayList<JLabel>();
		Answer[] answers = game.getQuiz().getCurrentQuestion().getAnswers();
		for(int i = 0; i< answers.length; i++){
			if(answers[i].isCorrect()) {
				JLabel answer = new JLabel("<html><span style='font-size:16px'>" +
										  (char)(65 + i) + ") " +
										  answers[i].getText() +
										  "</span></html>");
				answerLabels.add(answer);
				answer.setAlignmentX(LEFT_ALIGNMENT);
			}
		}
		
		//add explanation
		String explanationString = game.getQuiz().getCurrentQuestion().getExplanationText();
		if(explanationString != null){
			explanation = new JLabel("<html><span style='font-size:16px'>Explanation: " + explanationString + "</span></html>");
			explanation.setAlignmentX(LEFT_ALIGNMENT);
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
		answerInfo.add(questionLabel);
		answerInfo.add(Box.createRigidArea(new Dimension(0, 15)));
		for (int i = 0; i < answerLabels.size(); i++) {
			answerInfo.add(answerLabels.get(i));
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
