 /*   This is the method that is called when an action is performed.
    *   Over here I just simply show an error message if any of the text fields are empty or just show their names.
    */
package Treadmill;

import javax.swing.JOptionPane;

public class hobo{




	
	    public static void main(String[] args) {

	        String inputString = JOptionPane.showInputDialog(null, "INPUT A NUMER TO DISPLAY");
	        int input = Integer.parseInt(inputString);
	        System.out.println("User input: " + input);

	        JOptionPane.showMessageDialog(null, "User entered " + input);

	    }
	}
	