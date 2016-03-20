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

		setLayout(new GridLayout(0,1));
		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
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
		add(question);
		
		//adds correct answer(s)
		JLabel ans;
		for(int y = 0; y< answerLabel.size(); y++){
			ans = answerLabel.get(y);
			add(ans);
			ans.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		}
		
		//add explanation
		if(explanationString != null){
			add(explanation);
			explanation.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		}
		
		//add button
		add(moveOn);
		moveOn.setAlignmentX(JButton.CENTER_ALIGNMENT);
		
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
		int componentHeight = allLabels.get(0).getHeight();
		int fontSize = Math.min(newFontSize, componentHeight);
		Font newFont = new Font(labelFont.getName(), Font.PLAIN, fontSize);
		if (newFont.getSize()<12){
			newFont=new Font(labelFont.getName(), Font.PLAIN, 12);
		}
		
		
		for(JLabel l: allLabels){
			l.setFont(newFont);
		}
		moveOn.setFont(newFont);
		explanation.setFont(newFont);
		
		/*int height = moveOn.getFontMetrics(newFont).getHeight();
		int width = moveOn.getFontMetrics(newFont).stringWidth(moveOn.getText());
		Dimension buttonSize = new Dimension(width + 25, height + 10);
		moveOn.setPreferredSize(buttonSize);
		System.out.println(buttonSize);
		*/
	}

	public void actionPerformed(ActionEvent arg0) {
		SGS.goToResultMode();
	}
	
}
