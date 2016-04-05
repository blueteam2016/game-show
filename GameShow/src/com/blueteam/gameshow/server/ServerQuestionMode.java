package com.blueteam.gameshow.server;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.blueteam.gameshow.data.Answer;

public class ServerQuestionMode extends JPanel {

	private static final long serialVersionUID = -6719297104248411239L;
	private JScrollPane scrollPane;
	private JTextArea questionLabel;
	private JLabel timeRemaining;
	private JLabel countdown;
	private int seconds;
	private JButton back;
	private JButton pause;
	private JButton skip;
	private Timer timer;
	private ServerGameScreen sgScreen;
	private Game game;
	private boolean allResponded;
	private final static float DEFAULTFONTSIZE = 20;
	private float fontSize;

	public ServerQuestionMode(Game g, ServerGameScreen s) {

		sgScreen = s;
		game = g;
		fontSize = DEFAULTFONTSIZE;
		allResponded = false;
		
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// make timer
		timeRemaining = new JLabel("<html>Time Remaining: </html>");
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seconds -= 1;
				countdown.setText("<html>" +
						numberText(seconds / 60) + ":" +
						numberText(seconds % 60) +
						"</html>");
				if (seconds <= 0) {
					timer.stop();
					sgScreen.forwardToAnswerMode();
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
					unpause();
					if(allResponded){
						allResponded = false;
						sgScreen.forwardToAnswerMode();
						sgScreen.goToAnswerMode();
					}
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
		
		sgScreen.getWidth();
		
		addComponentListener(new ComponentAdapter() { 
			public void componentResized(ComponentEvent e) {
				updateFonts();
				if (scrollPane != null)
					scrollPane.setPreferredSize(scrollPane.getParent().getSize());
			} 
		});
	}

	public void allResponded(){
		if(pause.getActionCommand().equals("pause")){
			sgScreen.forwardToAnswerMode();
			sgScreen.goToAnswerMode();
		}else{
			allResponded = true;
		}
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
		updateFonts();
	}
	
	private void updateFonts() {
		float newWidth = sgScreen.getWidth();
		fontSize = (float)(DEFAULTFONTSIZE * (newWidth / 450.0));
		if (questionLabel != null)
			questionLabel.setFont(questionLabel.getFont().deriveFont(fontSize));
		if (timeRemaining != null)
			timeRemaining.setFont(timeRemaining.getFont().deriveFont(fontSize));
		if (countdown != null)
			countdown.setFont(countdown.getFont().deriveFont(fontSize));
		if (back != null)
			back.setFont(back.getFont().deriveFont(fontSize));
		if (pause != null)
			pause.setFont(pause.getFont().deriveFont(fontSize));
		if (skip != null)
			skip.setFont(skip.getFont().deriveFont(fontSize));	
		if (scrollPane != null)
			scrollPane.validate();	
	}
	
	private void formatTextArea(JTextArea textArea) {
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(getBackground());
	}
	
	private void setLabels() {
		// set time remaining
		if (!game.getQuiz().isFirstQuestion()) {
			back.setEnabled(true);
		}

		timeRemaining = new JLabel("<html>Time Remaining: </html>");
		countdown = new JLabel("<html>" +
				numberText(seconds / 60) + ":" +
				numberText(seconds % 60) +
				"</html>");
		
		//adds question
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(game.getQuiz().getCurrentQuestion().getText() + "\n");
		//adds answer(s)
		Answer[] answers = game.getQuiz().getCurrentQuestion().getAnswers();
		for(int i = 0; i< answers.length; i++){
			strBuilder.append("\n" + (char)(65 + i) + ") " + answers[i].getText());
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
		JPanel questionInfo = new JPanel();
		questionInfo.setLayout(new BoxLayout(questionInfo, BoxLayout.PAGE_AXIS));
		scrollPane.setViewportView(questionInfo);
		questionInfo.add(questionLabel);
		questionInfo.setAlignmentX(CENTER_ALIGNMENT);
		add(scrollPane);
		add(Box.createRigidArea(new Dimension(0, 15)));

		JPanel timePanel = new JPanel();
		timePanel.add(timeRemaining);
		timePanel.add(countdown);
		add(timePanel);

		JPanel buttonPanel = new JPanel();
		JButton[] buttons = {back, pause, skip};
		for(int i=0; i<buttons.length; i++){
			buttons[i].setFont(new Font(Font.DIALOG, Font.PLAIN, (int)fontSize));
			buttonPanel.add(buttons[i]);
		}
		add(buttonPanel);
		scrollPane.setPreferredSize(scrollPane.getParent().getSize());
		validate();
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
			sgScreen.forwardToAnswerMode();
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
