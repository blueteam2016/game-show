package com.blueteam.gameshow.server;
import java.awt.Font;
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

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
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
		
		add(question);
		for(int y = 0; y< answerLabel.size(); y++){
			
			add(answerLabel.get(y));
			
		}
		
		if(explanationString != null)
			add(explanation);
		
		add(moveOn);
		
		allLabels = new ArrayList<JLabel>();
		allLabels.add(question);
		allLabels.add(explanation);
		for(int y = 0; y< answerLabel.size(); y++){
			
			allLabels.add(answerLabel.get(y));
			
		}
		
		
		
		 addComponentListener(new ComponentAdapter() {
	            public void componentResized(ComponentEvent e) {
	            	
	            	for(JLabel label: allLabels){
	            	
	            	Font labelFont = label.getFont();
	            	
	            	String labelText = label.getText();
	            	
	            	int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
	            	
	            	int componentWidth = label.getWidth();
	            	
	            	double widthRatio = (double)componentWidth / (double)stringWidth;
	            	
	            	int newFontSize = (int)(labelFont.getSize() * widthRatio);
	            	
	            	int componentHeight = label.getHeight();
	            	
	            	int fontSizeToUse = Math.min(newFontSize, componentHeight);
	            	
	            	label.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
	             
	            	}
	            	
	            	Font labelFont = moveOn.getFont();
	            	
	            	String labelText = moveOn.getText();
	            	
	            	int stringWidth = moveOn.getFontMetrics(labelFont).stringWidth(labelText);
	            	
	            	int componentWidth = moveOn.getWidth();
	            	
	            	double widthRatio = (double)componentWidth / (double)stringWidth;
	            	
	            	int newFontSize = (int)(labelFont.getSize() * widthRatio);
	            	
	            	int componentHeight = moveOn.getHeight();
	            	
	            	int fontSizeToUse = Math.min(newFontSize, componentHeight);
	            	
	            	moveOn.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
	               
	            }
	        });
			
	}


	public void actionPerformed(ActionEvent arg0) {
		SGS.goToResultMode();
	}
	
}
