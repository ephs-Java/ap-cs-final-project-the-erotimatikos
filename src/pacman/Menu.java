package pacman;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Menu extends JFrame {

	//double buffering
	Image dbImage;
	Graphics dbg;
	
	//levels field
	Levels levels;
	
	public Menu() {
		
		//key listener
		addKeyListener(new keyboard());
		
		//declares fields
		levels = new Levels();
		levels.addNumLevels();
		System.out.println(levels.toString());
		
		//window properties
		setSize(1000, 700);
		setVisible(true);
		setTitle("Pac man menu");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public class keyboard extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
			
			switch(key) {
			case KeyEvent.VK_Q:
				System.exit(0);
				break;
			}
			
		}
		
	}
	
	//double buffering
	public void paint(Graphics g) {
		
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	public void paintComponent(Graphics g) {
		
		
		
	}
	
}
