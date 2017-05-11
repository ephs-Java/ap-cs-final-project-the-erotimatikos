package RobotMouse;


import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.InputEvent;

import javax.swing.JFrame;

public class MouseScreen extends JFrame{

	
	public MouseScreen(){
		this.setBackground(Color.darkGray); //Sets backgrounf color
		setTitle("Video one"); //sets title of the screen
		setSize(1000,1000); //sets size of the window
		setResizable(false); //Can user reset the window?
		setVisible(true); //Can user see the window?
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closes program when red X clicked
		
	}
	public static void main(String[] args){
		new MouseScreen(); //Runs program
	}
	public void paint(Graphics g){
	
		g.setColor(Color.orange); 
		g.drawRect(3, 3, 200, 200);
		g.setColor(Color.blue);
		g.fillRect(4,5, 100, 100);
		
		try {
			Robot mice = new Robot();
			mice.mouseMove(100, 750);
			mice.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			mice.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		
		
		
		//
		
		
		
	}
}