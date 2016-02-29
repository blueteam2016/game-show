package data;

import java.io.Serializable;

public class Question implements Serializable {
	private static final long serialVersionUID = -4479385172083211420L; //required for serialization
	private String question;
	private Answer[] answers;
	private String explanation;
	
	public Question(String question, Answer[] answers, String explanation) {
		this.question = question;
		this.answers = answers;
		this.explanation = explanation;
	}
	
	public Question(String question, Answer[] answers) {
		this.question = question;
		this.answers = answers;
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

}
