package pacman;

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

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class PacScreen extends JFrame implements Runnable {
	
	//double buffering variables
	Image dbImage;
	Graphics dbg;
	
	//maze field
	Maze maze;
	
	//ghosts field
	Ghosts ghosts;
	
	//queue field
	Queue queue;
	Queue tpwait;
	Queue mouthQueue;
	
	//pacman field
	Pacman pac;
	
	//images
	ImageIcon ghost;
	ImageIcon pacmanclosed;
	ImageIcon pacmanright;
	ImageIcon pacmanleft;
	ImageIcon pacmanup;
	ImageIcon pacmandown;
	
	//queue size
	final int QUEUESIZE = 10;
	final int TPQUEUE = 20;
	final int MOUTHQUEUE = 3;
	
	//width of each block
	final int BLOCKWIDTH = 30;
	
	//mouse x and y coordinates
	int mouseX = 100;
	int mouseY = 100;
	
	//pac man speed 
	int pacmanSpeed = 6;
	
	//maze dimensions
	final static int MAZEX = 30;
	final static int MAZEY = 15;
	
	//screen dimensions
	int screenX = MAZEX * (BLOCKWIDTH + 3);
	int screenY = MAZEY * (BLOCKWIDTH + 3);
	
	//the thread delay
	int threadDelay = 50;
	
	//current level that the user is on
	int level = 7;
	
	//the thread
	public void run() {
		
		try {
			
			while (true) {
				checkMovement();
				Thread.sleep(threadDelay);
				ghosts.updateAll(maze.maze, pac.getPacXindex(), pac.getPacYindex(), BLOCKWIDTH);
				
				//updates queues
				queue.update();
				tpwait.update();
				mouthQueue.update();
				
				if (lose()) {
					Thread.sleep(2000);
					setup();
				}
				if (maze.isVictory()) {
					Thread.sleep(2000);
					level ++;
					setup();
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//main method
	public PacScreen() {
		
		//ghost image
		ghost = new ImageIcon("src/pacman/ghost.gif");
		
		//pacman images
		pacmanright = new ImageIcon("src/pacman/pacmanright.gif");
		pacmanleft = new ImageIcon("src/pacman/pacmanleft.gif");
		pacmanup = new ImageIcon("src/pacman/pacmanup.gif");
		pacmandown = new ImageIcon("src/pacman/pacmandown.gif");
		pacmanclosed = new ImageIcon("src/pacman/pacmanclosed.gif");
		
		pac = new Pacman();
		
		setup();
		screenX = maze.maze.length * (BLOCKWIDTH + 3);
		screenY = maze.maze[0].length * (BLOCKWIDTH + 5);
		
		
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
	
	//updates pacman x and y index
	public void updateVars() {
		
		//updates pac man x and y index vars
		for (int r = 0; r < maze.maze.length; r++) {
			for (int c = 0; c < maze.maze[0].length; c++) {
				
				int xdif = Math.abs((r * BLOCKWIDTH + 25) - pac.getPacmanX());
				int ydif = Math.abs((c * BLOCKWIDTH + 50) - pac.getPacmanY());
				
//				System.out.println(xdif + " " + ydif + "r= " + r + " c= " + c);
				
				
				if (xdif < BLOCKWIDTH && ydif < BLOCKWIDTH) {
					pac.setPacXindex(r);
					pac.setPacYindex(c);
					return;
				}
				
			}
		}
		pac.setPacXindex(0);
		pac.setPacYindex(0);
		
		
	}
	
	//checks if the ghosts have intersected with the pac man
	public boolean lose() {
		
		for (int i = 0; i < ghosts.length(); i++) {
			int xdif = Math.abs(ghosts.get(i).getX() - pac.getPacmanX()) + BLOCKWIDTH / 2 + 1;
			int ydif = Math.abs(ghosts.get(i).getY() - pac.getPacmanY()) + BLOCKWIDTH / 2 + 1;
			if (xdif < BLOCKWIDTH && ydif < BLOCKWIDTH) {
				return true;
			}
		}
		return false;
		
	}
	
	//checks which direction to go in
	public void checkMovement() {
		
		boolean xAligned = true;
		boolean yAligned = true;
		
		int dir = pac.getDirection();
		
		//checks for x axis alignment
		if ((pac.getPacmanX() - 25) % BLOCKWIDTH > 0) {
//			movingUp = false;
//			movingDown = false;
			if (dir == pac.UP || dir == pac.DOWN) {
				pac.setDirection(pac.STOP);
			}
			xAligned = false;
		}
		
		//checks for y axis alignment
		if ((pac.getPacmanY() - 50) % BLOCKWIDTH > 0) {
//			movingLeft = false;
//			movingRight = false;
			if (dir == pac.LEFT || dir == pac.RIGHT) {
				pac.setDirection(pac.STOP);
			}
			yAligned = false;
			
		}
		
		updateVars();
		
		
		
		//checks for wall collision
		if (dir == pac.LEFT && maze.maze[pac.getPacXindex()][pac.getPacYindex()].getState() == Tile.WALL) {
			pac.setDirection(pac.STOP);
			pac.setPacmanX(25 + pac.getPacXindex() * BLOCKWIDTH + BLOCKWIDTH);
		}
		if (dir == pac.RIGHT && maze.maze[pac.getPacXindex() + 1][pac.getPacYindex()].getState() == Tile.WALL) {
			pac.setDirection(pac.STOP);
			pac.setPacmanX(25 + pac.getPacXindex() * BLOCKWIDTH);
		}
		if (dir == pac.UP && maze.maze[pac.getPacXindex()][pac.getPacYindex()].getState() == Tile.WALL) {
			pac.setDirection(pac.STOP);
			pac.setPacmanY(50 + pac.getPacYindex() * BLOCKWIDTH + BLOCKWIDTH);
		}
		if (dir == pac.DOWN && maze.maze[pac.getPacXindex()][pac.getPacYindex() + 1].getState() == Tile.WALL) {
			pac.setDirection(pac.STOP);
			pac.setPacmanY(50 + pac.getPacYindex() * BLOCKWIDTH);
		}
		
		//checks for pills
		if (maze.maze[pac.getPacXindex()][pac.getPacYindex() + 1].getState() == Tile.PILL && !yAligned) {
			maze.maze[pac.getPacXindex()][pac.getPacYindex() + 1].setState(Tile.BLANK);
			mouthQueue.add("EAT");
		}
		if (maze.maze[pac.getPacXindex()][pac.getPacYindex()].getState() == Tile.PILL) {
			maze.maze[pac.getPacXindex()][pac.getPacYindex()].setState(Tile.BLANK);
			mouthQueue.add("EAT");
		}
		if (maze.maze[pac.getPacXindex() + 1][pac.getPacYindex()].getState() == Tile.PILL && !xAligned) {
			maze.maze[pac.getPacXindex() + 1][pac.getPacYindex()].setState(Tile.BLANK);
			mouthQueue.add("EAT");
		}
		
		//checks for teleporters
		checkTeleport(xAligned, yAligned);
		
		//checks if you can move up
		if (queue.indexOf("UP") != -1 && xAligned && pac.getPacYindex() > 0
				&& maze.maze[pac.getPacXindex()][pac.getPacYindex() - 1].getState() != Tile.WALL) {
//			halt();
			pac.setDirection(pac.UP);
			queue.remove(queue.indexOf("UP"));
			queue.remove(queue.indexOf("DOWN"));
		}
		//checks if you can move left
		else if (queue.indexOf("LEFT") != -1 && yAligned && pac.getPacXindex() > 0
				&& maze.maze[pac.getPacXindex() - 1][pac.getPacYindex()].getState() != Tile.WALL) {
//			halt();
			pac.setDirection(pac.LEFT);
			queue.remove(queue.indexOf("LEFT"));
			queue.remove(queue.indexOf("RIGHT"));
			
		}
		//checks if you can move down
		else if (queue.indexOf("DOWN") != -1 && xAligned
				&& maze.maze[pac.getPacXindex()][pac.getPacYindex() + 1].getState() != Tile.WALL) {
//			halt();
			pac.setDirection(pac.DOWN);
			queue.remove(queue.indexOf("DOWN"));
			queue.remove(queue.indexOf("UP"));
		}
		//checks if you can move right
		else if (queue.indexOf("RIGHT") != -1 && yAligned
				&& maze.maze[pac.getPacXindex() + 1][pac.getPacYindex()].getState() != Tile.WALL) {
//			halt();
			pac.setDirection(pac.RIGHT);
			queue.remove(queue.indexOf("RIGHT"));
			queue.remove(queue.indexOf("LEFT"));
		}
		
		//updates the index
		pac.update(pacmanSpeed);
//		if (dir == pac.RIGHT && yAligned) {
//			pac.setPacmanX(pac.getPacmanX() + pacmanSpeed);
//		}
//		if (dir == pac.LEFT && yAligned) {
//			pac.setPacmanX(pac.getPacmanX() - pacmanSpeed);
//		}
//		if (dir == pac.UP && xAligned) {
//			pac.setPacmanY(pac.getPacmanY() - pacmanSpeed);
//		}
//		if (dir == pac.DOWN && xAligned) {
//			pac.setPacmanY(pac.getPacmanY() + pacmanSpeed);
//		}
		
	}
	
	//checks for teleportation
	public void checkTeleport(boolean xAligned, boolean yAligned) {
		
		if (maze.maze[pac.getPacXindex()][pac.getPacYindex() + 1].getState() == Tile.TELEPORTER && !yAligned
				&& tpwait.indexOf("teleport") == -1) {
			teleportFrom(pac.getPacXindex(), pac.getPacYindex() + 1);
		}
		if (maze.maze[pac.getPacXindex()][pac.getPacYindex()].getState() == Tile.TELEPORTER
				&& tpwait.indexOf("teleport") == -1) {
			teleportFrom(pac.getPacXindex(), pac.getPacYindex());
		}
		if (maze.maze[pac.getPacXindex() + 1][pac.getPacYindex()].getState() == Tile.TELEPORTER && !xAligned
				&& tpwait.indexOf("teleport") == -1) {
			teleportFrom(pac.getPacXindex() + 1, pac.getPacYindex());
		}
		//teleporter2
		if (maze.maze[pac.getPacXindex()][pac.getPacYindex() + 1].getState() == Tile.TELEPORTER2 && !yAligned
				&& tpwait.indexOf("teleport2") == -1) {
			teleportFrom(pac.getPacXindex(), pac.getPacYindex() + 1);
		}
		if (maze.maze[pac.getPacXindex()][pac.getPacYindex()].getState() == Tile.TELEPORTER2
				&& tpwait.indexOf("teleport2") == -1) {
			teleportFrom(pac.getPacXindex(), pac.getPacYindex());
		}
		if (maze.maze[pac.getPacXindex() + 1][pac.getPacYindex()].getState() == Tile.TELEPORTER2 && !xAligned
				&& tpwait.indexOf("teleport2") == -1) {
			teleportFrom(pac.getPacXindex() + 1, pac.getPacYindex());
		}
		
	}

	//teleport using teleporter1
	public void teleportFrom(int pacXindex, int pacYindex) {
//		System.out.println(pacXindex + " " + pacYindex);
		boolean is1 = maze.maze[pacXindex][pacYindex].getState() == Tile.TELEPORTER;
//		System.out.println(is1);
		
		int homeX = 0;
		int homeY = 0;
		
		for (int r = 0; r < maze.maze.length; r++) {
			for (int c = 0; c < maze.maze[0].length; c++) {
				
				if (maze.maze[r][c].getState() == Tile.SPAWN) {
					homeX = r;
					homeY = c;
				}
				
				if (maze.maze[r][c].getState() == Tile.TELEPORTER && (r != pacXindex || c != pacYindex) && is1
						&& tpwait.indexOf("teleport") == -1) {
					
//					System.out.println(r + " " + c);
					pac.setPacmanX(r * BLOCKWIDTH + 25);
					pac.setPacmanY(c * BLOCKWIDTH + 50);
					tpwait.add("teleport");
					return;
				}
				else if (maze.maze[r][c].getState() == Tile.TELEPORTER2 && (r != pacXindex || c != pacYindex) && !is1
						&& tpwait.indexOf("teleport2") == -1) {
					pac.setPacmanX(r * BLOCKWIDTH + 25);
					pac.setPacmanY(c * BLOCKWIDTH + 50);
					tpwait.add("teleport2");
					return;
				}
				
			}
		}
		pac.setPacmanX(homeX * BLOCKWIDTH + 25);
		pac.setPacmanX(homeY * BLOCKWIDTH + 50);
		
	}
	
	//stops all movement
	public void halt() {
		
		pac.setDirection(pac.STOP);
		
	}
	
	//sets up the pac man location for starting up the game and sets up ghost positions
	public void setup() {
		halt();
		
		//creates queues
		tpwait = new Queue(TPQUEUE);
		queue = new Queue(QUEUESIZE);
		mouthQueue = new Queue(MOUTHQUEUE);
		
		//creates ghosts
		ghosts = new Ghosts();
		
		//creates maze
		maze = new Maze(MAZEX, MAZEY);
		//loads maze
		try {
			load("src/pacman/level" + level + ".txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ghosts.removeAll();
		
		for (int r = 0; r < maze.maze.length; r++) {
			for (int c = 0; c < maze.maze[0].length; c++) {
				
				if (maze.maze[r][c].getState() == Tile.SPAWN) {
					//sets the pac man index and location on screen
					pac.setPacmanX(r * BLOCKWIDTH + 25);
					pac.setPacmanY(c * BLOCKWIDTH + 50);
					pac.setPacXindex(r);
					pac.setPacYindex(c);
				}
				if (maze.maze[r][c].getState() == Tile.GHOSTSPAWN) {
					Ghost g = new Ghost(r * BLOCKWIDTH + 25, c * BLOCKWIDTH + 50);
					ghosts.add(g);
				}
				
			}
		}	
		
//		System.out.println(ghosts);
//		pac.getPacmanX() = 0 * BLOCKWIDTH + 25;
//		pac.getPacmanY() = 0 * BLOCKWIDTH + 50;
//		
//		pac.getPacXindex() = 0;
//		pac.getPacYindex() = 0;
	}
	
	//loads the given file into the current state
	public void load(String s) throws FileNotFoundException{
		
		File f = new File(s);
		
		if (!f.exists()) {
//			System.out.println("file not found");
			level = 1;
			setup();
//			System.out.println("resetting level");
//			maze = new Maze(MAZEX, MAZEY);
//			save(s);
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
		input.close();
		update.close();
		
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
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
		}
	
	//class that handles key input
	public class keyboard extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
//			System.out.println(queue);
			
			switch (key) {
			
			case KeyEvent.VK_Q:
				System.exit(0);
				break;
			case KeyEvent.VK_R:
				setup();
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				queue.add("UP");
				queue.remove("DOWN");
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				queue.add("RIGHT");
				queue.remove("LEFT");
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				queue.add("DOWN");
				queue.remove("UP");
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				queue.add("LEFT");
				queue.remove("RIGHT");
				break;
			case KeyEvent.VK_PERIOD:
				level ++;
				setup();
				break;
			case KeyEvent.VK_COMMA:
				if (level > 1) {
					level --;
					setup();
				}
				break;
			
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
//					g.setColor(Color.BLUE);
//					g.fillOval(xloc + BLOCKWIDTH / 4,
//							yloc + BLOCKWIDTH / 4, BLOCKWIDTH / 2, BLOCKWIDTH / 2);
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
		
		//prints the ghosts
		for (int i = 0; i < ghosts.length(); i++) {
			g.setColor(Color.MAGENTA);
			g.drawImage(ghost.getImage(), ghosts.get(i).getX() + 2, ghosts.get(i).getY() + 2
					, BLOCKWIDTH, BLOCKWIDTH, this);
//			g.fillRect(ghosts.get(i).getX() + 2, ghosts.get(i).getY() + 2, BLOCKWIDTH - 4, BLOCKWIDTH - 4);
			
		}
		
		int dir = pac.getDirection();
		int ldir = pac.getLastDirection();
		if (dir == pac.DOWN || (dir == pac.STOP && ldir == pac.DOWN)) {
			g.drawImage(pacmandown.getImage(), pac.getPacmanX()
					, pac.getPacmanY(), BLOCKWIDTH, BLOCKWIDTH, this);
		}
		else if (dir == pac.RIGHT || (dir == pac.STOP && ldir == pac.RIGHT)) {
			g.drawImage(pacmanright.getImage(), pac.getPacmanX()
					, pac.getPacmanY(), BLOCKWIDTH, BLOCKWIDTH, this);
		}
		else if (dir == pac.UP || (dir == pac.STOP && ldir == pac.UP)) {
			g.drawImage(pacmanup.getImage(), pac.getPacmanX()
					, pac.getPacmanY(), BLOCKWIDTH, BLOCKWIDTH, this);
		}
		else if (dir == pac.LEFT || (dir == pac.STOP && ldir == pac.LEFT)) {
			g.drawImage(pacmanleft.getImage(), pac.getPacmanX()
					, pac.getPacmanY(), BLOCKWIDTH, BLOCKWIDTH, this);
		}
		else {
			g.drawImage(pacmanclosed.getImage(), pac.getPacmanX()
					, pac.getPacmanY(), BLOCKWIDTH, BLOCKWIDTH, this);
		}
		if (mouthQueue.indexOf("EAT") != -1) {
			g.drawImage(pacmanclosed.getImage(), pac.getPacmanX()
					, pac.getPacmanY(), BLOCKWIDTH, BLOCKWIDTH, this);
		}
		
		g.setColor(Color.white);
		g.drawString("Level: " + level, 100, screenY - 10);
		
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
