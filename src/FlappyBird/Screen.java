package FlappyBird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Screen extends JFrame implements Runnable{
	private Image dbImage;
	private Graphics dbg;
	private int SCREENX,SCREENY;
	private int Charlocy= 500;
	private int Charlocx = 400;
	private int changey= 0;
	Image birdused;
	Image bird;
	Image wing;
	Image fly;
	Image fall;
	private int pipecounter=0;
	private boolean gameover= false;
	private boolean passed= false;
	private boolean gameoversequence= false;
	private boolean tap= true;
	private int pipeheight, pipewidth;
	private int pipelocx = 700;
	private int pipelocy = 0;
	private int pixelcounter, pixelcounter2;
	private String theme= new String("normal");
	private int gravity= 1;
	public void run (){
			try{
				while(true){
					move();
					pipes();
					collision();
					counter();
					gameoveraction();
					Thread.sleep(6);
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
		setBackground(Color.cyan);
		ImageIcon image1 = new ImageIcon("Flappy_Bird.png");
		bird = image1.getImage();
		ImageIcon image2 = new ImageIcon("Wing.png");
		wing = image2.getImage();
		ImageIcon image3 = new ImageIcon("flying.png");
		fly = image3.getImage();
		ImageIcon image4 = new ImageIcon("falling.png");
		fall = image4.getImage();
		birdused=bird;
		
		
		pipewidth= 50;
		pipeheight= 255;
		}
		
	}
	public void counter() throws InterruptedException{
		if (pipelocx < Charlocx){
			pipecounter++;
		}
	}
	public void pipes(){
		pipelocx --;
		if (pipelocx <=0){
			pipelocx = SCREENX;
		}
	}
	public void collision(){
		if (Charlocy <= pipeheight && (Charlocx >pipelocx || Charlocx + 36 == pipelocx)&& Charlocx<pipelocx+pipewidth){
			gameover= true;
			Charlocy = 310;
			
			Charlocx= pipelocx;
			
		}
		if (Charlocy >= SCREENY-pipeheight && (Charlocx >pipelocx || Charlocx + 36 == pipelocx)&& Charlocx<pipelocx+pipewidth){
			gameover= true;
			Charlocy = 310;
			
			Charlocx= pipelocx;
			
		}
		if (Charlocy >= SCREENY){
			Charlocy= 0;
			gravity = 1;
			pixelcounter=0;
			pixelcounter2=0;
			gameover= true;
		}
		
	}
	public void gameoveraction(){
	if (gameover){
        Charlocy= SCREENY/2;
        Charlocx= pipelocx;
		gameoversequence=true;
	}
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
    g.drawImage(birdused, Charlocx ,Charlocy, this);
    g.fillRect(pipelocx, pipelocy, pipewidth, pipeheight);
    g.fillRect(pipelocx, SCREENY- pipeheight, pipewidth, pipeheight);
    if (gameoversequence){
    	Thread.sleep(700);
    	g.fillRect(0, 0, SCREENX, SCREENY);
    	g.setColor(Color.WHITE);
    	g.drawString("GAME OVER", 650, 350);
    	g.drawString("You passed"+ pipecounter + " Pipes.", 650, 400);
    	return;
    }
	repaint();
	}
	
	public void move(){
	Charlocy += changey;
    Charlocy += gravity;
    if (!gameover){
	Charlocy ++;
    pixelcounter++;
    pixelcounter2++;
    if (pixelcounter2 == 60){
    	birdused= bird;
    	gravity++;
    }
    if (pixelcounter == 110){
    	birdused= fall;
    	gravity ++;
    	pixelcounter=0;
    }
    }
	}
	public void sety(int a){
		changey=a;
	}
	
public class AL extends KeyAdapter{
	public void keyPressed(KeyEvent e){
		int KeyCode = e.getKeyCode();
		if (KeyCode == e.VK_SPACE){
			if(tap){
				pixelcounter=0;
				pixelcounter2=0;
			for( int i=0; i<15; i++){
				sety(-8);
			move();
			}	
			birdused= fly;
			tap= false;
			gravity=0;
			} 
			sety(0);
		
		}
	}
	public void keyReleased(KeyEvent e){
		int KeyCode = e.getKeyCode();
		if (KeyCode == e.VK_SPACE){
			sety(0);
			tap= true;
		}

	}
}
public static void main(String[] args){
	Screen josh= new Screen();
	Thread t1= new Thread(josh);
	t1.start();
}
}
