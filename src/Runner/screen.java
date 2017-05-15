package Runner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class screen extends JFrame{
	
	int x, y;
	
	int enemyX;
	int enemyY;
	
	private player enemy;
	
	private Image dbImage;
	private Graphics dbg;
	Image tanker;
	
	
	public static void main(String[] args){
		new screen();
	}
	public class AL extends KeyAdapter{
	public void keyPressed(KeyEvent e){
		int keyInput = e.getKeyCode(); //Code of each key pressed
		if(keyInput == e.VK_LEFT){	  //Left key
			if(x - 5 == 0){
				x = 5;
			}else
			x = x - 5;
		}
		if(keyInput == e.VK_RIGHT){	 //Right Key
			if(x + 10 == 500){
				x = 490;
			}else
			x = x + 5;
		}
		if(keyInput == e.VK_UP){	//Up arrow
			if(y - 10 == 10){
			y = 20;
			}else{
				y = y -5;
			}
		}
		if(keyInput == e.VK_DOWN){	//Down Arrow
			if(y + 10 == 500){
				y = 490;
			}else
					y = y + 5;
		}
		
			
			
			}
	}
	
	public screen(){
		
		ImageIcon tank = new ImageIcon("src/practice/bigTank.png");
		tanker = tank.getImage();
		
		enemy =  new player();
		
		enemyX = 100;
		enemyY = 100;
		x = 50;
		y = 50;
		addKeyListener(new AL());
		setTitle("Video4");
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
	
	public void paintComponent(Graphics g){
		
		g.drawRect(x, y, 10, 10);
		enemy.moveTo(x,y);
		g.drawRect(enemy.x, enemy.y, 10, 10);
		
		
		
		repaint(); //Causes it to refresh once it reaches this point
		
	}
	
	public void moveEnemy(int x, int y){
		
	}
	
}
