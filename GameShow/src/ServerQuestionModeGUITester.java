import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;



public class ServerQuestionModeGUITester implements ActionListener{

	private JFrame frame;
	private JPanel testPanel;

	private JLabel question;
	private ArrayList<JLabel> answers;
	private JLabel timeRemaining;
	private JLabel countdown;
	private int seconds = 15;
	private JButton back;
	private JButton pause;
	private JButton skip;
	private Timer timer;


	public ServerQuestionModeGUITester(){

		frame = new JFrame();
		
		testPanel = new JPanel();
		//make timer
		timeRemaining = new JLabel("Time Remaining");
		timer = new Timer(1000,this);
		timer.setActionCommand("time");
		System.out.println("YAAAAAAAA1");

		//set bottom 3 buttons

		back = new JButton("Back");
		back.setActionCommand("back");
		back.addActionListener(this);

		pause = new JButton("Pause");
		pause.setActionCommand("pause");
		pause.addActionListener(this);

		skip = new JButton("Skip");
		skip.setActionCommand("skip");
		skip.addActionListener(this);
		System.out.println("YAAAAAAAA2");


		countdown = new JLabel("00:"+seconds);

		question = new JLabel("How many games did the 95-96 Bulls win?");
		
		answers = new ArrayList<JLabel>();
		
		answers.add(new JLabel("a) 71"));
		answers.add(new JLabel("b) 72"));
		answers.add(new JLabel("c) 70"));
		answers.add(new JLabel("d) 68"));

		System.out.println("YAAAAAAAA3");

		testPanel.add(question);
		for(int i=0; i<answers.size(); i++){
			testPanel.add(answers.get(i));
		}

		JPanel timePanel = new JPanel();
		timePanel.add(timeRemaining);
		timePanel.add(countdown);
		testPanel.add(timePanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(back);
		buttonPanel.add(pause);
		buttonPanel.add(skip);
		testPanel.add(buttonPanel);
		System.out.println("YAAAAAAAA4");

		testPanel.setLayout(new BoxLayout(testPanel,BoxLayout.PAGE_AXIS));

		frame.setContentPane(testPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		System.out.println("YAAAAAAAA5");
	}


	public void actionPerformed(ActionEvent arg0) {
		String eventName = arg0.getActionCommand();

		switch(eventName){

		case "time":

			countdown = new JLabel("00:"+(seconds-1));
			seconds-=1;
			if(seconds==0){

				timer.stop();
				//qScreen.goToAnswerMode();
			}

			break;

		case "back":

			//new BackPopUp();

			break;

		case "pause":

			pause.setText("Run");
			pause.setActionCommand("run");
			//stopTimer();

			break;

		case "skip":

			//new SkipPopUp();

			break;

		case "run":

			pause.setText("Pause");
			pause.setActionCommand("pause");
			//stopTimer();

			break;

		}


	}


	private static void runGUI(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		new ServerQuestionModeGUITester();
	}
	
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				runGUI();
			}
		});
	}


}
