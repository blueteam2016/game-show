package com.blueteam.gameshow.client;
import javax.swing.*;
import java.util.ArrayList;

public class ClientQuestionMode extends JPanel
{
	private Question question;
	private JLabel questionText;
	private ArrayList<Answer> answerChoices;
	private JPanel displayAnswers;
	
	ClientQuestionMode(ClientIO c, ClientQuestionScreen qs)
	{
		question = c.getQuestion();
		questionText = new JLabel(question.getQuestionText());
		answerChoices = question.getAnswers();
		
		displayAnswers = new JPanel();
		displayAnswers.setLayout(new BoxLayout(displayAnswers, BoxLayout.Y_AXIS));
		
		for(int i = 0; i < answerChoices.size(); i++)
			
		
	}
}