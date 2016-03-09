package com.blueteam.gameshow.client;
import javax.swing.*;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Question;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientQuestionMode extends JPanel implements Runnable
{

	private static final long serialVersionUID = 1473664480186370825L;
	private ClientIO clientIO;
	private ClientQuestionScreen questScreen;
	private Question question;
	private JLabel questionText;
	private Answer[] answerChoices;
	private ArrayList<AnswerButton> answerButtons;
	private JPanel displayAnswers;
	
	public ClientQuestionMode(ClientQuestionScreen qs)
	{
		clientIO = qs.getClientIO();
		questScreen = qs;
	}
	
	@Override
	public void run() {
		question = clientIO.getQuestion();
		if (question == null) {
			questScreen.goToNoQuestion();
			while (question == null)
				question = clientIO.getQuestion();
		}
		questScreen.setQuestion(question);
		questionText = new JLabel(question.getText());
		answerChoices = question.getAnswers();
		
		answerButtons = new ArrayList<AnswerButton>();
		displayAnswers = new JPanel();
		displayAnswers.setLayout(new BoxLayout(displayAnswers, BoxLayout.Y_AXIS));
		
		for(int i = 0; i < answerChoices.length; i++)
		{
			AnswerButton answerSelect = new AnswerButton();
			answerButtons.add(answerSelect);
			JLabel answerText = new JLabel(answerChoices[i].getText());
			
			JPanel answer = new JPanel();
			answer.setLayout(new BoxLayout(answer, BoxLayout.X_AXIS));
			answer.add(answerSelect);
			answer.add(answerText);
			displayAnswers.add(answer);
		}
		
		this.add(questionText);
		this.add(displayAnswers);
		questScreen.goToQuestionMode();
	}
	
	class AnswerButton extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 688447363351164099L;
		
		public AnswerButton()
		{
			addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent arg0)
		{
			for (int i = 0; i < answerButtons.size(); i++)
				if (answerButtons.get(i).equals(arg0.getSource()))
					clientIO.sendAnswer(answerChoices[i]);
			questScreen.goToAnswerMode();
		}
	}
}
