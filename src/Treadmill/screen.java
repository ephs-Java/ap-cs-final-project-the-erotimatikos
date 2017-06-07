package Treadmill;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//avery was here


public class screen extends JFrame implements Runnable {

	int x, y,xDirect,yDirect, yh,xh,xr,yr,xt,yt,xc,yc;


	private Graphics dbg;;
	private Image dbImage;

	private Image bike;
	private Image road;
	private Image hobo;
	private Image rat;
	private Image car;
	private Image tanker;
	private Image train;
	private Image coin;
	
	
	
	private boolean gameover = false;
	
	
	double time = 0;
	double stime = 1;
	
	int numTimeErroFix =0;
	int level = 0;
	int speed = 4;
	int numOfCoins = 0;

	
	
	public screen(){
		xr = 150;
		yr = 10;
		x = 100;
		y=100;
		ImageIcon tank = new ImageIcon("src/Treadmill/road.jpeg");
		road = tank.getImage();
		ImageIcon biker = new ImageIcon("src/Treadmill/biker2.png");
		bike = biker.getImage();
		ImageIcon hobot = new ImageIcon("src/Treadmill/hobo.png");
		hobo = hobot.getImage();
		ImageIcon ratt = new ImageIcon("src/Treadmill/rat.png");
		rat = ratt.getImage();
		ImageIcon carr = new ImageIcon("src/Treadmill/car.png");
		car = carr.getImage();
		ImageIcon tankk = new ImageIcon("src/Treadmill/bigTank.png");
		tanker = tankk.getImage();
		ImageIcon trainn = new ImageIcon("src/Treadmill/train.png");
		train = trainn.getImage();
		ImageIcon coinn = new ImageIcon("src/Treadmill/coin2.png");
		coin = coinn.getImage();
		
		


		addKeyListener(new AL());
		setTitle("TreadMill");
		setSize(470,240);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.green); //Sets the background color to blue.
		
		


	}

	

	public static void main(String[] args){
		screen runner = new screen();
		Thread t = new Thread(runner);
		t.start();

	}
	
	public void run(){
		try{
			while(true){
				move();
				if(time < .5){
				Thread.sleep(100);
				moveHobo();
			
				}else{
					moveHobo();
				}
				if(level >= 1){
				moveRat();
				moveCoin();
			}
				if(level >= 2){
					moveTank();
				}if((int)time == 30){
					speed = 3;
				}
				if((int)time == 40){
					speed = 2;
				}
				if((int)time >= 60){
					speed = 1;
				}
				if(Math.abs(y - yh) < 20 && Math.abs(x - xh) < 10){
					gameover = true;
				}
				if(Math.abs(y - yr) < 20 && Math.abs(x - xr) < 10){
					gameover = true;
				}
				if(Math.abs(y - yt) < 20 && Math.abs(x - xt) < 10){
					gameover = true;
				}
				if(Math.abs(y - yt) < 20 && Math.abs(x - xt) < 10){
					gameover = true;
				}
				if(Math.abs(y - yc) < 10 && Math.abs(x - xc) < 10){
					numOfCoins++;
				}
				if(10 * stime < time){
					stime++;
					x += 100;
					level++;
					if(level == 1){
						bike = car;
					}if(level == 2){
						bike = train;
					}
				}
				
				if(gameover && numTimeErroFix == 0){
					numTimeErroFix++;
					Thread.sleep(100);
					String inputString = JOptionPane.showInputDialog(null, "Enter your name for the leaderboard");
			      
			        System.out.println("User input: " + inputString);
			        Leader me = new Leader(inputString,numOfCoins);
			        advLeaderboard board = new advLeaderboard();
			        board.addLeader(me);
			        board.writeLeaderboard();
			       JOptionPane.showMessageDialog(null,board.dog());
			        
				}
				Thread.sleep(10);
				
			}
		}catch(Exception e){
			System.out.println("Error");
			System.out.println(e.getMessage());
		}
	}


	public void moveHobo(){
		xh-= 4;
		
		if(xh <0){
			xh = 470;
			yh = ((int) (Math.random() * 240));
		}
		
	}
	public void moveRat(){
		xr-= 4;
		
		if(xr <0){
			xr = 470;
			yr = ((int) (Math.random() * 240));
		}
		
	}
	public void moveCoin(){
		xc-= 4;
		
		if(xc <0){
			xc = 470;
			yc = ((int) (Math.random() * 240));
		}
		
	}
	public void moveTank(){
		xt-= 4;
		
		if(xt <0){
			xt = 470;
			yt = ((int) (Math.random() * 240));
		}
		
	}
	public void move(){

		y+= yDirect;
		if(y> 200){
			y = 200;
			
		}if(y<20){
			y =20;
		}

	}

	
	public void setYDir(int YDir){
		yDirect = YDir;
	}

	public class AL extends KeyAdapter{

		public void keyPressed(KeyEvent e){
			int code = e.getKeyCode();
		
			if(code == e.VK_UP){
				setYDir(-speed);
			}
			if(code == e.VK_DOWN){
				setYDir(speed);
			}



		}
		public void keyReleased(KeyEvent e){
			int code = e.getKeyCode();
		
			if(code == e.VK_UP){
				setYDir(0);
			}
			if(code == e.VK_DOWN){
				setYDir(0);
			}

		}
	}
	public void paint(Graphics g){
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	 
	
	public void paintComponent(Graphics g){
	
		if(!gameover){
			time+= .001;
			g.drawImage(road, 0, 40, this);
			g.drawString("Time " + (int)time + " You are at level " + level + "					You have collected "+numOfCoins/3 + " number of coins",10,30);
		g.setColor(Color.red);
		g.drawImage(bike, x, y, this);
		g.drawImage(coin,xc,yc,this);
		
		g.drawImage(hobo, xh, yh, this);
		
		if(level >= 1){
		g.drawImage(rat,xr,yr,this);
		}
		if(level >= 2){
		g.drawImage(tanker,xt,yt,this);
		}
		
			
		
		}
		else{
			g.drawString("You have hit something and lasted "+ (int)time + " seconds", 100, 100);
			
		}
      repaint();
	}


}
