package pacman;

public class Tile {

	//the state of the current tile
	public int state;
	
	//constants for the state of the tile
	public static final int BLANK = 0;
	public static final int WALL = 1;
	public static final int PILL = 2;
	
	//creates a blank tile. default constructor
	public Tile() {
		this.state = 0;
	}
	
	//creates a tile with the given parameter as a tile state
	//makes sure it is in range
	public Tile(int s) {
		if (s < 0) {
			this.state = BLANK;
		}
		else if (s > PILL) {
			this.state = PILL;
		}
		else {
			this.state = s;
		}
	}
	
	//returns the current state of the tile
	public int getState() {
		return this.state;
	}
	
	//sets the state to the parameter
	//checks that it is in range
	public void setState(int s) {
		if (s < 0) {
			this.state = BLANK;
		}
		else if (s > PILL) {
			this.state = PILL;
		}
		else {
			this.state = s;
		}
	}
	
	
	
}
