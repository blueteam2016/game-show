package com.blueteam.gameshow.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class ServerQuestionMode extends JPanel{
	
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
	
	
	public ServerQuestionMode(Game g, ServerGameScreen s){
		
		qScreen = s;
		game = g;
		
		
		//make timer
		timeRemaining = new JLabel("Time Remaining: ");
		timer = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				seconds -= 1;
				countdown.setText(numberText(seconds/60) + ":" + numberText(seconds%60));
				if (seconds <= 0){
					timer.stop();
					qScreen.goToAnswerMode();
				}
			}
		});
		
		//set bottom 3 buttons
		
		back = new JButton("Back");
		back.setActionCommand("back");
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				stopTimer();
				new BackPopUp();	
			}
		});
		if(game.getQuiz().isFirstQuestion()){
			back.setEnabled(false);
		}
		
		
		pause = new JButton("Pause");
		pause.setActionCommand("pause");
		pause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("pause")){
					pause.setText("Run");
					pause.setActionCommand("run");
					stopTimer();
				}else if(e.getActionCommand().equals("run")){
					pause.setText("Pause");
					pause.setActionCommand("pause");
					startTimer();
				}
			}
		});
		
		skip = new JButton("Skip");
		skip.setActionCommand("skip");
		skip.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				stopTimer();
				new SkipPopUp();
			}
		});
		
		//sets Question info
		newQuestion();
		setUpGUI();
	}
	
	private static String numberText(int timeNum)
	{
		String numberText = "";
		if (timeNum >= 10)
			numberText = "" + timeNum;
		else if (timeNum < 10 && timeNum > 0)
			numberText = "0" + timeNum;
		else
			numberText = "00";
		return numberText;
	}
	
	public void newQuestion(){
		//set time remaining
		if(!game.getQuiz().isFirstQuestion()){
			back.setEnabled(true);
		}
	
		seconds = game.getQuiz().getCurrentQuestion().getTime();
		countdown = new JLabel("00:"+seconds);
		
		
		//set questions and answers

		question = new JLabel(game.getQuiz().getCurrentQuestion().getText());
		answers = new ArrayList<JLabel>();
		for(int i=0; i<game.getQuiz().getCurrentQuestion().getAnswers().length; i++){
			answers.add(new JLabel((char)(65+i) + ") " + game.getQuiz().getCurrentQuestion().getAnswers()[i].getText()));
		}
		
		setUpGUI();
	}
	
	private void setUpGUI(){
		//organizes components in visually appealing manner
		
		removeAll();
		
		add(question);
		for(int i=0; i<answers.size(); i++){
			add(answers.get(i));
		}
		
		JPanel timePanel = new JPanel();
		timePanel.add(timeRemaining);
		timePanel.add(countdown);
		add(timePanel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(back);
		buttonPanel.add(pause);
		buttonPanel.add(skip);
		add(buttonPanel);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
	
	public void startTimer(){	
		timer.start();	
	}
	
	public void stopTimer(){		
		timer.stop();
	}
	
	private class SkipPopUp extends PopUp{
		
		public SkipPopUp(){		
			super();
		}
		
		public void no(){
			frame.dispose();
			startTimer();	
		}	

		public void yes(){
			qScreen.goToAnswerMode();
			frame.dispose();
		}
	}

	private class BackPopUp extends PopUp{

		public BackPopUp(){				
			super();		
		}

		public void yes(){
			game.getQuiz().getLastQuestion();
			qScreen.goToResultMode();
			frame.dispose();
		}

		public void no(){
			frame.dispose();
			startTimer();		
		}
	}
}
