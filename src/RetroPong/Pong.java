package RetroPong;

import java.awt.Color;
import java.awt.Font;
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
    private int twox, twoy, changex, changey;
    private int change= 0;
    private int leftscore, rightscore;
	private int SCREENX;
	private int SCREENY;
	private int ballx = SCREENX/2;
	private int bally = SCREENY/2;
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
		ballx= SCREENX/2;
		bally= SCREENY/2;
	}
	public void Scorer() throws InterruptedException{
		if (ballx <= 0){
			rightscore ++;
		    Thread.sleep(900);
		    ballx = SCREENX/2;
		    bally= SCREENY/2;
		    changex= -2;
		    changey=0;
		    oney= 250;
		    twoy=250;
		    Thread.sleep(900);
		}
		    else if (ballx + 30 >= SCREENX){
			leftscore ++;
	        Thread.sleep(900);
	    	ballx = SCREENX/2;
	    	bally= SCREENY/2;
	    	changex=-2;
	    	changey=0;
	    	oney= 250;
	    	twoy =250;
	    	Thread.sleep(900);
		    }
		if (bally <=30){
			bally=30;
			changey = -changey;
		}
		else if (bally >=SCREENY-30){
			bally=SCREENY- 30;
			changey = -changey ;
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
		g.setColor(Color.white);
		g.fillRect(onex, oney, onew, oneh);
		g.fillRect(twox, twoy, onew, oneh);
		g.fillOval(ballx, bally, 30, 30);
		Font joshone= new Font("Times new roman", Font.PLAIN, 90);
	    g.setFont(joshone);
		g.drawString(leftscore+"", 277, 105);
		g.drawString(rightscore+"", 378, 105);
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
		if(oney <= 24){
			Ymove(0);
			oney = 25;
	}
		if (oney + oneh >= SCREENY){
			oney= SCREENY-oneh;
}
		if (ballx <= onex + onew && (bally<= oney+oneh && bally + 30 >= oney)){
			ballx= onex+onew+1;
			changex= -changex;
			 if (bally +30 >= oney && bally +30 <= oney+ oneh/2 ){
		    	   changey++;
		    	   changey= -changey;
		    	 System.out.println("a");
		     }
			 else if (bally > oney +oneh/2 && bally <= oney +oneh){
				 changey--;
		    	   changey= -changey;
		    	 System.out.println("c");
		     }
			if (changex < 3){
			changex ++;
			} else {
				changex--;
			}
		    
		}
		if (ballx+ 33 >= twox  && (bally<= twoy+oneh && bally + 30 >= twoy)){
			changex= -changex;
			ballx= twox-30;
			if (changex < 3){
				changex --;
				} else {
					changex++;
				}
			 if (bally +30 >= twoy && bally +30 <= twoy+ oneh/2 ){
		    	   changey++;
		    	   changey= -changey;
		    	 System.out.println("a");
		     }
			 else if (bally > twoy +oneh/2 && bally <= twoy +oneh){
				 changey--;
		    	   changey= -changey;
		    	 System.out.println("c");
		     }
		}
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
				Ymove(-3);
			 
			}
			if (KeyCode == e.VK_DOWN){
			     Ymove(3);
			}
		}
	
		public void keyReleased(KeyEvent e){
			int KeyCode = e.getKeyCode();
			if (KeyCode == e.VK_UP || KeyCode == e.VK_DOWN){
			Ymove(0);
			}
		}
	}
	public void ballmove() throws InterruptedException{
		ballx+= changex;
		bally+= changey;
		Thread.sleep(1);
	}
	public void ballX(int a){
		changex= a;
	}
	public void ballY(int b){	
		changey= b;
	}
	public void ai() throws InterruptedException{
		if (changey >0){
			if (bally < twoy){
			twoy-= 2;
			} else {
			twoy +=2;
			}
		}
		if (changey< 0){
			if (bally > twoy){
			twoy+= 2;
			} else {
				twoy -= 2;
			}
		}
		 if(twoy <= 24){
			twoy = 25;
			twoy-= 2;
	}
		if (twoy + oneh >= SCREENY){
			twoy= SCREENY-oneh - 2;
			twoy +=2;
}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         Pong ping= new Pong();
         Thread t2= new Thread(ping);
         t2.start();
	}

	@Override
	public void run (){
		try{
			ballX(-2);
			Thread.sleep(1900);
			while(true){
			collision();
			ai();
			move();
			Scorer();
			ballmove();
			Thread.sleep(3);
			}
		}
		catch(Exception e) {
			System.out.println("Error");
		}
		
	}
}
