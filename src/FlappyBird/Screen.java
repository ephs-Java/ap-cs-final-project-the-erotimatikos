package FlappyBird;

import java.awt.Color;

import javax.swing.JFrame;

public class Screen extends JFrame{
	
	private int SCREENX,SCREENY;
	private String theme= new String("normal");
	public Screen() {
		// TODO Auto-generated constructor stub
		SCREENX= 550;
		SCREENY= 550;
		setTitle("Flappy Bird");
		setVisible(true);
		setSize(SCREENX, SCREENY);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.green);
		
	}

	
	

}
