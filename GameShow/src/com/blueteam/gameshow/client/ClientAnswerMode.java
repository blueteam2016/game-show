package com.blueteam.gameshow.client;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JLabel timerLabel;
	private Timer timer;
	private int seconds;
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
			if (i == ClientQuestionMode.getChoice())
				answerText.setBackground(new Color(85, 140, 255));
			displayAnswers.add(answerText);
		}
		
		seconds = ClientQuestionMode.getseconds();
		
		timerLabel = new JLabel("Time Remaining: " + numberText(seconds/60) + ":" + numberText(seconds%60));
		timer = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				seconds -= 1;
				timerLabel.setText("Time Remaining: " + numberText(seconds/60) + ":" + numberText(seconds%60));
				if (seconds <= 0)
					timer.stop();
			}	
		});
	
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(questionText);
		this.add(displayAnswers);
		this.add(timerLabel);
	}
	
	private static String numberText(int timeNum)
	{
		String numberText = "";
		if (timeNum >= 10)
			numberText = "" + timeNum;
		else if (timeNum < 10 && timeNum > 0)
			numberText = "0" + timeNum;
		else
			numberText = "00";
		return numberText;
	}
	
}
