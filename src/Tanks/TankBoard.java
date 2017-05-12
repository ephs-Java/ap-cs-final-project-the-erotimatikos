package Tanks;

public class TankBoard {

	
	public TankTile[][] tiles = new TankTile[100][30];
	
	public TankBoard(){
		for(int row = 0; row < 100; row++){
			for(int col = 0;col < 30;col++){
				tiles[row][col] = new TankTile();
			}
		}
	}
}
