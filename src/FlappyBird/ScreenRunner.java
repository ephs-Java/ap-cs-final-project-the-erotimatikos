package FlappyBird;

public class ScreenRunner {

	public static void main(String[] args){
		Screen flap= new Screen();
		Thread t= new Thread((Runnable) flap);
	}
}
