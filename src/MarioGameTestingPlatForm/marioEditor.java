
package MarioGameTestingPlatForm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;



public class marioEditor extends JFrame implements Runnable{

	//creates an array of bricks
	field Field = new field();

//used for double buffering
	private Graphics dbg;;
	private Image dbImage;

	//35 r by 20c
	int screenX = 1050,screenY = 630, rowLeng = Field.rowLeng, colLeng = Field.colLeng ;
	int x,y,yDirect,xDirect,cordX,cordY;
//images
	Image brick;
	Image brickBreak;
	int level = 1;
	boolean startscreen = true;
	
//starts the thread
	public static void main(String[] args){
		marioEditor editor = new marioEditor();
		Thread t = new Thread(editor);
		t.start();
	}

	
	//Editor logic
	public marioEditor(){

		x = 90;
		y=540;
		ImageIcon brickk = new ImageIcon("src/MarioGameTestingPlatForm/brick.png");
		brick = brickk.getImage();

		ImageIcon brickbreakk = new ImageIcon("src/MarioGameTestingPlatForm/breakBrick.jpg");
		brickBreak = brickbreakk.getImage();
		
		addKeyListener(new AL());
		setTitle("MarioGame");
		setSize(screenX,screenY);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.cyan); //Sets the background color to blue.

	}

//Runs the thread
	public void run(){
		try {
			//imports level
			importer();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true){
			
			try{
				Thread.sleep(100);
				cordX = x/30;
				cordY = y /30;

				move();

			}
			catch(Exception e){
				System.out.println("Error");
				System.out.println(e.getMessage());
			}

		}
	}
	
	//imports the level
	public void importer() throws FileNotFoundException{
		brick[][] ne = new brick[rowLeng][colLeng]; //Creates a new arraylist of Strings called allWords
		//scan in the words, one on each line
		Scanner input = new Scanner(new File("src/MarioGameTestingPlatForm/level"+ level + ".txt"));

		for(int c = 0;c<colLeng;c++){
			for(int r = 0;r<rowLeng;r++){

				int typer = input.nextInt();
				ne[r][c] = new brick(typer);
			}

		}


		Field.array = ne;

	}
//Exports the level
	public void export() throws FileNotFoundException{


		PrintStream output = new PrintStream(new File("src/MarioGameTestingPlatForm/level"+ level + ".txt")); 
		for(int c = 0; c < colLeng;c++){
			for(int r = 0;r < rowLeng; r++){
				output.println(Field.array[r][c].type);
			}
		}
	}
	//selector movement
	public void move(){


		x+= xDirect;
		y+= yDirect;
		if(y>570){
			y = 570;
		}
		if(y<0){
			y=0;
		}

	}
	public void setXDirect(int n){
		xDirect = n;
	}
	public void setYDirect(int n){
		yDirect = n;
	}
//takes in user input
	public class AL extends KeyAdapter{

		public void keyPressed(KeyEvent e){
			int code = e.getKeyCode();

			if(code == e.VK_LEFT){
				setXDirect(-30);

			}
			if(x<=1020){
				if(code == e.VK_RIGHT){
					setXDirect(30);
				}
			}

			if(code == e.VK_UP){
				setYDirect(-30);
			}
			if(code == e.VK_DOWN){
				setYDirect(30);
			}
			if(code == e.VK_ENTER){
				startscreen = false;
			}
			
			if(code == e.VK_SPACE){

				Field.array[cordX][cordY].type++;
				if(Field.array[cordX][cordY].type >=4){
					Field.array[cordX][cordY].type = 0;
				}
			}
			if(code == e.VK_Z){

				Field.array[cordX][cordY].type = 9;
			}
			if(code == e.VK_S){

				Field.array[cordX][cordY].type = 6;
			}
			if(code == e.VK_B){

				Field.array[cordX][cordY].type = 11;
			}
			if(code == e.VK_L){
				
				level++;
				if(level > 5){
					level=1;
				}
				try {
					importer();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(code == e.VK_V){

				Field.array[cordX][cordY].type = 7;
			}
			if(code == e.VK_E){
				try {
					export();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
//paints on the screen
	public void paint(Graphics g){
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	Font big = new Font("Arial",Font.BOLD,100);
	public void paintComponent(Graphics g){
		if(startscreen){
			g.drawString("E = saves the levels", 100, 400);
			g.drawString("V = Places victory brick", 100, 420);
			g.drawString("B = Creates breakable brick", 100, 440);
			g.drawString("SPACE = places ordinary bricks", 100, 460);
			g.drawString("L = changes levels", 100, 480);
			g.drawString("CLICK ENTER TO START", 100, 500);
			g.setFont(big);
			g.drawString("MARIO EDITOR", 0, 200);
			
		}else{
//paints the coordniate grid
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
				if(Field.array[r][c].getType() == 9){
					g.setColor(Color.GREEN);
					g.fillRect(r*30, c*30, 30, 30);
				}
				if(Field.array[r][c].getType() == 6){
					g.setColor(Color.YELLOW);
					g.fillRect(r*30, c*30, 30, 30);
				}
				if(Field.array[r][c].getType() == 7){
					g.setColor(Color.blue);
					g.fillRect(r*30, c*30, 30, 30);
				}
				if(Field.array[r][c].getType() == 11){
					g.drawImage(brickBreak,r *30,c*30, this);
				}
			}
		}
		g.setColor(Color.blue);
		g.drawRect(x, y, 30, 30);


		
		
	}
		repaint();
	}



}
