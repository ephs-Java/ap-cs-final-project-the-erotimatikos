package MarioGameTestingPlatForm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pacman.Sound;





public class marioScreen extends JFrame implements Runnable {

	//Creates a 2d array of the field
	field Field = new field();
	//creates all goobas
	enemys ene = new enemys();

	
	//used for double buffering
	private Graphics dbg;;
	private Image dbImage;
	
	//35 r by 20c
	int screenX = 1050,screenY = 630, rowLeng = Field.rowLeng, colLeng = Field.colLeng ;
	int x,y,yDirect,xDirect, tempX,tempY,endX,endY;
	//number of jumps
	int marioJump = 0;
	//Level that you are on
	int level = 1;
	int prevTime=0;
	
	//Varibale used to run method once
	int once = 0;
	//Enemys will move on multiples of 3
	int enemySpeedGovenor = 0;
	
	//Images
	Image brick;
	Image goomba;
	Image mario;
	Image gameover;
	Image vicImage;
	Image lifes;
	Image brickBreak;
	
	//ending song
	boolean onceforendsong = false;
	//Checks to see if you lose
	boolean isOut = false;
	//Time that you finish the game
	boolean stopTime = false;
	
	//prevents double jumping
	boolean canJump = false;
	
	//Lets you fall
	boolean falling = true;
	boolean victory = false;
	//Creates a new sound
	Sound snd;
	boolean win = false;
	boolean startscreen= true;
	
	//how fast you fall
	int gravity = 5;
	
	//number of lives
	int numOfLives = 4;
	
	//time
	double time = 0;
	
	//Creates gravity in the game
	public void falling(){
		tempY = y;
		if(Field.array[x/30][y/30+1].type == 1){
			falling = false;
		}
		else{
			falling = true;
		}
		if(falling){
			tempY+= gravity;
			
		}
		if(Field.array[((int)(tempX)/30)][((int)(tempY)/30)].type != 11 &&Field.array[(tempX + 20)/30][(20+ tempY)/30].type != 11){//new
			
			
		if(Field.array[x/30][tempY/30].type != 1 &&Field.array[(tempX + 15)/30][(15+ tempY)/30].type != 1){
			y = tempY;
		
		}}
		
	}
	
	public void jumping(){
	
//Lets the users jump
		if(Field.array[x/30][y/30+1].type == 1){
			canJump = true;
		}else{
			canJump = false;
		}
		for(int i = 0;i<Field.brickList.size();i++){
			if(Field.brickList.get(i).xB == x/30 && (Field.brickList.get(i).yB ==( y/30 - 2) &&Field.brickList.get(i).yB ==(y/30 - 1))){
				if(Field.brickList.get(i).xB != (x/30 ) && Field.brickList.get(i).yB != (y/30 -1))
				canJump = false;
			}
			
		}
		if(canJump && Field.array[x/30][y/30-2].type !=1 && Field.array[x/30][y/30-1].type !=1 && Field.array[x/30][y/30-3].type !=1){
			y -= 85;
			snd.play("src/MarioGameTestingPlatForm/Mario Jump.wav/");
		}
		else if(canJump && Field.array[x/30][y/30-2].type !=1 && Field.array[x/30][y/30-1].type !=1){
				y -= 65;
				snd.play("src/MarioGameTestingPlatForm/Mario Jump.wav/");
			}
			else if(canJump && Field.array[x/30][y/30-1].type !=1){
				y-= 30;
				snd.play("src/MarioGameTestingPlatForm/Mario Jump.wav/");
			}
		
	
	}

	

// Main method that starts the thread
	public static void main(String[] args) throws FileNotFoundException{
		marioScreen game = new marioScreen();
		Thread t = new Thread(game);
		t.start();
	}


	public marioScreen() throws FileNotFoundException{
		//Imports the level
		importer();
		//Gets coordinate position for enemys
		enemyFinder(); 
		
		//Imports all of the images
		ImageIcon brickk = new ImageIcon("src/MarioGameTestingPlatForm/brick.png");
		brick = brickk.getImage();

		ImageIcon goom = new ImageIcon("src/MarioGameTestingPlatForm/goomb.png");
		goomba = goom.getImage();

		ImageIcon mar = new ImageIcon("src/MarioGameTestingPlatForm/mario.png");
		mario = mar.getImage();

		ImageIcon over = new ImageIcon("src/MarioGameTestingPlatForm/GameOver.png");
		gameover = over.getImage();
		
		ImageIcon victorious = new ImageIcon("src/MarioGameTestingPlatForm/Victory.png");
		vicImage = victorious.getImage();
		
		ImageIcon heart = new ImageIcon("src/MarioGameTestingPlatForm/lives.png");
		lifes = heart.getImage();
		
		ImageIcon brickbreakk = new ImageIcon("src/MarioGameTestingPlatForm/breakBrick.jpg");
		brickBreak = brickbreakk.getImage();
		
		 snd = new Sound();
		
		//Adds key input
		addKeyListener(new AL());
		//Creates the window
		setTitle("MarioGame");
		setSize(screenX,screenY);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.cyan); //Sets the background color to blue.

	}
	
	
	public void run(){
		//Finds coordinates for bricks
		Field.brickHunter();
		//Finds mario start location
		Field.getStart();
		//Finds mario end location
		Field.getEnd();
		
		//Position of end location
		endX = Field.endX;
		endY =Field.endY;
		
		//Where mario starts
		x = Field.startX;
		y= Field.startY;
		while(true){

			try{
		//Creates time
				if(!stopTime)
				time+=.1;
				//Speed of enemys
				if(enemySpeedGovenor % 3 == 0)
					ene.updateAll(Field.array);
				enemySpeedGovenor++;

				//gravity
				falling();
				
				//Player movement
				move();
				
				//Checks to see if player is out
				if(!isOut)
				amIOut();
				
				//checks to see if there is a winner
				if(!victory)
				isVictor();
				
				Thread.sleep(100);
			

			}
			catch(Exception e){
				System.out.println("Error");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			repaint();

		}
	}
	
	public void isVictor(){
		//Checks to see if you win
		if(endX == x && endY == y){
			victory = true;
		}
		
	}
	
	//checks to see if your out
	public void amIOut(){
		if(ene.amIOut(x,y) && (Math.abs(prevTime - (int)time) > 1)){
			prevTime = (int)time;
			numOfLives--;
			snd.play("src/MarioGameTestingPlatForm/Super Mario 2 - Die.wav/");
	}
	if(numOfLives < 1){
		stopTime = true;
		isOut = ene.amIOut(x,y);
	}
		
		
	}
//Imports new fields
	public void importer() throws FileNotFoundException{
		brick[][] ne = new brick[rowLeng][colLeng]; //Creates a new arraylist of Strings called allWords
		//scan in the words, one on each line
		Scanner input = new Scanner(new File("src/MarioGameTestingPlatForm/level" + level +".txt"));

		for(int c = 0;c<colLeng;c++){
			for(int r = 0;r<rowLeng;r++){

				int typer = input.nextInt();
				ne[r][c] = new brick(typer);
			}

		}
		Field.array = ne;

	}
//Controls all of marios movement
	public void move(){
		tempX = x;
		tempY = y;

		tempX+= xDirect;
		tempY += yDirect;

		if(Field.array[((int)(tempX)/30)][((int)(tempY)/30)].type != 11 &&Field.array[(tempX + 20)/30][(20+ tempY)/30].type != 11){//new


			if(Field.array[tempX/30][tempY/30].type == 11){
				Field.array[tempX/30][tempY/30].type = 0;

			}
		}
		if(Field.array[((int)(tempX)/30)][((int)(tempY)/30)].type != 11 &&Field.array[(tempX + 20)/30][(20+ tempY)/30].type != 11) //new
		if(Field.array[tempX/30][tempY/30].type != 1 &&Field.array[(tempX + 20)/30][(20+ tempY)/30].type != 1){
			y = tempY;
			x = tempX;
		}
		
	}
	
//used in mario movement
	public void setXDirect(int n){
		xDirect = n;
	}
	//used in mario movement
	public void setYDirect(int n){
		yDirect = n;

	}

	
	
	//Takes in user input
	public class AL extends KeyAdapter{

		public void keyPressed(KeyEvent e){
			int code = e.getKeyCode();


			try {
				Thread.sleep(10);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if(code == e.VK_LEFT){

				setXDirect(-30);
			}
			if(code == e.VK_SPACE){
				time = 0;
				startscreen = false;
			}
			if(code == e.VK_RIGHT && code != e.VK_DOWN){

				setXDirect(30);
			}
			if(code == e.VK_UP && code != e.VK_RIGHT){

				jumping();
			}

			if(code == e.VK_DOWN){

				if(Field.array[x/30][y/30 + 1].type == 11){//new
					Field.array[x/30][y/30 + 1].type = 0;//new
				}
			}
			if(code == e.VK_I){
				try {
					importer();

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}




		public void keyReleased(KeyEvent e){
			int code = e.getKeyCode();
			if(code == e.VK_LEFT){
				setXDirect(0);
			}
			if(code == e.VK_RIGHT){
				setXDirect(0);
			}
			if(code == e.VK_UP){
				setYDirect(0);
			}
			if(code == e.VK_DOWN){
				setYDirect(0);
			}

		}
	}


//Find coordinates for eneyms
	public void enemyFinder(){
		for(int c = 0;c<colLeng;c++){
			for(int r = 0;r<rowLeng;r++){

				if(Field.array[r][c].type ==9){
					ene.add(r *30, c*30);
					Field.array[r][c].type = 0;
				}
			}

		}
	}
	
	//part of double buffering
	public void paint(Graphics g){
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
	//Checks to see if the player won the level, if so, level changes
	public void winner() throws InterruptedException{
		 if(victory){
				snd.play("src/MarioGameTestingPlatForm/Win Stage.wav/");
			 level++;
			 
			 if(level >= 5){
				
				 if(level == 5){
					 win = true;
					 level = 1;
				 } else
				 level = 1;
			 }
			try {
				isOut = false;
				victory = false;
				ene.clearGoombas();
			
				importer();
			
				
				Field.clearBricks();
				Field.brickHunter();
				Field.getStart();
				Field.getEnd();
				endX = Field.endX;
				endY =Field.endY;
				x = Field.startX;
				y= Field.startY;
				enemyFinder();
				ene.resetFirst();
				ene.updateAll(Field.array);
				System.out.println(ene.flippers.toString());
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				System.out.println(level);

			
				
				
			// g.drawImage(vicImage, 10,10, this);
			
		}
		 
	}
	//Creates fonts used in the start screen
	Font big = new Font("Arial", Font.BOLD,100);
	Font itl = new Font("Arial", Font.ITALIC,50);

	public void paintComponent(Graphics g) throws InterruptedException, FileNotFoundException{
		//Decides if you have won the game
		winner();
		
		//Start screen
		if(startscreen){
			
			g.drawImage(goomba, 80, 200, this);
			g.drawString("Goomba", 80, 250);
			g.drawImage(brickBreak, 80, 350, this);
			g.drawString("Breakable Brick", 60, 390);
			g.drawString("Can jump though, and break by hitting down arrow", 60, 405);
			g.drawImage(brick, 900, 200, this);
			g.drawString("Regular Brick: ",780,250);
					g.drawString("cant be jumped through or broken", 800, 265);
			
			g.drawString("Welcome to mario game. Use arrow keys to move. Do not touch the goombas", 200, 220);
			g.drawString("They will take away one of your 4 lives. Your goal is to get to the Blue brick", 200, 240);
			g.drawString("Currently there are 4 levels. To access the level editor go to MarioEditor", 200, 260);
			g.drawString("You have one second of invincibility between goombe hits", 200, 280);
			g.setFont(big);
			g.drawString("Mario Game", 200, 200);
			g.setFont(itl);
			g.drawString("Click Space to begin", 200, 500);
		}
		else{
			//Displays the number of lives/hearts remaining
	g.drawString("Number of lives left: ", 30, 615);
	if(numOfLives == 4){
		g.drawImage(lifes, 190, 605, this);
		g.drawImage(lifes, 230, 605, this);
		g.drawImage(lifes, 270, 605, this);
		g.drawImage(lifes, 310, 605, this);
	}
	else if(numOfLives == 3){
		g.drawImage(lifes, 190, 605, this);
		g.drawImage(lifes, 230, 605, this);
		g.drawImage(lifes, 270, 605, this);
		
	}
	else if(numOfLives == 2){
		g.drawImage(lifes, 190, 605, this);
		g.drawImage(lifes, 230, 605, this);
	}
	else if(numOfLives == 1){
		g.drawImage(lifes, 190, 605, this);

	}
	g.drawString("Time: " + ((int)(time)), 400, 615);
	
	//Sees if you win
		if(win){
		
		
			g.drawImage(vicImage, 10, 10, this);
			
			if(once == 0){
				once++;
			String inputString = JOptionPane.showInputDialog(null, "Enter your name for the leaderboard");
		      
//	        System.out.println("User input: " + inputString);
	        Leader me = new Leader(inputString,(int)time);
	        advLeaderboard board = new advLeaderboard();
	        board.addLeader(me);
	        board.writeLeaderboard();
	       JOptionPane.showMessageDialog(null,board.dog());
			}
		} else{
			
		//Checks to make sure your in
	 if(!isOut){
			for(int r = 0;r<rowLeng;r++){
				for(int c = 0;c<colLeng;c++){
					if(Field.array[r][c].getType() == 11){
						g.drawImage(brickBreak,r *30,c*30, this);
					}

					if(Field.array[r][c].getType() == 0){
						g.setColor(Color.cyan);

						g.fillRect(r*30, c*30, 30,30);
						g.setColor(Color.cyan);
						g.drawRect(r*30, c*30, 30, 30);
					}
					if(Field.array[r][c].getType() == 1){
						g.drawImage(brick,r *30,c*30, this);
					}
					if(Field.array[r][c].getType() == 7){
						g.setColor(Color.blue);
						g.fillRect(endX, endY, 30, 30);
					}




				}
			}
			//	g.setColor(Color.blue);
			//g.fillRect(x, y, 30, 30);
			g.drawImage(mario, x, y, this);



			for(int i = 0; i < ene.enemyList.size();i++){
				g.setColor(Color.green);
				g.drawImage(goomba, ene.enemyList.get(i).xE, ene.enemyList.get(i).yE
						, this);
				//		g.fillRect(ene.enemyList.get(i).xE *30, *30, 30, 30);
			}


			
		}
		
	
		else{
			g.drawImage(gameover, 10,10, this);
			if(!onceforendsong){
				onceforendsong = true;
			snd.play("src/MarioGameTestingPlatForm/Super Mario 3 - Game Over.wav/");
			}
		}
		}
	}
	}

}
