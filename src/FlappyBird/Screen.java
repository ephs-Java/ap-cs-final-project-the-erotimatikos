package FlappyBird;

import javax.swing.JFrame;

public class Screen extends JFrame{
	
	private int SCREENX,SCREENY;
	
	public Screen() {
		// TODO Auto-generated constructor stub
		setTitle("Flappy Bird");
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
