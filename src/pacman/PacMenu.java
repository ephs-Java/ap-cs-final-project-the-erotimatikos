package pacman;

//-273.15˚C

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PacMenu extends JFrame implements Runnable {
	
	String MENUMUSIC = "src/pacman/sound/MenuMusic.wav";
	
	//if the menu is the active window
	private boolean isActive = true;
	
	//maze preview field
	Maze preview;
	
	//leaderboard object
	Leaderboard leaderboard;
	
	//sets blockwidth to 20
	int BLOCKWIDTH = 20;
	
	//x and y offset of the strings from the point (0, 0) on the jframe	
	int xOffset = 50;
	int yOffset = 80;
	
	//level that is currently selected
	int selectedIndex = 0;
	
	//screen dimensions
	final int SCREENX = 1000;
	final int SCREENY = 500;
	
	final int XOFFSETSTART = xOffset;
	final int YOFFSETSTART = yOffset;
		
	//pixel distance between each group of text
	final int ROWSIZE = 50;
	
	//scrolling speed
	final int SCROLLSPEED = ROWSIZE / 2;
	
	//name of the current user
	String username;
	
	//double buffering
	Image dbImage;
	Graphics dbg;
	
	//levels field
	Levels levels;
	
	//music field
	Sound snd = new Sound();
	
	//thread
	public void run() {
		
		try {
			
			while (true) {
				
				Thread.sleep(34);
				if (!isActive) {
					Thread.sleep(1000);
					continue;
				}
				
				repaint();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//main constructor
	public PacMenu() throws IOException {
		
		username = JOptionPane.showInputDialog(null, "Enter your name here for the leaderboard: ");
		if (username == null || username.equals("")) {
			username = "Clint Eastwood";
		}
		formatName();
		
		updateLevels();
		
		//enables key and mouse input on the jframe
		addKeyListener(new keyboard());
		addMouseListener(new mouse());
		addWindowListener(new window());
		
		//creates leaderboard object
		leaderboard = new Leaderboard();
		
		leaderboard.trim();
		leaderboard.writeToFile();
		
		
		
		
		//loads the 2d array of the selected level
		try {
			load(levels.get(selectedIndex).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//window properties
		setSize(SCREENX, SCREENY);
		setVisible(true);
		setTitle("Pac man menu");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
//		snd.play(MENUMUSIC);
		
	}
	
	//updates the levels
	public void updateLevels() {
		
		//declares fields
		levels = new Levels();
		levels.addNumLevels();
		
		//adds the custom level manually
		Level level = new Level("src/pacman/levels/custom.txt");
		levels.add(level);
		
	}
	
	//loads a preview of the level
	//loads the given file into the current state
	public void load(String s) throws FileNotFoundException{
		
		File f = new File(s);
		
		if (!f.exists()) {
			
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
		preview = new Maze(totalCols, totalRows);
		
		Scanner update = new Scanner(new File(s));
		int row = 0;
		while (update.hasNext()) {
			String line = update.nextLine();
			for (int i = 0; i < totalCols; i++) {
				preview.maze[i][row].setState(Integer.parseInt(line.substring(i * 2, i * 2 + 1)));
			}
			row ++;
		}
		input.close();
		update.close();
		
	}
	
	//saves the custom.txt file contents into the next available level 
	//saves the current state into the custom.txt file
	//saves the current state into the custom.txt file
	public void save() throws FileNotFoundException{
		
		leaderboard.removeFromLevel(selectedIndex);
		leaderboard.writeToFile();
		
		String newFile = levels.DIR + "level" + levels.lastNumLevel() + ".txt";
		
		File f = new File(newFile);
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
			
			String content = "";
			
			for (int c = 0; c < preview.maze[0].length; c++) {
				for (int r = 0; r < preview.maze.length; r++) {
					content += preview.maze[r][c].getState() + " ";
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
	
	//removes - and ; from the name
	public void formatName() {
		
		String newName = "";
		for (int i = 0; i < username.length(); i++) {
			char lett = username.charAt(i);
			if (lett != ':' && lett != ';') {
				newName += lett;
			}
		}
		username = newName;
	}
	
	//key input
	public class keyboard extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
			
			switch(key) {
			case KeyEvent.VK_Q:
				System.exit(0);
				break;
			
			case KeyEvent.VK_UP:
				//scrolling boundaries
				if (yOffset < 70) {
					yOffset += SCROLLSPEED;
//					System.out.println(yOffset);
				}
				break;
				
			case KeyEvent.VK_DOWN:
				//scrolling boundaries
				if (levels.numLevels() > 8) {
					if (yOffset > 480 - levels.numLevels() * ROWSIZE) {
						yOffset -= SCROLLSPEED;		
//						System.out.println(yOffset);
					}
				}
				break;
				
			case KeyEvent.VK_ENTER:
				PacScreen scr = new PacScreen(levels.get(selectedIndex).toString(), username, selectedIndex);
				Thread t1 = new Thread(scr);
				t1.start();
				break;
			}
			
		}
		
	}
	
	//mouse input
	public class mouse extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			
			int mouseX = e.getX();
			int mouseY = e.getY();
			//46 58
//			System.out.println(mouseX + " " + mouseY);
			
			//checks if the user clicked one of the level buttons
			if (mouseX > xOffset - 4 && mouseX < 50 + xOffset) {
				
				//gets the level
				int level = (mouseY - (yOffset - 22)) / ROWSIZE;
				
				//bounds check
				if (level > levels.numLevels() - 1) {
					return;
				}
				else if (level < 0) {
					return;
				}
				
				if (level == selectedIndex) {
					PacScreen scr = new PacScreen(levels.get(level).toString(), username, selectedIndex);
					Thread t1 = new Thread(scr);
					t1.start();
				}
				
				//updates the preview image
				try {
					load(levels.get(level).toString());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//selects the clicked level
				selectedIndex = level;
				
			}
			
			//checks if the user clicked the edit button
			else if (mouseX > SCREENX - 200 && mouseX < SCREENX - 100
					&& mouseY > 50 && mouseY < 110) {
				
				Editor ed = new Editor(levels.get(selectedIndex).toString(), selectedIndex);
				
			}
			
			//checks if the user clicked the export button for custom.txt
			else if (mouseX > SCREENX - 200 && mouseX < SCREENX - 100
					&& mouseY > 150 && mouseY < 210 && levels.neatToString(selectedIndex).equals("Custom  ")) {
//				System.out.println("yeet");
				try {
					save();
					updateLevels();
					selectedIndex ++;
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		
	}
	
	//detects when window is activated
	public class window extends WindowAdapter {
		
		public void windowDeactivated(WindowEvent e) {
			
			isActive = false;
//			snd.stop();
			
		}
		
		public void windowActivated(WindowEvent e) {
			
			isActive = true;
			
			try {
//				System.out.println("window activated");
				((PacMenu) e.getWindow()).load(levels.get(selectedIndex).toString());
				leaderboard = new Leaderboard();
//				leaderboard.writeToFile();
//				snd.playCompleted = true;
//				snd.playCompleted = false;
				
//				snd = new Sound();
//				snd.play(MENUMUSIC);
				
			} catch(Exception exception) {
				exception.printStackTrace();
			}
			
		}
		
	}
	
	//double buffering
	public void paint(Graphics g) {
		
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	//prints stuff to the screen
	public void paintComponent(Graphics g) {
		
		//prints the list of levels
		for (int i = 0; i < levels.numLevels(); i++) {
			
			int charLength = 8;
			
			String s = levels.get(i).toString();
			s = levels.neatToString(i);
			
			g.setColor(Color.BLUE);
			
			//blue rectangle around text
			if (i == selectedIndex) {
				g.setColor(Color.GREEN);
			}
			
			g.fillRect(xOffset - 5, i * ROWSIZE + yOffset - (ROWSIZE  / 2), 
					s.length() * charLength, ROWSIZE - 2);
			
			g.setColor(Color.BLACK);
			//black rectangle around blue rectangle
			g.drawRect(xOffset - 5 - 1, i * ROWSIZE + yOffset - (ROWSIZE  / 2) - 1,
					s.length() * charLength - 1, ROWSIZE - 1);
			
			//white text
			g.setColor(Color.WHITE);
			g.drawString(s, xOffset, i * ROWSIZE + yOffset);
			
		}
		
		//prints edit button
		g.setColor(Color.blue);
		g.fillRect(SCREENX - 200, 50, 100, 60);
		
		g.setColor(Color.BLACK);
		//black rectangle around blue rectangle
		g.drawRect(SCREENX - 200, 50, 100, 60);
		
		//white text
		g.setColor(Color.WHITE);
		g.drawString("EDIT", SCREENX - 180, 90);
		
		if (levels.neatToString(selectedIndex).equals("Custom  ")) {
			
			//prints edit button
			g.setColor(Color.blue);
			g.fillRect(SCREENX - 200, 150, 100, 60);
			
			g.setColor(Color.BLACK);
			//black rectangle around blue rectangle
			g.drawRect(SCREENX - 200, 150, 100, 60);
			
			//white text
			g.setColor(Color.WHITE);
			g.drawString("Export", SCREENX - 180, 190);
			
		}
		
		//prints small 2d array
		g.setColor(Color.black);
		g.fillRect(150, 50, BLOCKWIDTH * preview.maze.length, BLOCKWIDTH * preview.maze[0].length );
		for (int r = 0; r < preview.maze.length; r++) {
			for (int c = 0; c < preview.maze[0].length; c++) {
				
				int xloc = 150 + r * BLOCKWIDTH;
				int yloc = 50 + c * BLOCKWIDTH;
				
				int state = preview.maze[r][c].getState();
				
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
					g.fillOval(xloc + BLOCKWIDTH * 3 /7,
					yloc + BLOCKWIDTH * 3 / 7, BLOCKWIDTH / 4, BLOCKWIDTH / 4);
				}
				else if (state == Tile.SPAWN) {
					g.setColor(Color.green);
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
				else if (state == Tile.GHOSTSPAWN) {
					g.setColor(Color.red);
					g.fillOval(xloc + BLOCKWIDTH / 4,
							yloc + BLOCKWIDTH / 4, BLOCKWIDTH / 2, BLOCKWIDTH / 2);
				}
				else if (state == Tile.POWERPELLET) {
					g.setColor(Color.white);
					g.fillOval(xloc + BLOCKWIDTH / 4,
					yloc + BLOCKWIDTH / 4, BLOCKWIDTH / 2, BLOCKWIDTH / 2);
				}
				
				g.setColor(Color.blue);
				g.drawRect(xloc, yloc, BLOCKWIDTH, BLOCKWIDTH);
				
			}
		}
		
		//prints the leaderboard
		ArrayList<Leader> leaders = leaderboard.getLeadersFromLevel(selectedIndex);
		
		int leaderboardX = 150;
		int leaderboardY = 370;
		
		for (int i = 0; i < leaders.size(); i++) {
			
			if (i >= 5) {
				break;
			}
			
			Leader l = leaders.get(i);
			
			g.drawString(l.getName() + ": " + l.getScore(), leaderboardX, leaderboardY + i * 20);
			
		}
//		System.out.println(leaders);
		
//		repaint();
		
	}
	
}
