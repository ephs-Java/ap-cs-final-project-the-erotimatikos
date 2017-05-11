package pacman;

public class Ghost {

	//fields for the x and y location of the ghost and whether or not they are active
	private int x;
	private int y;
	private int speed;
	private boolean isActive;
	
	//constructor with a x and y location of the ghost
	public Ghost(int xloc, int yloc) {
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
	
	//makes the ghost not move
	public void activate() {
		this.isActive = true;
	}
	
	//makes the ghost move
	public void deactivate() {
		this.isActive = false;
	}
	
}
