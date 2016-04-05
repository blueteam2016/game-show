package com.blueteam.gameshow.server;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import com.blueteam.gameshow.data.Answer;


public class ServerAnswerMode extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JTextArea questionLabel;
	private ArrayList<JTextArea> answerLabels;
	private JTextArea explanation;
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
				resizeText();
			} 
		});

		newAnswer();
	}
	
	public void resizeText(){
		float newWidth = sgScreen.getWidth();
		if (newWidth != currentWidth) {
			fontSize = (int)(16 * (newWidth / 450.0));
			currentWidth = newWidth;
			setLabels();
			setUpGUI();
			while(getHeight()>ServerWindow.accessFrame().getHeight()){
				fontSize -=5;
				setLabels();
				setUpGUI();
			}
		}
	}
	
	public void newAnswer(){		
		setLabels();
		setUpGUI();
	}
	
	private void setLabels() {
		
		//set font size of button
		moveOn.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
		
		//adds question
		questionLabel = new JTextArea(game.getQuiz().getCurrentQuestion().getText());
		questionLabel.setEditable(false);
		questionLabel.setLineWrap(true);
		questionLabel.setWrapStyleWord(true);
		questionLabel.setBackground(getBackground());
		questionLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
		questionLabel.setAlignmentX(RIGHT_ALIGNMENT);
		
		//adds correct answer(s)
		answerLabels = new ArrayList<JTextArea>();
		Answer[] answers = game.getQuiz().getCurrentQuestion().getAnswers();
		for(int i = 0; i< answers.length; i++){
			if(answers[i].isCorrect()) {
				JTextArea answer = new JTextArea((char)(65 + i) + ") " +answers[i].getText());
				answer.setEditable(false);
				answer.setLineWrap(true);
				answer.setWrapStyleWord(true);
				answer.setBackground(getBackground());
				answer.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
				answerLabels.add(answer);
				answer.setAlignmentX(JTextArea.RIGHT_ALIGNMENT);
				//right alignment actually means left alignment or it works that way at least
			}
		}
		
		//add explanation
		String explanationString = game.getQuiz().getCurrentQuestion().getExplanationText();
		if(explanationString != null){
			explanation = new JTextArea(explanationString);
			explanation.setEditable(false);
			explanation.setLineWrap(true);
			explanation.setWrapStyleWord(true);
			explanation.setBackground(getBackground());
			explanation.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));			
			explanation.setAlignmentX(RIGHT_ALIGNMENT);
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
		//answerInfo.setAlignmentX(CENTER_ALIGNMENT);
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
