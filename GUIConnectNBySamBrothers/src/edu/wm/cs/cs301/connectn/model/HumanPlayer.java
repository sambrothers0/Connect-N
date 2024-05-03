package edu.wm.cs.cs301.connectn.model;
/*
 * This class implements the computer interface which includes the take turn method
 */

public class HumanPlayer implements Player {
	
	public HumanPlayer() {}
	/*
	 * this is the take turn method for player, which simply 
	 * returns which numbered column the player inputs from the main game loop.
	 */
	@Override
	public int takeTurn(String selection) {
		int column = Integer.parseInt(selection);
		column--;
		return column;
	}
}

