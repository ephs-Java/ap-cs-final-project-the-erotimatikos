package Menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Brickbreak.BrickScreen;
import FlappyBird.FlappyScreen;
import RetroPong.Pong;
import TicTackToe.Screen;
import minesweeper.MineScreen;
import pacman.PacMenu;
public class Menu extends JFrame implements Runnable {
    private int SCREENX, SCREENY;
	private Image dbImage;
	private Image minesweeper, flappy, pong, tic, pac, breakout;
	private Graphics dbg;
    private int MOUSEX, MOUSEY;
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
		}
		addMouseListener(new mouse());
		SCREENX= 1000;
		SCREENY= 1000;
		setTitle("Game library");
		setBackground(Color.darkGray);
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(false);
	
		//everything beyond here is images.
	}
public class mouse extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			MOUSEX = e.getX();
			MOUSEY = e.getY();
			System.out.println(MOUSEX + " , "+MOUSEY);
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
	
	public void gameStarter(){
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
				System.out.println("Pac man");
				PacMenu avery= new PacMenu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if ((MOUSEX>750 && MOUSEX<970) && (MOUSEY>150 && MOUSEY<500)){
			MOUSEX=0;
			MOUSEY=0;
			BrickScreen ok= new BrickScreen();
		}
	}
	
	public void paintComponent(Graphics g) throws InterruptedException {
		g.fillRect(50, 50, 210, 200);
		g.setColor(Color.WHITE);
		g.fillRect(55, 55, 200, 190);
		g.drawImage(minesweeper, 70, 60, this);
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
				gameStarter();
				Thread.sleep(3);
			}
		}
		catch(Exception e) {
				
			System.out.println("Error");
		}
		

		
		
	}

}
