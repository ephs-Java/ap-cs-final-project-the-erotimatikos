package Snake;

public class SnakeTile {
	private int row, col;
	private boolean isSnake= false;
	public SnakeTile(int r, int c){
	row= r;
    col= c;
	}
	public boolean getIsSnake(){
		return isSnake;
	}
}
