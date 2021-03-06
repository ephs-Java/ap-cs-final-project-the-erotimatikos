package pacman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Leaderboard {
	
	final String SOURCE = "src/pacman/";

	private ArrayList<Leader> leaders;
	
	//puts leaderboard info from the file 
	public Leaderboard() throws IOException {
		
		leaders = new ArrayList<Leader>();
		
		//creates leaderboard file if not present
		checkForFile();
		
		//file and scanner declaration
		File f = new File(SOURCE + "leaderboard.txt");
		Scanner filescan = new Scanner(f);
		
		//fills the arraylist
		while (filescan.hasNext()) {
			leaders.add(this.stringToLeader(filescan.nextLine()));
		}
		
		filescan.close();
		
	}
	
	//writes contents of the arraylist to the file
	public void writeToFile() {
		
//		System.out.println("written to file");
		
		//creates file object
		File f = new File(SOURCE + "leaderboard.txt");
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
			
			String content = "";
			
			for (int i = 0; i < leaders.size(); i++) {
				content += leaders.get(i).toString() + "\n";
			}
			
			bw.write(content);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	//given the toString of a leader, returns the leader that it once represented
	public Leader stringToLeader(String s) {
		
		//gets rid of '-' character
//		String message = "";
//		for (int i = 0; i < s.length(); i++) {
//			if (s.charAt(i) != '-') {
//				message += s.charAt(i);
//			}
//			else {
//				message += ';';
//			}
//		}
//		System.out.println(message);
//		s = message;
		
		String name = s.substring(0, s.indexOf(':'));
		
		int level = Integer.parseInt(s.substring(s.indexOf(':') + 1, s.indexOf(';')));
		
		int score = Integer.parseInt(s.substring(s.indexOf(';') + 1));
		
		Leader l = new Leader(name, level, score);
		return l;
		
	}
	
	//sorts the list by level followed by score
	public void sort() {
		
		ArrayList<Leader> newLeaderboard = new ArrayList<Leader>();
		
		while (leaders.size() > 0) {
			
			Leader min = leaders.get(0);
			int minIndex = 0;
			
			//finds the next item in the array
			for (int i = 0; i < leaders.size(); i++) {
				
				if (leaders.get(i).getLevel() < min.getLevel()) {
					min = leaders.get(i);
					minIndex = i;
				}
				else if (leaders.get(i).getLevel() == min.getLevel()
						&& leaders.get(i).getScore() > min.getScore()) {
					min = leaders.get(i);
					minIndex = i;
				}
				
			}
			newLeaderboard.add(min);
			leaders.remove(minIndex);
		}
		this.leaders = newLeaderboard;
	}
	
	//adds the leader to the arraylist
	public void add(Leader l) {
		leaders.add(l);
		sort();
	}
	
	//returns the leader object at the given index
	public Leader get(int index) {
		return leaders.get(index);
	}
	
	public int size() {
		return leaders.size();
	}
	
	//returns an arraylist of leaders, sorted, that are from a certain level
	public ArrayList<Leader> getLeadersFromLevel(int level) {
		
		sort();
		ArrayList<Leader> leadersFromLevel = new ArrayList<Leader>();
		for (int i = 0; i < leaders.size(); i++) {
			if (leaders.get(i).getLevel() == level) {
				leadersFromLevel.add(leaders.get(i));
			}	
		}
		return leadersFromLevel;
		
	}
	
	//clears all of the leaders of a file. Is called once a file is edited
	public void removeFromLevel(int level) {
		for (int i = 0; i < leaders.size(); i++) {
			if (leaders.get(i).getLevel() == level) {
				leaders.remove(i);
				i--;
			}
		}
		
	}
	
	//removes scores that are not in the top 5
	public void trim() {
		
		sort();
		
//		System.out.println(leaders.size() + "\n" + this + "\n\n\n");
		
		int leadersFromLevel = 0;
		int currentLevel = 0;
		
		for (int i = 0; i < leaders.size(); i++) {
			
			Leader l = leaders.get(i);
			
			if (l.getLevel() != currentLevel) {
				currentLevel ++;
				leadersFromLevel = 0;
			}
			
			leadersFromLevel ++;
			
			if (leadersFromLevel > 5) {
				leaders.remove(i);
				i--;
			}
			
		}
//		System.out.println(leaders.size() + "\n" + this);
		
	}
	
	//checks for leaderboard.txt and creates it if it is gone
	private void checkForFile() throws IOException {
		
		File f = new File(SOURCE + "leaderboard.txt");
		if (!f.exists()) {
			f.createNewFile();
		}
	}
	
	//returns the leader objects printed out one line at a time
	public String toString() {
		
		String message = "";
		
		for (int i = 0; i < leaders.size(); i++) {
			message += leaders.get(i).toString() + "\n";
		}
		
		return message;
		
	}
	
}
