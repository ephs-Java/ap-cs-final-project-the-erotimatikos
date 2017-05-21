package MarioGame;

import java.util.ArrayList;

public class enemys {

	private int flip = 1;
	ArrayList<enemy> enemyList = new ArrayList<enemy>();
	
	public void add(int x, int y){
		enemyList.add(new enemy(x,y));
	}
	
	public void updateAll(brick[][] arr){
		
		for(int i = 0;i<enemyList.size();i++){
		if(arr[enemyList.get(i).xE+flip][enemyList.get(i).yE].type == 1){
			
			flip = flip * -1;
		}
		else{
			enemyList.get(i).xE+=flip;
		}
	}
	}
}
