package RobotMouse;


import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import minesweeper.MineScreen.Mouse;
import practice.Video7.AL;

public class MouseScreen extends JFrame{
Image screen;
Image passe;

 Image dbImage;
 Graphics dbg;

String user = " ";
String pass = " ";
boolean usern = true;
int mouseX;
int mouseY;
String star = " ";
	
	public MouseScreen(){
		addKeyListener(new AL());
		addMouseListener(new Mouse());
		ImageIcon creen = new ImageIcon("src/RobotMouse/Screenshot 2017-06-01 11.57.24.png");
		screen = creen.getImage();
		ImageIcon pas = new ImageIcon("src/RobotMouse/pass.png");
		passe = pas.getImage();
		this.setBackground(Color.darkGray); //Sets backgrounf color
		setTitle("Administrator Access"); //sets title of the screen
		setSize(440,230); //sets size of the window
		setResizable(false); //Can user reset the window?
		setVisible(true); //Can user see the window?
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closes program when red X clicked
		
		
	
		
	}
	
	//mouse input
	public class Mouse extends MouseAdapter {
		
		public void mousePressed(MouseEvent e)  {
		
			
			
			mouseX = e.getX();
			mouseY = e.getY();
			if(mouseY > 100 && mouseY < 130){
				if(mouseX >170 && mouseX < 420){
					usern = true;
				}
			}
			if(mouseY > 140 && mouseY < 210){
				if(mouseX >200 && mouseX < 450){
					usern = false;
				}
			}
		}
		
	}
	
	
public class AL extends KeyAdapter{
		
		public void keyPressed(KeyEvent e){
			
			if(usern){
			if(user.length() < 36){
			user += "" + (e.getKeyChar());
			
			}
			}
			else{
				if(user.length() < 36){
				pass += "" + (e.getKeyChar());
				
				}
			}
			
			
			if(e.getKeyCode() == e.VK_ENTER){
				System.exit(0);
				}
			if(e.getKeyCode() == e.VK_BACK_SPACE){
				if(usern){
					user = user.substring(0, user.length()-2);
				}
				else{
					pass = pass.substring(0, pass.length()-2);
				}
				}
			
			
		}
	}
	public static void main(String[] args){
		new MouseScreen(); //Runs program
	}
	
	public void paint(Graphics g){
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	public void paintComponent(Graphics g){
		star = "";
		for(int i = 1;i<pass.length();i++){
			star+= "*";
		}
		if(usern){
		g.drawImage(screen, 0, 0, this);
		}
		else{
			g.drawImage(passe, 0, 0, this);
		}
		
	
		g.drawString(user, 170, 120);
		g.drawString(star, 170, 150);
		
	
//		try {
//			Robot mice = new Robot();
////			mice.mouseMove(100, 750);
////			mice.mousePress(InputEvent.BUTTON1_DOWN_MASK);
////			mice.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
////	mice.delay(10000);
////			mice.keyPress(KeyEvent.VK_META);
////			mice.keyPress(KeyEvent.VK_SPACE);
////			mice.keyRelease(KeyEvent.VK_SPACE);
////			mice.keyPress(KeyEvent.VK_A);
////			mice.keyRelease(KeyEvent.VK_A);
////			
////		mice.delay(100);
////		
////		
////			mice.keyRelease(KeyEvent.VK_META);
////			mice.keyPress(KeyEvent.VK_F);
////			mice.keyPress(KeyEvent.VK_I);
////			mice.keyPress(KeyEvent.VK_N);
////			mice.keyPress(KeyEvent.VK_D);
////			mice.keyPress(KeyEvent.VK_ENTER);
////			mice.keyRelease(KeyEvent.VK_F);
////			mice.keyRelease(KeyEvent.VK_I);
////			mice.keyRelease(KeyEvent.VK_N);
////			mice.keyRelease(KeyEvent.VK_D);
//		} catch (AWTException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		}
//		
//		
//		
//		
//		
//		
		
		//
	
		
		repaint();
	}
}