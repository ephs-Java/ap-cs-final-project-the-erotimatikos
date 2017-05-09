package practice;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;



/*Double buffering*/
public class Video3 extends JFrame{
	
	private Image dbImage;
	private Graphics dbg;

	int x, y;
	
	public Video3(){
		
		addKeyListener(new AL());
		this.setBackground(Color.darkGray); //Sets backgrounf color
		setTitle("Video two"); //sets title of the screen
		setSize(1000,1000); //sets size of the window
		setResizable(false); //Can user reset the window?
		setVisible(true); //Can user see the window?
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closes program when red X clicked
		x = 150;
		y = 150;
	}
	
	public  class AL extends KeyAdapter{ //Creates a class that takes key input
		
		public void keyPressed(KeyEvent e){ // If key is pressed
			int keyInput = e.getKeyCode(); //Code of each key pressed
			if(keyInput == e.VK_LEFT){	  //Left key
				x = x -10;
			}
			if(keyInput == e.VK_RIGHT){	 //Right Key
				x = x + 10;
			}
			if(keyInput == e.VK_UP){	//Up arrow
				y = y -10;
			}
			if(keyInput == e.VK_DOWN){	//Down Arrow
				y = y + 10;
			}
		}
		public void keyReleased(KeyEvent e){ //Release of key
			
		}
	}
	public static void main(String[] args){
		new Video3(); //Runs program
	}
	
	//Double buffering, it saves the previous screen, then rebroadcast the new screen
	public void paint(Graphics g){
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	public void paintComponent(Graphics g){
		
		
		g.setColor(Color.blue); 
		g.drawOval(x, y, 10, 10);
		g.setColor(Color.orange); 
		g.drawRect(100, 100, 200, 200);
		g.setColor(Color.blue);
		g.fillRect(100,100, 10, 10);
		
		repaint(); //Causes it to refresh once it reaches this point
		
		
		
		//
		
		
		
	}
}
