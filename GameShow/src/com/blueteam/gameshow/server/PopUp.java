package com.blueteam.gameshow.server;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PopUp implements ActionListener{

	private JFrame frame;
	private JPanel panel;
	private JPanel bp;
	private JLabel label;
	private JButton yes; 
	private JButton no;
	private boolean yn = true;
	
	public PopUp() {
		
		frame = new JFrame();
		panel = new JPanel();
		panel.setLayout(new GridLayout(2,1,5,5));
		bp = new JPanel();
		bp.setLayout(new GridLayout(1,2,5,5));
		label = new JLabel("Are you sure?", SwingConstants.CENTER);
		yes = new JButton("yes");
		yes.addActionListener(this);
		yes.setActionCommand("yes");
		no = new JButton("no");
		no.addActionListener(this);
		no.setActionCommand("no");
		
		panel.add(label);
		
		bp.add(yes);
		bp.add(no);
		
		panel.add(bp);
		
		frame.setContentPane(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}


	public void actionPerformed(ActionEvent arg0) {
		
		switch(arg0.getActionCommand()){
		
		case "yes":
			
			yn=true;
			
		case "no":
			
			yn=false;
			
		
		}
		
	}
	
	
	public boolean resultOfPopUp(){
		
		return yn;
				
	}
	
	
}
