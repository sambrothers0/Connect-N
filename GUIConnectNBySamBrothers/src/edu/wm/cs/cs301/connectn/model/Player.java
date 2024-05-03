/**
 * REPLACE THIS COMMENT WITH YOUR OWN JAVA DOC COMMENT
 * THAT DESCRIBES THE PURPOSE OF YOUR INTERFACE
 * 
 * You may add parameters to the takeTurn method and/or
 * change the return type of the method. But all classes that
 * implement the interface will need to be consistent with any
 * changes.
 *  
 * HOWEVER:
 *  - You may not move the file to a different package
 * 	- You may not change the name of the file or the interface
 *  - You may not change the name of the takeTurn method
 */
package edu.wm.cs.cs301.connectn.model;
/*
 * this interface has one modification made to it: a string parameter is
 * passed into the function based on the players input of their choice
 * of column in which to make a move.
 */
public interface Player {
	public int takeTurn(String selection);

}
