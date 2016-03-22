import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;


public class SortingTester {
	private ArrayList<String> teams = new ArrayList<String>();
	private	Timer time;
	private String[] teamNames = {"Blue", "Red", "Violet", "Apricot", "Orange", "Green", "Magenta",
			"Lavender", "Pink", "Yellow", "Gray", "Silver", "Gold", "Blue"};
	private int teamNum = 0;
	
	public SortingTester(){
		time = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//System.out.println(teamNames[teamNum]);
				//System.out.println("!!!!!!!");
				addTeam(teamNames[teamNum]);
				printTeams();
				teamNum++;
				if(teamNum>=teamNames.length){
					time.stop();
				}
			}
		});
	}
	
	public void startTimer(){
		//System.out.println("!!!!!!!");
		time.start();
	}
	
	public void addTeam(String teamName){
		boolean found = false;
		
		for(int i=0; i<teams.size() && !found;i++){
			if(teamName.compareTo(teams.get(i))<0){
				found = true;
				teams.add(i, teamName);
			}
		}
		
		if(!found){
			teams.add(teamName);
		}
	}
	
	public void printTeams(){
		for(int i=0; i<teams.size(); i++){
			//System.out.println(teams.get(i));
		}
		//System.out.println();
	}
	
	public static void main(String[] args){
		JFrame frame= new JFrame();
		frame.setVisible(true);
		SortingTester test = new SortingTester();
		test.startTimer();
	}
	
	
}
