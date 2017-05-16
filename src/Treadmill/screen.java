package Treadmill;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;




public class screen extends JFrame implements Runnable {

	int x, y,xDirect,yDirect, yh,xh;


	private Graphics dbg;;
	private Image dbImage;

	private Image bike;
	private Image road;
	private Image hobo;
	
	
	public screen(){
		x = 100;
		y=100;
		ImageIcon tank = new ImageIcon("src/Treadmill/road.jpeg");
		road = tank.getImage();
		ImageIcon biker = new ImageIcon("src/Treadmill/biker2.png");
		bike = biker.getImage();
		ImageIcon hobot = new ImageIcon("src/Treadmill/hobo.png");
		hobo = hobot.getImage();


		addKeyListener(new AL());
		setTitle("TreadMill");
		setSize(470,240);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.green); //Sets the background color to blue.



	}

	

	public static void main(String[] args){
		screen runner = new screen();
		Thread t = new Thread(runner);
		t.start();

	}
	
	public void run(){
		try{
			while(true){
				move();
				moveHobo();
				Thread.sleep(10);
			}
		}catch(Exception e){
			System.out.println("Error");
		}
	}


	public void moveHobo(){
		xh-= 4;
		
		if(xh <0){
			xh = 470;
			yh = ((int) (Math.random() * 240));
		}
		
	}
	public void move(){

		y+= yDirect;
		if(y> 200){
			y = 200;
			
		}if(y<20){
			y =20;
		}

	}

	
	public void setYDir(int YDir){
		yDirect = YDir;
	}

	public class AL extends KeyAdapter{

		public void keyPressed(KeyEvent e){
			int code = e.getKeyCode();
		
			if(code == e.VK_UP){
				setYDir(-4);
			}
			if(code == e.VK_DOWN){
				setYDir(4);
			}



		}
		public void keyReleased(KeyEvent e){
			int code = e.getKeyCode();
		
			if(code == e.VK_UP){
				setYDir(0);
			}
			if(code == e.VK_DOWN){
				setYDir(0);
			}

		}
	}
	public void paint(Graphics g){
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	
	public void paintComponent(Graphics g){
			g.drawImage(road, 0, 40, this);
		g.setColor(Color.red);
		g.drawImage(bike, x, y, this);
		g.drawImage(hobo, xh, yh, this);

      repaint();
	}


}
