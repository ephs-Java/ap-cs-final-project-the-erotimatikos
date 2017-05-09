package TicTackToe;

public class Tile {

	//0 == empty, 1 == X; Y== 3;
	private int XO;
	
	
	public Tile(){
		this.XO = 0;
		
	}
	public Tile(int n){
		this.XO = n;
		
	}
	
	public int get(){
		return this.XO;
	}
	
	//X is true
	public void setTo(int num){
		XO = num;
	}
	
	
	
	
	
}
