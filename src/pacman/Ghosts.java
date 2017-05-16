package pacman;

import java.util.ArrayList;
import java.util.Random;

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
	
	//resets the list
	public void removeAll() {
		ghosts = new ArrayList<Ghost>();
	}
	
	//updates the location of all of the ghosts based on their location in the array and
	//the location of the pac man
	public void updateAll(Tile[][] field, int pacX, int pacY, int blockwidth) {
		
		//updates each ghost
		for (int i = 0; i < ghosts.size(); i++) {
			
			Ghost g = get(i);
			
			//update the ghost location based on its direction
			update(g);
			
			//gets the index of each ghost
			int Xindex = (g.getX() - 25) / blockwidth;
			int Yindex = (g.getY() - 50) / blockwidth;
			
			//if the ghost is not aligned, skip this ghost
			if ((g.getX() - 25) % blockwidth != 0) {
				continue;
			}
			if ((g.getY() - 50) % blockwidth != 0) {
				continue;
			}
			
			//random chance variable
			int chance = (int) (Math.random() * oppositechance) + 1;
			
			//stops the ghost
			g.setDirection(Ghost.STILL);
			
			int xdif = Math.abs(pacX - Xindex);
			int ydif = Math.abs(pacY - Yindex);
//			System.out.println(xdif + " " + ydif);
			
			//moves towards the player
			if (chance <= oppositechance - 1) {
				
				if (xdif >= ydif) {
					//moving right
					if (pacX > Xindex && field[Xindex + 1][Yindex].getState() != Tile.WALL) {
						g.setDirection(Ghost.RIGHT);
					}
					//moving left
					else if (pacX < Xindex && field[Xindex - 1][Yindex].getState() != Tile.WALL) {
						g.setDirection(Ghost.LEFT);
					}
					else {
						updateRandom(field, g, blockwidth);
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
					}
					else {
						updateRandom(field, g, blockwidth);
					}
					
				}
			}
			//1 in oppositechance of going in a completely random direction
			else {
				updateRandom(field, g, blockwidth);
			}
			
		}
		
	}
	
	//updates the location of the parameter ghost based on which direction they are going
	public void update(Ghost g) {
		
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
	
	//moves the given ghost randomly
	public void updateRandom(Tile[][] field, Ghost g, int blockwidth) {
		
		ArrayList<Integer> nums = new ArrayList<Integer>();
		//adds the directions to the list
		nums.add(Ghost.RIGHT);
		nums.add(Ghost.DOWN);
		nums.add(Ghost.UP);
		nums.add(Ghost.LEFT);
		//gets indeces
		int Xindex = (g.getX() - 25) / blockwidth;
		int Yindex = (g.getY() - 50) / blockwidth;
		
		//random variables
		Random rand = new Random();
		int choice = 0;
		
		//randomly chooses a viable direction
		while (choice == 0) {
			
			//checks for empty list
			if (nums.size() == 0) {
//				System.out.println("empty");
				return;
			}
			
			choice = rand.nextInt(nums.size());
			int item = nums.get(choice);
			if (isBlocked(field, g, blockwidth, item)) {
				nums.remove(choice);
				choice = 0;
			}
			else {
				g.setDirection(item);
				return;
			}
			
		}
		
	}
	
	//returns if there is a block in the given direction
	public boolean isBlocked(Tile[][] field, Ghost g, int blockwidth,  int num) {
		
		int x = (g.getX() - 25) / blockwidth;
		int y = (g.getY() - 50) / blockwidth;
		
		if (num == Ghost.UP && field[x][y - 1].getState() == Tile.WALL) {
			return true;
		}
		if (num == Ghost.DOWN && field[x][y + 1].getState() == Tile.WALL) {
			return true;
		}
		if (num == Ghost.RIGHT && field[x + 1][y].getState() == Tile.WALL) {
			return true;
		}
		if (num == Ghost.LEFT && field[x - 1][y].getState() == Tile.WALL) {
			return true;
		}
		
		return false;
		
	}
	
	//detects a corner (3 adjacent blocks), returns the direction to go in, 0 if not a corner
	//not currently used, but works
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
