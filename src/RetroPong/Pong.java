package RetroPong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Pong extends JFrame implements Runnable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int onex, oney, oneh, onew;
    private int twox, twoy, changex, changey;
    private int change= 0;
    private int aidiff;
    private int leftscore, rightscore;
	private int SCREENX;
	private int SCREENY;
	private int ballx = SCREENX/2;
	private int bally = SCREENY/2;
	private int MOUSEX;
	private int MOUSEY;
	private Image dbImage, one, two, easy, hard, medium, pong;
	private Graphics dbg;
	private boolean start;
	private boolean twoplayer;
	private boolean other;
	private boolean info;
	private boolean finalstart;
	private int changeb;
	public Pong() {
		
		// TODO Auto-generated constructor stub
		addKeyListener( new AL()); 
		addMouseListener( new mouse());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SCREENX= 700;
		SCREENY= 500;
		setTitle("Pong");
		setBackground(Color.BLACK);
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(false);
		ImageIcon uno= new ImageIcon("src/RetroPong/1player.png");
		one= uno.getImage();
		ImageIcon dos= new ImageIcon("src/RetroPong/2player.png");
		two= dos.getImage();
		ImageIcon tres= new ImageIcon("src/RetroPong/easy.png");
		easy= tres.getImage();
		ImageIcon cuatro= new ImageIcon("src/RetroPong/medium.png");
		medium= cuatro.getImage();
		ImageIcon cinco= new ImageIcon("src/RetroPong/hard.png");
		hard= cinco.getImage();
		ImageIcon seis= new ImageIcon("src/RetroPong/pong.png");
		pong= seis.getImage();
		onex= 25;
		oney= 250;
		oneh= 80;
		onew= 20;
		twox= SCREENX-25-onew;
		twoy= 250;
		ballx= SCREENX/2;
		start= false;
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
		if (!start){
			g.setColor(Color.black);
			g.fillRect(0, 0, SCREENX, SCREENY);
			g.drawImage(pong, SCREENX/2 -150, 20, this);
			g.drawImage(one, 150, SCREENY/2 - 50, this);
			g.drawImage(two, 150, SCREENY/2  +50, this);
		} 
		else if (info){
			g.setColor(Color.black);
			g.fillRect(0, 0, SCREENX, SCREENY);
			g.setColor(Color.WHITE);
			g.drawString("Player 1 uses UP and DOWN keys to move", 100, 200);
			g.drawString("Player 2 uses W and S keys to move", 100, 300);
			g.drawString("Press Space to continue", 100, 400);
		}
		else {
		g.setColor(Color.white);
		g.fillRect(onex, oney, onew, oneh);
		g.fillRect(twox, twoy, onew, oneh);
		if (leftscore > 10){
			g.setColor(Color.GREEN);
		}
		g.fillOval(ballx, bally, 30, 30);
		Font joshone= new Font("Times new roman", Font.PLAIN, 90);
		g.setColor(Color.white);
	    g.setFont(joshone);
	    if (leftscore < 10){
	    	g.drawString(leftscore+"", 277, 105);
	    } else {
	    	g.drawString(leftscore+"", 247, 105);
	    }
		
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
		}
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
		if(twoy <= 24){
			changeb=0;
			twoy = 25;
	}
		if (twoy + oneh >= SCREENY){
			twoy= SCREENY-oneh;
}
		if (ballx <= onex + onew && (bally<= oney+oneh && bally + 30 >= oney)){
			ballx= onex+onew+1;
			changex= -changex;
			 if (bally +30 >= oney && bally +30 <= oney+ oneh/2 ){
		    	   changey++;
		    	   changey= -changey;
		    	
		     }
			 else if (bally > oney +oneh/2 && bally <= oney +oneh){
				 changey--;
		    	   changey= -changey;
		    	
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
		if (twoplayer){
			twoy += changeb;
		}
	}
	public void Ymove(int a){
	change= a;
	}
	public class mouse extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			MOUSEX = e.getX();
			MOUSEY = e.getY();
			if (!start){
			    if (MOUSEY >= SCREENY/2  +50){
			    	twoplayer= true;
			    	info= true;
			    	start= true;
			    } else{
			    	aidiff= 4;
			    	start= true;
			    	twoplayer= false;
			    	finalstart= true;
			    }
			}
		     
			}
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
			if (KeyCode == e.VK_SPACE){
				finalstart= true;
				info= false;
			}
			if (twoplayer){
				if (KeyCode == e.VK_W){
					changeb= -3;
				}
				if (KeyCode == e.VK_S){
					changeb = 3;
				}
			}
		}
	
		public void keyReleased(KeyEvent e){
			int KeyCode = e.getKeyCode();
			if (KeyCode == e.VK_UP || KeyCode == e.VK_DOWN){
			Ymove(0);
			}
			if (twoplayer){
				if (KeyCode == e.VK_W){
					changeb= 0;
				}
				if (KeyCode == e.VK_S){
					changeb = 0;
				}
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
		if (bally < twoy){
			twoy -=aidiff;
		
		}
		if (bally > twoy){
			twoy +=aidiff;
	
		} 
		 if(twoy <= 24){
			twoy = 25;
		
	}
		if (twoy + oneh >= SCREENY){
			twoy= SCREENY-oneh - 2;
			twoy +=2;
}
		if (bally < twoy -100){
			twoy -=6;
		}
		if (bally > twoy +100){
			twoy +=6;
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
		    	while(!start){
		    		Thread.sleep(30);
		    	}
		    	while(!finalstart){
		    		Thread.sleep(30);
		    	}
			ballX(-2);
			Thread.sleep(1900);
			if (!twoplayer){
				changeb= 0;
			}
			aidiff= 3;
			while(true){
			collision();
		    if (!twoplayer){
			ai();
		    }
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
