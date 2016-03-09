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
		
		sIO.openIn("");
		
		{
			sIO.sendQuestion(question);

			Answer recvAnswer = sIO.getAnswer();
			while (recvAnswer == null)
				recvAnswer = sIO.getAnswer();
		
			System.out.println("" + recvAnswer.isCorrect() + ",\t" + recvAnswer.getText());
		}
		
		Answer[] a = {new Answer("Pizza.", true)};
		
		Question nextQ = new Question("Pizza?", a, explanation, 0, 0);
		
		{
			sIO.sendQuestion(nextQ);
		
			Answer recvAnswer = sIO.getAnswer();
			while (recvAnswer == null)
				recvAnswer= sIO.getAnswer();
			
			System.out.println("" + recvAnswer.isCorrect() + ",\t" + recvAnswer.getText());
		}
		
		
		
	}

}
