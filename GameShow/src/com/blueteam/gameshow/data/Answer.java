package com.blueteam.gameshow.data;

import java.io.Serializable;

public class Answer implements Serializable{
	private static final long serialVersionUID = 6123925312090914404L; //required for serialization
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
