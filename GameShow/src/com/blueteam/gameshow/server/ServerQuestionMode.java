package com.blueteam.gameshow.server;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import com.blueteam.gameshow.data.Answer;

public class ServerQuestionMode extends JPanel {

	private static final long serialVersionUID = -6719297104248411239L;
	private JLabel question;
	private ArrayList<JLabel> answerLabels;
	private JLabel timeRemaining;
	private JLabel countdown;
	private int seconds;
	private JButton back;
	private JButton pause;
	private JButton skip;
	private Timer timer;
	private ServerGameScreen sgScreen;
	private Game game;
	private int fontSize;
	private float currentWidth;
	

	public ServerQuestionMode(Game g, ServerGameScreen s) {

		sgScreen = s;
		game = g;
		fontSize = 16;
		
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// make timer
		timeRemaining = new JLabel("<html><span style='font-size:" + fontSize + "px'>Time Remaining: </span></html>");
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seconds -= 1;
				countdown.setText("<html><span style='font-size:" + fontSize + "px'>" +
						numberText(seconds / 60) + ":" +
						numberText(seconds % 60) +
						"</span></html>");
				if (seconds <= 0) {
					timer.stop();
					sgScreen.goToAnswerMode();
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
		if (game.getQuiz().isFirstQuestion()) {
			back.setEnabled(false);
		}
		
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
		
		currentWidth = sgScreen.getWidth();
		
		addComponentListener(new ComponentAdapter() { 
			public void componentResized(ComponentEvent e) {
				float newWidth = sgScreen.getWidth();
				if (newWidth != currentWidth) {
					fontSize = (int)(16 * (newWidth / 450.0));
					currentWidth = newWidth;
					setLabels();
					setUpGUI();
				}
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
		seconds = game.getQuiz().getCurrentQuestion().getTime();
		setLabels();
		setUpGUI();
	}
	
	private void setLabels() {
		// set time remaining
		if (!game.getQuiz().isFirstQuestion()) {
			back.setEnabled(true);
		}

		timeRemaining = new JLabel("<html><span style='font-size:" + fontSize + "px'>Time Remaining: </span></html>");
		countdown = new JLabel("<html><span style='font-size:" + fontSize + "px'>" +
				numberText(seconds / 60) + ":" +
				numberText(seconds % 60) +
				"</span></html>");

		// set questions and answers (adds letter at beginning of answers:
		// A,B,C...)
		question = new JLabel("<html><table><tr><td width='" + currentWidth + "'><span style='font-size:" + fontSize + "px'>" +
							   game.getQuiz().getCurrentQuestion().getText() +
							   "</span></td></tr></table></html>");
		answerLabels = new ArrayList<JLabel>();
		Answer[] answers = game.getQuiz().getCurrentQuestion().getAnswers();
		for (int i = 0; i < answers.length; i++) {
			JLabel answer = new JLabel("<html><table><tr><td width='" + currentWidth + "'><span style='font-size:" + fontSize + "px'>" +
					(char) (65 + i) + ") " +
					answers[i].getText() +
					"</span></td></tr></table></html>");
			answerLabels.add(answer);
			answer.setAlignmentX(LEFT_ALIGNMENT);
		}
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
		for (int i = 0; i < answerLabels.size(); i++) {
			questionInfo.add(answerLabels.get(i));
			questionInfo.add(Box.createRigidArea(new Dimension(0, 5)));
		}
		questionInfo.setAlignmentX(CENTER_ALIGNMENT);
		add(questionInfo);
		add(Box.createRigidArea(new Dimension(0, 15)));

		JPanel timePanel = new JPanel();
		timePanel.add(timeRemaining);
		timePanel.add(countdown);
		add(timePanel);

		JPanel buttonPanel = new JPanel();
		JButton[] buttons = {back, pause, skip};
		for(int i=0; i<buttons.length; i++){
			buttons[i].setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
			buttonPanel.add(buttons[i]);
		}
		add(buttonPanel);
		sgScreen.getServerWindow().update();
	}

	public void startTimer() {
		timer.start();
	}

	public void stopTimer() {
		timer.stop();
	}

	public void unpause(){
		pause.setText("Pause");
		pause.setActionCommand("pause");
	}
	
	private class SkipPopUp extends PopUp {

		public SkipPopUp() {
			super();
		}

		@Override
		protected String messageText() {
			return "Are you sure?";
		}
		
		@Override
		protected void no() {
			dispose();
			startTimer();
		}

		@Override
		protected void yes() {
			unpause();
			sgScreen.goToAnswerMode();
			dispose();
		}

		@Override
		public void windowActivated(WindowEvent arg0) {}
		@Override
		public void windowClosed(WindowEvent arg0) {}
		@Override
		public void windowClosing(WindowEvent arg0) {
			startTimer();
		}
		@Override
		public void windowDeactivated(WindowEvent arg0) {}
		@Override
		public void windowDeiconified(WindowEvent arg0) {}
		@Override
		public void windowIconified(WindowEvent arg0) {}
		@Override
		public void windowOpened(WindowEvent arg0) {}

	}

	private class BackPopUp extends PopUp {

		public BackPopUp() {
			super();
		}

		@Override
		protected String messageText() {
			return "Are you sure?";
		}
		
		@Override
		protected void yes() {
			pause.setText("Pause");
			pause.setActionCommand("pause");
			game.getQuiz().getLastQuestion();
			sgScreen.goToResultMode();
			dispose();
		}

		@Override
		protected void no() {
			startTimer();
			dispose();
		}

		@Override
		public void windowActivated(WindowEvent arg0) {}
		@Override
		public void windowClosed(WindowEvent arg0) {}
		@Override
		public void windowClosing(WindowEvent arg0) {
			startTimer();
		}
		@Override
		public void windowDeactivated(WindowEvent arg0) {}
		@Override
		public void windowDeiconified(WindowEvent arg0) {}
		@Override
		public void windowIconified(WindowEvent arg0) {}
		@Override
		public void windowOpened(WindowEvent arg0) {}

	}
}
