package pacman;

import java.util.ArrayList;
import java.util.Random;

public class Ghosts {

	//ghosts field
	private ArrayList<Ghost> ghosts;
	
	//whether or not the ghosts run away instead of moving toward the player
	public boolean runAway = false;
	
	//1 in this number is the chance that a ghost will move randomly this turn
	private int oppositechance = 20; 
	
	public int ghostSpeed = 6;
	
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
	
	//activates all of the ghosts
	public void activateAll() {
		
		for (int i = 0; i < ghosts.size(); i++) {
			ghosts.get(i).activate();
		}
		
	}
	
	//updates the location of all of the ghosts based on their location in the array and
	//the location of the pac man
	public void updateAll(Tile[][] field, int pacX, int pacY, int blockwidth) {
		
		//updates each ghost
		for (int i = 0; i < ghosts.size(); i++) {
			
			Ghost g = get(i);
			
			if (!g.isActive) {
				continue;
			}
			
//			System.out.println(bestPathLength(field, g, pacX, pacY, blockwidth));
			
			//update the ghost location based on its direction
			
			update(g);
			
			//moves the ghost to the nearest block	
			int xLeftover = (g.getX() - 25) % blockwidth;
			int yLeftover = (g.getY() - 50) % blockwidth;
			
			//moving left
			if (g.getDirection() == Ghost.LEFT && xLeftover < ghostSpeed) {
				g.setLocation(g.getX() - xLeftover, g.getY());
			}
			//moving up
			if (g.getDirection() == Ghost.UP && yLeftover < ghostSpeed) {
				g.setLocation(g.getX(), g.getY() - yLeftover);
			}
			//moving right
			if (g.getDirection() == Ghost.RIGHT && (blockwidth - xLeftover) < ghostSpeed) {
				g.setLocation(g.getX() + (blockwidth - xLeftover), g.getY());
			}
			//moving down
			if (g.getDirection() == Ghost.DOWN && (blockwidth - yLeftover) < ghostSpeed) {
				g.setLocation(g.getX(), g.getY() + (blockwidth - yLeftover));
			}
			
			
			//if the ghost is not aligned, skip this ghost			
			if ((g.getX() - 25) % blockwidth != 0) {
				continue;
			}
			if ((g.getY() - 50) % blockwidth != 0) {
				continue;
			}
			
			g.setDirection(Ghost.STILL);
			
			//makes the ghosts run away if applicable
			if (runAway) {
				runAway (field, g, pacX, pacY, blockwidth);
				continue;
			}
			
			//gets the index of each ghost
			int Xindex = (g.getX() - 25) / blockwidth;
			int Yindex = (g.getY() - 50) / blockwidth;
			
			//random chance variable
			int chance = (int) (Math.random() * oppositechance) + 1;
			
			//stops the ghost
//			g.setDirection(Ghost.STILL);
			
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
	
	//checks for ghost collision with a wall, similar to pac man logic
	public void wallCollision(Tile[][] field, Ghost g, int blockwidth) {
		
		//gets the index of each ghost
		int Xindex = (g.getX() - 25) / blockwidth;
		int Yindex = (g.getY() - 50) / blockwidth;
		
		System.out.println(Xindex + " " + Yindex);
		
		
		
	}
	
	//aligns all ghosts to the nearest block
	public void alignAll(int blockwidth) {
		
		for (int i = 0; i < ghosts.size(); i++) {
			
			Ghost g = ghosts.get(i);
			
			int x = g.getX();
			int y = g.getY();
			
			x -= 25;
			y -= 50;
			
			g.setLocation(g.getX() - x % blockwidth, g.getY() - y % blockwidth);
			
//			int Xindex = (g.getX() - 25) / blockwidth;
//			int Yindex = (g.getY() - 50) / blockwidth;
			
//			g.setLocation(Xindex * blockwidth + 25, Yindex * blockwidth + 25);
			
		}
		
	}
	
	//aligns the given ghosts to the nearest block
	public void align(Ghost g, int blockwidth) {
		
		int Xindex = (g.getX() - 25) / blockwidth;
		int Yindex = (g.getY() - 50) / blockwidth;
		
		g.setLocation(Xindex * blockwidth + 25, Yindex * blockwidth + 50);
		
	}
	
	//updates the location of the parameter ghost based on which direction they are going
	public void update(Ghost g) {
		
		//updates the location of the ghost based on which direction they are going
		if (g.getDirection() == Ghost.RIGHT) {
			g.setLocation(g.getX() + ghostSpeed, g.getY());
		}
		if (g.getDirection() == Ghost.LEFT) {
			g.setLocation(g.getX() - ghostSpeed, g.getY());
		}
		if (g.getDirection() == Ghost.DOWN) {
			g.setLocation(g.getX(), g.getY() + ghostSpeed);
		}
		if (g.getDirection() == Ghost.UP) {
			g.setLocation(g.getX(), g.getY() - ghostSpeed);
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
	
	//moves the ghost away from the pac man
	public void runAway(Tile[][] field, Ghost g, int pacX, int pacY, int blockwidth) {
		
		int Xindex = (g.getX() - 25) / blockwidth;
		int Yindex = (g.getY() - 50) / blockwidth;
		
		int xdif = Math.abs(pacX - Xindex);
		int ydif = Math.abs(pacY - Yindex);
		
		if (xdif >= ydif) {
			//moving left, normally right
			if (pacX > Xindex && field[Xindex - 1][Yindex].getState() != Tile.WALL) {
				g.setDirection(Ghost.LEFT);
			}
			//moving right, normally left
			else if (pacX < Xindex && field[Xindex + 1][Yindex].getState() != Tile.WALL) {
				g.setDirection(Ghost.RIGHT);
			}
			else {
				updateRandom(field, g, blockwidth);
			}
			
		}
		
		else if (ydif > xdif){
			
			//moving down, normally up
			if (pacY < Yindex && field[Xindex][Yindex + 1].getState() != Tile.WALL) {
				g.setDirection(Ghost.DOWN);
			}
			//moving up, normally down
			else if (pacY > Yindex && field[Xindex][Yindex - 1].getState() != Tile.WALL) {
				g.setDirection(Ghost.UP);
			}
			else {
				updateRandom(field, g, blockwidth);
			}
			
		}
		
	}
	
	//finds the length of the most efficient path for the ghost
	public int bestPathLength (Tile[][] field, Ghost g, int pacX, int pacY, int blockwidth) {
		
//		System.out.println(pacX + " " + pacY);
		
		boolean[][] isChecked = new boolean[field.length][field[0].length];
		
		int score = 0;
		
		if (g.getX() == pacX && g.getY() == pacY) {
			return 0;
		}
		
		int Xindex = (g.getX() - 25) / blockwidth;
		int Yindex = (g.getY() - 50) / blockwidth;
		
		Ghost newGhost = new Ghost(g.getX(), g.getY());
		
		return 0;
		
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

	//resets the ghost to its original location given the arraylist index of the ghost
	public void returnToSpawn(Tile[][] field, int index, int blockwidth) {
		
		int spawnNum = -1;
		
		for (int r = 0; r < field.length; r++) {
			
			for (int c = 0; c < field[0].length; c++) {
				
				if (field[r][c].getState() == Tile.GHOSTSPAWN) {
					
					spawnNum ++;
					
					if (spawnNum == index) {
						ghosts.get(index).setLocation(r * blockwidth + 25, c * blockwidth + 50);
//						wallCollision(field, ghosts.get(index), blockwidth);
						align(ghosts.get(index), blockwidth);
						ghosts.get(index).setDirection(Ghost.STILL);
					}
				}
				
			}
			
		}
		
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
