import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.blueteam.gameshow.client.ClientIO;
import com.blueteam.gameshow.data.Answer;
import com.blueteam.gameshow.data.ClientProfile;
import com.blueteam.gameshow.data.Question;
import com.blueteam.gameshow.server.ServerIO;


public class IOTester {
	
//	public static Answer getAnswer(ObjectInputStream in) {
//		Answer qIn = null;
//		try {
//			qIn = (Answer)in.readObject();
//		} catch (EOFException e) {
//			return null;
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return qIn;
//	}
	
	public static void sendQuestion(ObjectOutputStream out, Question question) {
		try {
			out.writeObject(question);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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

		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		
		try {
			String path = ".question";
			Files.deleteIfExists(Paths.get(path));
			out = new ObjectOutputStream(new FileOutputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String sysName = null;
			
		try {
			sysName = InetAddress.getLocalHost().getHostName(); //apparently the only way to get the machine hostname in Java
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				in = new ObjectInputStream(new FileInputStream(".answer_" + sysName));
			} catch (FileNotFoundException e) {
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;	
		}

		
		{
			sendQuestion(out, question);

//			Answer recvAnswer = getAnswer(in);
//			while (recvAnswer == null)
//				recvAnswer = getAnswer(in);
//		
//			System.out.println("" + recvAnswer.isCorrect() + ",\t" + recvAnswer.getText());
		}
		
		Answer[] a = {new Answer("Pizza.", true)};
		
		Question nextQ = new Question("Pizza?", a, explanation, 0, 0);
		
		{
			//sendQuestion(out, nextQ);
		
//			Answer recvAnswer = getAnswer(in);
//			while (recvAnswer == null)
//				recvAnswer= getAnswer(in);
			
//			System.out.println("" + recvAnswer.isCorrect() + ",\t" + recvAnswer.getText());
		}
		
		
		
	}

}
