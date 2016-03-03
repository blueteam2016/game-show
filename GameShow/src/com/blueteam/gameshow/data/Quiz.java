package com.blueteam.gameshow.data;

import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.xml.sax.*;
import org.w3c.dom.*;

public class Quiz {
		
	private ArrayList<Question> questions;
	private Answer[] answerHolder;
	private int questionNum;
	
	public Quiz(String filePath)
	{
		Document quizFile;
		DocumentBuilderFactory quizBuilderFactory = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder quizBuilder = quizBuilderFactory.newDocumentBuilder();
			quizFile = quizBuilder.parse("XML Test");
			quizFile.getDocumentElement().normalize();
			Element root = quizFile.getDocumentElement();
			NodeList questionList = quizFile.getElementsByTagName("question");
			for(int index=0; index<questionList.getLength();index++)
			{
				Node currentNode = questionList.item(index);
				if(currentNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element currentElement = (Element) currentNode;
					System.out.println(currentElement.getAttribute("answerNum"));
				}
			}
		}catch(Exception e){System.out.println("THE QUIZ FILE WAS NOT BUILT CORRECTLY!!!");e.printStackTrace();}
		questions = new ArrayList<Question>();
		questionNum = 0;
	}
	
	public Question getCurrentQuestion()
	{
		return questions.get(questionNum);
	}
	
	public Question getLastQuestion()
	{
		if(questionNum > 0)
			return questions.get(questionNum - 1);
		else
			return null;
	}
	
	public boolean isLastQuestion()
	{
		if(questionNum == questions.size() - 1)
			return true;
		return false;
	}
	
	public Question nextQuestion()
	{
		questionNum++;
		return questions.get(questionNum);
	}
}