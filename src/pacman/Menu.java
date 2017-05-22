package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;

public class Menu extends JFrame {

	//maze preview field
	Maze preview;
	
	//sets blockwidth to 15
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
	
	//scrolling speed
	final int SCROLLSPEED = 10;
	
	//pixel distance between each group of text
	final int ROWSIZE = 50;
	
	//double buffering
	Image dbImage;
	Graphics dbg;
	
	//levels field
	Levels levels;
	
	public Menu() {
		
		
		//enables key and mouse input on the jframe
		addKeyListener(new keyboard());
		addMouseListener(new mouse());
		
		//declares fields
		levels = new Levels();
		levels.addNumLevels();
		
		//loads the 2d array of the selected level
		try {
			load(levels.get(selectedIndex).toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//window properties
		setSize(SCREENX, SCREENY);
		setVisible(true);
		setTitle("Pac man menu");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
	
	//key input
	public class keyboard extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
			
			switch(key) {
			case KeyEvent.VK_Q:
				System.exit(0);
				break;
			
			case KeyEvent.VK_UP:
				yOffset += SCROLLSPEED;
				break;
				
			case KeyEvent.VK_DOWN:
				yOffset -= SCROLLSPEED;
				
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
			
			if (mouseX > xOffset - 4 && mouseX < 150 + xOffset) {
				
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
					PacScreen scr = new PacScreen(levels.get(level).toString());
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
			
		}
		
	}
	
	//double buffering
	public void paint(Graphics g) {
		
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	public void paintComponent(Graphics g) {
		
		for (int i = 0; i < levels.numLevels(); i++) {
			
			int charLength = 8;
			
			String s = levels.get(i).toString();
			g.setColor(Color.BLUE);
			
			//blue rectangle around text
			if (i == selectedIndex) {
				g.setColor(Color.GREEN);
			}
			
			g.fillRect(xOffset - 5, i * ROWSIZE + yOffset - (ROWSIZE  / 2), s.length() * charLength, ROWSIZE - 2);
			
			g.setColor(Color.BLACK);
			//black rectangle around blue rectangle
			g.drawRect(xOffset - 5 - 1, i * ROWSIZE + yOffset - (ROWSIZE  / 2) - 1, s.length() * charLength - 1, ROWSIZE - 1);
			
			//white text
			g.setColor(Color.WHITE);
			g.drawString(s, xOffset, i * ROWSIZE + yOffset);
			
		}
		
		//prints small 2d array
		g.setColor(Color.black);
		g.fillRect(300, 50, BLOCKWIDTH * preview.maze.length, BLOCKWIDTH * preview.maze[0].length );
		for (int r = 0; r < preview.maze.length; r++) {
			for (int c = 0; c < preview.maze[0].length; c++) {
				
				int xloc = 300 + r * BLOCKWIDTH;
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
					g.fillOval(xloc + BLOCKWIDTH / 4,
					yloc + BLOCKWIDTH / 4, BLOCKWIDTH / 2, BLOCKWIDTH / 2);
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
				
				g.setColor(Color.blue);
				g.drawRect(xloc, yloc, BLOCKWIDTH, BLOCKWIDTH);
				
			}
		}
		
		repaint();
		
	}
	
}
