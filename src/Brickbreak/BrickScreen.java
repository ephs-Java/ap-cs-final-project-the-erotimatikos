package Brickbreak;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class BrickScreen extends JFrame implements Runnable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int SCREENX, SCREENY;
	private int paddlex;
	private int pchange;
	private int paddlew;
	private int row= 0;
	private int BRICKH, BRICKW;
	private boolean done;
	private Image dbImage;
	private Image title, paddle, end, ball;
	private Graphics dbg;
	private boolean finaldone;
	private int ballx, bally;
	Brick [][] bricks= new Brick[10][8];
	private int ballchangex;
	private int ballchangey;
	private boolean start = false;
	private Image bg;
	public BrickScreen() {
		// TODO Auto-generated constructor stub
		addKeyListener( new AL()); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SCREENX= 600;
		SCREENY= 700;
		setTitle("Brick Break");
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(false);
	    paddlex= SCREENX/2- 60;
	    BRICKH= 25;
	    BRICKW= 60;
	    paddlew= 125;
	    ballx=SCREENX/2- 20;
	    ballchangex= 1;
	    bally= SCREENY- 70;
	    ImageIcon paddlea= new ImageIcon("src/Brickbreak/paddle.png");
	    paddle= paddlea.getImage();
	    ImageIcon balla= new ImageIcon("src/Brickbreak/ball.png");
	    ball= balla.getImage();
	    ImageIcon back= new ImageIcon("src/Brickbreak/back.png");
	    bg= back.getImage();
	}
	public void paint(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		try {
			paintComponent(dbg);
		} catch (InterruptedException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(dbImage, 0, 0, this);
	}
	public void paintComponent(Graphics g) throws InterruptedException, FileNotFoundException {
		if (!start){
		 ImageIcon titlei= new ImageIcon("src/Brickbreak/title.png");
		 title= titlei.getImage();
		 ImageIcon a= new ImageIcon("src/Brickbreak/win.png");
		 end= a.getImage();
		 g.drawImage(bg, 0, 0, this);
		 g.setColor(Color.WHITE);
		 g.fillRect(SCREENX/2- 150, SCREENY/2, 300, 100);
		 g.fillRect(SCREENX/2 - 150, SCREENY/2+ 200, 300, 100);
		 g.drawImage(title, SCREENX/2 - 250, 50, this);
		 g.setColor(Color.BLACK);
		 Font font= new Font("Comic Sans MS", Font.PLAIN, 20);
		 g.setFont(font);
		 g.drawString("Use the arrow keys to control ", SCREENX/2- 140, SCREENY/2+ 40);
		 g.drawString("your paddle. ", SCREENX/2- 140, SCREENY/2+ 80);
		 g.drawString("Press \"SPACE\" to begin", SCREENX/2 - 120, SCREENY/2+ 260);
		} else {
		g.setColor(Color.BLACK);
		 g.drawImage(bg, 0, 0, this);
		g.drawImage(paddle, paddlex, SCREENY- 50, this);
		g.setColor(Color.red);
		for (int i=0; i<bricks.length; i++){
			for (int j=0; j<bricks[i].length; j++){
				if (!bricks[i][j].isAlive()){
					g.setColor(Color.green);
					g.fillRect(i*BRICKW , j*BRICKH + 22, BRICKW, BRICKH);
					g.setColor(Color.BLACK);
					g.draw3DRect(i*BRICKW, j*BRICKH+ 22, BRICKW, BRICKH, true);
				}
				bricks[i][j].setX(i*BRICKW);
				bricks[i][j].setY(j*BRICKH+ 22);
			}
			
		}
		g.drawImage(ball, ballx, bally, this);
		if (finaldone){
			g.setColor(Color.black);
			g.fillRect(0, 0, SCREENX, SCREENY);
			g.setColor(Color.white);
			g.drawImage(end, 25, 100, this);
			 paddlex= SCREENX/2- 60;
			    BRICKH= 25;
			    BRICKW= 60;
			    paddlew= 125;
			    ballx=SCREENX/2- 20;
			    ballchangex= 1;
			    bally= SCREENY- 70;
			    Random paul = new Random();
			    int a;
			    for (int i= 0; i< bricks.length; i++){
					for (int j= 0; j< bricks[row].length; j++){
						a= paul.nextInt(2) +1 ;
						bricks[i][j].setbool(false);
						if (a ==1){
							bricks[i][j].Break();
						}
					}
			    }
				
			}
		   
		}
		repaint();
		}
	public void movePaddle(){
		paddlex += pchange;
	}
	public void pboundaries (){
		if (paddlex < 0){
			paddlex=0;
		}
		if (paddlex + 125 > SCREENX){
			paddlex= SCREENX -125;
		}
	}
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int KeyCode = e.getKeyCode();
			if (KeyCode == e.VK_LEFT){
			     pchange= -5;
			}
			if (KeyCode == e.VK_RIGHT){
			     pchange=5;
		}
			if (KeyCode == e.VK_SPACE){
				start= true;
			}
		}
		public void keyReleased(KeyEvent e){
			int KeyCode = e.getKeyCode();
			if (KeyCode == e.VK_LEFT || KeyCode == e.VK_RIGHT){
		        pchange=0;
			}
			if (KeyCode == e.VK_SPACE){
				start= true;
			}
		}
	}
	public void fillBricks() {
		Random josha= new Random();
		int a;
		for (int i= 0; i< bricks.length; i++){
			for (int j= 0; j< bricks[row].length; j++){
				a= josha.nextInt(2) + 1;
				Brick josh= new Brick();
				bricks[i][j]= josh;
				if (a == 1){
					bricks[i][j].Break();
				}
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         BrickScreen ping= new BrickScreen();
         Thread t2= new Thread(ping);
         t2.start();
	}
    public void ballMove(){
    	ballx += ballchangex;
    	bally += ballchangey;
    }
	public void ballHit() throws InterruptedException, FileNotFoundException{
		if (((ballx< paddlex+ paddlew) && (ballx + 20 >= paddlex)) && bally + 20 >= SCREENY- 50 && bally <= SCREENY- 50 + 2){
			bally= SCREENY- 71;
			Thread.sleep(1);
			ballchangey= -ballchangey;
			if (ballx+20 < paddlex + 20){
				ballchangex= -2;
			}
			else if (ballx+20 <=  paddlex + 50){
				ballchangex= -1;
			}
			else if (ballx < paddlex+ 105 && ballx> paddlex+ 75){
				ballchangex= 1;
			}
			else if (ballx > paddlex + 105){
				ballchangex = 2;
			}
		}
		if (ballx < 0){
			ballx=0;
			ballchangex =-ballchangex;
		}
		if (ballx + 20 > SCREENX){
			ballx= SCREENX - 20;
			ballchangex =-ballchangex;
		}
		if (bally < 15){
			bally=15;
			ballchangey = -ballchangey;
		}
		if (bally> SCREENY){
			 paddlex= SCREENX/2- 60;
			    BRICKH= 25;
			    BRICKW= 60;
			    paddlew= 125;
			    ballx=SCREENX/2- 20;
			    ballchangex= 1;
			    bally= SCREENY- 70;
			    Random jeff= new Random();
			    int a;
			    for (int i= 0; i< bricks.length; i++){
					for (int j= 0; j< bricks[row].length; j++){
						a= jeff.nextInt(2)+ 1;
						if (a == 1){
						bricks[i][j].setbool(false);
						}
					}
			    }
		    	Thread.sleep(1500);
		}
	}
    public void brickBreak(){
    	int subx;
    	int suby;
    	done =true;
    	for (int i=0; i<bricks.length; i++){
			for (int j=0; j< bricks[i].length; j++){
				subx= bricks[i][j].getX();
				suby= bricks[i][j].getY();
				if (!bricks[i][j].isAlive()){
					done =false;
				if ((bally +20 >= suby && bally <= suby +BRICKH) && (ballx+ 20 >= subx && ballx <= subx + BRICKW)){
					if (ballx + 20 <= subx || ballx >= subx+BRICKW){
						ballchangex= -ballchangex;
						ballchangey = - ballchangey;
						ballMove();
						bricks[i][j].Break();
					} else {
					ballchangey = - ballchangey;
					ballMove();
					bricks[i][j].Break();
					}
				}
				}
			}
		}
    	if (done){
    		finaldone= true;
    	}
	}
	@Override
	public void run (){
		try{
			while(!start){
				fillBricks();
			    ballchangey= -4;
				Thread.sleep(50);
			}
		    Thread.sleep(2000);
			while(true){
			ballMove();
			ballHit();
			brickBreak();
		    movePaddle();
		    pboundaries();
		    if (finaldone){
		    	Thread.sleep(8000);
		    	finaldone=false;
		    }
			Thread.sleep(8);
			}
		}
		catch(Exception e) {
			System.out.println("Error");
		}
		
	}
}