package com.blueteam.gameshow.client;
import javax.swing.*;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Question;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientQuestionMode extends JPanel
{
	private Question question;
	private JLabel questionText;
	private Answer[] answerChoices;
	private ArrayList<AnswerButton> answerButtons;
	private JPanel displayAnswers;
	private ClientIO cIO;
	
	public ClientQuestionMode(ClientIO c, ClientQuestionScreen qs) {
		cIO = c;
		question = null;
		while (question == null)
			cIO.getQuestion();
		questionText = new JLabel(question.getText());
		answerChoices = question.getAnswers();
		
		displayAnswers = new JPanel();
		displayAnswers.setLayout(new BoxLayout(displayAnswers, BoxLayout.Y_AXIS));
		
		for (int i = 0; i < answerChoices.length; i++)
		{
			AnswerButton answerSelect = new AnswerButton();
			answerButtons.add(answerSelect);
			JLabel answerText = new JLabel(answerChoices[i].getText());
			
			JPanel answer = new JPanel();
			answer.setLayout(new BoxLayout(answer, BoxLayout.X_AXIS));
			answer.add(answerSelect);
			answer.add(answerText);
		}
		
	}
	
	private class AnswerButton extends JButton implements ActionListener {
		public AnswerButton() {
			addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent event) {
			
		}
	}
}