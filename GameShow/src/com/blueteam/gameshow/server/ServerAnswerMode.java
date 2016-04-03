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
	private ServerGameScreen sgScreen;
	private Game game;
	private int fontSize;
	private float currentWidth;

	public ServerAnswerMode(Game g, ServerGameScreen s){	
		sgScreen = s;
		game = g;
		fontSize = 16;
		
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		moveOn = new JButton("Continue");
		moveOn.addActionListener(this);

		currentWidth = sgScreen.getWidth();
		
		addComponentListener(new ComponentAdapter() { 
			public void componentResized(ComponentEvent e) {
				float newWidth = sgScreen.getWidth();
				if (newWidth != currentWidth) {
					fontSize = (int)(16 * (newWidth / 450.0));
					currentWidth = newWidth;
					setLabels();
					setUpGUI();
				}
			} 
		});

		newAnswer();
	}
	
	public void newAnswer(){		
		setLabels();
		setUpGUI();
	}
	
	private void setLabels() {
		//adds question
		questionLabel = new JLabel("<html><table><tr><td width='" + currentWidth + "'><span style='font-size:" + fontSize + "px'>" + game.getQuiz().getCurrentQuestion().getText() + "</span></td></tr></table></html>");
		
		//adds correct answer(s)
		answerLabels = new ArrayList<JLabel>();
		Answer[] answers = game.getQuiz().getCurrentQuestion().getAnswers();
		for(int i = 0; i< answers.length; i++){
			if(answers[i].isCorrect()) {
				JLabel answer = new JLabel("<html><table><tr><td width='" + currentWidth + "'><span style='font-size:" + fontSize + "px'>" +
										  (char)(65 + i) + ") " +
										  answers[i].getText() +
										  "</span></td></tr></table></html>");
				answerLabels.add(answer);
				answer.setAlignmentX(LEFT_ALIGNMENT);
			}
		}
		
		//add explanation
		String explanationString = game.getQuiz().getCurrentQuestion().getExplanationText();
		if(explanationString != null){
			explanation = new JLabel("<html><table><tr><td width='" + currentWidth + "'><span style='font-size:" + fontSize + "px'>Explanation: " + explanationString + "</span></td></tr></table></html>");
			explanation.setAlignmentX(LEFT_ALIGNMENT);
		}else{
			explanation = null;
		}
		
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
		if(explanation!= null){
			answerInfo.add(explanation);
		}
		answerInfo.setAlignmentX(CENTER_ALIGNMENT);
		add(answerInfo);
		add(Box.createRigidArea(new Dimension(0, 15)));
		
		//add button
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(moveOn);
		buttonPanel.setAlignmentX(JButton.CENTER_ALIGNMENT);
		add(buttonPanel);
		sgScreen.getServerWindow().update();
	}

	public void actionPerformed(ActionEvent arg0) {
		sgScreen.goToResultMode();
	}
	
}
