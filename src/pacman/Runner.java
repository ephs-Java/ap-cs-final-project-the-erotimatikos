package pacman;

import java.io.IOException;

public class Runner {

	public static void main(String[] args) {
		
		try {
			PacMenu pacmenu = new PacMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
