package RobotMouse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class erochange {

	ArrayList<String> list = new ArrayList<String>();
	
	
	/* constructor */
	public erochange() throws FileNotFoundException{
		ArrayList<String> list = new ArrayList<String>();
		// scan in the words, one on each line
		Scanner input = new Scanner(new File("src/RobotMouse/texter.txt"));
		int position = 0;
		while (input.hasNext()) {
			String l = input.next();
			
			
		
		for(int i = 0;i<l.length();i++){
			if(l.charAt(l.length() - 1) == ';'){
				l = l.substring(0,l.length()-1);
			}
			else if(l.charAt(i) == ';'){
				l = l.substring(0, i) + l.substring(i+1) + "Wow";
			}
			l = "cow";
			list.add(l);
			
		}
		
		}
		
		
	}
	
//	/* add a new leader to the arrayList in the right spot */
//	public void addLeader(Leader l){
//	
//		for(int i = 0; i < theLeaders.size();i++){
//			if(theLeaders.get(i).getScore() <= l.getScore()){
//			theLeaders.add(i, l);
//			break;
//			}
//			
//		}
//		
//	}
	

	
	
	/* write the new leaders out to the file */
	public void writeLeaderboard() throws FileNotFoundException{
		PrintStream output = new PrintStream(new File("src/RobotMouse/texter.txt"));
		
		//make each item in the arrayList look like 
		// x = nelson:23567
		
		
		
		for(int i = 0; i < list.size();i++){
			
		
		output.println(list.get(i)); 
		}
		
		//do this for each item in the arrayList
	}
}
