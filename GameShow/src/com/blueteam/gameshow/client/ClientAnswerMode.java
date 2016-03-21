package com.blueteam.gameshow.client;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Question;

public class ClientAnswerMode extends JPanel
{
	
	private static final long serialVersionUID = 159399403085037876L;
	private ClientQuestionScreen qs;
	private Question question;
	private JLabel questionText;
	private Answer[] answerChoices;
	private JPanel displayAnswers;
	private JLabel[] answers;

	public ClientAnswerMode(ClientQuestionScreen qs)
	{
		this.qs = qs;
	}

	public void update() {
		removeAll();

		question = qs.getQuestion();
		questionText = new JLabel(question.getText());

		answerChoices = question.getAnswers();
		answers = new JLabel[answerChoices.length];

		displayAnswers = new JPanel();
		displayAnswers.setLayout(new BoxLayout(displayAnswers, BoxLayout.PAGE_AXIS));

		for(int i = 0; i < answerChoices.length; i++){
			JLabel answerText = new JLabel(answerChoices[i].getText());
			if (i == ClientQuestionMode.getChoice()){
				answerText.setForeground(Color.RED);
			}
			answers[i] = answerText;
			displayAnswers.add(answerText);
			answerText.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		}

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(questionText);
		add(Box.createRigidArea(new Dimension(25,25)));
		add(displayAnswers);
	}
}
