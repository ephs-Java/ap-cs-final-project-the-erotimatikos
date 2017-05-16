package practice;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;



public class Video7 extends JFrame implements Runnable{
	
	int x, y, xDirect,yDirect;
	
	
	public void run(){
		try{
			while(true){
			move();
			Thread.sleep(5);
			}
		} catch(Exception e){
		System.out.println("error");
		}
		
		
	}
	
	private Image dbImage;
	private Graphics dbg;
	Image tanker;
	
	public void move(){
		x += xDirect;
		y += yDirect;
		if(x > 1000){
			x = 1000;
		}
		if(x < 0){
			x = 0;
		}
		if(y > 1000){
			y = 1000;
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
	public static void main(String[] args){
		Video7 game = new Video7();
		Thread t1 = new Thread(game);
		t1.start();
	}

	public class AL extends KeyAdapter{
		
		public void keyPressed(KeyEvent e){
			int code = e.getKeyCode();
			if(code == e.VK_LEFT){
				setXDir(-1);
			}
			if(code == e.VK_RIGHT){
				setXDir(1);
			}
			if(code == e.VK_UP){
				setYDir(-1);
			}
			if(code == e.VK_DOWN){
				setYDir(1);
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
	public Video7(){
		
		ImageIcon tank = new ImageIcon("src/practice/bigTank.png");
		tanker = tank.getImage();
	
		
		
		
		x = 100;
		y = 100;
		addKeyListener(new AL());
		setTitle("Video4");
		setSize(1000,1000);
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
	
	public void paintComponent(Graphics g){
		
//		Font amazing = new Font("Times New Roman",Font.BOLD,20);
//		g.setColor(Color.blue); 
		g.drawOval(x, y, 10, 10);
//		g.setColor(Color.orange); 
//		g.drawRect(100, 100, 200, 200);
//		g.setColor(Color.blue);
//		g.fillRect(100,100, 10, 10);
//		
//		g.setFont(amazing);
//		g.setColor(Color.black);
//		g.drawString("Hello", 90, 90);
		
	//	g.drawImage(tanker, x, y, this);
		
		repaint(); //Causes it to refresh once it reaches this point
		
	}
	
}
