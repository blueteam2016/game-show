package com.blueteam.gameshow.client;
import javax.swing.*;

import com.blueteam.gameshow.data.Answer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientQuestionMode extends JPanel
{
	private Question question;
	private JLabel questionText;
	private ArrayList<Answer> answerChoices;
	private ArrayList<AnswerSelect> answerButtons;
	private JPanel displayAnswers;
	
	ClientQuestionMode(ClientIO c, ClientQuestionScreen qs)
	{
		question = c.getQuestion();
		questionText = new JLabel(question.getQuestionText());
		answerChoices = question.getAnswers();
		
		displayAnswers = new JPanel();
		displayAnswers.setLayout(new BoxLayout(displayAnswers, BoxLayout.Y_AXIS));
		
		for(int i = 0; i < answerChoices.size(); i++)
		{
			JButton answerSelect = new AnswerButton();
			answerButtons.add(answerSelect);
			JLabel answerText = new JLabel(answerChoices.get(i).getAnswerText();
			
			JPanel answer = new JPanel();
			answer.setLayout(new BoxLayout(answer, BoxLayout.X_AXIS));
			answer.add(answerSelect);
			answer.add(answerText);
		}
		
	}
	class AnswerButton extends JButton implements ActionListener
	{
		AnswerButton()
		{
			addActionListener(this);
		}
		public void actionPerformed(ActionEvent arg0)
		{
			
		}
	}
}