package FlappyBird;

// "imports" methods from other classes.


// gets things like Color, font, pictures, graphics , and painting methods.
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
// the start of my new class "Flappy Screen"

//extends JFrame, inheriting the data contained in the JFrame class. (creates a popup window)

//implements "runnable", allowing me to use threads.

public class FlappyScreen extends JFrame implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	// Fields, or "private variables".
	// These can only be accessed through the methods in this class.
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
	//The try/catch loop that the thread continuously runs 
	public void run (){
			try{
				while(true){
					// while the game has not started
					startsequence();
				while(start){
					//when the game starts, these run nearly simultaneously
					yVelocity -= 1;
					update();
					pipes();
					collision();
					gameoveraction();
					//Thread.sleep allows the thread to pause for 45 milliseconds every time so it doesn't run insanely fast.
					Thread.sleep(45);
				}
				}
			}
			catch(Exception e) {
				System.out.println("Error");
			}
			
		}
	//implements all basic JFrame graphics, initializes images, some variables
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
		//initiation of all sprites with a "theme" variable, allowing possible later sprite customization
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
		}
		Random one= new Random();
		bottompipeh= one.nextInt(400) + 325;
		pipeheight= (SCREENY- 120) -bottompipeh;
		pipewidth= 50;
	}
	//makes the bird flap around on the start screen
	public void startsequence() throws InterruptedException{
		//makes the illusion of the bird flapping his wings
		birdused= first;
		Thread.sleep(155);
		birdused= second;
		Thread.sleep(155);
		birdused= third;
		Thread.sleep(155);
		birdused= second;
		Thread.sleep(155);
	}
    //makes the "course" for the bird to travel through
	public void pipes(){
		//moves the pipe forward
		pipelocx -=10 ;
		Random two= new Random();
		//when the pipe touches the left end of the screen, it randomizes the height and brings it back to the right.
		if (pipelocx <= 0){
			pipelocx = SCREENX;
			pipecounter++;
			bottompipeh= two.nextInt(400) + 325;
			pipeheight= (SCREENY- 120) -bottompipeh;
		}
	}
	//basic pipe hit detection
	public void collision(){

		// a lot of semi-functional "if" statements for hit detection of bird VS pipe
		
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
    //gameover steps
	public void gameoveraction(){
	// i don't remember what i made this for. this method is very redundant.
		if (gameover){
        Charlocy= SCREENY/2;
        Charlocx= pipelocx;
		gameoversequence=true;
	}
	}
	//graphics
	public void paint(Graphics g) {
		//dbImage is very complicated, basically erases the trail that sprites moving would leave behind.
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		//constantly paints to screen
		try {
			paintComponent(dbg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(dbImage, 0, 0, this);
	}
	//graphics methods
	public void paintComponent(Graphics g) throws InterruptedException {
	// the graphics of the starting screen
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
	//the "gameplay" graphics
	g.drawImage(birdused, Charlocx ,Charlocy, this);
	g.setColor(Color.GREEN);
    g.fillRect(pipelocx, pipelocy, pipewidth, pipeheight);
    g.fillRect(pipelocx, SCREENY- bottompipeh, pipewidth, bottompipeh);
    g.setColor(Color.GRAY);
    Font joshone= new Font("Times new roman", Font.PLAIN, 90);
    g.setFont(joshone);
    g.drawString(""+pipecounter, SCREENX/2, 100);
    if (gameoversequence){
       //what happens when you touch a pipe, the "reset"
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
	//constant painting
	repaint();
	}
	//fluid motion
	public void update() {
	    //position updating of the sprite. decievingly complex.
		Charlocy -= yVelocity;
	}
	//implements keyboard
	public class AL extends KeyAdapter{
	public void keyPressed(KeyEvent e){
		int KeyCode = e.getKeyCode();
		if (KeyCode == e.VK_SPACE){
            //when the mouse is pressed:
			
			//has the game started
			if (!hasstarted){
				    //start the game
			    	hasstarted= true;
			    	start= true;
			    }
			// else, make him jump!
			else if(tap){
				yVelocity = yVelocityUpdate;
				update();
				birdused=fly;
			tap= false;
			
			} 
		    update();
		}
	}
	//ALSO, BOOLEAN "TAP" IS ESSENTIAL. MAKES IT SO YOU CAN'T HOLD SQUARE, ONLY A TAP REGISTERS.
	public void keyReleased(KeyEvent e){
		int KeyCode = e.getKeyCode();
		if (KeyCode == e.VK_SPACE){
			//makes it so he doesn't shoot off the screen when you press space once
		    birdused= second;
			update();
			tap= true;
		}

	}
}
	//in-class runner
	public static void main(String[] args){
	//Runs the Program.
	FlappyScreen josh= new FlappyScreen();
	Thread t1= new Thread(josh);
	t1.start();
}
    }