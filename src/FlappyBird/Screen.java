package FlappyBird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Screen extends JFrame implements Runnable{
	private Image dbImage;
	private Graphics dbg;
	private int SCREENX,SCREENY;
	private int Charlocx,Charlocy;
	Image bird; 
	ImageIcon thisbird;
	private String theme= new String("normal");
	public void run (){
			try{
				while(true){
					
			
					
					Thread.sleep(5);
				}
			}
			catch(Exception e) {
				System.out.println("Error");
			}
			
		}
	public Screen() {
		// TODO Auto-generated constructor stub
		SCREENX= 1300;
		SCREENY= 700;
		setTitle("Flappy Bird");
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (theme.equals("normal")){
		setBackground(Color.green);
//		bird= new ImageIcon (File("bird.png"));
		}
		
	}
	public void paint(Graphics g) {
		
		
		
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	public void paintComponent(Graphics g) {
//	g.drawImage(bird, );
		
		
		repaint();
	}
public class AL extends KeyAdapter{
	public void keyPressed(KeyEvent e){
		int KeyCode = e.getKeyCode();
		if (KeyCode == e.VK_UP){
			
		}
		if (KeyCode == e.VK_DOWN){
			
		} 
			if (KeyCode == e.VK_W){
			
			}
			if (KeyCode == e.VK_S){
			
			} 
	}
	public void keyReleased(KeyEvent e){
		int KeyCode = e.getKeyCode();
		if (KeyCode == e.VK_S){
			
		}
		if (KeyCode == e.VK_W){
			
		}
		if (KeyCode == e.VK_UP){
		
		}
		if (KeyCode == e.VK_DOWN){
			
		} 
	}
}
}
