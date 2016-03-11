package com.blueteam.gameshow.client;
import java.util.ArrayList;

import javax.swing.*;

import com.blueteam.gameshow.client.ClientQuestionMode.AnswerButton;
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
	
	public ClientAnswerMode(ClientQuestionScreen qs)
	{
		this.qs = qs;
	}
	
	public void update() {
		question = qs.getQuestion();
		questionText = new JLabel(question.getText());
		answerChoices = question.getAnswers();
		
		displayAnswers = new JPanel();
		displayAnswers.setLayout(new BoxLayout(displayAnswers, BoxLayout.Y_AXIS));
	
		for(int i = 0; i < answerChoices.length; i++)
		{
			JLabel answerText = new JLabel(answerChoices[i].getText());
		
			JPanel answer = new JPanel();
			displayAnswers.add(answerText);
		}
	
		this.add(questionText);
		this.add(displayAnswers);
	}
	
}
