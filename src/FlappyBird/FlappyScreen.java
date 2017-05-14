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
public class FlappyScreen extends JFrame implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image dbImage;
	private Graphics dbg;
	private int SCREENX,SCREENY;
	private int Charlocy= 300;
	private int Charlocx = 50;
	Image first, second, third, birdused, bird, background, wing, fly, fall, movingBottom;
	private boolean gameover= false;
	private boolean gameoversequence= false;
	private boolean tap= true;
	private int pipeheight, pipewidth, bottompipeh, pipelocy,pipecounter;
	private boolean start = false;
	private boolean hasstarted= false;
	private int pipelocx = 700;
	private String theme= new String("normal");
	int yVelocity = 0;
	int yVelocityUpdate = 10;
	public void run (){
			try{
				while(true){
					startsequence();
				while(start){
					yVelocity -= 1;
					update();
					pipes();
					collision();
					gameoveraction();
					Thread.sleep(45);
				}
				}
			}
			catch(Exception e) {
				System.out.println("Error");
			}
			
		}
	public FlappyScreen() {
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
		ImageIcon image0 = new ImageIcon("src/Flappybird/back.png");
		background= image0.getImage();
		ImageIcon image1 = new ImageIcon("src/Flappybird/Flappy_Bird.png");
		bird = image1.getImage();
		ImageIcon image2 = new ImageIcon("src/Flappybird/Wing.png");
		wing = image2.getImage();
		ImageIcon image3 = new ImageIcon("src/Flappybird/flying.png");
		fly = image3.getImage();
		ImageIcon image4 = new ImageIcon("src/Flappybird/falling.png");
		fall = image4.getImage();
		ImageIcon image5 = new ImageIcon("src/Flappybird/sprite1.png");
		first = image5.getImage();
		ImageIcon image6 = new ImageIcon("src/Flappybird/sprite2.png");
		second = image6.getImage();
		ImageIcon image7 = new ImageIcon("src/Flappybird/sprite3.png");
		third = image7.getImage();
		birdused=bird;
		Random one= new Random();
		bottompipeh= one.nextInt(400) + 325;
		pipeheight= (SCREENY- 120) -bottompipeh;
		pipewidth= 50;
		}
		
	}
	public void startsequence() throws InterruptedException{
		birdused= first;
		Thread.sleep(155);
		birdused= second;
		Thread.sleep(155);
		birdused= third;
		Thread.sleep(155);
		birdused= second;
		Thread.sleep(155);
	}
	public void pipes(){
		pipelocx -=10 ;
		Random two= new Random();
		if (pipelocx <= 0){
			pipelocx = SCREENX;
			pipecounter++;
			bottompipeh= two.nextInt(400) + 325;
			pipeheight= (SCREENY- 120) -bottompipeh;
		}
	}
	public void collision(){
//		if (Charlocy <= pipeheight && (Charlocx >pipelocx || Charlocx + 30 == pipelocx)&& Charlocx<pipelocx+pipewidth){
//			gameover= true;
//			Charlocy = 310;
//			
//			Charlocx= pipelocx;
//			
//		}
		if (Charlocy <= pipeheight - 2 &&  (Charlocx + 30 >= pipelocx && Charlocx <= pipelocx+pipewidth-3)){
			gameover=true;
		}
		if (Charlocy+ 30 >= SCREENY-bottompipeh + 3 &&  (Charlocx + 30 >= pipelocx && Charlocx <= pipelocx+pipewidth-3)){
			gameover=true;
		}

		if (Charlocy >= SCREENY){
			Charlocy= 0;
			
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
	g.drawImage(birdused,SCREENX/2- 10 , SCREENY/2 - 175, this);
}
else if (hasstarted){
	g.drawImage(birdused, Charlocx ,Charlocy, this);
	g.setColor(Color.green);
    g.fillRect(pipelocx, pipelocy, pipewidth, pipeheight);
    g.fillRect(pipelocx, SCREENY- bottompipeh, pipewidth, bottompipeh);
    g.setColor(Color.GRAY);
    Font joshone= new Font("Times new roman", Font.PLAIN, 90);
    g.setFont(joshone);
    g.drawString(""+pipecounter, SCREENX/2, 100);
    if (gameoversequence){

    	start=false;
    	Charlocy= SCREENY/2;
    	Charlocx= 100;
    	pipecounter= 0;
    	pipelocx=SCREENX;
    	gameoversequence=false;
    	gameover= false;
    	hasstarted= false;
    	Thread.sleep(1500);
    }
   }
	repaint();
	}
	public void update() {
	Charlocy -= yVelocity;
		
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
				yVelocity = yVelocityUpdate;
				update();
				birdused=first;
			tap= false;
			
			} 
		    update();
		}
	}
	public void keyReleased(KeyEvent e){
		int KeyCode = e.getKeyCode();
		if (KeyCode == e.VK_SPACE){
		    birdused= second;
			update();
			tap= true;
		}

	}
}
	public static void main(String[] args){
	FlappyScreen josh= new FlappyScreen();
	Thread t1= new Thread(josh);
	t1.start();
}
    }