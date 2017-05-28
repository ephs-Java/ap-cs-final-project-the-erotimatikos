package pacman;

import java.io.File;
import java.util.ArrayList;

public class Levels {

	private ArrayList<Level> levels;
	int currentLevel;
	public final String DIR = "src/pacman/levels/";
	
	//default constructor
	public Levels() {
		levels = new ArrayList<Level>();
		currentLevel = 0;
	}
	
	//adds a level to the arraylist
	public void add(Level l) {
		levels.add(l);
	}
	
	//adds all level# levels to the list
	public void addNumLevels() {
		
		int level = 1;
		File f = new File(DIR + "level" + 1 + ".txt");
		
		do {
			Level l = new Level(f.getPath());
			levels.add(l);
			level ++;
			f = new File(DIR + "level" + level + ".txt");
		} while (f.exists());
		
	}
	
	//returns the level # of the next level to be added
	//for example, level1 through level5 would return 6
	public int lastNumLevel() {
		
		int level = 0;
		File f;
		
		do {
			level ++;
			f = new File(DIR + "level" + level + ".txt");
		} while (f.exists());
		
		return level;
	}
	
	//gets the level at the given index
	public Level get(int index) {
		return levels.get(index);
	}
	
	//returns the number of levels
	public int numLevels() {
		return this.levels.size();
	}
	
	//returns the next level and adds 1 to currentlevel
	public Level nextLevel() {
		if (currentLevel < levels.size()) {
			currentLevel ++;
		}
		return levels.get(currentLevel);
	}
	
	//returns the previous level and subtracts 1 from currentLevel if possible
	public Level previousLevel() {
		if (currentLevel > 0) {
			currentLevel --;
		}
		return levels.get(currentLevel);
	}
	
	//returns a string form of the list
	public String toString() {
		
		String s = "";
		for (int i = 0; i < this.levels.size(); i++) {
			Level l = levels.get(i);
			s += "" + i + ": " + l + "\n";
		}
		return s;
		
	}
	
	//returns a formatted string of the level at the given index
	public String neatToString(int index) {
		
		String message = "";
		Level l = levels.get(index);
		String levelstring = l.toString();
		
		int startIndex = levelstring.indexOf(DIR) + DIR.length() + 1;
		
		//custom.txt
		if (levelstring.substring(startIndex - 1).equalsIgnoreCase("custom.txt")) {
			return "Custom  ";
		}
		//first letter uppercase, "L"
		message += levelstring.substring(startIndex, startIndex + 1).toUpperCase();
		
		//
		message += levelstring.substring(startIndex + 1, levelstring.indexOf('.') - ("" + (index + 1)).length());
		
		message += " " + (index + 1);
		
		
		return message;
		
	}
	
}
