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
	private int changey= 0;
	
	Image bird;
	private String theme= new String("normal");
	public void run (){
			try{
				while(true){
					move();
					Thread.sleep(5);
				}
			}
			catch(Exception e) {
				System.out.println("Error");
			}
			
		}
	public Screen() {
		// TODO Auto-generated constructor stub
		addKeyListener( new AL()); 
		SCREENX= 1300;
		SCREENY= 700;
		setTitle("Flappy Bird");
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (theme.equals("normal")){
		setBackground(Color.green);
		ImageIcon image1 = new ImageIcon("Flappy_Bird.png");
		bird = image1.getImage();
		}
		
	}
	public void paint(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	public void paintComponent(Graphics g) {
    g.drawImage(bird, Charlocx ,Charlocy, this);
	repaint();
	}
	
	public void move(){
	Charlocy += changey;

	}
	public void sety(int a){
		changey=a;
	}
	
public class AL extends KeyAdapter{
	public void keyPressed(KeyEvent e){
		int KeyCode = e.getKeyCode();
		if (KeyCode == e.VK_SPACE){
			sety(-5);
		}

	}
	public void keyReleased(KeyEvent e){
		int KeyCode = e.getKeyCode();
		if (KeyCode == e.VK_SPACE){
			sety(0);
		}

	}
}
public static void main(String[] args){
	Screen josh= new Screen();
	Thread t1= new Thread(josh);
	t1.start();
}
}
