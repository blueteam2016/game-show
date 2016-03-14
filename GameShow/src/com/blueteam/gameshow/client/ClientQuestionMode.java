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
	private ClientWindow clientWindow;
	private ClientQuestionScreen questScreen;
	private ClientIO clientIO;
	private Question question;
	private JLabel questionText;
	private JLabel timerLabel;
	private int seconds;
	private Timer timer;
	private Answer[] answerChoices;
	private ArrayList<AnswerButton> answerButtons;
	private JPanel displayAnswers;
	
	public ClientQuestionMode(ClientQuestionScreen qs)
	{
		clientWindow = qs.getClientWindow();
		questScreen = qs;
	}
	
	public void register() {
		clientIO = clientWindow.getClientIO();
	}
	
	@Override
	public void run() {
		while (true) {
			question = clientIO.getQuestion();
			while (question == null)
				question = clientIO.getQuestion();
		
			seconds = question.getTime();
			questScreen.setQuestion(question);
			this.removeAll();
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
			
			timerLabel = new JLabel("Time Remaining: " + seconds/60 + ":" + secondsText(seconds));
			timer = new Timer(1000, new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
					seconds -= 1;
					timerLabel.setText("Time Remaining: " + seconds/60 + ":" + secondsText(seconds));
					if (seconds <= 0){
						timer.stop();
						questScreen.goToAnswerMode();
					}
				}	
			});
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(questionText);
			this.add(displayAnswers);
			this.add(timerLabel);
			questScreen.goToQuestionMode();
		}
	}
	public void startTimer()
	{	
		timer.start();	
	}	
	public void stopTimer()
	{		
		timer.stop();
	}
	private static String secondsText(int seconds)
	{
		String secondsText = "";
		if (seconds % 60 == 0)
			secondsText = "00";
		else if (seconds % 60 < 10)
			secondsText = "0" + seconds;
		else
			secondsText = "" + seconds%60;
		return secondsText;
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