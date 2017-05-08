package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

public class Screen extends JFrame {

	//double buffering
	Image dbImage;
	Graphics dbg;
	
	//main method
	public Screen() {
		
		
		//sets the properties of the screen
		setTitle("Pac-Man");
		setVisible(true);
		setSize(500, 500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		
	}
	
	//paints stuff to the jframe
	public void paintComponent(Graphics g) {
		
		g.setColor(Color.white);
		g.drawString("asdf", 100, 100);
		g.setColor(Color.blue);
		g.drawString("asdf", 100, 200);
		repaint();
		
	}
	
	//double buffering
	public void paint(Graphics g) {
		
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	
	
	
}
