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
	public boolean isCats(int numOfClicks){
		if(numOfClicks > 9){
			return true;
		}
		return false;
	}
	
	public String isWinner(int numOfClicks){
		
		if(tiles[0][0].get() == 1 && tiles[0][1].get() == 1 && tiles[0][2].get() == 1){
			return "Green Wins";
		}
		return "No Winner";
		
		
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

