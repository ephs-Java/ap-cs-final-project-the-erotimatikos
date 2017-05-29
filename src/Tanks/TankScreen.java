package Tanks;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class TankScreen extends JFrame{

	int tankOneX,tankOneY,tankTwoX,tankTwoY;

	TankBoard field = new TankBoard();

	TankTile tankOne = new TankTile(10,10);
	TankTile tankTwo = new TankTile(10,10);
	boolean isWallS = false;




	int oneNumBomb = 1000;
	int twoNumBomb = 1000;

	private Image dbImage;
	private Graphics dbg;

	public static void main(String[] args){
		new TankScreen();
	}

	public class AL extends KeyAdapter{





		public void keyPressed(KeyEvent e){



			int keyInput = e.getKeyCode(); //Code of each key pressed

			

			//The bomb setter for tank one
			if(oneNumBomb > 0 && keyInput == e.VK_U && !field.tiles[tankOneX/10][tankOneY/10].getIsWall()){
				field.tiles[tankOneX/10 - 1][tankOneY/10].SetMine();
				oneNumBomb--;

			}
			if(twoNumBomb > 0 && keyInput == e.VK_Q && !field.tiles[tankTwoX/10][tankTwoY/10].getIsWall()){
				field.tiles[tankTwoX/10-1][tankTwoY/10].SetMine();
				twoNumBomb--;

			}





			if(keyInput == e.VK_J){	  //Left key
				if(tankOneX - 10 <= 0){
					tankOneX = 0;
				}else if(!field.tiles[(tankOneX / 10) -1][tankOneY/10].getIsWall())
					tankOneX = tankOneX - 10;
			}
			if(keyInput == e.VK_L){	 //Right Key
				if(tankOneX + 10 >= 1000){
					tankOneX = 990;
				}else if(!field.tiles[(tankOneX / 10) +1][tankOneY/10].getIsWall())
					tankOneX = tankOneX + 10;
			}
			if(keyInput == e.VK_I){	//Up arrow
				if(tankOneY - 20 <= 0){
					tankOneY = 20;
				}else 
					tankOneY = tankOneY -10;
			}

			if(keyInput == e.VK_K){	//Down Arrow
				if(tankOneY + 10 > 290){
					tankOneY = 290;
				}else 
					tankOneY = tankOneY + 10;
			}




			if(keyInput == e.VK_A){	  //Left key
				if(tankTwoX - 10 <= 0){
					tankTwoX = 0;
				}else if(!field.tiles[(tankTwoX / 10) -1][tankTwoY/10].getIsWall())
					tankTwoX = tankTwoX - 10;
			}
			if(keyInput == e.VK_D){	 //Right Key
				if(tankTwoX + 10 >= 1000){
					tankTwoX = 990;
				}else if(!field.tiles[(tankTwoX / 10) +1][tankTwoY/10].getIsWall())
					tankTwoX = tankTwoX + 10;
			}
			if(keyInput == e.VK_W){	//Up arrow
				if(tankTwoY - 20 <= 0){
					tankTwoY = 20;
				}else 
					tankTwoY = tankTwoY -10;
			}

			if(keyInput == e.VK_S){	//Down Arrow
				if(tankTwoY + 10 > 290){
					tankTwoY = 290;
				}else 
					tankTwoY = tankTwoY + 10;
			}



			
			
			
			
			//Kills the tanks if they touch any bomb
			if(field.tiles[tankOneX/10][tankOneY/10].isMine()){
				tankOne.died();
			}
			if(field.tiles[tankTwoX/10][tankTwoY/10].isMine()){
				tankTwo.died();
			}
		}
	}

	public TankScreen(){
		tankOneX = 10;
		tankOneY = 100;
		tankTwoX = 900;
		tankTwoY = 100;
		addKeyListener(new AL());
		setTitle("Video4");
		setSize(1000,1000);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void paint(Graphics g){
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}

	public void paintComponent(Graphics g){

		if(!tankOne.isAlive()){
			
			g.drawString("GAME OVER>>>>>>>>",300, 500);
		}else if(!tankTwo.isAlive()){
			g.drawString("GAME OVER>>>>>>>>",300, 500);
		}
		else{
		
		for(int r = 0;r<100;r++){
			for(int c = 0;c<30;c++){
			
			
			 if(field.tiles[r][c].getIsWall() == true){
					g.setColor(Color.black);
					g.fillRect(10 * r, 10 * c, 10, 10);
				}
				else if(field.tiles[r][c].getMine()){
					g.setColor(Color.GRAY);
					g.fillRect(10 * r, 10 * c, 10, 10);
				}
				else{
					g.setColor(Color.blue);
					g.drawRect(10 * r, 10 * c, 10, 10);
				}
			}
		}
		g.fillRect(tankOneX, tankOneY, 10, 10);
		g.setColor(Color.red);
		g.fillRect(tankTwoX, tankTwoY, 10, 10);

		g.setColor(Color.black);
		g.drawString("Tank one has " + oneNumBomb + " bombs left", 100, 320);
		g.drawString("Tank one has " + twoNumBomb + " bombs left", 100, 340);
		
		repaint(); //Causes it to refresh once it reaches this point
		}
	}

}