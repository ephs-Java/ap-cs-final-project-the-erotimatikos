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
	
	//easy toggling of leaderboard
	private boolean SAVESCORES = true;
	
	//double buffering variables
	Image dbImage;
	Graphics dbg;
	
	//maze field
	Maze maze;
	
	//ghosts field
	Ghosts ghosts;
	
	//leaderboard for submitting scores
	Leaderboard leaderboard;
	
	//queue fields
	Queue queue;
	Queue tpwait;
	Queue mouthQueue;
	Queue powerQueue;
	
	//pacman field
	Pacman pac;
	
	//path to the images
	final String IMAGESOURCE = "src/pacman/images/";
	
	//images
	ImageIcon ghost;
	ImageIcon ghostrunning;
	ImageIcon pacmanclosed;
	ImageIcon pacmanright;
	ImageIcon pacmanleft;
	ImageIcon pacmanup;
	ImageIcon pacmandown;
	
	//score of the player
	int score = 0;
	
	//level and name of the player
	final String PLAYERNAME;
	final int LEVEL;
	
	//if exit
	boolean exit = false;
	
	//string path of the file to play
	final String FILEPATH;
	
	//queue size
	final int QUEUESIZE = 8;
	final int TPQUEUE = 20;
	final int MOUTHQUEUE = 3;
	final int POWERQUEUE = 100;
	
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
	
	//holding a key direction, uses Pacman final ints for directions
	int keyDirection;
	
	//points earned for eating a ghost, doubles with each ghost killed
	final int GHOSTPOINTS = 100;
	int currentGhostPoints = GHOSTPOINTS;
	
	//the thread
	public void run() {
		
		try {
			
			while (true) {
				
				//exits the game
				if (exit) {
					dispose();
					break;
				}
				
				checkMovement();
				Thread.sleep(threadDelay);
				
				//makes the ghosts go the opposite direction
				if (powerQueue.indexOf("RUN") >= 0) {
					ghosts.runAway = true;
					ghosts.ghostSpeed = 3;
//					ghosts.alignAll(BLOCKWIDTH);
				}
				else {
//					ghosts.alignAll(BLOCKWIDTH);
					ghosts.runAway = false;
					ghosts.ghostSpeed = 5;
					ghosts.activateAll();
					currentGhostPoints = GHOSTPOINTS;
				}
				
				ghosts.updateAll(maze.maze, pac.getPacXindex(), pac.getPacYindex(), BLOCKWIDTH);
				
//				System.out.println("yeet");
				
				//updates queues
				queue.update();
				tpwait.update();
				mouthQueue.update();
				powerQueue.update();
				
//				System.out.println("yeet2");
				
				if (lose()) {
					Thread.sleep(2000);
					setup();
//					if (score < 0) {score = 0;}
					if (SAVESCORES) {
						Leader l = new Leader(PLAYERNAME, score, LEVEL);
						leaderboard.add(l);
						leaderboard.writeToFile();
					}
					keyDirection = Pacman.STOP;
					score = 0;
				}
				if (maze.isVictory() && !exit) {
					Thread.sleep(2000);
					if (SAVESCORES) {
						Leader l = new Leader(PLAYERNAME, score, LEVEL);
						leaderboard.add(l);
						leaderboard.writeToFile();
					}
					dispose();
					exit = true;
//					level ++;
//					setup();
					
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//main method
	public PacScreen(String p, String n, int l) {
		
		PLAYERNAME = n;
		LEVEL = l;
		FILEPATH = p;
		
		//leaderboard for submitting scores
		try {
			leaderboard = new Leaderboard();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//ghost image
		ghost = new ImageIcon(IMAGESOURCE + "ghost.gif");
		ghostrunning = new ImageIcon(IMAGESOURCE + "ghostrunning.gif");
		
		//pacman images
		pacmanright = new ImageIcon(IMAGESOURCE + "pacmanright.gif");
		pacmanleft = new ImageIcon(IMAGESOURCE + "pacmanleft.gif");
		pacmanup = new ImageIcon(IMAGESOURCE + "pacmanup.gif");
		pacmandown = new ImageIcon(IMAGESOURCE + "pacmandown.gif");
		pacmanclosed = new ImageIcon(IMAGESOURCE + "pacmanclosed.gif");
		
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBackground(Color.black);
		
	}
	
	//updates pacman x and y index
	public void updateVars() {
		
		if (exit) {
			dispose();
		}
		
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
			
			if (xdif < BLOCKWIDTH && ydif < BLOCKWIDTH && ghosts.get(i).isActive) {
				if (ghosts.runAway) {
					ghosts.returnToSpawn(maze.maze, i, BLOCKWIDTH);
					ghosts.get(i).isActive = false;
					score += currentGhostPoints;
//					System.out.println(currentGhostPoints);
					currentGhostPoints *= 2;
					
//					ghosts.alignAll(BLOCKWIDTH);
				}
				else {
					return true;
				}
			}
		}
		return false;
		
	}
	
	//moves the pac man and activates teleporters, pellets, and power pellets
	public void checkMovement() {
		
		//updates the queue based on the pac man keydirection
		checkMovementDirection();
		
		//updates the pac man x and y index
		updateVars();
		
		//checks for wall collision
		checkWallCollision();
		
		//checks for pellets and power pellets
		pelletCheck();
		
		//checks for teleporters
		checkTeleport();
		
		//moves the pac man based on his direction
		move();
		
		//updates the location of the pac man object
		//based on its speed
		pac.update(pacmanSpeed);
		
	}

	//updates the queue based on the movement direction of the pac man
	public void checkMovementDirection() {
		
		//enables holding a direction constantly queueing in that direction
		if (keyDirection != Pacman.STOP) {
			if (keyDirection == Pacman.UP) {
				queue.add("UP");
				queue.remove("DOWN");
//						queue.remove("LEFT");
//						queue.remove("RIGHT");
			}
			 if (keyDirection == Pacman.LEFT) {
				queue.add("LEFT");
				queue.remove("RIGHT");
//						queue.remove("UP");
//						queue.remove("DOWN,");
			}
			if (keyDirection == Pacman.DOWN) {
				queue.add("DOWN");
				queue.remove("UP");
//						queue.remove("LEFT");
//						queue.remove("RIGHT");
			}
			if (keyDirection == Pacman.RIGHT) {
				queue.add("RIGHT");
				queue.remove("LEFT");
//						queue.remove("UP");
//						queue.remove("DOWN");
			}
		}
		
	}
	
	//checks for wall collision
	public void checkWallCollision() {
		
		//gets the pac man's direction
		int dir = pac.getDirection();
		
		if (dir == pac.LEFT && isXAligned() &&
				maze.maze[pac.getPacXindex() - 1][pac.getPacYindex()].getState() == Tile.WALL) {
			
			pac.setDirection(pac.STOP);
			pac.setPacmanX(25 + pac.getPacXindex() * BLOCKWIDTH);
//					queue.reset();
			queue.remove("LEFT");
		}
		if (dir == pac.RIGHT && 
				maze.maze[pac.getPacXindex() + 1][pac.getPacYindex()].getState() == Tile.WALL) {
			
			pac.setDirection(pac.STOP);
			pac.setPacmanX(25 + pac.getPacXindex() * BLOCKWIDTH);
//					queue.reset();
			queue.remove("RIGHT");
		}
		if (dir == pac.UP && isYAligned()
				&& maze.maze[pac.getPacXindex()][pac.getPacYindex() - 1].getState() == Tile.WALL) {
			
			pac.setDirection(pac.STOP);
			pac.setPacmanY(50 + pac.getPacYindex() * BLOCKWIDTH);
//					queue.reset();
			queue.remove("UP");
		}
		if (dir == pac.DOWN && 
				maze.maze[pac.getPacXindex()][pac.getPacYindex() + 1].getState() == Tile.WALL) {
			
			pac.setDirection(pac.STOP);
			pac.setPacmanY(50 + pac.getPacYindex() * BLOCKWIDTH);
//					queue.reset();
			queue.remove("DOWN");
		}
	
	}
	
	//moves the pac man based on his direction
	public void move() {
		
		//checks if you can move up
		if (queue.indexOf("UP") != -1 && isXAligned() && pac.getPacYindex() > 0
				&& maze.maze[pac.getPacXindex()][pac.getPacYindex() - 1].getState() != Tile.WALL) {
//					halt();
			pac.setDirection(pac.UP);
			queue.remove(queue.indexOf("UP"));
			queue.remove(queue.indexOf("DOWN"));
		}
		//checks if you can move left
		else if (queue.indexOf("LEFT") != -1 && isYAligned() && pac.getPacXindex() > 0
				&& maze.maze[pac.getPacXindex() - 1][pac.getPacYindex()].getState() != Tile.WALL) {
//					halt();
			pac.setDirection(pac.LEFT);
			queue.remove(queue.indexOf("LEFT"));
			queue.remove(queue.indexOf("RIGHT"));
			
		}
		//checks if you can move down
		else if (queue.indexOf("DOWN") != -1 && isXAligned()
				&& maze.maze[pac.getPacXindex()][pac.getPacYindex() + 1].getState() != Tile.WALL) {
//					halt();
			pac.setDirection(pac.DOWN);
			queue.remove(queue.indexOf("DOWN"));
			queue.remove(queue.indexOf("UP"));
		}
		//checks if you can move right
		else if (queue.indexOf("RIGHT") != -1 && isYAligned()
				&& maze.maze[pac.getPacXindex() + 1][pac.getPacYindex()].getState() != Tile.WALL) {
//					halt();
			pac.setDirection(pac.RIGHT);
			queue.remove(queue.indexOf("RIGHT"));
			queue.remove(queue.indexOf("LEFT"));
		}		
	
	}
	
	//checks for intersecting with pellets and power pellets
	public void pelletCheck() {
		
		//checks for pills
		if (maze.maze[pac.getPacXindex()][pac.getPacYindex() + 1].getState() == Tile.PILL 
				&& !isYAligned()) {
			
			maze.maze[pac.getPacXindex()][pac.getPacYindex() + 1].setState(Tile.BLANK);
			mouthQueue.add("EAT");
			score += 100;
		}
		if (maze.maze[pac.getPacXindex()][pac.getPacYindex()].getState() == Tile.PILL) {
			maze.maze[pac.getPacXindex()][pac.getPacYindex()].setState(Tile.BLANK);
			mouthQueue.add("EAT");
			score += 100;
		}
		if (maze.maze[pac.getPacXindex() + 1][pac.getPacYindex()].getState() == Tile.PILL 
				&& !isXAligned()) {
			
			maze.maze[pac.getPacXindex() + 1][pac.getPacYindex()].setState(Tile.BLANK);
			mouthQueue.add("EAT");
			score += 100;
		}
		
		//checks for power pellets
		if (maze.maze[pac.getPacXindex()][pac.getPacYindex() + 1].getState() == Tile.POWERPELLET 
				&& !isYAligned()) {
			
			maze.maze[pac.getPacXindex()][pac.getPacYindex() + 1].setState(Tile.BLANK);
			mouthQueue.add("EAT");
			powerQueue.add("RUN");
//					ghosts.alignAll(BLOCKWIDTH);
			score += 200;
		}
		if (maze.maze[pac.getPacXindex()][pac.getPacYindex()].getState() == Tile.POWERPELLET) {
			maze.maze[pac.getPacXindex()][pac.getPacYindex()].setState(Tile.BLANK);
			mouthQueue.add("EAT");
			powerQueue.add("RUN");
//					ghosts.alignAll(BLOCKWIDTH);
			score += 200;
		}
		if (maze.maze[pac.getPacXindex() + 1][pac.getPacYindex()].getState() == Tile.POWERPELLET 
				&& !isXAligned()) {
			
			maze.maze[pac.getPacXindex() + 1][pac.getPacYindex()].setState(Tile.BLANK);
			mouthQueue.add("EAT");
			powerQueue.add("RUN");
//					ghosts.alignAll(BLOCKWIDTH);
			score += 200;
		}
		
	}
	
	//returns if the pac man is aligned along the x axis of the grid
	public boolean isXAligned() {
		int dir = pac.getDirection();
		
		//checks for x axis alignment
		if ((pac.getPacmanX() - 25) % BLOCKWIDTH > 0) {
//			movingUp = false;
//			movingDown = false;
			if (dir == pac.UP || dir == pac.DOWN) {
				pac.setDirection(pac.STOP);
			}
			return false;
		}	
		return true;
	}
	
	//returns if the pac man is aligned along the y axis of the grid
	public boolean isYAligned() {
		
		int dir = pac.getDirection();
		
		//checks for y axis alignment
		if ((pac.getPacmanY() - 50) % BLOCKWIDTH > 0) {
//					movingLeft = false;
//					movingRight = false;
			if (dir == pac.LEFT || dir == pac.RIGHT) {
				pac.setDirection(pac.STOP);
			}
			return false;
		}
		return true;
	}
	
	//checks for teleportation
	public void checkTeleport() {
		
		boolean xAligned = isXAligned();
		boolean yAligned = isYAligned();
		
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
		//subtracts 50 from score to prevent overuse of teleportation
		score -= 50;
		
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
					updateVars();
					checkWallCollision();
					tpwait.add("teleport");
					return;
				}
				else if (maze.maze[r][c].getState() == Tile.TELEPORTER2 && (r != pacXindex || c != pacYindex) && !is1
						&& tpwait.indexOf("teleport2") == -1) {
					pac.setPacmanX(r * BLOCKWIDTH + 25);
					pac.setPacmanY(c * BLOCKWIDTH + 50);
					updateVars();
					checkWallCollision();
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
		powerQueue = new Queue (POWERQUEUE);
		
		//creates ghosts
		ghosts = new Ghosts();
		
		//creates maze
		maze = new Maze(MAZEX, MAZEY);
		//loads maze
		try {
			load(FILEPATH);
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
//			level = 1;
//			setup();
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
		
		public void keyReleased(KeyEvent e) {
			
			int key = e.getKeyCode();
			
			switch (key) {
			
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
			case KeyEvent.VK_A:
			case KeyEvent.VK_S:
			case KeyEvent.VK_D:
				keyDirection = Pacman.STOP;
				break;
				
			}
			
		}
		
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
//			System.out.println(queue);
			
			switch (key) {
			
			case KeyEvent.VK_Q:
//				System.exit(0);
				exit = true;
				break;
//			case KeyEvent.VK_R:
//				setup();
//				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				keyDirection = Pacman.UP;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				keyDirection = Pacman.RIGHT;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				keyDirection = Pacman.DOWN;
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				keyDirection = Pacman.LEFT;
				break;
			case KeyEvent.VK_SPACE:
				//speedup mode
				if (threadDelay == 50) {
//					threadDelay = 1;
				}
				else {
					threadDelay = 50;
				}
				break;
			}
			
		}
		
		
	}
	
	//paints stuff to the screen
	public void paintComponent(Graphics g) {

		
		for (int r = 0; r < maze.maze.length; r++) {
			for (int c = 0; c < maze.maze[0].length; c++) {
				
				int xloc = 25 + r * BLOCKWIDTH;
				int yloc = 50 + c * BLOCKWIDTH;
				
				int state = maze.maze[r][c].getState();
				
				switch (state) {
				
				case Tile.WALL:
					g.setColor(Color.blue);
					g.fillRect(xloc, yloc, BLOCKWIDTH, BLOCKWIDTH);
					break;
					
				case Tile.BLANK:
					g.setColor(Color.black);
					g.fillRect(xloc, yloc, BLOCKWIDTH, BLOCKWIDTH);
					break;
				
				case Tile.PILL:
					g.setColor(Color.white);
					g.fillOval(xloc + BLOCKWIDTH * 3 /7,
					yloc + BLOCKWIDTH * 3 / 7, BLOCKWIDTH / 4, BLOCKWIDTH / 4);	
					break;
					
				case Tile.POWERPELLET:
					g.setColor(Color.white);
					g.fillOval(xloc + BLOCKWIDTH / 4,
					yloc + BLOCKWIDTH / 4, BLOCKWIDTH / 2, BLOCKWIDTH / 2);
					break;
					
				case Tile.TELEPORTER:
					g.setColor(Color.yellow);
					g.fillOval(xloc + BLOCKWIDTH / 4,
							yloc + BLOCKWIDTH / 4, BLOCKWIDTH / 2, BLOCKWIDTH / 2);
					break;
					
				case Tile.TELEPORTER2:
					g.setColor(Color.MAGENTA);
					g.fillOval(xloc + BLOCKWIDTH / 4,
							yloc + BLOCKWIDTH / 4, BLOCKWIDTH / 2, BLOCKWIDTH / 2);
					break;
					
				}
				g.setColor(Color.blue);
				g.drawRect(xloc, yloc, BLOCKWIDTH, BLOCKWIDTH);
				
			}
		}
		
		//prints the ghosts
		for (int i = 0; i < ghosts.length(); i++) {
			g.setColor(Color.MAGENTA);
			//flashes the ghosts
//			System.out.println(powerQueue.get(powerQueue.indexOf("RUN")));
			if (powerQueue.indexOf("RUN") != -1 && ghosts.runAway && 
					powerQueue.get(powerQueue.indexOf("RUN")) > POWERQUEUE - POWERQUEUE / 4) {
				if (powerQueue.get(powerQueue.indexOf("RUN")) % 2 == 0) {
					g.drawImage(ghostrunning.getImage(), ghosts.get(i).getX() + 2, ghosts.get(i).getY() + 2
							, BLOCKWIDTH, BLOCKWIDTH, this);
				}
				else {
					g.drawImage(ghost.getImage(), ghosts.get(i).getX() + 2, ghosts.get(i).getY() + 2
							, BLOCKWIDTH, BLOCKWIDTH, this);
				}
				continue;
			}
			if (!ghosts.get(i).isActive) {
				continue;
			}
			if (ghosts.runAway) {
				g.drawImage(ghostrunning.getImage(), ghosts.get(i).getX() + 2, ghosts.get(i).getY() + 2
						, BLOCKWIDTH, BLOCKWIDTH, this);
			}
			else {
				g.drawImage(ghost.getImage(), ghosts.get(i).getX() + 2, ghosts.get(i).getY() + 2
						, BLOCKWIDTH, BLOCKWIDTH, this);
			}
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
		g.drawString("Score: " + score, 100, screenY - 10);
		
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
