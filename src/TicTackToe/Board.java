package TicTackToe;


//2d Array of Tiles
public class Board {

	public Tile[][] tiles;
	
	public Board(){
		tiles = new Tile[3][3];
		for(int r = 0; r<3;r++){
			for(int c = 0;c<3;c++){
				tiles[r][c] = new Tile();
			}
		}
	}
	
	public void print(){
		for(int r = 0;r<3;r++){
			for(int c = 0; c<3;c++){
				System.out.print(tiles[r][c] + " ");
					
				}
			System.out.println();
			}
		}
	}

