package practice;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import practice.Video4.AL;

public class Video5 extends JFrame{
	
	int x, y;
	
	
	private Image dbImage;
	private Graphics dbg;
	Image tanker;
	
	
	public static void main(String[] args){
		new Video5();
	}

	public class AL extends KeyAdapter{
		
		public void keyPressed(KeyEvent e){
			int code = e.getKeyCode();
			if(code == e.VK_LEFT){
				x = x - 5;
			}
		}
	}
	public Video5(){
		
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
	
	public void paintComponent(Graphics g){
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	public void paint(Graphics g){
		
//		Font amazing = new Font("Times New Roman",Font.BOLD,20);
//		g.setColor(Color.blue); 
//		g.drawOval(x, y, 10, 10);
//		g.setColor(Color.orange); 
//		g.drawRect(100, 100, 200, 200);
//		g.setColor(Color.blue);
//		g.fillRect(100,100, 10, 10);
//		
//		g.setFont(amazing);
//		g.setColor(Color.black);
//		g.drawString("Hello", 90, 90);
		
		g.drawImage(tanker, 100, 100, this);
		
		repaint(); //Causes it to refresh once it reaches this point
		
	}
	
}
