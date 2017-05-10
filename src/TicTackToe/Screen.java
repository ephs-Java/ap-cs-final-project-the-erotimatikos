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

	 boolean player1Turn;
	int x, y;
	private static int numOfClicks = 0;
	
	private Board field = new Board();
	

	
	private Image dbImage;
	private Graphics dbg;
	
	public static void main(String[] args){
		
		new Screen();
	}

	public class AL extends KeyAdapter{
		
		public void keyPressed(KeyEvent e){
			int keyInput = e.getKeyCode(); //Code of each key pressed
			if(keyInput == e.VK_LEFT){	  //Left key
				if(x - 50 == 0){
					x = 50;
				}else
				x = x - 100;
			}
			if(keyInput == e.VK_RIGHT){	 //Right Key
				if(x + 50 == 300){
					x = 250;
				}else
				x = x + 100;
			}
			if(keyInput == e.VK_UP){	//Up arrow
				if(y - 50 == 0){
				y = 50;
				}else{
					y = y -100;
				}
			}
			if(keyInput == e.VK_DOWN){	//Down Arrow
				if(y + 50 == 300){
					y = 250;
				}else
						y = y + 100;
			}
			if(keyInput == e.VK_ENTER){
				if(field.tiles[x / 100][y / 100].get() == 0){
				if(numOfClicks % 2 == 0){
					field.tiles[x / 100][y / 100].setTo(1);
				}
				else{
					field.tiles[x / 100][y / 100].setTo(2);
				}
				
				numOfClicks++;
				}
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
		
		
	
		
		for(int r = 0; r <= 2;r++){
			for(int c = 0; c<= 2;c++){
				if(field.tiles[r][c].get() == 1){
					
					
					g.setColor(Color.green);
					g.fillRect(100 * r, 100 * c, 100, 100);
				}
				else if(field.tiles[r][c].get() == 2){
						g.setColor(Color.red);
						g.fillRect(100 * r, 100 * c, 100, 100);
					
				}
				g.setColor(Color.blue);
				g.drawRect(100 * r, 100 * c, 100, 100);
			}
		}
		
		g.setColor(Color.blue); 
		g.drawOval(x, y, 10, 10);
		
		g.setColor(Color.black);
		
		if(numOfClicks % 2 == 0){
			player1Turn = true;
		}
		else{
			player1Turn = false;
		}
		g.drawString("Player 1 turn " + player1Turn, 0, 350);
		g.drawString("Player 2 turn " + !player1Turn,0, 362);
		g.drawString(field.isWinner(numOfClicks), 10, 374);
		repaint(); //Causes it to refresh once it reaches this point
		
		
	}
	


}
