package RetroPong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;


public class Pong extends JFrame implements Runnable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int onex, oney, oneh, onew;
    private int twox, twoy;
    private int change= 0;
	private int SCREENX;
	private int SCREENY;
	private Image dbImage;
	private Graphics dbg;

	public Pong() {
		// TODO Auto-generated constructor stub
		addKeyListener( new AL()); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SCREENX= 700;
		SCREENY= 500;
		setTitle("Pong");
		setBackground(Color.BLACK);
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(false);
		onex= 25;
		oney= 250;
		oneh= 80;
		onew= 20;
		twox= SCREENX-25-onew;
		twoy= 250;
	}
	public void paint(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		try {
			paintComponent(dbg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(dbImage, 0, 0, this);
	}
	public void paintComponent(Graphics g) throws InterruptedException {
		g.setColor(Color.white);
		g.fillRect(onex, oney, onew, oneh);
		g.fillRect(twox, oney, onew, oneh);
		g.fillRect(339, 35, 20, 20);
		g.fillRect(339, 75, 20, 20);
		g.fillRect(339, 115, 20, 20);
		g.fillRect(339, 155, 20, 20);
		g.fillRect(339, 195, 20, 20);
		g.fillRect(339, 235, 20, 20);
		g.fillRect(339, 275, 20, 20);
		g.fillRect(339, 315, 20, 20);
		g.fillRect(339, 355, 20, 20);
		g.fillRect(339, 395, 20, 20);
		g.fillRect(339, 435, 20, 20);
		g.fillRect(339, 475, 20, 20);
		repaint();
	}
	public void collision(){
		if(oney <= 25)
			Ymove(0);
		if (oney + oneh >= SCREENY)
			oney= SCREENY-oneh;
	}
	public void move() throws InterruptedException{
		oney += change;
		
	}
	public void Ymove(int a){
	change= a;
	}
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int KeyCode = e.getKeyCode();
			if (KeyCode == e.VK_UP){
			     if(oney >25)
				Ymove(-1);
			 
			}
			if (KeyCode == e.VK_DOWN){
			     Ymove(1);
			}
		}
	
		public void keyReleased(KeyEvent e){
			int KeyCode = e.getKeyCode();
			if (KeyCode == e.VK_UP || KeyCode == e.VK_DOWN){
			Ymove(0);
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         Pong ping= new Pong();
         Thread t1= new Thread(ping);
         t1.start();
	}

	@Override
	public void run (){
		try{
			while(true){
			move();
			collision();
			Thread.sleep(3);
			}
		}
		catch(Exception e) {
			System.out.println("Error");
		}
		
	}
}
