package MarioGame;

public class field {
	
	public int rowLeng = 35;
	public int colLeng = 20;
	
	public brick[][] array;
	
	
	
	
	public field(){
		array = new brick[rowLeng][colLeng];
		for(int r = 0;r<rowLeng;r++){
		for(int c = 0;c<colLeng;c++){
		
			
				array[r][c] = new brick();
				array[r][c].setType(0);
			}
		}
	}
	
	public field(brick[][] arr){
		array = new brick[rowLeng][colLeng];
		for(int r = 0;r<rowLeng;r++){
		for(int c = 0;c<colLeng;c++){
		
			
				array[r][c] = new brick(arr[r][c].getType());
				
			}
		}
	}
//	public void printField(){
//		for(int r = 0;r<200;r++){
//			for(int c = 0;c<200;c++){
//				
//			}
//		}
//	}
}
