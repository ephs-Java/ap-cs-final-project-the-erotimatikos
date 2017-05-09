package minesweeper;

import java.lang.reflect.Field;
import java.text.FieldPosition;

import minesweeper.Tile;

public class Mines {

	// The 2d grid of tile objects. represents the field
	public Tile[][] tiles;

	// 10 is a good # for the default dimensions, 1 in n chance of a bomb
	// spawning at each tile
	private final double BOMBCHANCE = 6;

	// 0 = open 9 == mine, 1= 1 mine nearby, 2 = 2 mines nearby ...
	// Randomly assigns the tiles in a 2d grid a value of bomb or not.
	public Mines(int row, int column) {

		// creates a minesweeper board 20x20
		tiles = new Tile[row][column];

		for (int r = 0; r < row; r++) {

			for (int c = 0; c < column; c++) {
				// Randomly assigns tile as mine or not mine
				int ran = ((int) (Math.random() * BOMBCHANCE + 1));
				if (ran == 1) {
					tiles[r][c] = new Tile(true, true, 9);
				} else {
					tiles[r][c] = new Tile(true, false, 0);
				}
			}
		}
	}

	public boolean isVictory() {

		for (int r = 0; r < tiles.length; r++) {
			for (int c = 0; c < tiles[0].length; c++) {

				if (tiles[r][c].getIsHidden() && !tiles[r][c].getIsMine()) {
					return false;
				}
			}
		}
		return true;

	}

	public boolean isNextTo(int a, int b, int a2, int b2) {

		if (a == a2 && b == b2 + 1) {
			return true;
		} else if (a == a2 && b == b2 - 1) {
			return true;
		} else if (a == a2 + 1 && b == b2 - 1) {
			return true;
		} else if (a == a2 + 1 && b == b2) {
			return true;
		} else if (a == a2 + 1 && b == b2 + 1) {
			return true;
		} else if (a == a2 - 1 && b == b2 - 1) {
			return true;
		} else if (a == a2 - 1 && b == b2) {
			return true;
		} else if (a == a2 - 1 && b == b2 + 1) {
			return true;
		}
		return false;
	}

	// how to format input: a,b =tiles[row][col].
	// returns int of how many bombs around
	public int getBombs(int rowa, int cola) {
		int counter = 0;
		for (int row = 0; row < tiles.length; row++) {
			for (int col = 0; col < tiles[row].length; col++) {
				if (isNextTo(rowa, cola, row, col)) {
					if ((tiles[row][col].getIsMine())) {
						counter++;
					}
				}
			}
		}
		return counter;

	}

	// returns the number of adjacent bombs to a point. faster than the previous
	// method, but a little messier. 
	/**
	 * Returns the number of adjacent bombs to a point. Faster than the previous version.
	 * 
	 * @param r The row index of the point to be checked.
	 * @param c The column index of the point to be checked.
	 * @return The number of adjacent bombs to the point at tiles[r][c]
	 */
	public int getBombs2(int r, int c) {

		int adj = 0;
		// upper left
		if (r > 0 && c > 0 && tiles[r - 1][c - 1].getIsMine()) {
			adj++;
		}
		// up
		if (r > 0 && tiles[r - 1][c].getIsMine()) {
			adj++;
		}
		// left
		if (c > 0 && tiles[r][c - 1].getIsMine()) {
			adj++;
		}
		// down
		if (r < tiles.length - 1 && tiles[r + 1][c].getIsMine()) {
			adj++;
		}
		// right
		if (c < tiles[0].length - 1 && tiles[r][c + 1].getIsMine()) {
			adj++;
		}
		// lower right
		if (c < tiles[0].length - 1 && r < tiles.length - 1 && tiles[r + 1][c + 1].getIsMine()) {
			adj++;
		}
		// lower left
		if (c > 0 && r < tiles.length - 1 && tiles[r + 1][c - 1].getIsMine()) {
			adj++;
		}
		// upper right
		if (c < tiles[0].length - 1 && r > 0 && tiles[r - 1][c + 1].getIsMine()) {
			adj++;
		}

		return adj;

	}
	
	/**
	 * Updates the number of adjacent bombs for each non-bomb tile. Used so the paintComponent method
	 * does not have to run getBombs constantly, but can just get the value set by this method.
	 */
	public void updateAllNums() {

		for (int r = 0; r < tiles.length; r++) {

			for (int c = 0; c < tiles[0].length; c++) {

				tiles[r][c].setNum(getBombs2(r, c));

			}

		}

	}

	// Prints minefield of bomb status
	public void printMineField() {
		for (int r = 0; r < tiles.length; r++) {

			for (int c = 0; c < tiles[0].length; c++) {

				System.out.print(tiles[r][c].getIsMine() + " ");
				if (tiles[r][c].getIsMine()) {
					System.out.print(" ");
				}

			}
			System.out.println();
		}

	}

	// returns the tile at the given index
	public Tile select(int r, int c) {
		this.tiles[r][c].show();
		if (tiles[r][c].getIsMine()) {
			boom();
		}
		return tiles[r][c];

	}

	/**
	 * Unhides all bombs. Is called when the user clicks on a bomb. Indicates game over. 
	 * 
	 */
	public void boom() {

		for (int r = 0; r < tiles.length; r++) {

			for (int c = 0; c < tiles[0].length; c++) {

				if (tiles[r][c].getIsMine()) {

					tiles[r][c].show();

				}

			}

		}

	}

	public void clearMine(Tile tile) {
		tile.removeBomb();
	}

	
	/**
	 * Updates the screen from the given point. First, makes a 3 by 3 area around the point visible.
	 * Then, it checks for adjacent blocks with zero nearby bombs, then runs the method with that point
	 * as a parameter. Runs recursively until the screen is updated.
	 * 
	 * @param row The row for the point to be updated.
	 * @param col The column for the point to be updated.
	 */
	public void updateFromPoint(int row, int col) {

		tiles[row][col].show();

		if (tiles[row][col].getIsNum() != 0 || tiles[row][col].getIsMine()) {
			return;
		}
		//makes 3 by 3 area visible
		for (int r = 0; r < tiles.length; r++) {

			for (int c = 0; c < tiles[0].length; c++) {

				if (isNextTo(row, col, r, c)) {
					// clearMine(tiles[r][c]);
					tiles[r][c].show();
				}
			}
		}

		for (int r = 0; r < tiles.length; r++) {

			for (int c = 0; c < tiles[0].length; c++) {

				if (tiles[r][c].getIsNum() == 0 && isNextTo(row, col, r, c) && !isCleared(r, c)) {
					updateFromPoint(r, c);
					// System.out.println("r = " +r + " c = " + c);
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

	public void clearArea(int rowa, int cola) {

		clearMine(tiles[rowa][cola]);
		for (int r = 0; r < tiles.length; r++) {
			for (int c = 0; c < tiles[r].length; c++) {
				if (isNextTo(rowa, cola, r, c)) {
					clearMine(tiles[r][c]);
					tiles[r][c].show();
				}
			}
		}
	}

}
