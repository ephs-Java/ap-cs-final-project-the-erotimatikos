package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class Editor extends JFrame {
	
	//double buffering
	Image dbImage;
	Graphics dbg;
	
	//maze field
	Maze maze;
	
	//maze dimensions
	final static int MAZEX = 30;
	final static int MAZEY = 15;
	
	//width of each block
	final int BLOCKWIDTH = 30;
	
	//mouse x and y coordinates
	int mouseX = 100;
	int mouseY = 100;
	
	//x and y index for the selected block
	int blockX = 0;
	int blockY = 0;
	
	final static int SCREENX = MAZEX * 33;
	final static int SCREENY = MAZEY * 33 + 100;
	
	//main method
	public Editor() {
		
		//assigns value to maze field
		maze = new Maze(MAZEX, MAZEY);
		System.out.println(maze);
		
		//keyboard listener
		addKeyListener(new keyboard());
		
		//mouse listener
		addMouseListener(new mouse());
		
		//sets the properties of the screen
//		this.setLocation(500, 100);
		setTitle("Pac-Man Level Editor");
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBackground(Color.black);
		
	}

	//class that handles mouse input
	public class mouse extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			
			mouseX = e.getX();
			mouseY = e.getY();
			
			blockX = (mouseX - 27) / BLOCKWIDTH;
			blockY = (mouseY - 54) / BLOCKWIDTH;
			
			if (blockX < 0) {
				blockX = 0;
			}
			if (blockY < 0) {
				blockY = 0;
			}
			
			if (blockX > maze.maze.length - 1) {
				blockX = maze.maze.length - 1;
			}
			if (blockY > maze.maze[0].length - 1) {
				blockY = maze.maze[0].length - 1;
			}
			
			
			
			updateAltState(blockX, blockY);
			
		}
		
		public void mouseReleased(MouseEvent e) {
			
			
			
		}
		
	}
	
	//updates the state from the given point
	public void updateState(int r, int c) {
		
		int blockState = maze.maze[r][c].getState();
		blockState ++;
		
		if (blockState > Tile.PILL) {
			blockState = Tile.BLANK;
		}
		maze.maze[r][c].setState(blockState);
		
	}
	
	//updates the state from the given point using the alt items
	public void updateAltState(int r, int c) {
		
		int blockState = maze.maze[r][c].getState();
		blockState ++;
		
		if (blockState < Tile.SPAWN || blockState > Tile.TELEPORTER2) {
			blockState = Tile.SPAWN;
		}
		
		maze.maze[r][c].setState(blockState);
		
	}
	
	//class that handles key input
	public class keyboard extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
			
			if (key == e.VK_Q) {
				System.exit(0);
			}
			if (key == e.VK_LEFT) {
				if (blockX > 0) {
					blockX --;
				}
			}
			if (key == e.VK_UP) {
				if (blockY > 0) {
					blockY --;
				}
			}
			if (key == e.VK_RIGHT) {
				if (blockX < maze.maze.length) {
					blockX ++;
				}
			}
			if (key == e.VK_DOWN) {
				if (blockY < maze.maze[0].length) {
					blockY ++;
				}
			}
			if (key == e.VK_Z) {
				updateState(blockX, blockY);
			}
			
		}
		
		public void keyReleased(KeyEvent e) {
			
			int key = e.getKeyCode();
			
		}
		
	}
	
	//paints stuff to the jframe
	public void paintComponent(Graphics g) {
		
		for (int r = 0; r < maze.maze.length; r++) {
			for (int c = 0; c < maze.maze[0].length; c++) {
				
				int xloc = 25 + r * BLOCKWIDTH;
				int yloc = 50 + c * BLOCKWIDTH;
				
				int state = maze.maze[r][c].getState();
				
				if (state == Tile.WALL) {
					g.setColor(Color.blue);
					g.fillRect(xloc, yloc, BLOCKWIDTH, BLOCKWIDTH);
				}
				else if (state == Tile.BLANK) {
					g.setColor(Color.black);
					g.fillRect(xloc, yloc, BLOCKWIDTH, BLOCKWIDTH);
				}
				else if (state == Tile.PILL) {
					g.setColor(Color.white);
					g.fillOval(xloc + BLOCKWIDTH / 4,
					yloc + BLOCKWIDTH / 4, BLOCKWIDTH / 2, BLOCKWIDTH / 2);
				}
				else if (state == Tile.SPAWN) {
					g.setColor(Color.BLUE);
					g.fillOval(xloc + BLOCKWIDTH / 4,
							yloc + BLOCKWIDTH / 4, BLOCKWIDTH / 2, BLOCKWIDTH / 2);
				}
				else if (state == Tile.TELEPORTER) {
					g.setColor(Color.yellow);
					g.fillOval(xloc + BLOCKWIDTH / 4,
							yloc + BLOCKWIDTH / 4, BLOCKWIDTH / 2, BLOCKWIDTH / 2);
				}
				else if (state == Tile.TELEPORTER2) {
					g.setColor(Color.MAGENTA);
					g.fillOval(xloc + BLOCKWIDTH / 4,
							yloc + BLOCKWIDTH / 4, BLOCKWIDTH / 2, BLOCKWIDTH / 2);
				}
				
				g.setColor(Color.blue);
				g.drawRect(xloc, yloc, BLOCKWIDTH, BLOCKWIDTH);
				
			}
		}
		
		//cursor
		g.setColor(Color.red);
		g.drawRect(25 + blockX * BLOCKWIDTH - 1, 50 + blockY * BLOCKWIDTH - 1,
				BLOCKWIDTH + 2, BLOCKWIDTH + 2);
		g.drawRect(25 + blockX * BLOCKWIDTH - 2, 50 + blockY * BLOCKWIDTH - 2,
				BLOCKWIDTH + 4, BLOCKWIDTH + 4);
		
		g.setColor(Color.white);
		g.drawString("Mouse X: " + mouseX + " Mouse Y: " + mouseY, 50, SCREENY - 50);
		g.drawString("Block X: " + blockX + " Block Y: " + blockY, 50, SCREENY - 30);
		
		repaint();
		
	}
	
	//double buffering
	public void paint(Graphics g) {
		
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	
	
	
}
