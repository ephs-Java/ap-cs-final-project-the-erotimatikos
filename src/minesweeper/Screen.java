//Jframe that displays 
package minesweeper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Screen extends JFrame implements Runnable {

	//the mines
	private Mines field;
	Image bomb;
	Image flag;
	Image explode;
	//constant for the block width
	private final int BLOCKWIDTH = 15;
	
	//screen dimensions variables
	private final int SCREENX = 800, SCREENY = 500;
	
	int minesX = 50;
	int minesY = 25;
	
	//double buffering
	private Image dbImage;
	private Graphics dbg;
	
	
	//mouse x and y variables
	private int mouseX = 0, mouseY = 0;
	
	//the default constructor, creates JFrame
	public Screen() {
		ImageIcon i= new ImageIcon("bomb_PNG26.png");
		bomb= i.getImage();
		ImageIcon j= new ImageIcon("boom.png");
		explode= j.getImage();
		//creates mines object
		field = new Mines(minesX, minesY);
		
		//mouse listener
		addMouseListener(new Mouse());
		
		//key listener
		addKeyListener(new Keyboard());
		
		setTitle("Minesweeper");
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private static int count = 0;
	//the thread
	public void run() {
		
		try {
			
			while (true) {
				
				
				
			}
			
		}
		catch(Exception e) {
			System.out.println("Error in thread: " + e.getMessage());
		}
		
	}
	
	//mouse input
	public class Mouse extends MouseAdapter {
		
		public void mousePressed(MouseEvent e)  {
		
			mouseX = e.getX();
			mouseY = e.getY();
			
			//handles flag placement
			if (e.isControlDown()) {
				
			}
			
//			System.out.println("Mouse X: " + mouseX + " Mouse Y: " + mouseY);
			
			int blockX = (mouseX - 18) / BLOCKWIDTH;
			int blockY = (mouseY - 34) / BLOCKWIDTH;
			
			//prevents array index out of bounds
			if (blockX > field.tiles.length) {
				blockX = field.tiles.length - 1;
			}
			if (blockY > field.tiles[0].length) {
				blockY = field.tiles[0].length - 1;
			}
			if (blockX < 0) {
				blockX = 0;
			}
			if (blockY < 0) {
				blockY = 0;
			}
			

			count++;
			if(count == 1){
				field.clearArea(blockX,blockY);
				field.updateAllNums();
			}
			if (count > 1000) {
				count = 1000;
			}
//<<<<<<< HEAD
//=======
			field.tiles[blockX][blockY].show();
			field.updateFromPoint(blockX, blockY);
//			field.select(blockX, blockY);
//			System.out.println("Block X: " + blockX + " Block Y: " + blockY);
//			System.out.println("Adjacent blocks: " + field.getBombs(blockX, blockY));
//>>>>>>> branch 'master' of https://github.com/Jythonscript/Minesweeper.git

//			System.out.println("Block X: " + blockX + " Block Y: " + blockY);
//			System.out.println("Adjacent bombs: " + field.getBombs(blockX, blockY));

			
		}
		
	}
	
	//key input
	public class Keyboard extends KeyAdapter {
		
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
	
	//printing stuff to the screen
	public void paintComponent(Graphics g) {
		
		for (int r = 0; r < this.field.tiles.length; r++) {
			
			for (int c = 0; c < this.field.tiles[0].length; c++) {
				
				Tile item = this.field.tiles[r][c];
				g.setColor(Color.black);
				if (item.getIsMine() && !item.getIsHidden()) {
					g.setColor(Color.red);
					g.fillRect(15 + r * BLOCKWIDTH, 30 + c * BLOCKWIDTH, BLOCKWIDTH, BLOCKWIDTH);
					g.drawImage(bomb,15 + r * BLOCKWIDTH, 30+c * BLOCKWIDTH, this);
					g.setColor(Color.black);
					g.drawRect(15 + r * BLOCKWIDTH, 30 + c * BLOCKWIDTH, BLOCKWIDTH, BLOCKWIDTH);
					for (int row= 0; row<this.field.tiles.length; row++){
						for(int col=0; col<this.field.tiles[row].length; col++){
							if (this.field.tiles[row][col].getIsMine()){
								g.setColor(Color.white);
								g.fillRect(15 + row * BLOCKWIDTH, 30 + col * BLOCKWIDTH, BLOCKWIDTH, BLOCKWIDTH);
								g.drawImage(bomb,15 + row * BLOCKWIDTH, 30+col * BLOCKWIDTH, this);
								g.setColor(Color.black);
								g.drawRect(15 + row* BLOCKWIDTH, 30 + col * BLOCKWIDTH, BLOCKWIDTH, BLOCKWIDTH);
							}
						}
					}
	
				}
				else if(item.getIsHidden()){
					g.setColor(Color.gray);
					g.fillRect(15 + r * BLOCKWIDTH, 30 + c * BLOCKWIDTH, BLOCKWIDTH, BLOCKWIDTH);
					g.setColor(Color.black);
					g.drawRect(15 + r * BLOCKWIDTH, 30 + c * BLOCKWIDTH, BLOCKWIDTH, BLOCKWIDTH);
				}
				//
				else {
					g.drawRect(15 + r * BLOCKWIDTH, 30 + c * BLOCKWIDTH, BLOCKWIDTH, BLOCKWIDTH);
					if (this.field.tiles[r][c].getIsNum() != 0) {
						g.drawString("" + this.field.tiles[r][c].getIsNum(), (r * BLOCKWIDTH) + 18, (c * BLOCKWIDTH) + 43);
					}
				}
				
				
			}
			
		}
		
		g.drawString("X: " + (mouseX - 18) / BLOCKWIDTH + " Y: " + (mouseY - 34) / BLOCKWIDTH, 250, SCREENY - 50);
		
		g.drawString("Mouse X: " + mouseX + " Mouse Y: " + mouseY, 20, SCREENY - 50);
		
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
