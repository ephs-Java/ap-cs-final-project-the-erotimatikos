package pacman;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

public class Screen extends JFrame implements Runnable {
	
	//double buffering
	Image dbImage;
	Graphics dbg;
	
	//maze field
	Maze maze;
	
	//ghosts field
	Ghosts ghosts;
	
	//queue field
	Queue queue;
	
	//queue size
	final int QUEUESIZE = 100;
	
	//width of each block
	final int BLOCKWIDTH = 30;
	
	//mouse x and y coordinates
	int mouseX = 100;
	int mouseY = 100;
	
	//pac man x and y coordinates
	int pacmanX;
	int pacmanY;
	
	//pac man x and y index
	int pacmanXindex;
	int pacmanYindex;
	
	//pacman moving direction
	boolean movingUp = false;
	boolean movingRight = false;
	boolean movingDown = false;
	boolean movingLeft = false;
	
	//maze dimensions
	final static int MAZEX = 30;
	final static int MAZEY = 15;
	
	//screen dimensions
	int screenX = MAZEX * 33;
	int screenY = MAZEY * 33;
	
	//current level that the user is on
	int level = 1;
	
	//the thread
	public void run() {
		
		try {
			
			while (true) {
				
				checkMovement();
				Thread.sleep(10);
				queue.update();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//main method
	public Screen() {
		
		ghosts = new Ghosts();
		queue = new Queue(QUEUESIZE);
		maze = new Maze(MAZEX, MAZEY);
		//loads maze
		try {
			load("src/pacman/level" + level + ".txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setup();
		screenX = maze.maze.length * 33;
		screenY = maze.maze[0].length * 35;
		
		
//		System.out.println(maze);
		
		//keyboard listener
		addKeyListener(new keyboard());
		
		//sets the properties of the screen
		setTitle("Pac-Man");
		setVisible(true);
		setSize(screenX, screenY);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		
	}
	
	public void updateVars() {
		
		//updates pac man x and y index vars
		for (int r = 0; r < maze.maze.length; r++) {
			for (int c = 0; c < maze.maze[0].length; c++) {
				
				int xdif = Math.abs((r * BLOCKWIDTH + 25) - pacmanX);
				int ydif = Math.abs((c * BLOCKWIDTH + 50) - pacmanY);
				
//				System.out.println(xdif + " " + ydif + "r= " + r + " c= " + c);
				
				
				if (xdif < BLOCKWIDTH && ydif < BLOCKWIDTH) {
					pacmanXindex = r;
					pacmanYindex = c;
					return;
				}
				
			}
		}
		pacmanXindex = 0;
		pacmanYindex = 0;
		
		
	}
	
	//checks which direction to go in
	public void checkMovement() {
		
		boolean xAligned = true;
		boolean yAligned = true;
		
		//checks for x axis alignment
		if ((pacmanX - 25) % BLOCKWIDTH <= 0) {
			
		}
		else {
			movingUp = false;
			movingDown = false;
			xAligned = false;
		}
		//checks for y axis alignment
		if ((pacmanY - 50) % BLOCKWIDTH <= 0) {
			
		}
		else {
			movingLeft = false;
			movingRight = false;
			yAligned = false;
		}
		
		updateVars();
		
		//checks for wall collision
		if (movingLeft && maze.maze[pacmanXindex][pacmanYindex].getState() == Tile.WALL) {
			movingLeft = false;
			pacmanX = 25 + pacmanXindex * BLOCKWIDTH + BLOCKWIDTH;
		}
		if (movingRight && maze.maze[pacmanXindex + 1][pacmanYindex].getState() == Tile.WALL) {
			movingRight = false;
			pacmanX = 25 + pacmanXindex * BLOCKWIDTH;
		}
		if (movingUp && maze.maze[pacmanXindex][pacmanYindex].getState() == Tile.WALL) {
			movingUp = false;
			pacmanY = 50 + pacmanYindex * BLOCKWIDTH + BLOCKWIDTH;
		}
		if (movingDown && maze.maze[pacmanXindex][pacmanYindex + 1].getState() == Tile.WALL) {
			movingDown = false;;
			pacmanY = 50 + pacmanYindex * BLOCKWIDTH;
		}
		
		//checks if you can move up
		if (queue.indexOf("UP") != -1 && xAligned) {
//			pacmanY --;
			halt();
			movingUp = true;
			queue.remove(queue.indexOf("UP"));
		}
		//checks if you can move left
		else if (queue.indexOf("LEFT") != -1 && yAligned) {
			
//			pacmanX --;
//			System.out.println((pacmanX - 25) % BLOCKWIDTH);
			halt();
			movingLeft = true;
			queue.remove(queue.indexOf("LEFT"));
			
		}
		//checks if you can move down
		else if (queue.indexOf("DOWN") != -1 && xAligned) {
//			pacmanY ++;
			halt();
			movingDown = true;
			queue.remove(queue.indexOf("DOWN"));
		}
		//checks if you can move right
		else if (queue.indexOf("RIGHT") != -1 && yAligned) {
//			pacmanX ++;
			halt();
			movingRight = true;
			queue.remove(queue.indexOf("RIGHT"));
		}
		
		//updates the index
		if (movingRight && yAligned) {
			pacmanX ++;
		}
		if (movingLeft && yAligned) {
			pacmanX --;
		}
		if (movingUp && xAligned) {
			pacmanY --;
		}
		if (movingDown && xAligned) {
			pacmanY ++;
		}
		
		
		
	}
	
	//stops all movement
	public void halt() {
		
		movingRight = false;
		movingLeft = false;
		movingUp = false;
		movingDown = false;
		
	}
	
	//sets up the pac man location for starting up the game and sets up ghost positions
	public void setup() {
		
		for (int r = 0; r < maze.maze.length; r++) {
			for (int c = 0; c < maze.maze[0].length; c++) {
				
				if (maze.maze[r][c].getState() == Tile.SPAWN) {
					
					//sets the pac man index and location on screen
					pacmanX = r * BLOCKWIDTH + 25;
					pacmanY = c * BLOCKWIDTH + 50;
					pacmanXindex = r;
					pacmanYindex = c;
					return;
					
				}
				
			}
		}		
		pacmanX = 0 * BLOCKWIDTH + 25;
		pacmanY = 0 * BLOCKWIDTH + 50;
		
		pacmanXindex = 0;
		pacmanYindex = 0;
	}
	
	//loads the given file into the current state
	public void load(String s) throws FileNotFoundException{
		
		File f = new File(s);
		
		if (!f.exists()) {
			System.out.println("file not found");
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("new file created");
			maze = new Maze(MAZEX, MAZEY);
			save(s);
			return;
		}
		
		Scanner input = new Scanner(f);
		
		
		
		int totalRows = 0;
		int totalCols = 0;
		
		//gets number of rows and columns
		while (input.hasNext()) {
			String line = input.nextLine();
			totalCols = line.length() / 2;
			totalRows ++;
		}
		maze = new Maze(totalCols, totalRows);
		
		Scanner update = new Scanner(new File(s));
		int row = 0;
		while (update.hasNext()) {
			String line = update.nextLine();
			for (int i = 0; i < totalCols; i++) {
				maze.maze[i][row].setState(Integer.parseInt(line.substring(i * 2, i * 2 + 1)));
			}
			row ++;
		}
		
	}
	
	//saves the current state into the given file
	public void save(String s) throws FileNotFoundException{
			
			File f = new File(s);
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
				
				String content = "";
				
				for (int c = 0; c < maze.maze[0].length; c++) {
					for (int r = 0; r < maze.maze.length; r++) {
						content += maze.maze[r][c].getState() + " ";
					}
					content += "\n";
				}
				
				bw.write(content);
				
				// no need to close it.
				//bw.close();
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
		}
	
	//class that handles key input
	public class keyboard extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
//			System.out.println(queue);
			if (key == e.VK_Q) {
				System.exit(0);
			}
			else if (key == e.VK_UP) {
				queue.add("UP");
			}
			else if (key == e.VK_RIGHT) {
				queue.add("RIGHT");
			}
			else if (key == e.VK_DOWN) {
				queue.add("DOWN");
			}
			else if (key == e.VK_LEFT) {
				queue.add("LEFT");
			}
			
			
		}
		
		public void keyReleased(KeyEvent e) {
			
			int key = e.getKeyCode();
			
		}
		
	}
	
	//paints stuff to the screen
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
		
		g.setColor(Color.yellow);
		g.fillOval(pacmanX, pacmanY, 30, 30);
		
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
