package pacman;

public class Maze {

	Tile[][] maze;
	
	//blank 10 by 10 default array for testing
	public Maze() {
		
		maze = new Tile[10][10];
		
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[0].length; c++) {
				
				Tile blank = new Tile(0);
				maze[r][c] = blank;
				
			}
		}
		
	}
	
	//maze with given dimensions
	public Maze(int x, int y) {
		
		maze = new Tile[x][y];
		
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[0].length; c++) {
				
				Tile blank = new Tile(0);
				maze[r][c] = blank;
				
			}
		}
		
	}
	
	//maze with a 2d array to replace the current one with
	public Maze(Tile[][] m) {
		
		maze = new Tile[m.length][m[0].length];
		
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[0].length; c++) {
				
				maze[r][c] = m[r][c];
				
			}
		}
		
	} 
	
	//checks for victory
	public boolean isVictory() {
		
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[0].length; c++) {
				if (maze[r][c].getState() == Tile.PILL) {
					return false;
				}
			}
		}
		
		return true;
		
	}
	
	//puts blocks around the edges of the screen
	public void fillEdges() {
		
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[0].length; c++) {
				
				if (r == 0 || c == 0 || r == maze.length - 1 || c == maze[0].length - 1) {
					maze[r][c].setState(Tile.WALL);
				}
				
			}
		}
		
	}
	
	//prints out the current maze 2d array. each item is the number value of each tile
	public String toString() {
		
		String message = "";
		
		
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[0].length; c++) {
				
				message += maze[r][c].getState() + " ";
				
			}
			message += "\n";		
		}
		
		return message;
		
	}
	
}
