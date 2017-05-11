package FlappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Screen extends JFrame implements Runnable{
	private Image dbImage;
	private Graphics dbg;
	private int SCREENX,SCREENY;
	private int Charlocy= 250;
	private int Charlocx = 100;
	private int changey= 0;
	Image first, second, third;
	Image birdused;
	Image bird, background;
	Image wing;
	Image fly;
	Image fall;
	Image movingBottom;
	private int pipecounter=0;
	private boolean gameover= false;
	private boolean passed= false;
	private boolean gameoversequence= false;
	private boolean tap= true;
	private int pipeheight, pipewidth;
	private int bottompipeh;
	private boolean start = false;
	private boolean hasstarted= false;
	private int pipelocx = 700;
	private int pipelocy = 0;
	private int pixelcounter, pixelcounter2;
	private String theme= new String("normal");
	private int gravity= 1;
	public void run (){
			try{
				while(true){
				startsequence();
				while(start){
					move();
					pipes();
					collision();
					gameoveraction();
					Thread.sleep(6);
				}
				}
			}
			catch(Exception e) {
				System.out.println("Error");
			}
			
		}
	public Screen() {
		// TODO Auto-generated constructor stub
		addKeyListener( new AL()); 
		SCREENX= 500;
		SCREENY= 885;
		setTitle("Flappy Bird");
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (theme.equals("normal")){
		ImageIcon image0 = new ImageIcon("back.png");
		background= image0.getImage();
		ImageIcon image1 = new ImageIcon("Flappy_Bird.png");
		bird = image1.getImage();
		ImageIcon image2 = new ImageIcon("Wing.png");
		wing = image2.getImage();
		ImageIcon image3 = new ImageIcon("flying.png");
		fly = image3.getImage();
		ImageIcon image4 = new ImageIcon("falling.png");
		fall = image4.getImage();
		ImageIcon image5 = new ImageIcon("sprite1.png");
		first = image5.getImage();
		ImageIcon image6 = new ImageIcon("sprite2.png");
		second = image6.getImage();
		ImageIcon image7 = new ImageIcon("sprite3.png");
		third = image7.getImage();
		birdused=bird;
		Random one= new Random();
		bottompipeh= one.nextInt(500) + 200;
		pipeheight= (SCREENY- 160) -bottompipeh;
		pipewidth= 50;
		}
		
	}

	public void startsequence() throws InterruptedException{
		birdused= first;
		Thread.sleep(200);
		birdused= second;
		Thread.sleep(200);
		birdused= third;
		Thread.sleep(200);
		birdused= second;
		Thread.sleep(200);
	}
	
	public void pipes(){
		pipelocx --;
		Random two= new Random();
		if (pipelocx <= 0){
			pipelocx = SCREENX;
			pipecounter++;
			bottompipeh= two.nextInt(500) + 200;
			pipeheight= (SCREENY- 160) -bottompipeh;
		}
	}
	public void collision(){
		if (Charlocy <= pipeheight && (Charlocx >pipelocx || Charlocx + 30 == pipelocx)&& Charlocx<pipelocx+pipewidth){
			gameover= true;
			Charlocy = 310;
			
			Charlocx= pipelocx;
			
		}
		if (Charlocy+ 30 >= SCREENY-bottompipeh && (Charlocx >pipelocx || Charlocx + 30 == pipelocx)&& Charlocx<pipelocx+pipewidth){
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
	g.drawImage(background, 0, 0, this);
	Font josh= new Font("Bloody", Font.PLAIN, 26);
		if (!hasstarted){
			
			
	g.setColor(Color.black);
	g.fillRect(SCREENX/2- 50, SCREENY/2 -110, 120, 50);
	g.setColor(Color.white);
	g.fillRect(SCREENX/2- 45, SCREENY/2 -105, 110, 38);
	g.setColor(Color.orange);
	g.fillRect(SCREENX/2- 43, SCREENY/2 -103, 105, 34);
	g.setFont(josh);
	g.setColor(Color.WHITE);
	g.drawString("Start", SCREENX/2- 25, SCREENY/2 -80);
	g.drawString("Press Space to Start", SCREENX/2- 115, SCREENY/2 - 10);
	g.drawImage(birdused,SCREENX/2 , SCREENY/2 - 200, this);
}
else if (hasstarted){
	g.drawImage(birdused, Charlocx ,Charlocy, this);
    g.fillRect(pipelocx, pipelocy, pipewidth, pipeheight);
    g.fillRect(pipelocx, SCREENY- bottompipeh, pipewidth, bottompipeh);
    g.setColor(Color.GRAY);
    g.setFont(josh);
    g.drawString(""+pipecounter, SCREENX/2, 100);
    if (gameoversequence){
    	g.drawString("GAME OVER", SCREENX/2-75, SCREENY/2-70);
    	g.drawString("You passed "+ pipecounter + " Pipes.", SCREENX/2-75, SCREENY/2 -30);
    	start=false;
    	Thread.sleep(3000);
    	Charlocy= SCREENY/2;
    	Charlocx= 100;
    	pipecounter= 0;
    	pipelocx=SCREENX;
    	gameoversequence=false;
    	gameover= false;
    	hasstarted= false;
    	
    }
   }
	repaint();
	}
	
	public void move() throws InterruptedException{
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
			    if (!hasstarted){
			    	hasstarted= true;
			    	start= true;
			    }
			if(tap){
				pixelcounter=0;
				pixelcounter2=0;
			for( int i=0; i<15; i++){
				sety(-8);
			try {
				move();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
