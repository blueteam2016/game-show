package com.blueteam.gameshow.data;

import javax.xml.parsers.*;

import org.w3c.dom.*;

public class Quiz {
		
	private Question[] questions;
	private Answer[] answers;
	private int questionNum;
	
	public Quiz(String filePath)
	{
		Document quizFile;
		DocumentBuilderFactory quizBuilderFactory = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder quizBuilder = quizBuilderFactory.newDocumentBuilder();
			quizFile = quizBuilder.parse(filePath);
			quizFile.getDocumentElement().normalize();
			Element root = quizFile.getDocumentElement();
			NodeList questionList = root.getElementsByTagName("item");
			questions = new Question[questionList.getLength()];
			for(int index=0; index < questionList.getLength();index++)
			{
				Node currentQuestion = questionList.item(index);
				if(currentQuestion.getNodeType() == Node.ELEMENT_NODE)
				{
					Element questionElement = (Element) currentQuestion;
					NodeList answerList = questionElement.getElementsByTagName("answer");
					answers = new Answer[answerList.getLength()];
					for(int answerIndex=0;answerIndex<answerList.getLength();answerIndex++)
					{
						Node currentAnswer = answerList.item(answerIndex);
						if(currentAnswer.getNodeType() == Node.ELEMENT_NODE)
						{
							Element answerElement = (Element)currentAnswer;
							answers[answerIndex]=new Answer(answerElement.getTextContent(),answerElement.getAttribute("correct").equals("true"));
						}
					}
					String questionString =questionElement.getElementsByTagName("question").item(0).getTextContent();
					String explanation;
					try{
						explanation = questionElement.getElementsByTagName("explanation").item(0).getTextContent();
					}catch(Exception e){explanation = null;}
					int pointValue;
					int time;
					try{
						pointValue = Integer.parseInt(questionElement.getElementsByTagName("point_value").item(0).getTextContent());
					}catch(Exception e){pointValue = Profile.getDefaultValue();}
					try{
						time = Integer.parseInt(questionElement.getElementsByTagName("time").item(0).getTextContent());
					}catch(Exception e){time = Profile.getDefaultTime();}
					questions[index] = new Question(questionString,answers,explanation,pointValue,time);
				}
			}
			
		}catch(Exception e){System.out.println("THE QUIZ FILE WAS NOT BUILT CORRECTLY");e.printStackTrace();}
		questionNum = 0;
	}
	
	public Question getCurrentQuestion()
	{
		return questions[questionNum];
	}
	
	public Question getLastQuestion()
	{
		if(questionNum > 0)
			return questions[questionNum-1];
		else
			return null;
	}
	
	public boolean isLastQuestion()
	{
		if(questionNum == questions.length - 1)
			return true;
		return false;
	}
	
	public Question nextQuestion()
	{
		questionNum++;
		return questions[questionNum];
	}
}
