package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class Screen extends JFrame {
	
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
	
	final static int SCREENX = MAZEX * 33;
	final static int SCREENY = MAZEY * 33;
	
	//main method
	public Screen() {
		
		//assigns value to maze field
		maze = new Maze(MAZEX, MAZEY);
		System.out.println(maze);
		
		//keyboard listener
		addKeyListener(new keyboard());
		
		//sets the properties of the screen
		setTitle("Pac-Man Level Editor");
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		
	}
	
	//class that handles key input
	public class keyboard extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
			
			if (key == e.VK_Q) {
				System.exit(0);
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
				g.setColor(Color.WHITE);
				g.drawString(maze.maze[r][c].getState() + "",
						25 + r * BLOCKWIDTH, 50 + c * BLOCKWIDTH);
				
			}
		}
		
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
