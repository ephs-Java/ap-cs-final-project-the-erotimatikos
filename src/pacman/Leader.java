package pacman;

public class Leader {

	private String name;
	private int level;
	private long score;
	
	//constructor
	public Leader(String name, int score, int level) {
		
		this.name = name;
		this.score = score;
		this.level = level;
		
	}
	
	//getters
	public String getName() {
		return name;
	}
	
	public int getLevel() {
		return level;
	}
	
	public long getScore() {
		return score;
	}
	
	//returns string leader information
	public String toString() {
		return name  + ":" + score + ";" + level;
	}

	
}
