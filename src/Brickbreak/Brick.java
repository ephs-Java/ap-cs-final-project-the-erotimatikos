package Brickbreak;

public class Brick {

	private boolean broken;
	private int xloc;
	private int yloc;
	public Brick() {
		broken =false;
	}
	public Brick(int x, int y){
		xloc=x;
		yloc=y;
	}
    public void Break(){
    	broken= true;
    }
    public boolean isAlive(){
    	return broken;
    }
    public void setbool(boolean a){
    	broken= a;
    }
    public int getX(){
    	return xloc;
    }
    public int getY(){
    	return yloc;
    }
    public void setX(int a){
    	xloc= a;
    }
    public void setY(int a){
    	yloc= a;
    }
}
