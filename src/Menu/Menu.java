package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

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
public class Menu extends JFrame implements Runnable {
    private int SCREENX, SCREENY;
	private Image dbImage;
	private Image minesweeper, flappy, pong, tic, pac, breakout, mario;
	private Graphics dbg;
    private int MOUSEX, MOUSEY;
	private Image runner;
	private Image credit;
	private Font creditFont= new Font("Courier", 62, Font.PLAIN);
	private boolean creditsequence= false;
	public Menu() {
		// TODO Auto-generated constructor stub
		for(int i=0; i<6; i++){
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
		ImageIcon image8= new ImageIcon("src/Menu/credits.png");
		credit= image8.getImage();
		}
		addMouseListener(new mouse());
		SCREENX= 1380;
		SCREENY= 3000;
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
			try{
			g.drawString("Josh", 10, 10);
			g.fillOval(300, 300, 50, 50);
			}
			catch(Exception e){
				System.out.println("WORK.");
			}
			g.setColor(Color.black);
			g.fillRect(580, 580, 200, 50);
			g.drawImage(credit, 580 , 580, this);
		}
		repaint();
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
					Thread.sleep(300);
				}
				if (!creditsequence){
				gameStarter();
				Thread.sleep(3);
			}
			}
		}
			
		catch(Exception e) {
				
			System.out.println("Error");
		}
		

		
		
	}

}
