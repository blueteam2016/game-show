package com.blueteam.gameshow.server;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class ServerQuestionMode extends JPanel implements ActionListener{
	
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
		timeRemaining = new JLabel("Time Remaining");
		timer = new Timer(1000,this);
		timer.setActionCommand("time");
		
		//set bottom 3 buttons
		
		back = new JButton("Back");
		back.setActionCommand("back");
		back.addActionListener(this);
		
		pause = new JButton("Pause");
		pause.setActionCommand("pause");
		pause.addActionListener(this);
		
		skip = new JButton("Skip");
		skip.setActionCommand("skip");
		skip.addActionListener(this);
		
		//sets Question info
		newQuestion();
		setUpGUI();
	}
	
	public void newQuestion(){
		//set time remaining
		
	
		seconds = game.getQuiz().getCurrentQuestion().getTime();
		countdown = new JLabel("00:"+seconds);
		
		
		//set questions and answers

		question = new JLabel(game.getQuiz().getCurrentQuestion().getText());
		answers = new ArrayList<JLabel>();
		for(int i=0; i<game.getQuiz().getCurrentQuestion().getAnswers().length; i++){
			answers.add(new JLabel((char)(65+i) + ") " + game.getQuiz().getCurrentQuestion().getAnswers()[i].getText()));
		}
		
	}
	
	private void setUpGUI(){
		//organizes components in visually appealing manner
		
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
	
	public void actionPerformed(ActionEvent arg0) {
		String eventName = arg0.getActionCommand();
	
		switch(eventName){
		
		case "time":
				
			countdown = new JLabel("00:"+(seconds-1));
			seconds-=1;
			if(seconds==0){
				
				timer.stop();
				qScreen.goToAnswerMode();
			}
			
			break;
			
		case "back":
			
			new BackPopUp();
			
			break;
			
		case "pause":
			
			pause.setText("Run");
			pause.setActionCommand("run");
			stopTimer();
			
			break;
		
		case "skip":
			
			new SkipPopUp();
			
			break;
			
		case "run":
			
			pause.setText("Pause");
			pause.setActionCommand("pause");
			stopTimer();
		
			break;
			
		}
		
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
		
		public void actionPerformed(ActionEvent arg0) {
			
			switch(arg0.getActionCommand()){
			
			case "yes":
				
				yn=true;
				qScreen.goToAnswerMode();
				frame.dispose();
				
			case "no":
				
				yn=false;
				frame.dispose();
				
			
			}
		}
		
	}
	
	private class BackPopUp extends PopUp{
			
			public BackPopUp(){
				
				super();
				
			}
			
			public void actionPerformed(ActionEvent arg0) {
				
				switch(arg0.getActionCommand()){
				
				case "yes":
					
					yn=true;
					qScreen.goToResultMode();
					frame.dispose();
					
				case "no":
					
					yn=false;
					frame.dispose();
					
				
				}
			}
			
		}
		
	
}
