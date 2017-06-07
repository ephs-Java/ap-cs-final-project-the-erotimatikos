package Menu;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import Brickbreak.BrickScreen;
import FlappyBird.FlappyScreen;
import MarioGameTestingPlatForm.marioScreen;
import RetroPong.Pong;
import Runner.screen;
import TicTackToe.Screen;
import minesweeper.MineScreen;
import pacman.PacMenu;
import pacman.Sound;
public class Menu extends JFrame implements Runnable {
    private int SCREENX, SCREENY;
	private Image dbImage;
	private Image minesweeper, flappy, pong, tic, pac, breakout, mario;
	private Graphics dbg;
    private int MOUSEX, MOUSEY;
    private int creditLoc= 580;
    private int ballMom= 5;
    private int ballsize= 25; 
    private int ballLocY;
	private Image runner;
	private Image credit;
	private Font creditFont= new Font("Courier", 62, Font.PLAIN);
	Sound creditmusic = new Sound();
	private boolean creditsequence= false;
	private Image ms;
	private boolean alex= false;
	private Image f;
	private Image Ashton;
	private Image Avery;
	private Image M;
	private Image P;
	private Image created;
	private Image Josh;
	private int  ballMomx= 3;
	private int ballLocX = 250;
	private Image man;
	private Image T;
	private Image bout;
	private boolean exit;
	private Image click;
	private Image noise;
	private Image tm;
	public Menu() {
		// TODO Auto-generated constructor stub
		ImageIcon image0= new ImageIcon("src/Menu/minesweeper.png");
		minesweeper= image0.getImage();
		ImageIcon image1= new ImageIcon("src/Menu/Flappy_Bird_icon.png");
		flappy= image1.getImage();
		ImageIcon image2= new ImageIcon("src/Menu/pong.png");
		pong= image2.getImage();
		ImageIcon image3= new ImageIcon("src/Menu/tic.png");
		tic= image3.getImage();
		ImageIcon image4= new ImageIcon("src/Menu/pacman.png");
		pac= image4.getImage();
		ImageIcon image5= new ImageIcon("src/Menu/breakout_boost_ios.png");
		breakout= image5.getImage();
		ImageIcon image6= new ImageIcon("src/Menu/mario.png");
		mario= image6.getImage();
		ImageIcon image7= new ImageIcon("src/Menu/run.png");
		runner= image7.getImage();
		// CREDIT IMAGES
		ImageIcon image8= new ImageIcon("src/Menu/credits.png");
		credit= image8.getImage();
		ImageIcon image9= new ImageIcon("src/Menu/ms.png");
		ms= image9.getImage();
		ImageIcon image10= new ImageIcon("src/Menu/flappybird.png");
		f= image10.getImage();
		ImageIcon image11= new ImageIcon("src/Menu/ashton.png");
		Ashton = image11.getImage();
		ImageIcon image13= new ImageIcon("src/Menu/avery.png");
		Avery= image13.getImage();
		ImageIcon image14= new ImageIcon("src/Menu/M.png");
		M= image14.getImage();
		ImageIcon image15= new ImageIcon("src/Menu/p.png");
		P= image15.getImage();
		ImageIcon image16= new ImageIcon("src/Menu/created.png");
		created= image16.getImage();
		ImageIcon image17= new ImageIcon("src/Menu/josh.png");
		Josh= image17.getImage();
		ImageIcon image18= new ImageIcon("src/Menu/pman.png");
		man= image18.getImage();
		ImageIcon image19= new ImageIcon("src/Menu/tag.png");
    	T= image19.getImage();
		ImageIcon image20= new ImageIcon("src/Menu/bout.png");
		bout= image20.getImage();
		ImageIcon image21= new ImageIcon("src/Menu/ex.png");
		click= image21.getImage();
		ImageIcon image22= new ImageIcon("src/Menu/treadmill.png");
		tm= image22.getImage();
		ImageIcon image23= new ImageIcon("src/Menu/sound.png");
		noise= image23.getImage();
		addMouseListener(new mouse());
		SCREENX= 1370;
		SCREENY= 700;
		setTitle("Game library");
		setBackground(Color.blue);
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(false);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		//everything beyond here is images.
	}
    public class mouse extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			MOUSEX = e.getX();
			MOUSEY = e.getY();
			try {
				gameStarter();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
}
    public void paint(Graphics g) {
		//dbimage serves for constantly updating images.
		//dbimage works by creating and printing a blank image, erasing previous paint that continously updates.
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		
			try {
				paintComponent(dbg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				
			}
		
		g.drawImage(dbImage, 0, 0, this);
	}
	public void gameStarter() throws FileNotFoundException{
		if (!creditsequence){
		if ((MOUSEX>50 && MOUSEX<270) && (MOUSEY>50 && MOUSEY<270)){
			MineScreen josh= new MineScreen();
			MOUSEX=0;
			MOUSEY=0;
		}
		if ((MOUSEX>400 && MOUSEX<620) && (MOUSEY>50 && MOUSEY<270)){
			MOUSEX=0;
			MOUSEY=0;
			FlappyScreen josh= new FlappyScreen();
			Thread t1= new Thread(josh);
			t1.start();
			
		}
		if ((MOUSEX>750 && MOUSEX<970) && (MOUSEY>50 && MOUSEY<270)){
			MOUSEX=0;
			MOUSEY=0;
			Screen ok= new Screen();
		}
		if ((MOUSEX>50 && MOUSEX<270) && (MOUSEY>150 && MOUSEY<500)){
			MOUSEX=0;
			MOUSEY=0;
			Pong josh= new Pong();
			Thread t1= new Thread(josh);
			t1.start();
		}
		if ((MOUSEX>400 && MOUSEX<620) && (MOUSEY>150 && MOUSEY<500)){
			MOUSEX=0;
			MOUSEY=0;
			try {
				
				PacMenu avery= new PacMenu();
				Thread t1 = new Thread(avery);
				t1.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if ((MOUSEX>750 && MOUSEX<970) && (MOUSEY>150 && MOUSEY<500)){
			MOUSEX=0;
			MOUSEY=0;
		    BrickScreen ping= new BrickScreen();
	         Thread t2= new Thread(ping);
	         t2.start();
			
		}
		if ((MOUSEX>1100 && MOUSEX<1320) && (MOUSEY>50 && MOUSEY<270)){
			marioScreen game = new marioScreen();
			Thread t = new Thread(game);
			t.start();
			MOUSEX=0;
			MOUSEY=0;
		}
		if ((MOUSEX>1100 && MOUSEX<1320) && (MOUSEY>150 && MOUSEY<500)){
			screen game = new screen();
			Thread t = new Thread(game);
			t.start();
			MOUSEX=0;
			MOUSEY=0;
		}
		if ((MOUSEX>580 && MOUSEX<780) && (MOUSEY>580 && MOUSEY<630)){
			creditsequence= true;
			creditmusic.play("src/Menu/music.wav");
			MOUSEX=0;
			MOUSEY=0;
		}
		}
		if (creditsequence) {
			if ((MOUSEX>5 && MOUSEX<105) && (MOUSEY>25 && MOUSEY<125)){
				exit= true;
                
				MOUSEX=0;
				MOUSEY=0;
			}
		}
	}
	public void paintComponent(Graphics g) throws InterruptedException {
		int counter= 0;
		if (!creditsequence){
		g.fillRect(50, 50, 210, 200);
		g.setColor(Color.WHITE);
		g.fillRect(55, 55, 200, 190);
		g.drawImage(minesweeper, 70, 60, this);
		//
		g.setColor(Color.black);
		g.fillRect(400, 50, 210, 200);
		g.setColor(Color.WHITE);
		g.fillRect(405, 55, 200, 190);
		g.drawImage(flappy, 420, 60, this);
		//
		g.setColor(Color.black);
		g.fillRect(750, 50, 210, 200);
		g.setColor(Color.WHITE);
		g.fillRect(755, 55, 200, 190);
		g.drawImage(tic, 770, 60, this);
		//
		g.setColor(Color.black);
		g.fillRect(50, 300, 210, 200);
		g.setColor(Color.WHITE);
		g.fillRect(55, 305, 200, 190);
		g.drawImage(pong, 70, 310, this);
		//
		g.setColor(Color.black);
		g.fillRect(400, 300, 210, 200);
		g.setColor(Color.WHITE);
		g.fillRect(405, 305, 200, 190);
		g.drawImage(pac, 420 , 310, this);
		//
		g.setColor(Color.black);
		g.fillRect(750, 300, 210, 200);
		g.setColor(Color.WHITE);
		g.fillRect(755, 305, 200, 190);
		g.drawImage(breakout, 770 , 310, this);
		//
		g.setColor(Color.black);
		g.fillRect(1100, 50, 210, 200);
		g.setColor(Color.WHITE);
		g.fillRect(1105, 55, 200, 190);
		g.drawImage(mario, 1118, 63, this);
		//
		g.setColor(Color.black);
		g.fillRect(1100, 300, 210, 200);
		g.setColor(Color.WHITE);
		g.fillRect(1105, 305, 200, 190);
		g.drawImage(runner, 1120 , 310, this);
		//
		g.setColor(Color.black);
		g.fillRect(580, 580, 200, 50);
		g.drawImage(credit, 580 , 580, this);
		}
		else {
			g.setColor(Color.black);
			g.fillRect(0, 0, SCREENX, SCREENY);
			g.setFont(creditFont);
			g.setColor(Color.white);
			g.setColor(Color.black);
			g.fillRect(580, 580, 200, 50);
			g.drawImage(credit, 580 ,creditLoc, this);
			//MINESWEEPER
			g.drawImage(ms, 380 ,creditLoc+ 200, this);
			g.drawImage(created, 380 ,creditLoc + 400, this);
			g.drawImage(Avery, 580 ,creditLoc + 550, this);
			g.drawImage(Ashton, 380 ,creditLoc+ 750, this);
			g.drawImage(Josh, 580 ,creditLoc + 950, this);
			// PONG 
			g.drawImage(P, 380 ,creditLoc+ 1200, this);
			g.drawImage(created, 380 ,creditLoc + 1400, this);
			g.drawImage(Josh, 580 ,creditLoc + 1550, this);
			// PACMAN
			g.drawImage(man, 380 ,creditLoc+ 1900, this);
			g.drawImage(created, 380 ,creditLoc + 2100, this);
			g.drawImage(Avery, 580 ,creditLoc + 2250, this);
			// MARIO
			g.drawImage(M, 380 ,creditLoc+ 2500, this);
			g.drawImage(created, 380 ,creditLoc + 2700, this);
			g.drawImage(Ashton, 580 ,creditLoc + 2850, this);
			// FLAPPY BIRD
			g.drawImage(f, 380 ,creditLoc+ 3100, this);
			g.drawImage(created, 380 ,creditLoc + 3300, this);
			g.drawImage(Josh, 580 ,creditLoc + 3450, this);
			// Tag
			g.drawImage(T , 380 ,creditLoc+ 3700, this);
			g.drawImage(created, 380 ,creditLoc + 3900, this);
			g.drawImage(Ashton, 580 ,creditLoc + 4050, this);
			// BREAKOUT
			g.drawImage(bout, 380 ,creditLoc+ 4300, this);
			g.drawImage(created, 380 ,creditLoc + 4500, this);
			g.drawImage(Josh, 580 ,creditLoc + 4650, this);
			// Sound
			g.drawImage(noise, 380 ,creditLoc+ 4900, this);
			g.drawImage(created, 380 ,creditLoc + 5100, this);
			g.drawImage(Avery, 580 ,creditLoc + 4250, this);
			//TREADMILL
			g.drawImage(tm, 380 ,creditLoc+ 4900, this);
			g.drawImage(created, 380 ,creditLoc + 5100, this);
			g.drawImage(Ashton, 580 ,creditLoc + 4250, this);
			//
			if (creditLoc + 5000 < 0   || exit){
				creditsequence= false;
			    creditmusic.stop();
				creditLoc= 580;
				ballMom= 5;
				ballLocX = 250;
				ballMomx= 3;
				exit= false;
			}
			
			g.drawImage(click, 5, 25, this);
		}
	
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Menu test= new Menu();
			Thread t1= new Thread(test);
			t1.start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(true){
				
				if (creditsequence){
				
				
					
					CreditMove();
					coolBall();
					ballMove();
					repaint();
					Thread.sleep(10);
				}
				if (!creditsequence){
				repaint();
				Thread.sleep(1000);
			}
			}
		}
			
		catch(Exception e) {
				
			System.out.println("Error");
		}
		

		
		
	}
	public void coolBall(){
		ballLocY += ballMom;
		ballLocX += ballMomx;
	}
	public void ballMove(){
		Random rand= new Random();
		int a = rand.nextInt(4) + 1;
	
		if (ballLocY + ballsize> SCREENY){
			ballLocY = SCREENY- (ballsize + 7);
			ballMom = -ballMom;
			coolBall();
			if (a == 1){
				ballMom ++;
				ballMomx --; 
			}
			else {
				ballMomx++;
				ballMomx --; 
			}
		}
		if (ballLocY < 10){
			ballLocY = 10;

			ballMom = -ballMom;
			coolBall();
			if ( a == 1){
				ballMom ++;
				ballMomx --; 
			}
			else {
				ballMomx++;
				ballMomx --; 
			}
		}
		 if (ballLocX < 0){
			 ballLocX= 1;
			 ballMomx = -ballMomx;
			coolBall();
			if ( a == 1){
				ballMom ++;
				ballMomx --; 
			}
			else {
				ballMomx++;
				ballMomx --; 
			}
		 }
		 if (ballLocX+ ballsize > SCREENX){
			 ballLocX= SCREENX - (ballsize + 1);
			 ballMomx = -ballMomx;
			coolBall();
			if ( a == 1){
				ballMom ++;
				ballMomx --; 
			}
			else {
				ballMomx++;
				ballMomx --; 
			}
		 }
		
	}
    public void CreditMove(){
    	creditLoc -= 1; 
    	try {
    		
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}