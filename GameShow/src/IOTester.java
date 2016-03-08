import com.blueteam.gameshow.client.ClientIO;
import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.ClientProfile;
import com.blueteam.gameshow.data.Question;
import com.blueteam.gameshow.server.ServerIO;


public class IOTester {
	
	public static void main(String[] args) {
		String questionText = "Why is Trump winning the GOP primary?";
		Answer[] answers = {
			new Answer("Because people are easily swayed by the media.", true),
			new Answer("People are stupid.", true),
			new Answer("Donald Trump is a good candidate", false),
			new Answer("Trump will make Ameica great again", false),
			new Answer("Who knows?", true)
		};
		String explanation = "We're doomed.";
		
		Question question = new Question(questionText, answers, explanation, 0, 0);
		
		ServerIO sIO = new ServerIO("");
		ClientIO cIO = new ClientIO("", new ClientProfile("", ""));
		
		sIO.openIn("");
		
		{
			sIO.sendQuestion(question);
		
			Question recvQuest = cIO.getQuestion();
			Answer[] ans = recvQuest.getAnswers();
		
			System.out.println(recvQuest.getText());
			for (int i = 0; i < ans.length; i++)
				System.out.println("" + ans[i].isCorrect() + ",\t" + ans[i].getText());
			System.out.println(recvQuest.getExplanationText());
		
			cIO.sendAnswer(ans[ans.length - 1]);
		
			Answer recvAnswer = sIO.getAnswer();
		
			System.out.println("" + recvAnswer.isCorrect() + ",\t" + recvAnswer.getText());
		}
		
		Answer[] a = {new Answer("Pizza.", true)};
		
		Question nextQ = new Question("Pizza?", a, explanation, 0, 0);
		
		{
			sIO.sendQuestion(nextQ);
		
			Question recvQuest = cIO.getQuestion();
			Answer[] ans = recvQuest.getAnswers();
		
			System.out.println(recvQuest.getText());
			for (int i = 0; i < ans.length; i++)
				System.out.println("" + ans[i].isCorrect() + ",\t" + ans[i].getText());
			System.out.println(recvQuest.getExplanationText());
		
			cIO.sendAnswer(ans[ans.length - 1]);
		
			Answer recvAnswer = sIO.getAnswer();
			recvAnswer = sIO.getAnswer();
			if (recvAnswer != null)
				System.out.println("" + recvAnswer.isCorrect() + ",\t" + recvAnswer.getText());
		}
		
		
		
	}

}
