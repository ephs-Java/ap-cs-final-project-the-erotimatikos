package pacman;

public class Pacman {

	//location on the screen
	private int pacmanX;
	private int pacmanY;
	
	//index in the maze
	private int pacXindex;
	private int pacYindex;
	
	//direction that the pacman is heading
	private int direction;
	private int lastDirection;
	
	//direction constants
	public static final int STOP = 0;
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	public static final int LEFT = 4;
	
	//main constructor
	public Pacman() {
		
		pacmanX = 0;
		pacmanY = 0;
		
		pacXindex = 0;
		pacYindex = 0;
		
	}
	
	//updates location based on direction
	public void update(int speed) {
		
		switch (direction) {
		
		case UP:
			pacmanY -= speed;
			lastDirection = UP;
			break;
		case RIGHT:
			pacmanX += speed;
			lastDirection = RIGHT;
			break;
		case DOWN:
			pacmanY += speed;
			lastDirection = DOWN;
			break;
		case LEFT:
			pacmanX -= speed;
			lastDirection = LEFT;
			break;
		
		}
		
	}
	
	//getters and setters
	public int getPacmanX() {
		return pacmanX;
	}
	public void setPacmanX(int pacmanX) {
		this.pacmanX = pacmanX;
	}
	
	public int getPacmanY() {
		return pacmanY;
	}
	public void setPacmanY(int pacmanY) {
		this.pacmanY = pacmanY;
	}
	
	public int getPacXindex() {
		return pacXindex;
	}
	public void setPacXindex(int pacXindex) {
		this.pacXindex = pacXindex;
	}
	
	public int getPacYindex() {
		return pacYindex;
	}
	public void setPacYindex(int pacYindex) {
		this.pacYindex = pacYindex;
	}

	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public int getLastDirection() {
		return this.lastDirection;
	}
	
}
