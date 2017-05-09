package TicTackToe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import practice.Video4;
import practice.Video4.AL;

public class Screen extends JFrame {

	
	int x, y;
	
	
	private Image dbImage;
	private Graphics dbg;
	
	public static void main(String[] args){
		new Screen();
	}

	public class AL extends KeyAdapter{
		
		public void keyPressed(KeyEvent e){
			int keyInput = e.getKeyCode(); //Code of each key pressed
			if(keyInput == e.VK_LEFT){	  //Left key
				x = x - 100;
			}
			if(keyInput == e.VK_RIGHT){	 //Right Key
				x = x + 100;
			}
			if(keyInput == e.VK_UP){	//Up arrow
				y = y -100;
			}
			if(keyInput == e.VK_DOWN){	//Down Arrow
				y = y + 100;
			}
		}
	}
	public Screen(){
		x = 50;
		y = 50;
		addKeyListener(new AL());
		setTitle("Video4");
		setSize(300,400);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void paint(Graphics g){
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	public void paintComponent(Graphics g){
		
		
		g.setColor(Color.blue); 
		g.drawOval(x, y, 10, 10);
		g.drawLine(100, 0, 100, 300);
		g.drawLine(200, 0, 200, 300);
		g.drawLine(0, 100, 300, 100);
		g.drawLine(0, 200, 300, 200);
		g.drawLine(0, 300, 300, 300);
		repaint(); //Causes it to refresh once it reaches this point
		
	}
	


}
