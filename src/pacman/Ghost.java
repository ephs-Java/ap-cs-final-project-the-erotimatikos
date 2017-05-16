package pacman;

public class Ghost {

	//fields for the x and y location of the ghost and whether or not they are active
	private int x;
	private int y;
	private int speed;
	private boolean isActive;
	
	//fields for which direction the ghost is moving in
//	public boolean movingUp;
//	public boolean movingLeft;
//	public boolean movingRight;
//	public boolean movingDown;
	
	private int direction;
	
	
	//direction constants
	public static final int STILL = 0;
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public static final int LEFT = 4;
	
	//constructor with a x and y location of the ghost
	public Ghost(int xloc, int yloc) {
		this.direction = 0;
		this.x = xloc;
		this.y = yloc;
	}
	
	//moves the ghost to the parameter location
	public void setLocation(int xloc, int yloc) {
		this.x = xloc;
		this.y = yloc;
	}
	
	//sets the speed of the ghost
	public void setSpeed(int s) {
		this.speed = s;
	}
	
	//returns the current speed
	public int getSpeed() {
		return this.speed;
	}
	
	//returns the x location
	public int getX() {
		return this.x;
	}
	
	//returns the y location
	public int getY() {
		return this.y;
	}
	
	//makes the ghost not move
	public void activate() {
		this.isActive = true;
	}
	
	//makes the ghost move
	public void deactivate() {
		this.isActive = false;
	}
	
	public int getDirection() {
		return this.direction;
	}
	
	//sets the direction
	public void setDirection(int d) {
		this.direction = d;
	}
	
	//returns a string representation of the ghost with the x and y location
	public String toString() {
		return "X: " + this.x + " Y: " + this.y;
	}
	
}
