package com.blueteam.gameshow.client;
import javax.swing.*;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.EmptyFileException;
import com.blueteam.gameshow.data.Question;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ClientQuestionMode extends JPanel implements Runnable {

	private static final long serialVersionUID = 1473664480186370825L;
	private ClientWindow clientWindow;
	private ClientQuestionScreen questScreen;
	private ClientIO clientIO;
	private Question question;
	private JLabel questionText;
	private Answer[] answerChoices;
	private ArrayList<AnswerButton> answerButtons;
	private JPanel displayAnswers;
	private JPanel displayButtons;
	private static int choice;
	private boolean receivedQuestions;
	
	public ClientQuestionMode(ClientQuestionScreen qs) {
		clientWindow = qs.getClientWindow();
		questScreen = qs;
		receivedQuestions = false;
	}
	
	public void register() {
		clientIO = clientWindow.getClientIO();
	}
	
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				question = clientIO.getQuestion();
			} catch (EmptyFileException e) {
				if (receivedQuestions) {
					questScreen.goToAnswerMode();
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Lost connection to server!");
				clientWindow.reset();
				return;
			}
			while (question == null) {
				if (Thread.currentThread().isInterrupted())
					return;
				try {
					question = clientIO.getQuestion();
				} catch (EmptyFileException e) {
					if (receivedQuestions) {
						questScreen.goToAnswerMode();
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Lost connection to server!");
					clientWindow.reset();
					return;
				}
			}
			if (!receivedQuestions)
				receivedQuestions = true;
			questScreen.setQuestion(question);
				
			setUpGUI();
			questScreen.goToQuestionMode();
		}
	}
	
	private void setUpGUI(){
		this.removeAll();
		questionText = new JLabel(question.getText());
		answerChoices = question.getAnswers();
	
		answerButtons = new ArrayList<AnswerButton>();
		displayAnswers = new JPanel();
		displayAnswers.setLayout(new BoxLayout(displayAnswers, BoxLayout.Y_AXIS));
		displayButtons = new JPanel();
		displayButtons.setLayout(new BoxLayout(displayButtons, BoxLayout.PAGE_AXIS));
	
		for(int i = 0; i < answerChoices.length; i++) {
			displayAnswers.add(Box.createRigidArea(new Dimension(5,5)));
			JLabel answerText = new JLabel(answerChoices[i].getText());
			answerText.setFont(new Font(answerText.getFont().getName(), Font.PLAIN, 16));
			displayAnswers.add(answerText);
			answerText.setAlignmentX(JLabel.LEFT_ALIGNMENT);
			
			AnswerButton answerSelect = new AnswerButton((char) (65 + i) + "");
			answerSelect.setSize(new Dimension(answerText.getHeight(), answerText.getHeight()));
			answerButtons.add(answerSelect);
			displayButtons.add(answerSelect);
			answerSelect.setAlignmentX(JButton.CENTER_ALIGNMENT);
		}
		
		JPanel answerStuff = new JPanel();
		answerStuff.add(displayButtons);
		answerStuff.add(displayAnswers);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(questionText);
		questionText.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
		this.add(answerStuff);
		choice = -1;
	}

	public static int getChoice() {
		return choice;
	}
	
	class AnswerButton extends JButton implements ActionListener {
		private static final long serialVersionUID = 688447363351164099L;
		
		public AnswerButton(String text)
		{
			super(text);
			addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent arg0)
		{
			for (int i = 0; i < answerButtons.size(); i++)
				if (answerButtons.get(i).equals(arg0.getSource()))
				{
					clientIO.sendAnswer(answerChoices[i]);
					choice = i;
				}
			questScreen.goToAnswerMode();
		}
	}
}

