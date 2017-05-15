package pacman;

import java.util.ArrayList;

public class Queue {

	//fields
	private ArrayList<String> queue;
	private ArrayList<Integer> nums;
	private int buffer;
	
	//initializes queue object with the given buffer
	public Queue(int b) {
		queue = new ArrayList<String>();
		nums = new ArrayList<Integer>();
		buffer = b;
	}
	
	//adds an item to both lists. starts a new corresponding counter
	public void add(String s) {
		
		if (queue.indexOf(s) == -1) {
			queue.add(s);
			nums.add(0);
		}
		else {
			nums.set(queue.indexOf(s), 0);
		}
	}
	
	//removes the given index from both arrays, if it exists
	public void remove(int i) {
		if (i > queue.size() - 1 || i < 0) {
			return;
		}
		queue.remove(i);
		nums.remove(i);
	}
	
	//removes the string from the lists, if it exists
	public void remove(String s) {
		int index = indexOf(s);
		if (index >= 0) {
			queue.remove(index);
			nums.remove(index);
			return;
		}
	}
	
	//reurns the index of the string in the arraylist, if it exists
	public int indexOf(String s) {
		
		for (int i = 0; i < queue.size(); i++) {
			if (queue.get(i).equals(s)) {
				return i;
			}
		}
		return -1;
		
	}
	
	
	//returns true if the given string is in the list. returns false otherwise
	public boolean exists(String s) {
		
		for (int i = 0; i < queue.size(); i++) {
			if (queue.get(i).equals(s)) {
				return true;
			}
		}
		return false;
		
	}
	
	//adds 1 to every number in nums
	//also removes things over the buffer size
	public void update() {
		for (int i = 0; i < nums.size(); i++) {
			nums.set(i, nums.get(i) + 1);
			if (nums.get(i) >= buffer) {
				remove(i);
			}
		}
//		System.out.println("sasdf");
	}
	
	public String toString() {
		
		String message = "";
		for (int i = 0; i < queue.size(); i++) {
			message += queue.get(i) + " : " + nums.get(i) + ", ";
		}
		return message;
		
	}
}
