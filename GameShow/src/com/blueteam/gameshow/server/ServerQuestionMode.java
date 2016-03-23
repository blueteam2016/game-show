package com.blueteam.gameshow.server;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class ServerQuestionMode extends JPanel {

	private static final long serialVersionUID = -6719297104248411239L;
	private JLabel question;
	private ArrayList<JLabel> answers;
	private JLabel timeRemaining;
	private JLabel countdown;
	private int seconds;
	private JButton back;
	private JButton pause;
	private JButton skip;
	private Timer timer;
	private ServerGameScreen qScreen;
	private Game game;

	public ServerQuestionMode(Game g, ServerGameScreen s) {

		qScreen = s;
		game = g;
		
		// make timer
		timeRemaining = new JLabel("Time Remaining: ");
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seconds -= 1;
				countdown.setText(numberText(seconds / 60) + ":"
						+ numberText(seconds % 60));
				if (seconds <= 0) {
					timer.stop();
					qScreen.goToAnswerMode();
				}
			}
		});

		// set bottom 3 buttons
		back = new JButton("Back");
		back.setMinimumSize(new Dimension(80, 40));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer();
				new BackPopUp();
			}
		});

		pause = new JButton("Pause");
		pause.setActionCommand("pause");
		pause.setMinimumSize(new Dimension(80, 40));
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("pause")) {
					pause.setText("Run");
					pause.setActionCommand("run");
					stopTimer();
				} else if (e.getActionCommand().equals("run")) {
					pause.setText("Pause");
					pause.setActionCommand("pause");
					startTimer();
				}
			}
		});
		skip = new JButton("Skip");
		skip.setMinimumSize(new Dimension(80, 40));
		skip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer();
				new SkipPopUp();
			}
		});
		
		// add resizing stuff
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				resizeText();
			}
		});
	}

	private static String numberText(int timeNum) {
		String numberText = "";
		if (timeNum >= 10)
			numberText = "" + timeNum;
		else if (timeNum < 10 && timeNum > 0)
			numberText = "0" + timeNum;
		else
			numberText = "00";
		return numberText;
	}

	public void newQuestion() {
		game.sendQuestion(game.getQuiz().getCurrentQuestion());
		// set time remaining

		seconds = game.getQuiz().getCurrentQuestion().getTime();
		countdown = new JLabel(numberText(seconds / 60) + ":"
				+ numberText(seconds % 60));

		// set questions and answers (adds letter at beginning of answers:
		// A,B,C...)

		question = new JLabel(game.getQuiz().getCurrentQuestion().getText());
		answers = new ArrayList<JLabel>();
		for (int i = 0; i < game.getQuiz().getCurrentQuestion().getAnswers().length; i++) {
			answers.add(new JLabel((char) (65 + i)
					+ ") "
					+ game.getQuiz().getCurrentQuestion().getAnswers()[i]
							.getText()));
			answers.get(i).setAlignmentX(LEFT_ALIGNMENT);
		}

		setUpGUI();
	}

	private void setUpGUI() {
		// organizes components in visually appealing manner

		removeAll();
		// Sets layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JPanel questionInfo = new JPanel();
		questionInfo.setLayout(new BoxLayout(questionInfo, BoxLayout.PAGE_AXIS));
		questionInfo.add(question);
		questionInfo.add(Box.createRigidArea(new Dimension(0, 15)));
		for (int i = 0; i < answers.size(); i++) {
			questionInfo.add(answers.get(i));
			questionInfo.add(Box.createRigidArea(new Dimension(0, 5)));
		}
		questionInfo.setAlignmentX(CENTER_ALIGNMENT);
		add(questionInfo);

		JPanel timePanel = new JPanel();
		timePanel.add(timeRemaining);
		timePanel.add(countdown);
		add(timePanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(back);
		buttonPanel.add(pause);
		buttonPanel.add(skip);
		add(buttonPanel);
		
		resizeText();
	}

	private void resizeText(){
		JComponent[] textBits = { question, timeRemaining,
				countdown, back, pause, skip}; //components with text to be resized
		
		//finds longest answer
		int longWidth=0;
		Font ansFont = answers.get(0).getFont();
		String ansText;
		int ansWidth;
		for(int i=0; i<answers.size(); i++){
			ansText = answers.get(i).getText();
			ansWidth = answers.get(i).getFontMetrics(ansFont).stringWidth(ansText);
			if(ansWidth>longWidth){
				longWidth = ansWidth;
			}
		}
		
		//decides if question is longer than longest answer
		int questionLength = question.getFontMetrics(ansFont).stringWidth(question.getText());
		if(questionLength >longWidth){
			longWidth = questionLength;
		}
		
		//sets font size to something that will fit the longest element(as determined earlier)
		double widthRatio = (double)getWidth()/((double)longWidth+10);
		int fontSize = (int) (ansFont.getSize()*widthRatio);
		Font newFont = new Font(ansFont.getName(), Font.PLAIN, fontSize);
		if (newFont.getSize()<12)
			newFont=new Font(ansFont.getName(), Font.PLAIN, 12);
		if(newFont.getSize()>65)
			newFont=new Font(ansFont.getName(), Font.PLAIN, 65);
		
		//set elements to font size
		for(int i=0; i<answers.size(); i++){
			answers.get(i).setFont(newFont);
		}
		for(int i=0; i<textBits.length; i++){
			textBits[i].setFont(newFont);
		}
		
	}

	public void startTimer() {
		timer.start();
	}

	public void stopTimer() {
		timer.stop();
	}

	private class SkipPopUp extends PopUp {

		public SkipPopUp() {
			super();
		}

		public void no() {
			popUp.dispose();
			startTimer();
		}

		public void yes() {
			pause.setText("Pause");
			pause.setActionCommand("pause");
			qScreen.forwardToAnswerMode();
			qScreen.goToAnswerMode();
			popUp.dispose();
		}
	}

	private class BackPopUp extends PopUp {

		public BackPopUp() {
			super();
		}

		public void yes() {
			game.getQuiz().getLastQuestion();
			qScreen.goToResultMode();
			popUp.dispose();
		}

		public void no() {
			popUp.dispose();
			startTimer();
		}
	}
}
