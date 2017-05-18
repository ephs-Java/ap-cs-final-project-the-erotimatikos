package Treadmill;

//Creates leaders to be added to the leaderboard
public class Leader {
	String name;
	int score;
	
	public Leader(String n, int s){
		this.name = n;
		this.score = s;
	}
	
	public String getName(){
		return this.name;
		
	}
	public int getScore(){
		return this.score;
	}
}
