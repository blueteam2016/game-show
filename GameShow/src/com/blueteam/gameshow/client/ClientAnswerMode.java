package com.blueteam.gameshow.client;
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
	
	public ClientAnswerMode(ClientQuestionScreen qs)
	{
		this.qs = qs;
	}
	
	public void update() {
		question = qs.getQuestion();
		questionText = new JLabel(question.getText());
		answerChoices = question.getAnswers();
	}
	
}
