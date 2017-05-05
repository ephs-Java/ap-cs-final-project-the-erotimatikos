package minesweeper;

import minesweeper.Tile;

//The 2d grid of
public class Mines {
	public Tile[][] tiles;
	
//Enter th
	private final double BOMBSTAT = 10;
	
	
	//public Tile(boolean h, boolean m, int n){
	//0 = open 9 == mine, 1= 1 mine nearby, 2 = 2 mines nearby ...
	//Randomly assigns the tiles in a 2d grid a value of bomb or not.	
	public Mines(int row, int column){

		//creates a minesweeper board 20x20
		tiles = new Tile[row][column];

		for(int r = 0;r<row;r++){

			for(int c = 0;c<column;c++){
				//Randomly assigns tile as mine or not mine
				int ran = ((int)(Math.random() * BOMBSTAT + 1));
				if(ran == 1){
					tiles[r][c] = new Tile(true,true,9);
				}
				else{
					tiles[r][c] = new Tile(true,false,0);
				}
			}
		}


	}
	public boolean isNextTo(int a, int b, int a2, int b2){
		boolean next= false;
		if (a== a2 && b== b2+1){
			next= true;
		}
		else if (a== a2 && b== b2-1){
			next= true;
		}
		else if (a== a2+1 && b== b2-1){
			next= true;
		}
		else if (a== a2+1 && b== b2){
			next= true;
		}
		else if (a== a2+1 && b== b2+1){
			next= true;
		}
		else if (a== a2-1 && b== b2-1){
			next= true;
		}
		else if (a== a2-1 && b== b2){
			next= true;
		}
		else if (a== a2-1 && b== b2+1){
			next= true;
		}
		return next;
	}
	// how to format input: a,b =tiles[row][col].
	// returns int of how many bombs around
	public int getBombs(int rowa, int cola){
		int counter=0;
		for (int row= 0; row<tiles.length; row++){
			for(int col=0; col<tiles[row].length; col++){
				if (isNextTo(rowa, cola, row, col)){
					if ((tiles[row][col].getIsMine())){
					counter++;
					}
				}
			}
		}
		return counter;
		
	}
	
	//updates the number for every bomb on the screen
	public void updateAllNums() {
		
		for (int r = 0; r < tiles.length; r++) {
			
			for (int c = 0; c < tiles[0].length; c++) {
				
				tiles[r][c].setNum(getBombs(r, c));
				
			}
			
		}
		
	}
	
	//Prints minefield of bomb status 
	public void printMineField(){
		for(int r = 0;r<tiles.length;r++){

			for(int c = 0;c<tiles[0].length;c++){

				System.out.print(tiles[r][c].getIsMine() + " ");
				if (tiles[r][c].getIsMine()) {
					System.out.print(" ");
				}

			}
			System.out.println();
		}


	}

	//returns the tile at the given index
	public Tile select(int r, int c){
		this.tiles[r][c].show();
		if(tiles[r][c].getIsMine()){
			boom(tiles[r][c]);
		}
		
		return tiles[r][c];
		
	}
	
	// Sets off all bombs. It is called each time bomb is selected 
	public void boom(Tile ne){
		int off = 0;
		
		if(ne.getIsMine()){
		for(int r = 0;r<tiles.length;r++){
			for(int c = 0;c<tiles[0].length;c++){
				if(tiles[r][c].getIsMine()){
					tiles[r][c].show();
				}
				
				
			}
		}
		}
		System.out.println("Game over");
	}

	public void clearMine(Tile ne){
		ne.removeBomb();
	}
	
	public void updateFromPoint(int row, int col) {
		
		tiles[row][col].show();
		
		//clears 3 by 3 area
		if (getBombs(row, col) != 0 || tiles[row][col].getIsMine()) {
			return;
		}
		for (int r = 0; r < tiles.length; r++){
			
			for(int c = 0; c < tiles[0].length; c++){
				
				if (isNextTo(row, col, r, c)){
//					clearMine(tiles[r][c]);
					tiles[r][c].show();
				}
			}
		}
		
		for (int r = 0; r < tiles.length; r++) {
			
			for (int c = 0; c < tiles[0].length; c++) {
				
				if (getBombs(r, c) == 0 && isNextTo(row, col, r, c) && !isCleared(r, c)) {
					updateFromPoint(r, c);
//					System.out.println("r = "  +r + " c = " +  c);
				}
				
			}
		}
		
	}
	
	public boolean isCleared(int row, int col) {
		
		for (int r = 0; r < tiles.length; r++) {
			
			for (int c = 0; c < tiles[0].length; c++) {
				
				if (isNextTo(r, c, row, col) && tiles[r][c].getIsHidden()) {
					return false;
				}
				
			}
			
		}
		return true;
	}
	
	public void clearArea(int rowa, int cola){
						
		clearMine(tiles[rowa][cola]);
		for (int r= 0; r<tiles.length; r++){
			for(int c=0; c<tiles[r].length; c++){
				if (isNextTo(rowa, cola, r, c)){
					clearMine(tiles[r][c]);
					tiles[r][c].show();
				}
			}
		}
	}
	
//g5y3rqufweahasuh
// WORK!

}

