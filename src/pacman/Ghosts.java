package pacman;

import java.util.ArrayList;

public class Ghosts {

	//ghosts field
	private ArrayList<Ghost> ghosts;
	
	//default constructor
	public Ghosts() {
		ghosts = new ArrayList<Ghost>();
	}
	
	//adds a ghost to the arraylist field
	public void add(Ghost g) {
		ghosts.add(g);
	}
	
	public void updateAll(Tile[][] field, int pacX, int pacY) {
		
		
		
	}
	
}
