package com.blueteam.gameshow.server;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.blueteam.gameshow.data.Answer;


public class ServerAnswerMode extends JPanel implements ActionListener{

	private JLabel question;
	private ArrayList<JLabel> answerLabel;
	private JLabel explanation;
	private JButton moveOn;
	private ServerGameScreen SGS;
	private Game game;
	
	private ArrayList<JLabel> allLabels;
	
	public ServerAnswerMode(Game g, ServerGameScreen s){	
		SGS = s;
		game = g;
		
		moveOn = new JButton("Continue");
		moveOn.addActionListener(this);
		
		
		newQuestion();
	}
	
	public void newQuestion(){
		removeAll();

		setLayout(new GridLayout(0,1,30,30));
		
		question = new JLabel(game.getQuiz().getCurrentQuestion().getText());
		
		
		String explanationString = game.getQuiz().getCurrentQuestion().getExplanationText();
		if(explanationString != null)
			explanation = new JLabel("Explanation: " + game.getQuiz().getCurrentQuestion().getExplanationText());
		
		answerLabel = new ArrayList<JLabel>();
		
		Answer[] answers = game.getQuiz().getCurrentQuestion().getAnswers();
		
		for(int x = 0; x< answers.length; x++){
			if(answers[x].isCorrect())
				answerLabel.add(new JLabel((char)(65+x) + ") " + answers[x].getText()));	
		}
		
		//adds question
		
		question.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		add(question);
		
		//adds correct answer(s)
		
		for(int y = 0; y< answerLabel.size(); y++){
			
			add(answerLabel.get(y));
			
		}
		
		//add explanation
		
		if(explanationString != null)
			add(explanation);
		
		//add button
		
		moveOn.setAlignmentX(JButton.CENTER_ALIGNMENT);
		add(moveOn);
		
		
		
		
		
		 addComponentListener(new ComponentAdapter() {
	            public void componentResized(ComponentEvent e) {
	            	
	            	allLabels = new ArrayList<JLabel>();
	        		allLabels.add(question);
	        		allLabels.add(explanation);
	        		for(int y = 0; y< answerLabel.size(); y++){
	        			
	        			allLabels.add(answerLabel.get(y));
	        			
	        		}
	            	
	            	for(JLabel l: allLabels){
	            	
	            	Font labelFont = l.getFont();
	            	
	            	String labelText = l.getText();
	            	
	            	int stringWidth = l.getFontMetrics(labelFont).stringWidth(labelText);
	            	
	            	int componentWidth = l.getWidth();
	            	
	            	double widthRatio = (double)componentWidth / (double)stringWidth;
	            	
	            	int newFontSize = (int)(labelFont.getSize() * widthRatio);
	            	
	            	int componentHeight = l.getHeight();
	            	
	            	int fontSizeToUse = Math.min(newFontSize, componentHeight);
	            	
	            	l.setFont(new Font(labelFont.getName(), labelFont.getStyle(), fontSizeToUse));
	             
	            	}
	            	
	            	Font labelFont = moveOn.getFont();
	            	
	            	String labelText = moveOn.getText();
	            	
	            	int stringWidth = moveOn.getFontMetrics(labelFont).stringWidth(labelText);
	            	
	            	int componentWidth = moveOn.getWidth();
	            	
	            	double widthRatio = (double)componentWidth / (double)stringWidth;
	            	
	            	int newFontSize = (int)(labelFont.getSize() * widthRatio);
	            	
	            	int componentHeight = moveOn.getHeight();
	            	
	            	int fontSizeToUse = Math.min(newFontSize, componentHeight);
	            	
	            	moveOn.setFont(new Font(labelFont.getName(), labelFont.getStyle(), fontSizeToUse));
	               
	            }
	        });
	        
	        
			
	}


	public void actionPerformed(ActionEvent arg0) {
		SGS.goToResultMode();
	}
	
}
