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
			
			Ghost g = get(i);
			
			if (g.movingRight) {
				g.setLocation(g.getX() + 1, g.getY());
				g.movingDown = false;
				g.movingUp = false;
			}
			if (g.movingLeft) {
				g.setLocation(g.getX() - 1, g.getY());
				g.movingDown = false;
				g.movingUp = false;
			}
			if (g.movingDown) {
				g.setLocation(g.getX(), g.getY() + 1);
				g.movingLeft = false;
				g.movingRight = false;
			}
			if (g.movingUp) {
				g.setLocation(g.getX(), g.getY() - 1);
				g.movingLeft = false;
				g.movingRight = false;
			}
			
			int Xindex = (g.getX() - 25) / blockwidth;
			int Yindex = (g.getY() - 50) / blockwidth;
			
//			System.out.println(Xindex + " " + Yindex);
			
			int chance = (int) (Math.random() * 3) + 1;
			
			//moves towards the player
			if (chance <= 2 && neighbors(field, i, blockwidth) < 2) {
				halt(g);
				//moving right
				if (pacX > Xindex && field[Xindex + 1][Yindex].getState() != Tile.WALL) {
					g.movingRight = true;
				}
				//moving left
				if (pacX < Xindex && field[Xindex - 1][Yindex].getState() != Tile.WALL) {
					g.movingLeft = true;
				}
				
				//moving up
				if (pacY < Yindex && field[Xindex][Yindex - 1].getState() != Tile.WALL) {
					g.movingUp = true;
				}
				
				//moving down
				if (pacY > Yindex && field[Xindex][Yindex + 1].getState() != Tile.WALL) {
					g.movingDown = true;
				}
				
			}
			
		}
		
	}
	
	//stops the momentum of the parameter ghost
	public void halt(Ghost g) {
		
		g.movingRight = false;
		g.movingLeft = false;
		g.movingUp = false;
		g.movingDown = false;
		
	}
	
	//returns the number of blocks adjacent to the ghost at the given index in the field array
	public int neighbors(Tile[][] field, int index, int blockwidth) {
		
		int neighbors = 0;
		Ghost g = ghosts.get(index);
		
		int Xindex = (ghosts.get(index).getX() - 25) / blockwidth;
		int Yindex = (ghosts.get(index).getY() - 50) / blockwidth;
		
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
	
	//prints out the ghosts
	public String toString() {
		
		String message = "";
		
		for (int i = 0; i < ghosts.size(); i++) {
			
			message += i + " " + get(i) + ", ";
			
		}
		
		return message;
		
	}
	
}
