package com.blueteam.gameshow.server;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.blueteam.gameshow.data.Answer;


public class ServerAnswerMode extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollPane;
	private JTextArea questionLabel;
	private JButton moveOn;
	private ServerGameScreen sgScreen;
	private Game game;
	private final static float DEFAULTFONTSIZE = 24;
	private float fontSize;

	public ServerAnswerMode(Game g, ServerGameScreen s){	
		sgScreen = s;
		game = g;
		fontSize = DEFAULTFONTSIZE;
		
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		moveOn = new JButton("Continue");
		moveOn.addActionListener(this);

		sgScreen.getWidth();
		
		addComponentListener(new ComponentAdapter() { 
			public void componentResized(ComponentEvent e) {
				if (scrollPane != null)
					scrollPane.setPreferredSize(scrollPane.getParent().getSize());
				updateFonts();
			} 
		});

		newAnswer();
	}
	
	public void newAnswer(){		
		setLabels();
		setUpGUI();
		updateFonts();
	}
	
	private void updateFonts() {
		float newWidth = sgScreen.getWidth();
		fontSize = (float)(DEFAULTFONTSIZE * (newWidth / 450.0));
		if (questionLabel != null) {
			questionLabel.setFont(questionLabel.getFont().deriveFont(fontSize));
			questionLabel.setSize(scrollPane.getViewport().getSize());
		}
		if (moveOn != null)
			moveOn.setFont(moveOn.getFont().deriveFont(fontSize));
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
		
		//set font size of button
		moveOn.setFont(new Font(Font.DIALOG, Font.PLAIN, (int)fontSize));
		
		//adds question
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(game.getQuiz().getCurrentQuestion().getText() + "\n\n");
		//adds correct answer(s)
		Answer[] answers = game.getQuiz().getCurrentQuestion().getAnswers();
		for(int i = 0; i< answers.length; i++){
			if(answers[i].isCorrect()) {
				strBuilder.append((char)(65 + i) + ") " + answers[i].getText() + "\n");
			}
		}
		//add explanation
		String explanationString = game.getQuiz().getCurrentQuestion().getExplanationText();
		if(explanationString != null){
			strBuilder.append("\nExplanation: " + explanationString);
		}
		
		questionLabel = new JTextArea(strBuilder.toString());
		formatTextArea(questionLabel);
	}
	
	private void setUpGUI() {
		// organizes components in visually appealing manner
		removeAll();
		
		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		// Sets layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JPanel answerInfo = new JPanel();
		answerInfo.setLayout(new BoxLayout(answerInfo, BoxLayout.PAGE_AXIS));
		scrollPane.setViewportView(answerInfo);
		answerInfo.add(questionLabel);
		answerInfo.setAlignmentX(CENTER_ALIGNMENT);
		add(scrollPane);
		add(Box.createRigidArea(new Dimension(0, 15)));
		
		//add button
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(moveOn);
		buttonPanel.setAlignmentX(JButton.CENTER_ALIGNMENT);
		add(buttonPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
		scrollPane.setPreferredSize(scrollPane.getParent().getSize());
		validate();
	}

	public void actionPerformed(ActionEvent arg0) {
		sgScreen.goToResultMode();
	}
	
}
