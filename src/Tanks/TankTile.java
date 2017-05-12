package Tanks;

public class TankTile {

	private boolean isEnemy;
	private boolean isWall;
	private boolean isTank;
	private int row;
	private int col;
	
	public TankTile(){
		isEnemy = false;
		isWall = false;
		isTank = false;
	}
	
	public TankTile(int row, int col){
		isEnemy = false;
		isWall = false;
		isTank = true;
		this.row = row;
		this.col = col;
	}
	
	public boolean getIsWall(){
		return isWall;
	}

	
}
