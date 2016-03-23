package com.blueteam.gameshow.server;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import com.blueteam.gameshow.data.Answer;


public class ServerAnswerMode extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JLabel question;
	private ArrayList<JLabel> answerLabel;
	private JTextArea explanation;
	private JButton moveOn;
	private ServerGameScreen SGS;
	private Game game;

	private ArrayList<JLabel> allLabels;

	public ServerAnswerMode(Game g, ServerGameScreen s){	
		SGS = s;
		game = g;

		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		moveOn = new JButton("Continue");
		moveOn.addActionListener(this);


		newQuestion();
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				resizeFonts();
			}
		});
	}
	
	public void newQuestion(){
		removeAll();

		//setLayout(new GridLayout(0,1));
		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setLayout(new BorderLayout());
		
		question = new JLabel(game.getQuiz().getCurrentQuestion().getText());
		
		String explanationString = game.getQuiz().getCurrentQuestion().getExplanationText();
		if(explanationString != null){
			explanation = new JTextArea("Explanation: " + explanationString);
			explanation.setEditable(false);
			explanation.setLineWrap(true);
			explanation.setWrapStyleWord(true);
			explanation.setBackground(getBackground());
		}
		
		answerLabel = new ArrayList<JLabel>();
		Answer[] answers = game.getQuiz().getCurrentQuestion().getAnswers();
		for(int x = 0; x< answers.length; x++){
			if(answers[x].isCorrect())
				answerLabel.add(new JLabel((char)(65+x) + ") " + answers[x].getText()));	
		}
		
		//adds question
		question.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		question.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		add(question, BorderLayout.NORTH);
		
		//adds correct answer(s)
		JPanel answerPanel = new JPanel();
		answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.PAGE_AXIS));
		JLabel ans;
		for(int y = 0; y< answerLabel.size(); y++){
			ans = answerLabel.get(y);
			answerPanel.add(ans);
			ans.setAlignmentX(JLabel.LEFT_ALIGNMENT);
			answerPanel.add(Box.createRigidArea(new Dimension(10,10)));
		}
		
		//add explanation
		if(explanationString != null){
			answerPanel.add(explanation);
			explanation.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		}
		
		add(answerPanel, BorderLayout.CENTER);
		
		//add button
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(moveOn);
		add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setAlignmentX(JButton.CENTER_ALIGNMENT);
		
		resizeFonts();
	}

	private void resizeFonts(){	
		allLabels = new ArrayList<JLabel>();
		allLabels.add(question);
		for(int y = 0; y< answerLabel.size(); y++){
			allLabels.add(answerLabel.get(y));
		}
		
		int longWidth = 0;
		Font labelFont = allLabels.get(0).getFont();

		for(JLabel l: allLabels){
			String labelText = l.getText();
			int stringWidth = l.getFontMetrics(labelFont).stringWidth(labelText);
			if(stringWidth>longWidth){
				longWidth = stringWidth;
			}
		}
		
		double widthRatio = (double)getWidth() / ((double)longWidth+20);
		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		Font newFont = new Font(labelFont.getName(), Font.PLAIN, newFontSize);
		if (newFont.getSize()<12){
			newFont=new Font(labelFont.getName(), Font.PLAIN, 12);
		}
		
		
		for(JLabel l: allLabels){
			l.setFont(newFont);
		}
		moveOn.setFont(newFont);
		explanation.setFont(newFont);
		
		int height = moveOn.getFontMetrics(newFont).getHeight();
		int width = moveOn.getFontMetrics(newFont).stringWidth(moveOn.getText()) * 2;
		Dimension buttonSize = new Dimension(width, height + 10);
		moveOn.setPreferredSize(buttonSize);
		//System.out.println(buttonSize);
	}

	public void actionPerformed(ActionEvent arg0) {
		SGS.goToResultMode();
	
	}
	
}
