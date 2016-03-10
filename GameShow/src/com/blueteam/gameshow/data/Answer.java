package com.blueteam.gameshow.data;

import java.io.Serializable;

public class Answer implements Serializable{
	private static final long serialVersionUID = -9143774235663880153L;
	private String text;
	private boolean correct;
	
	public Answer(String text, boolean correct) {
		this.text = text;
		this.correct = correct;
	}
	
	public String getText() {
		return text;
	}
	
	public boolean isCorrect() {
		return correct;
	}
	
}
