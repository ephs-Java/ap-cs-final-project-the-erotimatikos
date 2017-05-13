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
	
	//returns the number of ghosts
	public int length() {
		return ghosts.size();
	}

	//returns the ghost object at the given index
	public Ghost get(int i) {
		if (i >= 0 && i < ghosts.size()) {
			return ghosts.get(i);
		}
		return null;
	}
	
	//updates the location of all of the ghosts based on their location in the array and
	//the location of the pac man
	public void updateAll(Tile[][] field, int pacX, int pacY, int blockwidth) {
		
		//updates each ghost
		for (int i = 0; i < ghosts.size(); i++) {
			
			Ghost g = ghosts.get(i);
			
			if (g.movingRight) {
				ghosts.get(i).setLocation(g.getX() + 1, g.getY());
			}
			if (g.movingLeft) {
				ghosts.get(i).setLocation(g.getX() - 1, g.getY());
			}
			if (g.movingDown) {
				ghosts.get(i).setLocation(g.getX(), g.getY() + 1);
			}
			if (g.movingUp) {
				ghosts.get(i).setLocation(g.getX(), g.getY() - 1);
			}
			
			int Xindex = (g.getX() - 25) / blockwidth;
			int Yindex = (g.getY() - 50) / blockwidth;
			
//			System.out.println(Xindex + " " + Yindex);
			
			int chance = (int) Math.random() * 3 + 1;
			//moves towards the player
			if (chance <= 2 && neighbors(field, i, blockwidth) < 2) {
				//moving right
				if (pacX > Xindex && field[Xindex + 1][Yindex].getState() != Tile.WALL) {
					g.movingRight = true;
				}
				else {
					g.movingRight = false;
				}
				//moving left
				if (pacX < Xindex && field[Xindex - 1][Yindex].getState() != Tile.WALL) {
					g.movingLeft = true;
				}
				else {
					g.movingLeft = false;
				}
				
				//moving up
				if (pacY < Yindex && field[Xindex][Yindex - 1].getState() != Tile.WALL) {
					g.movingUp = true;
				}
				else {
					g.movingUp = false;
				}
				
				//moving down
				if (pacY > Yindex && field[Xindex][Yindex + 1].getState() != Tile.WALL) {
					g.movingDown = true;
				}
				else {
					g.movingDown = false;
				}
				
			}
			
		}
		
	}
	
	public int neighbors(Tile[][] field, int index, int blockwidth) {
		
		int neighbors = 0;
		Ghost g = ghosts.get(index);
		
		int Xindex = (g.getX() - 25) / blockwidth;
		int Yindex = (g.getY() - 50) / blockwidth;
		
		if (field[Xindex - 1][Yindex].getState() == Tile.WALL) {
			neighbors ++;
		}
		if (field[Xindex + 1][Yindex].getState() == Tile.WALL) {
			neighbors ++;
		}
		if (field[Xindex][Yindex - 1].getState() == Tile.WALL) {
			neighbors ++;
		}
		if (field[Xindex][Yindex + 1].getState() == Tile.WALL) {
			neighbors ++;
		}
		
		return neighbors;
		
	}
	
	public String toString() {
		
		String message = "";
		
		for (int i = 0; i < ghosts.size(); i++) {
			
			message += i + " " + ghosts.get(i) + ", ";
			
		}
		
		return message;
		
	}
	
}
