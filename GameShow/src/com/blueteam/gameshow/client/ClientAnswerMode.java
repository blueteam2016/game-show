package com.blueteam.gameshow.client;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Question;

public class ClientAnswerMode extends JPanel
{
	
	private static final long serialVersionUID = 159399403085037876L;
	private ClientQuestionScreen qScreen;
	private JScrollPane scrollPane;
	private Question question;
	private JTextArea questionLabel;
	private final static float DEFAULTFONTSIZE = 16;
	private float fontSize;

	public ClientAnswerMode(ClientQuestionScreen qs)
	{
		this.qScreen = qs;
		fontSize = DEFAULTFONTSIZE;
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		qScreen.getWidth();
		
		addComponentListener(new ComponentAdapter() { 
			public void componentResized(ComponentEvent e) {
				if (scrollPane != null)
					scrollPane.setPreferredSize(scrollPane.getParent().getSize());
				updateFonts();
			} 
		});
	}

	public void newAnswer() {
		setLabels();
		setUpGUI();
		updateFonts();
	}
	
	private void updateFonts() {
		float newWidth = qScreen.getWidth();
		fontSize = (float)(DEFAULTFONTSIZE * (newWidth / 450.0));
		Font newFont = new Font(Font.DIALOG, Font.PLAIN, (int)fontSize);
		if (questionLabel != null) {
			questionLabel.setFont(newFont);
			questionLabel.setSize(scrollPane.getViewport().getSize());
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
		//adds question
		question = qScreen.getQuestion();
		
		//adds question
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(question.getText() + "\n\n");
		//adds answer(s)
		Answer[] answers = question.getAnswers();
		for(int i = 0; i< answers.length; i++){
			strBuilder.append((char)(65 + i) + ") " + answers[i].getText() + "\n");
		}

		questionLabel = new JTextArea(strBuilder.toString());
		formatTextArea(questionLabel);
		
		HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);
		int lineNumber = ClientQuestionMode.getChoice() + 3;
		int startIndex;
		int endIndex;
		try {
			startIndex = questionLabel.getLineStartOffset(lineNumber);
			endIndex = questionLabel.getLineEndOffset(lineNumber);
			questionLabel.getHighlighter().addHighlight(startIndex, endIndex, painter);
		} catch (BadLocationException e) {}
	}
	
	private void setUpGUI(){
		// organizes components in visually appealing manner
		removeAll();
		// Sets layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JPanel answerInfo = new JPanel();
		answerInfo.setLayout(new BoxLayout(answerInfo, BoxLayout.PAGE_AXIS));
		scrollPane.setViewportView(answerInfo);
		answerInfo.add(questionLabel);
		add(scrollPane);
		add(Box.createRigidArea(new Dimension(0, 15)));
		scrollPane.setPreferredSize(scrollPane.getParent().getSize());
		validate();
	}
	
}
