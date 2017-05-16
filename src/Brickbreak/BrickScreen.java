package Brickbreak;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
	private int BRICKH, BRICKW;
	private boolean done;
	private Image dbImage;
	private Graphics dbg;
	private boolean finaldone;
	private int ballx, bally;
	Brick [][] bricks= new Brick[20][4];
	private int ballchangex;
	private int ballchangey;
	public BrickScreen() {
		// TODO Auto-generated constructor stub
		addKeyListener( new AL()); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SCREENX= 600;
		SCREENY= 700;
		setTitle("Brick Break");
		setBackground(Color.CYAN);
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(false);
	    paddlex= SCREENX/2- 60;
	    BRICKH= 30;
	    BRICKW=30;
	    paddlew= 125;
	    ballx=SCREENX/2- 20;
	    ballchangex= 1;
	    bally= SCREENY- 70;
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
		g.setColor(Color.GRAY);
		g.fillRect(paddlex, SCREENY- 50, paddlew, 10);
	    g.setColor(Color.darkGray);
		g.fill3DRect(paddlex, SCREENY-50, paddlew, 10, false);
		g.setColor(Color.red);
		
		for (int i=0; i<bricks.length; i++){
			for (int j=0; j<bricks[i].length; j++){
				if (!bricks[i][j].isAlive()){
					g.setColor(Color.green);
					g.fillRect(i*BRICKH , j*BRICKW+ 15, BRICKW, BRICKH);
					g.setColor(Color.BLACK);
					g.draw3DRect(i*BRICKH, j*BRICKW+ 15, BRICKW, BRICKH, true);
				}
				bricks[i][j].setX(i*BRICKH);
				bricks[i][j].setY(j*BRICKW +15);
			}
			
		}
		g.setColor(Color.red);
		g.fillOval(ballx, bally, 20, 20);
		if (finaldone){
			g.drawString("GAME OVER", SCREENX/2, SCREENY/2);
			Thread.sleep(8000);
			paddlex= SCREENX/2- 60;
		    BRICKH= 75;
		    BRICKW=75;
		    paddlew= 125;
		    ballx= 290;
		    ballchangex= 0;
		    ballchangey= 0;
		    for (int i=0; i<bricks.length; i++){
				for (int j=0; j< bricks[i].length; j++){
					bricks[i][j].setbool(false);
				}
			}
		    ballchangey= 1;
		    bally= 350;
		    finaldone= false;
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
			     pchange= -2;
			}
			if (KeyCode == e.VK_RIGHT){
			     pchange=2;
				
		}
		}
		public void keyReleased(KeyEvent e){
			int KeyCode = e.getKeyCode();
			if (KeyCode == e.VK_LEFT || KeyCode == e.VK_RIGHT){
		        pchange=0;
			}
		}
	}
	public void fillBricks(){
		for (int i=0; i<bricks.length; i++){
			for (int j=0; j< bricks[i].length; j++){
				bricks[i][j]= new Brick();
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
	public void ballHit(){
		if (((ballx<= paddlex+ paddlew) && (ballx + 20 >= paddlex)) && bally + 20 >= SCREENY- 50 && bally <= SCREENY- 50 + 10){
			ballchangey= -ballchangey;
			
			if (ballx+20 > paddlex && ballx<paddlex+paddlew/2 -45){
				ballchangex= 1;
			}
			else if (ballx <= paddlex+paddlew && ballx > paddlex+ paddlew/2 + 20){
				ballchangex= -1;
			}
			else {
				ballchangex= 0;
			}
		}
		if (ballx < 0){
			ballx=0;
			ballchangex =-ballchangex;
		}
		if (ballx > SCREENX){
			ballx= SCREENX;
			ballchangex =-ballchangex;
		}
		if (bally < 0){
			bally=0;
			ballchangey = -ballchangey;
			ballchangex= -ballchangex;
		}
		if (bally> SCREENY){
			paddlex= SCREENX/2- 60;
		    BRICKH= 75;
		    BRICKW=75;
		    paddlew= 125;
		    ballx= 290;
		    ballchangex= 0;
		    ballchangey= 0;
		    for (int i=0; i<bricks.length; i++){
				for (int j=0; j< bricks[i].length; j++){
					bricks[i][j].setbool(false);
				}
			}
		    ballchangey= 1;
		    bally= 350;
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
					bricks[i][j].Break();
					ballchangey = 1;
					ballchangex= -ballchangex; 
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
		    fillBricks();
		    ballchangey= -1;
		    Thread.sleep(2000);
			while(true){
			ballMove();
			ballHit();
			brickBreak();
		    movePaddle();
		    pboundaries();
			Thread.sleep(3);
			}
		}
		catch(Exception e) {
			System.out.println("Error");
		}
		
	}
}
