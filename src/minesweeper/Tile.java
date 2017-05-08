package minesweeper;

//determines the properties that the tiles can have
public class Tile {
	
	//fields
	private boolean isHidden;
	private boolean isMine;
	private boolean isFlagged;
	private int num;
	
	
	//default constructor 0 represents open, 9 represents mines
	public Tile(boolean h, boolean m, int n){
		this.isHidden = h;
		this.isMine = m;
		this.num = n;
	}
	
	//constructor for before the number is known defaults the number to zero
	public Tile(boolean h, boolean m) {
		this.isHidden = h;
		this.isMine = m;
		this.num = 0;
	}
	
	public boolean isFlagged() {
		return this.isFlagged;
	}
	
	//toggles flag
	public void toggleFlag() {
		if (this.isFlagged) {
			this.isFlagged = false;
		}
		else {
			this.isFlagged = true;
		}
	}
	
	public void removeBomb() {
		this.isMine = false;
	}
	
	//un-hides a tile
	public void show() {
		this.isHidden = false;
	}
	
	//sets the number representing how many mines are adjacent to a cell
	public void setNum(int n) {
		this.num = n;
	}
	
	//returns if the tile is hidden
	public boolean getIsHidden() {
		return this.isHidden;
	}
	
	//returns if the tile is a mine
	public boolean getIsMine() {
		return this.isMine;
	}
	
	//returns the number on the tile
	public int getIsNum() {
		return this.num;
	}
	// returns true if the tile is a neighbor
	
}
