package MarioGameTestingPlatForm;

import java.util.ArrayList;

public class enemys {

	
	
	ArrayList<Integer> flippers = new ArrayList<Integer>();
	
	ArrayList<enemy> enemyList = new ArrayList<enemy>();
	
	
	
	int first = 0;
	int flip;
	public void add(int x, int y){
		enemyList.add(new enemy(x,y));
	}
	
	
	public boolean amIOut(int x, int y){
		for(int i = 0;i<enemyList.size();i++){
			if(enemyList.get(i).xE == x && enemyList.get(i).yE == y){
				return true;
			}
		}
		return false;
	}
	
	
	public void updateAll(brick[][] arr){
		
		if(first == 0){
			for(int i = 0;i<enemyList.size();i++){
				flippers.add(1);
			}
		}
		first++;
		for(int i = 0;i<enemyList.size();i++){			flip = flippers.get(i);
		if(arr[enemyList.get(i).xE/30+flip][enemyList.get(i).yE/30].type == 1){
			
			flip = flippers.set(i,flip * -1);
		}
		else{
			enemyList.get(i).xE+=flip *30;
		}
	}
	}
}
