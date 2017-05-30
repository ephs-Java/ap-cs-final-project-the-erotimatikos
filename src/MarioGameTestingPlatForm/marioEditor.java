
package MarioGameTestingPlatForm;

import java.awt.Color;
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

	field Field = new field();


	private Graphics dbg;;
	private Image dbImage;

	//35 r by 20c
	int screenX = 1050,screenY = 630, rowLeng = Field.rowLeng, colLeng = Field.colLeng ;
	int x,y,yDirect,xDirect,cordX,cordY;

	Image brick;
	int level = 1;
	public static void main(String[] args){
		marioEditor editor = new marioEditor();
		Thread t = new Thread(editor);
		t.start();
	}

	public marioEditor(){

		x = 90;
		y=540;
		ImageIcon brickk = new ImageIcon("src/MarioGame/brick.png");
		brick = brickk.getImage();

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

	public void export() throws FileNotFoundException{


		PrintStream output = new PrintStream(new File("src/MarioGameTestingPlatForm/level"+ level + ".txt")); 
		for(int c = 0; c < colLeng;c++){
			for(int r = 0;r < rowLeng; r++){
				output.println(Field.array[r][c].type);
			}
		}
	}
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
			if(code == e.VK_L){

				level++;
				if(level > 4){
					level=1;
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

	public void paint(Graphics g){
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	public void paintComponent(Graphics g){

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
			}
		}
		g.setColor(Color.blue);
		g.drawRect(x, y, 30, 30);


		repaint();
		
	}




}
