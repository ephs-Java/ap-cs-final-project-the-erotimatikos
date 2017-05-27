package Treadmill;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class advLeaderboard {
	/* the array list of leader objects */
	ArrayList<Leader> theLeaders = new ArrayList<Leader>();
	
	
	/* constructor */
	public advLeaderboard() throws FileNotFoundException{
		ArrayList<Leader> theLeaders = new ArrayList<Leader>();
		// scan in the words, one on each line
		Scanner input = new Scanner(new File("src/Treadmill/NewLeaderBoard.txt"));
		int position = 0;
		while (input.hasNext()) {
			String l = input.next();
			//looks like nelson:23567
			
			//Sepertaes the score and the name
			int colon = l.indexOf(':');
			String name = l.substring(0, colon);
			String score2 = l.substring(colon+1);
			
			//Turns the score that was a string to an integer
			int score = Integer.parseInt(score2);
			
			//creates a leader out of info from document and adds it to theLeaders Array
			Leader t = new Leader(name, score);
			this.theLeaders.add(t);
		}
		
	}
	
	/* add a new leader to the arrayList in the right spot */
	public void addLeader(Leader l){
	
		for(int i = 0; i < theLeaders.size();i++){
			if(theLeaders.get(i).getScore() <= l.getScore()){
			theLeaders.add(i, l);
			break;
			}
			
		}
		
	}
	
	/* print out leaderboard arraylist */
	public String toString(){
		String leaderboard = "";
		for(int i = 0; i < theLeaders.size();i++){
			
			
			
			leaderboard = leaderboard + theLeaders.get(i).getName()+ ":" + theLeaders.get(i).getScore() + "\n";
		}
		return leaderboard;
	}
	
	
	/* write the new leaders out to the file */
	public void writeLeaderboard() throws FileNotFoundException{
		PrintStream output = new PrintStream(new File("src/Treadmill/NewLeaderBoard.txt"));
		
		//make each item in the arrayList look like 
		// x = nelson:23567
		
		
		
		for(int i = 0; i < theLeaders.size();i++){
			
		
		output.println(theLeaders.get(i).getName() + ":" + theLeaders.get(i).getScore()); 
		}
		
		//do this for each item in the arrayList
	}
	public String dog(){
		String ne = "";
		for(int i = 0;i<theLeaders.size();i++){
			ne += theLeaders.get(i).getName() + " " + theLeaders.get(i).getScore()+"\n";
		}
		return ne;
	}
}