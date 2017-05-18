package pacman;

public class Runner {

	public static void main(String[] args) {
		
		Screen scr = new Screen();
		Thread t1 = new Thread(scr);
		t1.start();
		
	}
	
}
