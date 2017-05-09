package pacman;

public class Maze {

	Tile[][] maze;
	
	public Maze() {
		
		maze = new Tile[10][10];
		
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[0].length; c++) {
				
				Tile blank = new Tile(0);
				maze[r][c] = blank;
				
			}
		}
		
	}
	
	public Maze(Tile[][] m) {
		
		maze = new Tile[m.length][m[0].length];
		
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[0].length; c++) {
				
				maze[r][c] = m[r][c];
				
			}
		}
		
	}
	
	public String toString() {
		
		String message = "";
		
		
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[0].length; c++) {
				
				message += maze[r][c] + " ";
				
			}
			message += "\n";		}
		
		return message;
		
	}
	
}
