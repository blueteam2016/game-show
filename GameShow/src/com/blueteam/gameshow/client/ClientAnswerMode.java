package com.blueteam.gameshow.client;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.*;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Question;

public class ClientAnswerMode extends JPanel
{
	
	private static final long serialVersionUID = 159399403085037876L;
	private ClientQuestionScreen qScreen;
	private Question question;
	private JLabel questionLabel;
	private ArrayList<JLabel> answerLabels;
	private int fontSize;
	private float oldWidth;

	public ClientAnswerMode(ClientQuestionScreen qs)
	{
		this.qScreen = qs;
		fontSize = 12;
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		oldWidth = qScreen.getWidth();
		
		addComponentListener(new ComponentAdapter() { 
			public void componentResized(ComponentEvent e) {
				float newWidth = qScreen.getWidth();
				if (newWidth != oldWidth) {
					if (qScreen.getQuestion() != null) {
						fontSize = (int)(12 * (newWidth / 450.0));
						setLabels();
						setUpGUI();
					}
				}
			} 
		});
	}

	public void newAnswer() {
		setLabels();
		setUpGUI();
	}
	
	private void setLabels() {
		//adds question
		Dimension textMaxSize = new Dimension();
		textMaxSize.setSize(qScreen.getWidth(), Double.POSITIVE_INFINITY);
		question = qScreen.getQuestion();
		questionLabel = new JLabel("<html><span style='font-size:" + fontSize + "px'>" + question.getText() + "</span></html>");
		questionLabel.setAlignmentX(LEFT_ALIGNMENT);
		questionLabel.setMaximumSize(textMaxSize);
		//adds answer(s)
		answerLabels = new ArrayList<JLabel>();
		Answer[] answers = question.getAnswers();
		for (int i = 0; i < answers.length; i++) {
			JLabel answer = new JLabel("<html><span style='font-size:" + fontSize + "px'>" +
		 			        		   answers[i].getText() +
									   "</span></html>");
			answerLabels.add(answer);
			answer.setAlignmentX(LEFT_ALIGNMENT);
			answer.setMaximumSize(textMaxSize);
			answer.setOpaque(true);
			if (i == ClientQuestionMode.getChoice()){
				answer.setBackground(new Color(0, 0, 0));
				answer.setForeground(new Color(255, 255, 255));
			}
		}
	}
	
	private void setUpGUI(){
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
		add(answerInfo);
		qScreen.getClientWindow().update();
	}
	
}
