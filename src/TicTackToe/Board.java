package TicTackToe;


//2d Array of Tiles
public class Board {

	private Tile[][] tiles;
	
	private void set(int num){
		for(int r = 0;r<tiles.length;r++){
			for(int c = 0; c<tiles[0].length;c++){
				tiles[r][c].setTo(num);
					
				}
			}
		}
	}

