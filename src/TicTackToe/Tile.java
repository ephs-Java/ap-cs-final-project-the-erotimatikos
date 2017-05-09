package TicTackToe;

public class Tile {

	//0 == empty, 1 == X; Y== 3;
	private int XO;
	
	
	public Tile(){
		this.XO = 0;
		
	}
	
	
	
	
	public int get(){
		return XO;
	}
	
	//X is true
	public void setTo(int num){
		XO = num;
	}
	
	
	
	
	
}
