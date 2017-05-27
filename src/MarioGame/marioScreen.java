package MarioGame;

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



public class marioScreen extends JFrame implements Runnable {

	field Field = new field();
	enemys ene = new enemys();

	private Graphics dbg;;
	private Image dbImage;
	//35 r by 20c
	int screenX = 1050,screenY = 630, rowLeng = Field.rowLeng, colLeng = Field.colLeng ;
	int x,y,yDirect,xDirect,cordX,cordY, tempX,tempY;
	int marioJump = 0;


	//Enemys will move on multiples of 3
	int enemySpeedGovenor = 0;
	Image brick;
	Image goomba;
	Image mario;

	boolean isOut = false;

	boolean canJump = false;
	boolean falling = true;
	int gravity = 6;
	int maxDy = 30;


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
		
	}
	
	public void jumping(){
		if(Field.array[x/30][y/30+1].type == 1){
		canJump = true;
		}
		else{
			canJump =false;
		}
		if(canJump && (Field.array[x/30][y/30-2].type != 1 && Field.array[x/30][y/30-1].type != 1)){
			y -= 60;
			canJump = false;
		}
		else if(Field.array[x/30][y/30-1].type != 1 && canJump){
			y-= 30;
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
		x = 180;
		y=540;
		ImageIcon brickk = new ImageIcon("src/MarioGame/brick.png");
		brick = brickk.getImage();


		ImageIcon goom = new ImageIcon("src/MarioGame/goomb.png");
		goomba = goom.getImage();

		ImageIcon mar = new ImageIcon("src/MarioGame/mario.png");
		mario = mar.getImage();

		addKeyListener(new AL());
		setTitle("MarioGame");
		setSize(screenX,screenY);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.cyan); //Sets the background color to blue.

	}
	public void run(){
		while(true){

			try{
				//if(!isOut){

				if(enemySpeedGovenor % 3 == 0)
					ene.updateAll(Field.array);
				enemySpeedGovenor++;

				cordX = x/30;
				cordY = y /30;
				falling();
				move();
				
				

				isOut = amIOut();
				
				Thread.sleep(50);
				//}else
				//Thread.sleep(10000);

			}
			catch(Exception e){
				System.out.println("Error");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		}
	}
	public boolean amIOut(){
		return ene.amIOut(x,y);
	}

	public void importer() throws FileNotFoundException{
		brick[][] ne = new brick[rowLeng][colLeng]; //Creates a new arraylist of Strings called allWords
		//scan in the words, one on each line
		Scanner input = new Scanner(new File("src/MarioGame/level1.txt"));

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
		//tempY = y;

		if(cordX + xDirect/30 > 0 && cordX + xDirect/30 < 35) 
			if(Field.array[cordX + xDirect/30][cordY].type == 1){


			}else{

				tempX+= xDirect;
			}

//		if(cordY + yDirect/30 < 20 && cordY + yDirect/30 > 0)
//			if(Field.array[cordX][cordY + yDirect/30].type == 1 ){
//
//
//			}else  if(marioJump % 2 == 0 || marioJump % 3 == 0){
//
//				tempY+= yDirect;
//			}

		if(y>570){
			y = 570;
		}
		if(y<0){
			y=0;
		}
		marioJump++;
		
		if(Field.array[tempX/30][tempY/30].type == 1){

		}
		else{
			x = tempX;
			y = tempY;
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
					ene.add(r, c);
					Field.array[r][c].type = 0;
				}
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

		if(!amIOut()){
			for(int r = 0;r<rowLeng;r++){
				for(int c = 0;c<colLeng;c++){


					if(Field.array[r][c].getType() == 0){
						g.setColor(Color.red);

						g.fillRect(r*30, c*30, 30,30);
						g.setColor(Color.BLACK);
						g.drawRect(r*30, c*30, 30, 30);
					}
					if(Field.array[r][c].getType() == 1){
						g.drawImage(brick,r *30,c*30, this);
					}




				}
			}
			//	g.setColor(Color.blue);
			//g.fillRect(x, y, 30, 30);
			g.drawImage(mario, x, y, this);



			for(int i = 0; i < ene.enemyList.size();i++){
				g.setColor(Color.green);
				g.drawImage(goomba, ene.enemyList.get(i).xE *30, ene.enemyList.get(i).yE * 30
						, this);
				//		g.fillRect(ene.enemyList.get(i).xE *30, *30, 30, 30);
			}


			//		g.drawString("T" + Field.array[cordX][cordY].type,30,615);
		}
		else{
			g.drawString("GAME OVER, Thanks for playing", 100, 100);
		}
		repaint();
	}

}
