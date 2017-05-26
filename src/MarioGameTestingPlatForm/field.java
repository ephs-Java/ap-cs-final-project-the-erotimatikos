package MarioGameTestingPlatForm;

import java.util.ArrayList;

public class field {
	
	public int rowLeng = 35;
	public int colLeng = 20;
	
	
	public int startX;
	public int startY;
	public int endX;
	public int endY;
	public brick[][] array;
	public ArrayList<brick> brickList = new ArrayList<brick>();
	
	
	
	//Finds the end coordinate of the mario finish line brick
	public void getEnd(){
		for(int c = 0;c<colLeng;c++){
			for(int r = 0; r<rowLeng;r++){
				if(array[r][c].type == 7){
					endX  = r*30;
					endY  = c*30;
				}
			}
		}

	}
	//Gets the x and y coordinates of the mario start brick
	public void getStart(){
		for(int c = 0;c<colLeng;c++){
			for(int r = 0; r<rowLeng;r++){
				if(array[r][c].type == 6){
					startX  = r*30;
					startY  = c*30;
				}
			}
		}
	}
	public void clearBricks(){
			brickList.clear();
		
	}
	
	
	
	//Finds all of the bricks in the game, it is necesary in order for the game to know the pixel coordinartes of the bricks	
	public void brickHunter(){
		for(int c= 0;c<colLeng;c++){
			for(int r = 0; r < rowLeng;r++){
				if(array[r][c].type == 1){
					array[r][c].xB = r * 30;
					array[r][c].yB = c * 30;
					brickList.add(array[r][c]);
					System.out.println(array[r][c].xB/30 + " " +array[r][c].yB/30);
				}
			}
		}
	}
	
	
	//Constructor that is used at the beginning
	public field(){
		array = new brick[rowLeng][colLeng];
		for(int r = 0;r<rowLeng;r++){
		for(int c = 0;c<colLeng;c++){
		
			
				array[r][c] = new brick();
				array[r][c].setType(0);
			}
		}
	}
	
	
	//Creates the game field, it is called in importer
	public field(brick[][] arr){
		array = new brick[rowLeng][colLeng];
		for(int r = 0;r<rowLeng;r++){
		for(int c = 0;c<colLeng;c++){
		
			
				array[r][c] = new brick(arr[r][c].getType());
				
			}
		}
	}

}
