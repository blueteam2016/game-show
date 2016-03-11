
import javax.swing.*;

import com.blueteam.gameshow.client.ClientQuestionScreen;
import com.blueteam.gameshow.client.ClientWindow;
import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.Question;

public class ClientQuestionModeTester
{
	public static void main(String[] args)
	{
		ClientWindow w = new ClientWindow();
		
		ClientQuestionScreen screen = new ClientQuestionScreen(w);
		
		String questionText = "Why are chickens awesome because";
		Answer[] answers = {new Answer("they are fluffy", true),
							new Answer("they like hugs", true),
							new Answer("they dislike shoes", false),
							new Answer("they have cool hair", false),
							new Answer("they make cool noises", true)};
		String explanation = "get a chicken";
		Question question1 = new Question(questionText, answers, explanation, 10, 10);
		
		screen.setQuestion(question1);
		screen.goToQuestionMode();
		
		JFrame window = new JFrame();
		window.add(screen);
		window.setVisible(true);
	}
}
