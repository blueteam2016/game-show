package com.blueteam.gameshow.client;
import javax.swing.*;

import com.blueteam.gameshow.client.ClientQuestionMode.AnswerButton;
import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Question;

import java.util.ArrayList;

public class ClientAnswerMode extends JPanel
{
	private ClientQuestionScreen qs;
	private Question question;
	private JLabel questionText;
	private Answer[] answerChoices;
	private JPanel displayAnswers;
	
	public ClientAnswerMode(ClientQuestionScreen qs)
	{
		this.qs = qs;
		question = qs.getQuestion();
		questionText = new JLabel(question.getText());
		answerChoices = question.getAnswers();
		
	}
}
