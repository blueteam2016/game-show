package com.blueteam.gameshow.server;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	
	
	
	public ServerQuestionMode(Game g, ServerQuestionScreen s){
		
		//set questions and answers
		
		question = new JLabel(g.getQuiz().getCurrentQuestion().getQuestionText());
		answers = g.getQuiz().getCurrentQuestion().getAnswers();
		
		//set time remaining
		
		timer = new Timer(1000,this);
		timer.setActionCommand("time");
		
		timeRemaining = new JLabel("Time Remaining");
		seconds = g.getProfile().getDefaultTime();
		countdown = new JLabel("00:"+seconds);
	
		
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
		
		
		
	
		
		
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		String eventName = arg0.getActionCommand();
		switch(eventName){
		case "time":
				
			countdown = new JLabel("00:"+(seconds-1));
			seconds-=1;
			if(seconds==0){
				
				timer.stop();
				goToAnswerMode();
			}
			
			break;
			
		case "back":
			
			break;
			
		case "pause":
			
			pause.setText("Run");
			pause.setActionCommand("run");
			stopTimer();
			
			break;
		
		case "skip":
			
			if((new popUp()).resultOfPopUp()){
				
				goToAnswerMode();
				
			}else{
				
				
				
			}
			
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
	
	
}
