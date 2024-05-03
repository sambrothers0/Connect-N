package edu.wm.cs.cs301.connectn.model;
/*
 * This class implements the computer interface which includes the take turn method
 */
public class ComputerPlayer implements Player {
	
	public ComputerPlayer() {
	}
	/*
	 * this is the take turn method for computer, which simply 
	 * returns which numbered column the computer selects from the main game loop.
	 */
	@Override
	public int takeTurn(String selection) {
		int column = Integer.parseInt(selection);
		column--;
		return column;
	}

}
