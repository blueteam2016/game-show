package com.blueteam.gameshow.client;
import javax.swing.*;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Question;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

public class ClientQuestionMode extends JPanel implements Runnable {

	private static final long serialVersionUID = 1473664480186370825L;
	private ClientWindow clientWindow;
	private ClientQuestionScreen questScreen;
	private ClientIO clientIO;
	private Question question;
	private JLabel questionLabel;
	private ArrayList<JLabel> answerLabels;
	private ArrayList<AnswerButton> answerButtons;
	private static int choice;
	private boolean receivedQuestions;
	private boolean inAnswerMode; // DON'T REMOVE! Checking state via ClientQuestionScreen causes a racetime condition, so a local boolean is necessary
	
	public ClientQuestionMode(ClientQuestionScreen qs) {
		clientWindow = qs.getClientWindow();
		questScreen = qs;

		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		receivedQuestions = false;
		inAnswerMode = false;
	}
	
	public void register() {
		clientIO = clientWindow.getClientIO();
	}
	
	@Override
	public void run() {
		inAnswerMode = false;
		while (!Thread.currentThread().isInterrupted()) {
			
			try {
				question = clientIO.getQuestion();
			} catch (EOFException e) {
				if (receivedQuestions) {
					if (!inAnswerMode) {
						inAnswerMode = true;
						questScreen.goToAnswerMode();
					}
				} else {
					if (questScreen.getCurrentMode() != ClientQuestionScreen.NOQUESTIONMODE)
						questScreen.goToNoQuestionMode();
					inAnswerMode = false;
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Lost connection to server!");
				receivedQuestions = false;
				clientWindow.reset();
				return;
			}
			
			while (question == null) {
				
				if (Thread.currentThread().isInterrupted())
					return;
				
				try {
					question = clientIO.getQuestion();
				} catch (EOFException e) {
					if (receivedQuestions) {
						if (!inAnswerMode) {
							inAnswerMode = true;
							questScreen.goToAnswerMode();
						}
					} else {
						if (questScreen.getCurrentMode() != ClientQuestionScreen.NOQUESTIONMODE)
							questScreen.goToNoQuestionMode();
						inAnswerMode = false;
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Lost connection to server!");
					receivedQuestions = false;
					clientWindow.reset();
					return;
				}
			}
			
			if (!receivedQuestions)
				receivedQuestions = true;
			questScreen.setQuestion(question);
			
			newQuestion();
			
			questScreen.goToQuestionMode();
			inAnswerMode = false;
		}
	}
	
	public void newQuestion() {
		//adds question
		questionLabel = new JLabel("<html><span style='font-size:12px'>" + question.getText() + "</span></html>");
		questionLabel.setAlignmentX(LEFT_ALIGNMENT);
		//adds answer(s)
		answerLabels = new ArrayList<JLabel>();
		answerButtons = new ArrayList<AnswerButton>();
		Answer[] answers = question.getAnswers();
		for (int i = 0; i < answers.length; i++) {
			JLabel answer = new JLabel("<html><span style='font-size:12px'>" +
		 			        		   answers[i].getText() +
									   "</span></html>");
			answerLabels.add(answer);
			answer.setAlignmentX(LEFT_ALIGNMENT);
			
			AnswerButton answerSelect = new AnswerButton((char) (65 + i) + "");
			answerSelect.setSize(new Dimension(answer.getHeight(), answer.getHeight()));
			answerButtons.add(answerSelect);
			answerSelect.setAlignmentX(LEFT_ALIGNMENT);
		}
		
		setUpGUI();
	}
	
	private void setUpGUI(){
		// organizes components in visually appealing manner
		removeAll();
		
		// Sets layout
		setLayout(new BorderLayout());
		JPanel gridBagPanel = new JPanel(new GridBagLayout());
		GridBagConstraints constr = new GridBagConstraints();

		constr.gridx = 0;
		constr.gridy = 0;
		constr.gridwidth = 2;
		constr.gridheight = 1;
		constr.anchor = GridBagConstraints.WEST;
		constr.insets = new Insets(0,0,10,0);
		gridBagPanel.add(questionLabel, constr);
		
		for (int i = 0; i < answerLabels.size(); i++) {
			constr.gridx = 0;
			constr.gridy = i + 1;
			constr.gridwidth = 1;
			constr.gridheight = 1;
			constr.anchor = GridBagConstraints.CENTER;
			constr.insets = new Insets(0,0,10,10);
			gridBagPanel.add(answerButtons.get(i), constr);
			constr.gridx = 1;
			constr.anchor = GridBagConstraints.WEST;
			constr.insets = new Insets(0,0,10,0);
			gridBagPanel.add(answerLabels.get(i), constr);
		}
		
		setAlignmentX(TOP_ALIGNMENT);
		
		add(gridBagPanel, BorderLayout.NORTH);

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
			inAnswerMode = true;
			Answer[] answers = questScreen.getQuestion().getAnswers();
			for (int i = 0; i < answerButtons.size(); i++)
				if (answerButtons.get(i).equals(arg0.getSource()))
				{
					clientIO.sendAnswer(answers[i]);
					choice = i;
				}
			questScreen.goToAnswerMode();
		}
	}
}

