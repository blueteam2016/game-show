package com.blueteam.gameshow.client;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Question;

public class ClientAnswerMode extends JPanel
{
	
	private static final long serialVersionUID = 159399403085037876L;
	private ClientQuestionScreen qs;
	private Question question;
	private JLabel questionLabel;
	private ArrayList<JLabel> answerLabels;

	public ClientAnswerMode(ClientQuestionScreen qs)
	{
		this.qs = qs;

		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	}

	public void newAnswer() {
		//adds question
		question = qs.getQuestion();
		questionLabel = new JLabel("<html><span style='font-size:12px'>" + question.getText() + "</span></html>");
		questionLabel.setAlignmentX(LEFT_ALIGNMENT);
		//adds answer(s)
		answerLabels = new ArrayList<JLabel>();
		Answer[] answers = question.getAnswers();
		for (int i = 0; i < answers.length; i++) {
			JLabel answer = new JLabel("<html><span style='font-size:12px'>" +
		 			        		   answers[i].getText() +
									   "</span></html>");
			answerLabels.add(answer);
			answer.setAlignmentX(LEFT_ALIGNMENT);
			answer.setOpaque(true);
		}
		setUpGUI();
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
			if (i == ClientQuestionMode.getChoice()){
				answerLabels.get(i).setBackground(new Color(0, 0, 0));
				answerLabels.get(i).setForeground(new Color(255, 255, 255));
			}
		}
		add(answerInfo);
	}
	
}
