package MarioGameTestingPlatForm;

public class brick {

	public int type; //0 == open, 1 == mario, 2 == wall, 3 == .....
	//private boolean isAlive;
	public boolean isAlive;
	
	public int xB,yB;
	public static boolean flip = true;
	
	
	
	
	public brick(){
		
		this.type = 0;
		
	}
	public brick(int x, int y){
		type = 9;
		this.xB =x/30;
		this.yB = y/30;
		
	}
	public void setBrick(int x, int y){
		this.xB =x;
		this.yB = y;
		
	}
	
//	public void move(brick[][] array){
//		if(array[xE+1][yE].getType() != 1 && flip){
//			xE++;
//		}
//		
//		else if(array[xE-1][xE].getType() != 1 && !flip){
//			xE--;
//		}
//		else{
//			flip = !flip;
//		}
//		
//	}
	
	
	public brick(int i){
		this.type = i;
	}
	public int getType(){
		return this.type;
	}
	public void setType(int i){
		this.type = i;
	}
}
