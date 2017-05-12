package Snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class SnakeScreen extends JFrame implements Runnable{
	private Image dbImage;
	private Graphics dbg;
	private int SCREENX;
	private int SCREENY;
	private SnakeTile[][] tiles;
	/**
	 * 
	 */
	public void run(){
			try{
				while(true){
				
				}
			}
			catch(Exception e) {
				System.out.println("Error");
			}
			
		}
	public SnakeScreen() {
		// TODO Auto-generated constructor stub
		tiles = new SnakeTile[SCREENX/10][SCREENY/10];
		ArrayList <SnakeTile> snake= new ArrayList();
		snake.add(tiles[SCREENX/2][SCREENY/2]);
		snake.add(tiles[SCREENX/2][SCREENY/2 + 1]);
		addKeyListener( new AL()); 
		SCREENX= 500;
		SCREENY= 500;
		setTitle("Snake");
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		}
		
	public void paint(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		try {
			paintComponent(dbg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(dbImage, 0, 0, this);
	}
	public void paintComponent(Graphics g) throws InterruptedException {
	
	repaint();
	}
	
	public class AL extends KeyAdapter{
	public void keyPressed(KeyEvent e){
		int KeyCode = e.getKeyCode();
		if (KeyCode == e.VK_SPACE){
			 
	}
	}
	public void keyReleased(KeyEvent e){
		int KeyCode = e.getKeyCode();
		if (KeyCode == e.VK_SPACE){
		
		
		}

	}
}
	public static void main(String[] args){
	SnakeScreen josh= new SnakeScreen();
	Thread t1= new Thread(josh);
	t1.start();
}
    }
