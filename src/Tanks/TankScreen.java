package Tanks;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class TankScreen extends JFrame{
	
	int tankOneX,tankOneY,tankTwoX,tankTwoY;
	
	TankBoard field = new TankBoard();
	
	TankTile tankOne = new TankTile(10,10);
	TankTile tankTwo = new TankTile(10,10);
	
	private Image dbImage;
	private Graphics dbg;
	
	public static void main(String[] args){
		new TankScreen();
	}

	public class AL extends KeyAdapter{
		
		public void keyPressed(KeyEvent e){
			int keyInput = e.getKeyCode(); //Code of each key pressed
			if(keyInput == e.VK_LEFT){	  //Left key
				if(tankOneX - 10 <= 0){
					tankOneX = 0;
				}else
					tankOneX = tankOneX - 10;
			}
			if(keyInput == e.VK_RIGHT){	 //Right Key
				if(tankOneX + 10 >= 1000){
					tankOneX = 990;
				}else
					tankOneX = tankOneX + 10;
			}
			if(keyInput == e.VK_UP){	//Up arrow
				if(tankOneY - 20 <= 0){
					tankOneY = 20;
				}else{
					tankOneY = tankOneY -10;
				}
			}
			if(keyInput == e.VK_DOWN){	//Down Arrow
				if(tankOneY + 10 > 290){
					tankOneY = 290;
				}else
					tankOneY = tankOneY + 10;
			}
		
		
		
		
		
		

		
		if(keyInput == e.VK_LEFT){	  //Left key
			if(tankTwoX - 10 <= 0){
				tankTwoX = 0;
			}else
				tankTwoX = tankTwoX - 10;
		}
		if(keyInput == e.VK_RIGHT){	 //Right Key
			if(tankTwoX + 10 >= 1000){
				tankTwoX = 990;
			}else
				tankTwoX = tankTwoX + 10;
		}
		if(keyInput == e.VK_UP){	//Up arrow
			if(tankTwoY - 20 <= 0){
				tankTwoY = 20;
			}else{
				tankTwoY = tankTwoY -10;
			}
		}
		if(keyInput == e.VK_DOWN){	//Down Arrow
			if(tankTwoY + 10 > 290){
				tankTwoY = 290;
			}else
				tankTwoY = tankTwoY + 10;
		}
	
		}
	}
	
	public TankScreen(){
		tankOneX = 10;
		tankOneY = 100;
		tankTwoX = 900;
		tankTwoY = 100;
		addKeyListener(new AL());
		setTitle("Video4");
		setSize(2000,1000);
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
		
		
		
		
		
//		g.setColor(Color.blue); 
//	//	g.drawOval(x, y, 10, 10);
//		g.setColor(Color.orange); 
//		g.drawRect(100, 100, 200, 200);
//		g.setColor(Color.blue);
//		g.fillRect(100,100, 10, 10);
		
		for(int r = 0;r<100;r++){
			for(int c = 0;c<30;c++){
				if(field.tiles[r][c].getIsWall() == true){
					g.setColor(Color.red);
					g.drawRect(10 * r, 10 * c, 10, 10);
				}
				else{
					g.setColor(Color.blue);
					g.drawRect(10 * r, 10 * c, 10, 10);
				}
			}
		}
		g.fillRect(tankOneX, tankOneY, 10, 10);
		g.setColor(Color.red);
		g.fillRect(tankTwoX, tankTwoY, 10, 10);
		
		repaint(); //Causes it to refresh once it reaches this point
		
	}
	
}