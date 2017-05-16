package Runner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import practice.Video7;


public class screen extends JFrame implements Runnable{
	
	int x, y,xDirect,yDirect;

	
	private player enemy;
	private Image dbImage;
	private Graphics dbg;
	private boolean winner = false;
	
	boolean gameOver = false;
	Image tanker;
	public int counter = 0;
	int time = 0;

	public void run(){
		try{

			while(true){
				move();
				if(counter > 100){
					enemy.moveTo(x,y);

				}else{
					counter++;
				}
				if(!gameOver)
time++;
				Thread.sleep(10);
				if(((enemy.x ==x || enemy.x ==x+1 || enemy.x ==x + 2 )&&(enemy.y == y || enemy.y == y+1||enemy.y == y+2))){
					gameOver = true;
					
			}
			}
			}
		catch(Exception e){
			System.out.println("Error");
		}
	}
	
	public static void main(String[] args){
		screen game = new screen();
		Thread t = new Thread(game);
		t.start();
	}
	
	public void move(){
		x += xDirect;
		y += yDirect;
		if(x > 500){
			x = 490;
		}
		if(x < 0){
			x = 0;
		}
		if(y > 500){
			y = 490;
		}
		if(y < 0){
			y = 0;
		}
		
		
		
	}
	public void setXDir(int xDir){
		xDirect = xDir;
	}
	public void setYDir(int YDir){
		yDirect = YDir;
	}


	public class AL extends KeyAdapter{
		
		public void keyPressed(KeyEvent e){
			int code = e.getKeyCode();
			if(code == e.VK_LEFT){
				setXDir(-4);
			}
			if(code == e.VK_RIGHT){
				setXDir(4);
			}
			if(code == e.VK_UP){
				setYDir(-4);
			}
			if(code == e.VK_DOWN){
				setYDir(4);
			}
			
			
			
		}
		public void keyReleased(KeyEvent e){
			int code = e.getKeyCode();
	if(code == e.VK_LEFT){
		setXDir(0);
			}
			if(code == e.VK_RIGHT){
				setXDir(0);
			}
			if(code == e.VK_UP){
				setYDir(0);
			}
			if(code == e.VK_DOWN){
				setYDir(0);
			}
			
		}
	}
	
	public screen(){
		
		ImageIcon tank = new ImageIcon("src/practice/bigTank.png");
		tanker = tank.getImage();
		
		enemy =  new player();
		
	
		x = 50;
		y = 50;
		addKeyListener(new AL());
		setTitle("Runner");
		setSize(500,500);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.blue); //Sets the background color to blue.
		
		
		
	}
	
	public void paint(Graphics g){
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	public void paintComponent(Graphics g) {
		int first = 0;
		if(!gameOver){
		g.drawRect(x, y, 10, 10);
		
			
			g.drawRect(enemy.x, enemy.y, 10, 10);
			
		
		
			g.drawRect(enemy.x, enemy.y, 10, 10);
		
		}else{
		Font big = new Font("Arial",Font.BOLD,30);
		g.drawString("GAME OVER", 100, 100);
		g.drawString("You have lasted " + (time / 100) + " Seconds", 100, 120);
		}
		
		repaint(); //Causes it to refresh once it reaches this point
		
	}
	
	
	
}
