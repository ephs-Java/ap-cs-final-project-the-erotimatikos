package MarioGameTestingPlatForm;

import java.awt.Color;
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





public class marioScreen extends JFrame implements Runnable {

	field Field = new field();
	enemys ene = new enemys();

	private Graphics dbg;;
	private Image dbImage;
	//35 r by 20c
	int screenX = 1050,screenY = 630, rowLeng = Field.rowLeng, colLeng = Field.colLeng ;
	int x,y,yDirect,xDirect, tempX,tempY,endX,endY;
	int marioJump = 0;
	int level = 1;
	int prevTime=0;
	int once = 0;
	//Enemys will move on multiples of 3
	int enemySpeedGovenor = 0;
	Image brick;
	Image goomba;
	Image mario;
	Image gameover;
	Image vicImage;

	boolean isOut = false;
	boolean stopTime = false;
	boolean canJump = false;
	boolean falling = true;
	boolean victory = false;

	boolean win = false;
	
	int gravity = 6;
	int numOfLives = 4;
	
	double time = 0;
	
	
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
		if(Field.array[x/30][tempY/30].type != 1 &&Field.array[(tempX + 15)/30][(15+ tempY)/30].type != 1){
			y = tempY;
		
		}
		
	}
	
	public void jumping(){
	
		tempY = y;
		if(Field.array[x/30][y/30+1].type == 1){
		canJump = true;
		}
		else{
			canJump =false;
		}
		if(canJump && (Field.array[x/30][y/30-2].type != 1 && Field.array[x/30][y/30-1].type != 1)){
			tempY -= 60;
			canJump = false;
		}
		else if(Field.array[x/30][y/30-1].type != 1 && canJump){
			tempY-= 30;
		}
		
		
		

		if(Field.array[x/30][tempY/30].type != 1&&Field.array[(tempX + 15)/30][(15+ tempY)/30].type != 1){
		
			y = tempY;
			
		}
	}

	


	public static void main(String[] args) throws FileNotFoundException{
		marioScreen game = new marioScreen();
		Thread t = new Thread(game);
		t.start();
	}


	public marioScreen() throws FileNotFoundException{

		importer();
		enemyFinder(); 
		
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
		
		addKeyListener(new AL());
		setTitle("MarioGame");
		setSize(screenX,screenY);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.cyan); //Sets the background color to blue.

	}
	
	
	public void run(){
		
		Field.brickHunter();
		Field.getStart();
		Field.getEnd();
		endX = Field.endX;
		endY =Field.endY;
		
		
		x = Field.startX;
		y= Field.startY;
		while(true){

			try{
		
				if(!stopTime)
				time+=.1;
				if(enemySpeedGovenor % 3 == 0)
					ene.updateAll(Field.array);
				enemySpeedGovenor++;

				
				falling();
				
				move();
				if(!isOut)
				amIOut();
				if(!victory)
				isVictor();
				
				Thread.sleep(100);
			

			}
			catch(Exception e){
				System.out.println("Error");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		}
	}
	
	public void isVictor(){
		if(endX == x && endY == y){
			victory = true;
		}
		
	}
	public void amIOut(){
		if(ene.amIOut(x,y) && (Math.abs(prevTime - (int)time) > 1)){
			prevTime = (int)time;
			numOfLives--;
	}
	if(numOfLives < 1){
		stopTime = true;
		isOut = ene.amIOut(x,y);
	}
		
		
	}

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

	public void move(){
		tempX = x;
		tempY = y;

		tempX+= xDirect;
		tempY += yDirect;

		if(Field.array[tempX/30][tempY/30].type != 1 &&Field.array[(tempX + 15)/30][(15+ tempY)/30].type != 1){
			y = tempY;
			x = tempX;
		}
	}
	

	public void setXDirect(int n){
		xDirect = n;
	}
	public void setYDirect(int n){
		yDirect = n;

	}

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
			if(code == e.VK_RIGHT && code != e.VK_DOWN){

				setXDirect(30);
			}
			if(code == e.VK_UP && code != e.VK_RIGHT){

				jumping();
			}

			if(code == e.VK_DOWN){

				setYDirect(30);
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
	
	public void winner() throws InterruptedException{
		 if(victory){
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
	public void paintComponent(Graphics g) throws InterruptedException, FileNotFoundException{
		winner();
	g.drawString("Number of lives left: " + numOfLives, 30, 615);
	g.drawString("Time: " + ((int)(time)), 200, 615);
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
			
		
	 if(!isOut){
			for(int r = 0;r<rowLeng;r++){
				for(int c = 0;c<colLeng;c++){


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
			
		}
		}
		repaint();
	}

}
