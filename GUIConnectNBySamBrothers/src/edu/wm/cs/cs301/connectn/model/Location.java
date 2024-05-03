package edu.wm.cs.cs301.connectn.model;
/*
 * this class is the location object which controls all object functionality
 */
public class Location {
	private Character symbol;
	
	public Location() {
	}
	/*
	 * update token will take a string as an argument that is passed in the GameBoardModel class and 
	 * changes the objects symbol to represent the appropriate token. valid symbols are 'O', 'X',
	 * and ' '.
	 */
	public void updateToken(String token){
		if (token.equals("X")) {
			symbol = 'X';
		} else if (token.equals("O")){
			symbol = 'O';
		} else {
			symbol = ' ';
		}
	}
	
	public Character getToken() {
		return symbol;
	}
	/*
	 * this method returns true if the location object has ' ' as its symbol and false if it has
	 * 'X' or 'O'
	 */
	public boolean isEmpty() {
		if (this.getToken() == 'X' | this.getToken( )== 'O'){
			return false;
		} else {
			return true;
		}
	}
	/*
	 * the equals method overrides the default object equals method by comparing the symbols of one instance
	 * of the location object with another. if the two objects have different symbols or are not of the correct
	 * type, the method returns false, otherwise it returns true
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Location)) {
			return false;
		}
		Location other = (Location) o;
		if (other.getToken() == 'X' && this.getToken() == 'X') {
			return true;
		} else if (other.getToken() == 'O' && this.getToken() == 'O'){
			return true;
		} else {
		return false;
		}
	}
	
}
