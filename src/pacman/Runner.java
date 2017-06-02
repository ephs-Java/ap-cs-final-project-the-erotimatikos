package pacman;

import java.io.IOException;

public class Runner {

	public static void main(String[] args) {
		
		try {
			PacMenu pacmenu = new PacMenu();
			Thread t1 = new Thread(pacmenu);
			t1.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
