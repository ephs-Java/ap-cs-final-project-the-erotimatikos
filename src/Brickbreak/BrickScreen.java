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
	private int onex, oney, oneh, onew;
    private int twox, twoy, changex, changey;
    private int change= 0;
    private int leftscore, rightscore;
	private int SCREENX;
	private int SCREENY;
	private int ballx = SCREENX/2;
	private int bally = SCREENY/2;
	private Image dbImage;
	private Graphics dbg;
	public BrickScreen() {
		// TODO Auto-generated constructor stub
		addKeyListener( new AL()); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SCREENX= 700;
		SCREENY= 500;
		setTitle("Pong");
		setBackground(Color.BLACK);
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(false);
		onex= 25;
		oney= 250;
		oneh= 80;
		onew= 20;
		twox= SCREENX-25-onew;
		twoy= 250;
		ballx= SCREENX/2;
		bally= SCREENY/2;
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
			if (KeyCode == e.VK_UP){
			     
			
			 
			}
			if (KeyCode == e.VK_DOWN){
			     	
				
		}
		}
		public void keyReleased(KeyEvent e){
			int KeyCode = e.getKeyCode();
			if (KeyCode == e.VK_UP || KeyCode == e.VK_DOWN){
		
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
		
			Thread.sleep(3);
			}
		}
		catch(Exception e) {
			System.out.println("Error");
		}
		
	}
}
