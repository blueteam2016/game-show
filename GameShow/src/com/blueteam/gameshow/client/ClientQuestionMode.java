package com.blueteam.gameshow.client;
import javax.swing.*;
import java.util.ArrayList;

public class ClientQuestionMode extends JPanel
{
	private Question question;
	private JLabel questionText;
	private ArrayList<JLabel> answers;
	JPanel displayAnswers;
	
	ClientQuestionMode(ClientIO c, ClientQuestionScreen qs)
	{
		question = c.getQuestion();
		questionText = new JLabel(question.getQuestionText());
		answers = question.getAnswers();
		
		displayAnswers = new JPanel();
		displayAnswers.setLayout(new BoxLayout(displayAnswers, BoxLayout.Y_AXIS));
	}
}