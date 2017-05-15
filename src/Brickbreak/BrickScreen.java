package Brickbreak;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;


public class BrickScreen extends JFrame implements Runnable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int SCREENX, SCREENY;
	private int paddlex;
	private int pchange;
	private int BRICKH, BRICKW;
	private Image dbImage;
	private Graphics dbg;
	
	
	Brick [][] bricks= new Brick[5][3];
	
	
	public BrickScreen() {
		// TODO Auto-generated constructor stub
		addKeyListener( new AL()); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SCREENX= 600;
		SCREENY= 700;
		setTitle("Pong");
		setBackground(Color.CYAN);
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(false);
	    paddlex= SCREENX/2- 60;
	    BRICKH= 2;
	    BRICKW=2;
	    
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
		g.setColor(Color.GRAY);
		g.fillRect(paddlex, SCREENY- 50, 125, 20);
	    g.setColor(Color.darkGray);
		g.fill3DRect(paddlex, SCREENY-50, 125, 20, false);
		for (int i=0; i<bricks.length; i++){
			for (int j=0; j<bricks[i].length; j++){
				if (bricks[i][j].isAlive()){
					g.setColor(Color.green);
					g.fillRect(i*BRICKH, j*BRICKW, BRICKW, BRICKH);
				}
			}
			
		}
		repaint();
	}
	public void movePaddle(){
		paddlex += pchange;
	}
	public void pboundaries (){
		if (paddlex < 0){
			paddlex=0;
		}
		if (paddlex + 125 > SCREENX){
			paddlex= SCREENX -125;
		}
	}
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int KeyCode = e.getKeyCode();
			if (KeyCode == e.VK_LEFT){
			     pchange= -1;
			}
			if (KeyCode == e.VK_RIGHT){
			     pchange=1;
				
		}
		}
		public void keyReleased(KeyEvent e){
			int KeyCode = e.getKeyCode();
			if (KeyCode == e.VK_LEFT || KeyCode == e.VK_RIGHT){
		        pchange=0;
			}
		}
	}
	public void fillBricks(){
		for (int i=0; i<bricks.length; i++){
			for (int j=0; j< bricks[i].length; j++){
				bricks[i][j]= new Brick();
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         BrickScreen ping= new BrickScreen();
         Thread t2= new Thread(ping);
         t2.start();
	}

	@Override
	public void run (){
		try{
		
			while(true){
		    movePaddle();
		    pboundaries();
			Thread.sleep(3);
			}
		}
		catch(Exception e) {
			System.out.println("Error");
		}
		
	}
}
