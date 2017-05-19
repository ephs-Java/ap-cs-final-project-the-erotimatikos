package pacman;

public class Runner {

	public static void main(String[] args) {
		
//		Menu menu = new Menu();
		PacScreen scr = new PacScreen();
		Thread t1 = new Thread(scr);
		t1.start();
		
	}
	
}
