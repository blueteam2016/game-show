package com.blueteam.gameshow.server;

import javax.swing.*; 
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.ArrayList;
import java.awt.Graphics;
import java.io.*;
import java.awt.event.*;

import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.*;



public class ProfileScreen extends JPanel implements ActionListener {


	private JLabel defTimeLabel, servFoldLabel, clientFoldLabel, defQValLabel, qFileLabel, sec, point;
	private JButton qFileBrowser, servFoldBrowser, clientFoldBrowser, confirmButton;
	private JTextField qFileText, servFoldText, clientFoldText;

	private int defaultTimeInt=10, defaultValueInt=10;
	private String folderLocation = "", servFoldLoc = "", clientFoldLoc = "", questionFileLoc = "";

	private JSpinner spinnerDefTime, spinnerDefVal;

	private JFileChooser folderChooser;


	Game gameParameter;
	ServerWindow serverWindowParameter;


	public ProfileScreen(Game g, ServerWindow sw) {
		this.setLayout(new GridLayout(6,3,10,10));


		gameParameter = g;
		serverWindowParameter = sw;


		defTimeLabel = new JLabel("Default Time");
		this.add(defTimeLabel);
		spinnerDefTime = new JSpinner(new SpinnerNumberModel(defaultTimeInt, 1, 999, 1));
		spinnerDefTime.addChangeListener(new setDefaultTime());
		this.add(spinnerDefTime);
		sec = new JLabel("seconds");
		this.add(sec);	


		servFoldLabel = new JLabel("Server Output Folder");
		this.add(servFoldLabel);
		servFoldBrowser = new JButton("Browse");
		servFoldBrowser.addActionListener(new ServerButton());
		this.add(servFoldBrowser);
		servFoldText = new JTextField();
		this.add(servFoldText);


		clientFoldLabel = new JLabel("Client Output Folder");
		this.add(clientFoldLabel);
		clientFoldBrowser = new JButton("Browse");
		clientFoldBrowser.addActionListener(new ClientButton());
		this.add(clientFoldBrowser);
		clientFoldText = new JTextField();
		this.add(clientFoldText);


		defQValLabel = new JLabel("Default Question Value");
		this.add(defQValLabel);
		spinnerDefVal = new JSpinner(new SpinnerNumberModel(defaultValueInt, 1, 999, 1));
		spinnerDefVal.addChangeListener(new setDefaultValue());
		this.add(spinnerDefVal);
		point = new JLabel("points");
		this.add(point);


		qFileLabel = new JLabel("Question File");
		this.add(qFileLabel);
		qFileBrowser = new JButton("Browse");
		qFileBrowser.addActionListener(new QuestionButton());
		this.add(qFileBrowser);
		qFileText = new JTextField();
		this.add(qFileText);

		this.add(new JLabel());

		confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new confirmButtonPressed());
		this.add(confirmButton);



	}




	public void actionPerformed(ActionEvent event) {

	}


	public String fileChooser() {

		folderLocation = "";
		folderChooser = new JFileChooser();

		int returnVal = folderChooser.showOpenDialog(folderChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			folderLocation = folderChooser.getSelectedFile().getAbsolutePath();
		}

		return(folderLocation);

	}

	class ServerButton implements ActionListener{

		public void actionPerformed(ActionEvent event) {

			String eventName = event.getActionCommand();

			servFoldLoc = fileChooser();
			servFoldText.setText(servFoldLoc);


		}
	}

	class ClientButton implements ActionListener{

		public void actionPerformed(ActionEvent event) {

			clientFoldLoc = fileChooser();
			clientFoldText.setText(clientFoldLoc);

		}

	}class QuestionButton implements ActionListener{

		public void actionPerformed(ActionEvent event) {

			String eventName = event.getActionCommand();

			questionFileLoc = fileChooser();
			qFileText.setText(questionFileLoc);

		}

	}class setDefaultTime implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent arg0) {
			defaultTimeInt = (Integer) spinnerDefTime.getValue();
		}

	}class setDefaultValue implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent arg0) {
			defaultValueInt = (Integer) spinnerDefVal.getValue();
		}

	} class confirmButtonPressed implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {

			if (servFoldLoc.equals("")||questionFileLoc.equals("")||clientFoldLoc.equals("")){
				JOptionPane pane = new JOptionPane();
				pane.showMessageDialog(null, "Enter all of the required information.");
			}else{
				gameParameter.getProfile().setDefaultTime(defaultTimeInt);
				gameParameter.getProfile().setDefaultValue(defaultValueInt);
				gameParameter.getProfile().setServerFolderLoc(servFoldLoc);
				gameParameter.getProfile().setClientFolderLoc(clientFoldLoc);
				gameParameter.getProfile().setQuestionFileLoc(questionFileLoc);
			}

			if (gameParameter.getProfile().isComplete()) {
				serverWindowParameter.enableTabs();
			}



		}




	} 

}