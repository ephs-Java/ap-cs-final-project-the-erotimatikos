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
	public int counter = 0;
	boolean startScreen = true;
	int time = 0;
	
Image monster;
Image kim;

	
	
	
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
			
							
			if(code == e.VK_R){
				startScreen = false;

				gameOver = false;
				x = 50;
				y = 50;
				enemy.x = 150;
				enemy.y = 150;
				counter = 0;
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
		
		ImageIcon mon = new ImageIcon("src/Runner/monster.png");
		monster = mon.getImage();
		
		ImageIcon kimj = new ImageIcon("src/Runner/kimJonUn.png");
		kim = kimj.getImage();
		
		
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
	Font big = new Font("Arial",Font.BOLD,32);
	public void paintComponent(Graphics g) {
		
		if(startScreen == true){
			g.drawString("The goal is to use the arrow keys to avoid the monster for as long as you can.", 10, 80);
			g.drawString("Click R to start",20,100);
		g.setFont(big);
		g.drawString("Welcome to Runner", 50, 50);
		}else{
		int first = 0;
		if(!gameOver){
		g.drawImage(kim,x, y,this);
		
			
			
			g.drawImage(monster, enemy.x,enemy.y, this);
		
		
	
		
		}else{
		Font big = new Font("Arial",Font.BOLD,30);
		g.drawString("GAME OVER", 100, 100);
		g.drawString("You have lasted " + (time / 100) + " Seconds", 100, 120);
		g.drawString("Click \"R\" to restart", 100, 190);
		
		}
		}
		repaint(); //Causes it to refresh once it reaches this point
		
	}
	
	
	
}
