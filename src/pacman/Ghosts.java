package pacman;

import java.util.ArrayList;

public class Ghosts {

	//ghosts field
	private ArrayList<Ghost> ghosts;
	
	//1 in this number is the chance that a ghost will go the opposite way
	private int oppositechance = 20; 
	
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
			
			//update the ghost location based on its direction
			update(field, g, blockwidth);
			
			//gets the index of each ghost
			int Xindex = (g.getX() - 25) / blockwidth;
			int Yindex = (g.getY() - 50) / blockwidth;
			
//			System.out.println(Xindex + " " + Yindex);
			
			//if the ghost is not aligned, skip this ghost
			if ((g.getX() - 25) % blockwidth != 0) {
				continue;
			}
			if ((g.getY() - 50) % blockwidth != 0) {
				continue;
			}
			else {
//				System.out.println("X:  " + Xindex + " Y: " + Yindex + " aligned");
			}
//			else if ((g.getY() - 50) % blockwidth != 0) {
//				System.out.println("Not aligned y " + i);
//				continue;
//			}
			
			//random chance variable
			int chance = (int) (Math.random() * oppositechance) + 1;
			
			//stops the ghost
			g.setDirection(Ghost.STILL);
			
			int xdif;
			int ydif;
			System.out.println(pacX + " " + g.getX());
			if (pacX > g.getX()) {
				xdif = pacX - g.getX();
			}
			else {
				xdif = g.getX() - pacX;
			}
			
			if (pacY > g.getY()) {
				ydif = pacY - g.getY();
			}
			else {
				ydif = g.getY() - pacY;
			}
			
			//moves towards the player
			if (chance <= oppositechance - 1) {
				
				if (xdif > ydif) {
					//moving right
					if (pacX > Xindex && field[Xindex + 1][Yindex].getState() != Tile.WALL) {
						g.setDirection(Ghost.RIGHT);
					}
					//moving left
					else if (pacX < Xindex && field[Xindex - 1][Yindex].getState() != Tile.WALL) {
						g.setDirection(Ghost.LEFT);
					}
					//otherwise moving up
					else if (field[Xindex][Yindex - 1].getState() != Tile.WALL) {
						g.setDirection(Ghost.UP);
						System.out.println("up " + xdif + " " + ydif);
					}
					//otherwise moving down
					else if (field[Xindex][Yindex + 1].getState() != Tile.WALL) {
						g.setDirection(Ghost.DOWN);
//						System.out.println("down");
						System.out.println("down " + xdif + " " + ydif);
					}
				}
				
				else if (ydif > xdif){
					
					//moving up
					if (pacY < Yindex && field[Xindex][Yindex - 1].getState() != Tile.WALL) {
						g.setDirection(Ghost.UP);
					}
					//moving down
					else if (pacY > Yindex && field[Xindex][Yindex + 1].getState() != Tile.WALL) {
						g.setDirection(Ghost.DOWN);
//						System.out.println("dow");
					}
					//otherwise moving right
					else if (field[Xindex + 1][Yindex].getState() != Tile.WALL){
						g.setDirection(Ghost.RIGHT);
					}
					//otherwise moving left
					else if (field[Xindex - 1][Yindex].getState() != Tile.WALL){
						g.setDirection(Ghost.LEFT);
					}
				}
			}
			//1 in oppositechance of going the opposite direction from the pac man
			else {
				
				//moving left, normally right
				if (pacX > Xindex && field[Xindex - 1][Yindex].getState() != Tile.WALL) {
					g.setDirection(Ghost.LEFT);
				}
				//moving right, normally left
				if (pacX < Xindex && field[Xindex + 1][Yindex].getState() != Tile.WALL) {
					g.setDirection(Ghost.RIGHT);
				}
				
				//moving down, normally up
				if (pacY < Yindex && field[Xindex][Yindex + 1].getState() != Tile.WALL) {
					g.setDirection(Ghost.DOWN);
				}
				
				//moving up, normally down
				if (pacY > Yindex && field[Xindex][Yindex - 1].getState() != Tile.WALL) {
					g.setDirection(Ghost.UP);
				}
				
			}
			int corner = detectCorner(field, g, blockwidth);
			if (corner != 0) {
				g.setDirection(corner);
			}
			
		}
		
	}
	
	//updates the location of the parameter ghost based on which direction they are going
	public void update(Tile[][] field, Ghost g, int blockwidth) {
		
		//updates the location of the ghost based on which direction they are going
		if (g.getDirection() == Ghost.RIGHT) {
			g.setLocation(g.getX() + 1, g.getY());
		}
		if (g.getDirection() == Ghost.LEFT) {
			g.setLocation(g.getX() - 1, g.getY());
		}
		if (g.getDirection() == Ghost.DOWN) {
			g.setLocation(g.getX(), g.getY() + 1);
		}
		if (g.getDirection() == Ghost.UP) {
			g.setLocation(g.getX(), g.getY() - 1);
		}
		
	}
	
	//detects a corner (3 adjacent blocks), returns the direction to go in, 0 if not a corner
	public int detectCorner(Tile[][] field, Ghost g, int blockwidth) {
		
		int Xindex = (g.getX() - 25) / blockwidth;
		int Yindex = (g.getY() - 50) / blockwidth;
		
		if (neighbors(field, g, blockwidth) != 3) {
			return 0;
		}
		
		//detects upwards opening corner
		if (field[Xindex][Yindex - 1].getState() == Tile.BLANK){
//			System.out.println("up");
			return Ghost.UP;
		}
		//left
		if (field[Xindex - 1][Yindex].getState() == Tile.BLANK){
//			System.out.println("left");
			return Ghost.LEFT;
		}
		//right
		if (field[Xindex + 1][Yindex].getState() == Tile.BLANK){
//			System.out.println("right");
			return Ghost.RIGHT;
		}
		//down
		if (field[Xindex][Yindex + 1].getState() == Tile.BLANK){
//			System.out.println("down " + Xindex + " " + Yindex);
			return Ghost.DOWN;
			
		}
		
		return Ghost.STILL;
		
	}
	
	//stops the momentum of the parameter ghost
	public void halt(Ghost g) {
		
		g.setDirection(Ghost.STILL);
		
	}
	
	//returns the number of blocks adjacent to the ghost at the given index in the field array
	public int neighbors(Tile[][] field, Ghost g, int blockwidth) {
		
		int neighbors = 0;
		
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
	
	//prints out the ghosts
	public String toString() {
		
		String message = "";
		
		for (int i = 0; i < ghosts.size(); i++) {
			
			message += i + " " + get(i) + ", ";
			
		}
		
		return message;
		
	}
	
}
