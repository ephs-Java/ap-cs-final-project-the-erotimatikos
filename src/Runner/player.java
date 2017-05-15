package Runner;

public class player {

	public int x;
	public int y;
	
	boolean isAlive;
	
	public player(){
		x = 100;
		y = 100;
	}
	
	public void dead(){
		this.isAlive = false;
	}
	public void moveTo(int xT, int yT){
		
		if(xT > x){
			this.x += .01;	
		}
		else{
			this.x = this.x - 1;
		}
		if(yT > y){
			this.y = this.y + 1;
		}
		else{
			this.y = this.y - 1;
		}
	}
}
