package minesweeper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Screen extends JFrame {

	//boolean gameOver
	private boolean isGameOver = false;
	
	//boolean for winning the game
	private boolean victory = false;
	
	//the mines
	private Mines field;
	Image bomb;
	Image flag;
	Image explode;
	//constant for the block width
	private final int BLOCKWIDTH = 15;
	
	//screen dimensions variables
	private final int SCREENX = 1300, SCREENY = 750;//800 by 500 default
	
	int minesX = 80;//default 50
	int minesY = 40;//default 25
	
	//double buffering
	private Image dbImage;
	private Graphics dbg;
	
	
	//mouse x and y variables
	private int mouseX = 0, mouseY = 0;
	
	//how many non flag placing clicks the user has made
	private static int count = 0;
	
	//the default constructor, creates JFrame
	public Screen() {
		
		//handles pictures
		ImageIcon image1 = new ImageIcon("bomb_PNG26.png");
		bomb = image1.getImage();
		ImageIcon image2 = new ImageIcon("boom.png");
		explode = image2.getImage();
		
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
	
	//mouse input
	public class Mouse extends MouseAdapter {
		
		public void mousePressed(MouseEvent e)  {
		
			if (isGameOver) {
				return;
			}
			
			mouseX = e.getX();
			mouseY = e.getY();
			
			
//			System.out.println("Mouse X: " + mouseX + " Mouse Y: " + mouseY);
			
			int blockX = (mouseX - 18) / BLOCKWIDTH;
			int blockY = (mouseY - 34) / BLOCKWIDTH;
			
			//prevents array index out of bounds for clicking on a block
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
			
			//handles flag placement
			//can use right click, control click, alt click, or shift click to place a flag
			if (e.getButton() == MouseEvent.BUTTON3 || e.isControlDown() || e.isAltDown() || e.isShiftDown()) {
				field.tiles[blockX][blockY].toggleFlag();
				return;
			}
			//adds 1 to count
			count++;
			//clears a 3 by 3 area on the first turn
			if(count == 1){
				field.clearArea(blockX,blockY);
				field.updateAllNums();
			}
			//disables count stack overflow
			if (count > 1000) {
				count = 1000;
			}
			
			field.tiles[blockX][blockY].show();
			field.updateFromPoint(blockX, blockY);
			
			victory = field.isVictory();
			
		}
		
	}
	
	//key input
	public class Keyboard extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
			
			if (key == e.VK_Q) {
				System.exit(0);
			}
			if (key == e.VK_R) {
				field = new Mines(minesX, minesY);
				isGameOver = false;
				victory = false;
				count = 0;
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
				
				//detects game over, clicking on a mine
				if (item.getIsMine() && !item.getIsHidden() ) {
					
					g.setColor(Color.red);
					g.fillRect(15 + r * BLOCKWIDTH, 30 + c * BLOCKWIDTH, BLOCKWIDTH, BLOCKWIDTH);
					g.drawImage(bomb,15 + r * BLOCKWIDTH, 30+c * BLOCKWIDTH, this);
					
					//prevents the boom method from running over and over
					if (isGameOver) {
						continue;
					}
					this.field.boom();
					isGameOver = true;
					
				}
				//prints a gray block
				else if(item.getIsHidden()){
					g.setColor(Color.gray);
					//colors flags green
					if (field.tiles[r][c].isFlagged()) {
						g.setColor(Color.green);
					}
					g.fillRect(15 + r * BLOCKWIDTH, 30 + c * BLOCKWIDTH, BLOCKWIDTH, BLOCKWIDTH);
					
				}
				//prints a number on exposed blocks
				else {
					if (this.field.tiles[r][c].getIsNum() != 0) {
						g.setColor(Color.black);
						g.drawString("" + this.field.tiles[r][c].getIsNum(), (r * BLOCKWIDTH) + 18, (c * BLOCKWIDTH) + 43);
					}
				}
				//prints the tiles no matter what
				g.setColor(Color.black);
				g.drawRect(15 + r * BLOCKWIDTH, 30 + c * BLOCKWIDTH, BLOCKWIDTH, BLOCKWIDTH);
				g.setColor(Color.gray);
				
				
			}
			
		}
		
		g.setColor(Color.black);
		
		g.drawString("X: " + (mouseX - 18) / BLOCKWIDTH + " Y: " + (mouseY - 34) / BLOCKWIDTH, 250, SCREENY - 50);
		
		g.drawString("Mouse X: " + mouseX + " Mouse Y: " + mouseY, 20, SCREENY - 50);
		
		if (victory) {
//			System.out.println("asdfa");
			g.setColor(Color.green);
			g.drawString("VICTORY!!", 400, SCREENY - 50);
			g.setColor(Color.black);
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
