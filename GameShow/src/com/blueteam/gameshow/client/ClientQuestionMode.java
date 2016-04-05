package com.blueteam.gameshow.client;
import javax.swing.*;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Question;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

public class ClientQuestionMode extends JPanel implements Runnable {

	private static final long serialVersionUID = 1473664480186370825L;
	private ClientWindow clientWindow;
	private ClientQuestionScreen qScreen;
	private ClientIO clientIO;
	private JScrollPane scrollPane;
	private Question question;
	private JTextArea questionLabel;
	private ArrayList<JTextArea> answerLabels;
	private ArrayList<AnswerButton> answerButtons;
	private static int choice;
	private boolean receivedQuestions;
	private boolean inAnswerMode; // DON'T REMOVE! Checking state via ClientQuestionScreen causes a racetime condition, so a local boolean is necessary
	private float fontSize;
	private final static float DEFAULTFONTSIZE = 16;

	public ClientQuestionMode(ClientQuestionScreen qs) {
		clientWindow = qs.getClientWindow();
		qScreen = qs;
		fontSize = DEFAULTFONTSIZE;
		
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		receivedQuestions = false;
		inAnswerMode = false;
		
		qScreen.getWidth();
		
		addComponentListener(new ComponentAdapter() { 
			public void componentResized(ComponentEvent e) {
				if (scrollPane != null)
					scrollPane.setPreferredSize(scrollPane.getParent().getSize());
				updateFonts();
			} 
		});
		
	}
	
	public void register() {
		clientIO = clientWindow.getClientIO();
	}
	
	private void reset() {
		JOptionPane.showMessageDialog(null, "Lost connection to server!", "Server Error", JOptionPane.ERROR_MESSAGE);
		receivedQuestions = false;
		clientWindow.reset();
	}
	
	private void timesUpHandler() {
		if (receivedQuestions) {
			clientIO.clearAnswer();
			if (!inAnswerMode) {
				inAnswerMode = true;
				qScreen.goToAnswerMode();
			}
		} else {
			if (qScreen.getCurrentMode() != ClientQuestionScreen.NOQUESTIONMODE)
				qScreen.goToNoQuestionMode();
			inAnswerMode = false;
		}
	}
	
	@Override
	public void run() {
		inAnswerMode = false;
		while (!Thread.currentThread().isInterrupted()) {
			try {
				question = clientIO.getQuestion();
			} catch (EOFException e) {
				timesUpHandler();
			} catch (IOException e) {
				reset();
				return;
			}
			
			while (question == null) {
				if (Thread.currentThread().isInterrupted())
					return;
				try {
					question = clientIO.getQuestion();
				} catch (EOFException e) {
					timesUpHandler();
				} catch (IOException e) {
					reset();
					return;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
			
			if (!receivedQuestions)
				receivedQuestions = true;
			
			qScreen.setQuestion(question);
			
			newQuestion();
			
			qScreen.goToQuestionMode();
			inAnswerMode = false;
		}
	}
	
	public void newQuestion() {
		setLabels();
		setUpGUI();
		updateFonts();
		choice = -1;
	}
	
	private void updateFonts() {
		float newWidth = qScreen.getWidth();
		fontSize = (float)(DEFAULTFONTSIZE * (newWidth / 450.0));
		Font newFont = new Font(Font.DIALOG, Font.PLAIN, (int)fontSize);
		Dimension viewportSize = null;
		if (scrollPane != null)
			viewportSize = scrollPane.getViewport().getSize();
		if (questionLabel != null) {
			questionLabel.setFont(newFont);
			if (viewportSize != null) {
				Dimension prefSize = questionLabel.getPreferredSize();
				prefSize.setSize(viewportSize.getWidth() - (prefSize.getHeight() + 10), prefSize.getHeight());
				questionLabel.setSize(prefSize);
			}
		}
		if (answerLabels != null)
			for (JTextArea answerLabel : answerLabels) {
				answerLabel.setFont(newFont);
				if (viewportSize != null) {
					Dimension prefSize = answerLabel.getPreferredSize();
					prefSize.setSize(viewportSize.getWidth() - (prefSize.getHeight() + 10), prefSize.getHeight());
					answerLabel.setSize(prefSize);
					}
			}
		if (answerButtons != null)
			for (AnswerButton answerButton: answerButtons) {
				answerButton.setFont(newFont);
			}
		if (scrollPane != null)
			scrollPane.validate();
	}

	private void formatTextArea(JTextArea textArea) {
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		textArea.setBackground(getBackground());
	}
	
	private void setLabels() {
		Question currentQuestion = qScreen.getQuestion();
		//adds question
		questionLabel = new JTextArea(currentQuestion.getText());
		formatTextArea(questionLabel);
		questionLabel.setAlignmentX(LEFT_ALIGNMENT);
		//adds answer(s)
		answerLabels = new ArrayList<JTextArea>();
		answerButtons = new ArrayList<AnswerButton>();
		Answer[] answers = currentQuestion.getAnswers();
		for (int i = 0; i < answers.length; i++) {
			JTextArea answerLabel = new JTextArea(answers[i].getText());
			formatTextArea(answerLabel);
			answerLabels.add(answerLabel);
			answerLabel.setAlignmentX(LEFT_ALIGNMENT);
			
			AnswerButton answerSelect = new AnswerButton((char) (65 + i) + "");
			answerSelect.setSize(new Dimension(answerLabel.getHeight(), answerLabel.getHeight()));
			answerButtons.add(answerSelect);
			answerSelect.setAlignmentX(LEFT_ALIGNMENT);
		}
	}
	
	private void setUpGUI(){
		// organizes components in visually appealing manner
		removeAll();

		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		// Sets layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JPanel gridBagPanel = new JPanel(new GridBagLayout());
		scrollPane.setViewportView(gridBagPanel);
		GridBagConstraints constr = new GridBagConstraints();

		constr.gridx = 0;
		constr.gridy = 0;
		constr.gridwidth = 2;
		constr.gridheight = 1;
		constr.anchor = GridBagConstraints.WEST;
		constr.insets = new Insets(0,0,20,0);
        constr.weightx = 1.0;
        constr.fill = GridBagConstraints.HORIZONTAL;
		gridBagPanel.add(questionLabel, constr);
		
		for (int i = 0; i < answerLabels.size(); i++) {
			constr.gridx = 1;
			constr.gridy = i + 1;
			constr.gridwidth = 1;
			constr.gridheight = 1;
			constr.anchor = GridBagConstraints.WEST;
			if (i == answerLabels.size() - 1)
				constr.insets = new Insets(0,0,20,0);
			else
				constr.insets = new Insets(0,0,10,0);
			constr.fill = GridBagConstraints.HORIZONTAL;
			gridBagPanel.add(answerLabels.get(i), constr);
			constr.gridx = 0;
			constr.anchor = GridBagConstraints.CENTER;
			if (i == answerLabels.size() - 1)
				constr.insets = new Insets(0,0,20,10);
			else
				constr.insets = new Insets(0,0,10,10);
			constr.fill = GridBagConstraints.NONE;
			gridBagPanel.add(answerButtons.get(i), constr);
		}
		
		setAlignmentX(TOP_ALIGNMENT);
		add(scrollPane);
		add(Box.createRigidArea(new Dimension(0, 15)));
		scrollPane.setPreferredSize(scrollPane.getParent().getSize());
		validate();
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
			if (!inAnswerMode) {
				inAnswerMode = true;
				Answer[] answers = qScreen.getQuestion().getAnswers();
				for (int i = 0; i < answerButtons.size(); i++)
					if (answerButtons.get(i).equals(arg0.getSource()))
					{
						clientIO.sendAnswer(answers[i]);
						choice = i;
						break;
					}
				qScreen.goToAnswerMode();
			}
		}
	}
}

