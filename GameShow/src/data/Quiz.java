package data;

import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.xml.sax.*;
import org.w3c.dom.*;

import data.Question;

public class Quiz {
		
	private ArrayList<Question> questions;
	private int questionNum;
	
	public Quiz(String filePath)
	{
		Document quizFile;
		DocumentBuilderFactory quizBuilder = DocumentBuilderFactory.newInstance();
		questions = ArrayList<Question>();
		questionNum = 0;
	}
	
	public Question getCurrentQuestion()
	{
		return questions.get(questionNum);
	}
	
	public Question getLastQuestion()
	{
		if(questionNum>0)
			return questions.get(questionNum-1);
		else
			return null;
	}
	
	public boolean isLastQuestion()
	{
		if(questionNum == questions.size()-1)
			return true;
		return false;
	}
	
	public Question nextQuestion()
	{
		questionNum++;
		return questions.get(questionNum);
	}
}