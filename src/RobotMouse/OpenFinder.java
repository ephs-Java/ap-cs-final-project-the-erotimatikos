package RobotMouse;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class OpenFinder {

	public static void main(String[] args){
		try{
	Robot mice = new Robot();
	

//	mice.keyPress(KeyEvent.VK_META);
//	mice.keyPress(KeyEvent.VK_SPACE);
//	mice.keyRelease(KeyEvent.VK_SPACE);
//	mice.keyPress(KeyEvent.VK_A);
//	mice.keyRelease(KeyEvent.VK_A);	
	

	
	mice.keyPress(KeyEvent.VK_META);

	mice.keyPress(KeyEvent.VK_SPACE);
	mice.keyRelease(KeyEvent.VK_SPACE);
	mice.keyRelease(KeyEvent.VK_META);
	mice.delay(100);
	mice.keyPress(KeyEvent.VK_F);
	mice.keyPress(KeyEvent.VK_I);
	mice.keyPress(KeyEvent.VK_N);
	mice.keyPress(KeyEvent.VK_D);
	mice.delay(100);
	mice.keyRelease(KeyEvent.VK_F);
	mice.keyRelease(KeyEvent.VK_I);
	mice.keyRelease(KeyEvent.VK_N);
	mice.keyRelease(KeyEvent.VK_D);
	mice.delay(100);
	mice.keyPress(KeyEvent.VK_ENTER);
	mice.delay(100);
	mice.keyRelease(KeyEvent.VK_ENTER);
	
	
	
} catch (AWTException e) {// TODO Auto-generated catch block
	e.printStackTrace();
	
}
//
//
//
//
//
//

//
	}
}
