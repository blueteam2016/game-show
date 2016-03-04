package com.blueteam.gameshow.data;

import java.io.Serializable;

public class Question implements Serializable {
	private static final long serialVersionUID = -4479385172083211420L; //required for serialization
	private String question;
	private Answer[] answers;
	private String explanation;
	private int pointValue;
	private int time;
	
	public Question(String question, Answer[] answers, String explanation, int pointValue, int time) {
		this.question = question;
		this.answers = answers;
		this.explanation = explanation;
		this.pointValue = pointValue;
		this.time = time;
	}
	
	public String getText() {
		return question;
	}
	
	public Answer[] getAnswers() {
		return answers;
	}
	
	public String getExplanationText() {
		return explanation;
	}
	
	public int getPointValue() {
		return pointValue;
	}
	
	public int getTime() {
		return time;
	}

}
