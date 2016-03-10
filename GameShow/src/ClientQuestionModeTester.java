

import javax.swing.*;

import com.blueteam.gameshow.client.ClientQuestionScreen;
import com.blueteam.gameshow.client.ClientWindow;
import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Question;

public class ClientQuestionModeTester
{
	public static void main(String[] args)
	{
		ClientWindow window = new ClientWindow(); 
		ClientQuestionScreen screen = new ClientQuestionScreen(window);
		screen.goToQuestionMode();
		
		String questionText = "";
		Answer[] answers = {new Answer("", true)};
		String explanation = "";
		Question question1 = new Question(questionText, answers, explanation, 10, 10);
	}
}
