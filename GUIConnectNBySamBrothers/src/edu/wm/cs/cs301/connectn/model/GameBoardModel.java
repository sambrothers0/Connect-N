package edu.wm.cs.cs301.connectn.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.wm.cs.cs301.connectn.view.*;
/*
 * this class controls all single game functionality related to changing the
 * state of the board. This includes updating the board based
 * on which columns are selected by the player or computer during takeTurn()
 * it handles checking whether a winning move is 
 * possible and whether a winning move has been made. it also 
 * detects if the game is tied
 * 
 */
public class GameBoardModel {
	private Location[][] board;			//do not change!
	private int width;
	private int height;
	private int winSize;
	private int turns;
	private String mode;
	private Random rand = new Random();
	private HumanPlayer human = new HumanPlayer();
	private GameBoardView view;
	private boolean firstGame = true;
	private String name;

	public GameBoardModel() {
		modeMedium();
	}
	
	public void initializeGameBoard() {
		turns = 0;
		this.board = new Location[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board[i][j] = new Location();
				board[i][j].updateToken(" ");
			}
		}
	}
	
	public void addView(GameBoardView view) {
		this.view = view;
	}
	
	public void repaint() {
		view.repaintConnectNGridPanel();
		view.repaintTitlePanel();
	}
	
	public void modeSmall() {
		width = 5;
		height = 4;
		winSize = 3;
		mode = "Small";
		initializeGameBoard();
	}
	
	public void modeMedium() {
		width = 7;
		height = 6;
		winSize = 4;
		mode = "Medium";
		initializeGameBoard();
	}
	
	public void modeLarge() {
		width = 9;
		height = 8;
		winSize = 5;
		mode = "Large";
		initializeGameBoard();
	}
	
	public int getColumnCount() {
		return width;
	}
	
	public int getRowCount() {
		return height;
	}
	
	public int getTurns() {
		return turns;
	}
	
	public int getWinSize() {
		return winSize;
	}
	
	public Location[][] getConnectNGrid() {
		return board;
	}
	
	public void updateFirstGame() {
		firstGame = false;
	}
	
	public boolean getFirstGame() {
		return firstGame;
	}
	
	public void updateName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void incrTurns() {
		turns++;
	}
	
	/*
	 * this method checks if there are any possible computer moves that would end the
	 * game, and makes one if such a move exists. otherwise, it randomly chooses from
	 * a set of non-full columns in which to place its token.
	 */
	
	public void placeComputerToken() {
		if (checkWinnable()) {
			return;
		}
		List<String> emptySlots = getEmptySlots();
		int column = rand.nextInt(width);
		while (!(emptySlots.contains(Integer.toString(column)))) {
			column = rand.nextInt(width);
		}
		for (int i = 0; i < height; i++) {
			if (!(board[i][column].isEmpty())) {
				board[i-1][column].updateToken("O");
				break;
			}
			if (i == height-1) {
				board[i][column].updateToken("O");
				break;
			}
		}
	}
	
	/*
	 * this method take a string contained the column number of the players selection
	 * as an argument and updates the appropriate position on the board based on how
	 * many tokens have already been placed in that column.
	 */
	
	public void placeHumanToken(String selection) {
		int column = human.takeTurn(selection);
		for (int i = 0; i < height; i++) {
			if (!(board[i][column].isEmpty())) {
				board[i-1][column].updateToken("X");
				break;
			}
			if (i == height-1) {
				board[i][column].updateToken("X");
				break;
			}
		}
	}
	
	/*
	 * this method and all the following methods that begin with "checkWinnable"
	 * read the game board and check for consecutive 'O' tokens in a row that would
	 * indicate a possible winning move to be made by the computer. if any such 
	 * move exists, the board is updated and the method returns true.
	 */
	public boolean checkWinnableVertical() {
		if (width == 5 ) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < width; j++) {
					if ((board[i+1][j].getToken() == 'O')
							&& board[i+2][j].getToken() == 'O') {
						board[i][j].updateToken("O");
						return true;
					}
				}
			}
		}
		else if (width == 7) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < width; j++) {
					if ((board[i+1][j].getToken() == 'O')
							&& board[i+2][j].getToken() == 'O'
							&& board[i+3][j].getToken() == 'O') {
						board[i][j].updateToken("O");
						return true;
					}
				}
			}
		}
		else {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < width; j++) {
					if ((board[i+1][j].getToken() == 'O')
							&& board[i+2][j].getToken() == 'O'
							&& board[i+3][j].getToken() == 'O'
							&& board[i+4][j].getToken() == 'O') {
						board[i][j].updateToken("O");
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkWinnableHorizontalRight() {
		if (width == 5) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < 3; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i][j+1].getToken() == 'O')) {
						if (i == height-1) {
							if (board[i][j+2].isEmpty()) {
								board[i][j+2].updateToken("O");
								return true;
							}
						}
						else if (!board[i+1][j+2].isEmpty() && board[i][j+2].isEmpty()) {
							board[i][j+2].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 7) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < 4; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i][j+1].getToken() == 'O')
						&& (board[i][j+2].getToken() == 'O')) {
						if (i == height-1) {
							if (board[i][j+3].isEmpty()) {
								board[i][j+3].updateToken("O");
								return true;
							}
						}
						else if (!board[i+1][j+3].isEmpty() && board[i][j+3].isEmpty()) {
							board[i][j+3].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < 5; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i][j+1].getToken() == 'O')
						&& (board[i][j+2].getToken() == 'O')
						&& (board[i][j+3].getToken() == 'O')) {
						if (i == height-1) {
							if (board[i][j+4].isEmpty()){
								board[i][j+4].updateToken("O");
								return true;
							}
						}
						else if (!board[i+1][j+4].isEmpty() && board[i][j+4].isEmpty()) {
							board[i][j+4].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean checkWinnableHorizontalMiddle() {
		if (width == 5) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < 3; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i][j+2].getToken() == 'O')) {
						if (i == height-1) {
							if (board[i][j+1].isEmpty()){
								board[i][j+1].updateToken("O");
								return true;
							}
						}
						else if (!board[i+1][j+1].isEmpty() && board[i][j+1].isEmpty()) {
							board[i][j+1].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 9){
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < 5; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i][j+1].getToken() == 'O')
						&& (board[i][j+3].getToken() == 'O')
						&& (board[i][j+4].getToken() == 'O')) {
						if (i == height-1) {
							if (board[i][j+2].isEmpty()) {
								board[i][j+2].updateToken("O");
								return true;
							}
						}
						else if (!board[i+1][j+2].isEmpty() && board[i][j+2].isEmpty()) {
							board[i][j+2].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkWinnableHorizontalLeft() {
		if (width == 5) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < 3; j++) {
					if ((board[i][j+1].getToken() == 'O')
						&& (board[i][j+2].getToken() == 'O')) {
						if (i == height-1) {
							if (board[i][j].isEmpty()) {
								board[i][j].updateToken("O");
								return true;
							}
						}
						else if (!board[i+1][j].isEmpty() && board[i][j].isEmpty()) {
							board[i][j].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 7) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < 4; j++) {
					if ((board[i][j+1].getToken() == 'O')
						&& (board[i][j+2].getToken() == 'O')
						&& (board[i][j+3].getToken() == 'O')) {
						if (i == height-1) {
							if (board[i][j].isEmpty()) {
								board[i][j].updateToken("O");
								return true;
							}
						}
						else if (!board[i+1][j].isEmpty() && board[i][j].isEmpty()) {
							board[i][j].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < 5; j++) {
					if ((board[i][j+1].getToken() == 'O')
						&& (board[i][j+2].getToken() == 'O')
						&& (board[i][j+3].getToken() == 'O')
						&& (board[i][j+4].getToken() == 'O')) {
						if (i == height-1) {
							if (board[i][j].isEmpty()){
								board[i][j].updateToken("O");
								return true;
							}
						}
						else if (!board[i+1][j].isEmpty() && board[i][j].isEmpty()) {
							board[i][j].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkWinnableHorizontalMiddleRight() {
		if (width == 7) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < 3; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i][j+1].getToken() == 'O')
						&& (board[i][j+3].getToken() == 'O')) {
						if (i == height-1) {
							if (board[i][j+2].isEmpty()) {
								board[i][j+2].updateToken("O");
								return true;
							}
						}
						else if (!board[i+1][j+2].isEmpty() && board[i][j+2].isEmpty()) {
							board[i][j+2].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 9){
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < 5; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i][j+1].getToken() == 'O')
						&& (board[i][j+2].getToken() == 'O')
						&& (board[i][j+4].getToken() == 'O')) {
						if (i == height-1) {
							if (board[i][j+3].isEmpty()) {
								board[i][j+3].updateToken("O");
								return true;
							}
						}
						else if (!board[i+1][j+3].isEmpty() && board[i][j+3].isEmpty()) {
							board[i][j+3].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkWinnableHorizontalMiddleLeft() {
		if (width == 7) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < 4; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i][j+2].getToken() == 'O')
						&& (board[i][j+3].getToken() == 'O')) {
						if (i == height-1) {
							if (board[i][j+1].isEmpty()) {
								board[i][j+1].updateToken("O");
								return true;
							}
						}
						else if (!board[i+1][j+1].isEmpty() && board[i][j+1].isEmpty()) {
							board[i][j+1].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 9){
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < 5; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i][j+2].getToken() == 'O')
						&& (board[i][j+3].getToken() == 'O')
						&& (board[i][j+4].getToken() == 'O')) {
						if (i == height-1) {
							if (board[i][j+1].isEmpty()) {
								board[i][j+1].updateToken("O");
								return true;
							}
						}
						else if (!board[i+1][j+1].isEmpty() && board[i][j+1].isEmpty()) {
							board[i][j+1].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean checkWinnableDiagonalRightUp() {
		if (width == 5) {
			for (int i = 0; i < 2; i++) {
				for (int j = 2; j < 5; j++) {
					if ((board[i+1][j-1].getToken() == 'O')
						&& (board[i+2][j-2].getToken() == 'O')) {
						if (!board[i+1][j].isEmpty() && board[i][j].isEmpty()) {
							board[i][j].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 7) {
			for (int i = 0; i < 3; i++) {
				for (int j = 3; j < 7; j++) {
					if ((board[i+1][j-1].getToken() == 'O')
						&& (board[i+2][j-2].getToken() == 'O')
						&& (board[i+3][j-3].getToken() == 'O')) {
						if (!board[i+1][j].isEmpty() && board[i][j].isEmpty()) {
							board[i][j].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else {
			for (int i = 0; i < 4; i++) {
				for (int j = 4; j < 9; j++) {
					if ((board[i+1][j-1].getToken() == 'O')
						&& (board[i+2][j-2].getToken() == 'O')
						&& (board[i+3][j-3].getToken() == 'O')
						&& (board[i+4][j-4].getToken() == 'O')) {
						if (!board[i+1][j].isEmpty() && board[i][j].isEmpty()) {
							board[i][j].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkWinnableDiagonalRightMiddle() {
		if (width == 5) {
			for (int i = 0; i < 2; i++) {
				for (int j = 2; j < 5; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+2][j-2].getToken() == 'O')) {
						if (!board[i+2][j-1].isEmpty() && board[i+1][j-1].isEmpty()) {
							board[i+1][j-1].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 9) {
			for (int i = 0; i < 4; i++) {
				for (int j = 4; j < 9; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+1][j-1].getToken() == 'O')
						&& (board[i+3][j-3].getToken() == 'O')
						&& (board[i+4][j-4].getToken() == 'O')) {
						if (!board[i+3][j-2].isEmpty() && board[i+2][j-2].isEmpty()) {
							board[i+2][j-2].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkWinnableDiagonalRightDown() {
		if (width == 5) {
			for (int i = 0; i < 2; i++) {
				for (int j = 2; j < 5; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+1][j-1].getToken() == 'O')) {
						if (!board[i+3][j-2].isEmpty() && board[i+2][j-2].isEmpty()) {
							board[i+2][j-2].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 7) {
			for (int i = 0; i < 3; i++) {
				for (int j = 3; j < 7; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+1][j-1].getToken() == 'O')
						&& (board[i+2][j-2].getToken() == 'O')) {
						if (!board[i+4][j-3].isEmpty() && board[i+3][j-3].isEmpty()) {
							board[i+3][j-3].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else {
			for (int i = 0; i < 5; i++) {
				for (int j = 4; j < 9; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+1][j-1].getToken() == 'O')
						&& (board[i+2][j-2].getToken() == 'O')
						&& (board[i+3][j-3].getToken() == 'O')) {
						if (!board[i+5][j-4].isEmpty() && board[i+4][j-4].isEmpty()) {
							board[i+4][j-4].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkWinnableDiagonalRightMiddleUp() {
		if (width == 7) {
			for (int i = 0; i < 3; i++) {
				for (int j = 3; j < 7; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+2][j-2].getToken() == 'O')
						&& (board[i+3][j-3].getToken() == 'O')) {
						if (!board[i+2][j-1].isEmpty() && board[i+1][j-1].isEmpty()) {
							board[i+1][j-1].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 9) {
			for (int i = 0; i < 5; i++) {
				for (int j = 4; j < 9; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+2][j-2].getToken() == 'O')
						&& (board[i+3][j-3].getToken() == 'O')
						&& (board[i+4][j-4].getToken() == 'O')) {
						if (!board[i+2][j-1].isEmpty() && board[i+1][j-1].isEmpty()) {
							board[i+1][j-1].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkWinnableDiagonalRightMiddleDown() {
		if (width == 7) {
			for (int i = 0; i < 3; i++) {
				for (int j = 3; j < 7; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+1][j-1].getToken() == 'O')
						&& (board[i+3][j-3].getToken() == 'O')) {
						if (!board[i+3][j-2].isEmpty() && board[i+2][j-2].isEmpty()) {
							board[i+2][j-2].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 9) {
			for (int i = 0; i < 5; i++) {
				for (int j = 4; j < 9; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+1][j-1].getToken() == 'O')
						&& (board[i+2][j-3].getToken() == 'O')
						&& (board[i+4][j-4].getToken() == 'O')) {
						if (!board[i+4][j-3].isEmpty() && board[i+3][j-3].isEmpty()) {
							board[i+3][j-3].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkWinnableDiagonalLeftUp() {
		if (width == 5) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 3; j++) {
					if ((board[i+1][j+1].getToken() == 'O')
						&& (board[i+2][j+2].getToken() == 'O')) {
						if (!board[i+1][j].isEmpty() && board[i][j].isEmpty()) {
							board[i][j].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 7) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					if ((board[i+1][j+1].getToken() == 'O')
						&& (board[i+2][j+2].getToken() == 'O')
						&& (board[i+3][j+3].getToken() == 'O')) {
						if (!board[i+1][j].isEmpty() && board[i][j].isEmpty()) {
							board[i][j].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					if ((board[i+1][j+1].getToken() == 'O')
						&& (board[i+2][j+2].getToken() == 'O')
						&& (board[i+3][j+3].getToken() == 'O')
						&& (board[i+4][j+4].getToken() == 'O')) {
						if (!board[i+1][j].isEmpty() && board[i][j].isEmpty()) {
							board[i][j].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkWinnableDiagonalLeftMiddle() {
		if (width == 5) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 3; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+2][j+2].getToken() == 'O')) {
						if (!board[i+2][j+1].isEmpty() && board[i+1][j+1].isEmpty()) {
							board[i+1][j+1].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 9){
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+1][j+1].getToken() == 'O')
						&& (board[i+3][j+3].getToken() == 'O')
						&& (board[i+4][j+4].getToken() == 'O')) {
						if (!board[i+3][j+2].isEmpty() && board[i+2][j+2].isEmpty()) {
							board[i+2][j+2].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkWinnableDiagonalLeftDown() {
		if (width == 5) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 3; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+1][j+1].getToken() == 'O')) {
						if (!board[i+3][j+2].isEmpty() && board[i+2][j+2].isEmpty()) {
							board[i+2][j+2].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 7) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+1][j+1].getToken() == 'O')
						&& (board[i+2][j+2].getToken() == 'O')) {
						if (!board[i+4][j+3].isEmpty() && board[i+3][j+3].isEmpty()) {
							board[i+3][j+3].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+1][j+1].getToken() == 'O')
						&& (board[i+2][j+2].getToken() == 'O')
						&& (board[i+3][j+3].getToken() == 'O')) {
						if (!board[i+5][j+4].isEmpty() && board[i+4][j+4].isEmpty()) {
							board[i+4][j+4].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkWinnableDiagonalLeftMiddleUp() {
		if (width == 7) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+2][j+2].getToken() == 'O')
						&& (board[i+3][j+3].getToken() == 'O')) {
						if (!board[i+2][j+1].isEmpty() && board[i+1][j+1].isEmpty()) {
							board[i+1][j+1].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 9) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+2][j+2].getToken() == 'O')
						&& (board[i+3][j+3].getToken() == 'O')
						&& (board[i+4][j+4].getToken() == 'O')) {
						if (!board[i+2][j+1].isEmpty() && board[i+1][j+1].isEmpty()) {
							board[i+1][j+1].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkWinnableDiagonalLeftMiddleDown() {
		if (width == 7) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+1][j+1].getToken() == 'O')
						&& (board[i+3][j+3].getToken() == 'O')) {
						if (!board[i+3][j+2].isEmpty() && board[i+2][j+2].isEmpty()) {
							board[i+2][j+2].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		else if (width == 9) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					if ((board[i][j].getToken() == 'O')
						&& (board[i+1][j+1].getToken() == 'O')
						&& (board[i+2][j+2].getToken() == 'O')
						&& (board[i+4][j+4].getToken() == 'O')) {
						if (!board[i+4][j+3].isEmpty() && board[i+3][j+3].isEmpty()) {
							board[i+3][j+3].updateToken("O");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/*
	 * this method runs all the checkWinnable methods and if any returns true, then
	 * it also returns true
	 */
	public boolean checkWinnable() {
		if (checkWinnableVertical()
			|| checkWinnableHorizontalRight()
			|| checkWinnableHorizontalMiddle()
			|| checkWinnableHorizontalLeft()
			|| checkWinnableHorizontalMiddleRight()
			|| checkWinnableHorizontalMiddleLeft()
			|| checkWinnableDiagonalRightUp()
			|| checkWinnableDiagonalRightMiddle()
			|| checkWinnableDiagonalRightDown()
			|| checkWinnableDiagonalRightMiddleUp()
			|| checkWinnableDiagonalRightMiddleDown()
			|| checkWinnableDiagonalLeftUp()
			|| checkWinnableDiagonalLeftMiddle()
			|| checkWinnableDiagonalLeftDown()
			|| checkWinnableDiagonalLeftMiddleUp()) {
			return true;
		}
		return false;
	}
	
	/*
	 * this method determines which of the columns contain an empty space in thier topmost row
	 * in order to inform the computer on which slots are valid to make a move in
	 */
	
	public List<String> getEmptySlots() {
		List<String> emptySlots = new ArrayList<String>();
		for (int i = 0; i < width; i++) {
			if (board[0][i].isEmpty()) {
				emptySlots.add(Integer.toString(i+1));
			}
		}
		return emptySlots;
	}

	public Character checkNHorizontal() {
		if (width == 5) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < winSize; j++) {
					if (board[i][j].equals(board[i][j])
							&& board[i][j+1].equals(board[i][j])
							&& board[i][j+2].equals(board[i][j])) {
						return board[i][j].getToken();
					}
				}
			}
		}
		if (width == 7) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < winSize; j++) {
					if (board[i][j].equals(board[i][j])
							&& board[i][j+1].equals(board[i][j])
							&& board[i][j+2].equals(board[i][j])
							&& board[i][j+3].equals(board[i][j])) {
						return board[i][j].getToken();
					}	
				}
			}
		}
		if (width == 9) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < winSize; j++) {
					if (board[i][j].equals(board[i][j])
							&& board[i][j+1].equals(board[i][j])
							&& board[i][j+2].equals(board[i][j])
							&& board[i][j+3].equals(board[i][j])
							&& board[i][j+4].equals(board[i][j])) {
						return board[i][j].getToken();
					}
				}
			}
		}
		return ' ';
	}
	
	public Character checkNVertical() {
		if (width == 5) {
			for (int j = 0; j < width; j++) {
				for (int i = 0; i < 2; i++) {
					if (board[i][j].equals(board[i][j])
							&& board[i+1][j].equals(board[i][j])
							&& board[i+2][j].equals(board[i][j])) {
						return board[i][j].getToken();
					}	
				}
			}
		}
		if (width == 7) {
			for (int j = 0; j < width; j++) {
				for (int i = 0; i < 3; i++) {
					if (board[i][j].equals(board[i][j])
							&& board[i+1][j].equals(board[i][j])
							&& board[i+2][j].equals(board[i][j])
							&& board[i+3][j].equals(board[i][j])) {
						return board[i][j].getToken();
					}	
				}
			}
		}
		if (width == 9) {
			for (int j = 0; j < width; j++) {
				for (int i = 0; i < 4; i++) {
					if (board[i][j].equals(board[i][j])
							&& board[i+1][j].equals(board[i][j])
							&& board[i+2][j].equals(board[i][j])
							&& board[i+3][j].equals(board[i][j])
							&& board[i+4][j].equals(board[i][j])) {
						return board[i][j].getToken();
					}	
				}
			}
		}
		return ' ';
	}
	
	public Character checkNDiagonalRight() {
		if (width == 5) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < winSize; j++) {
					if (board[i][j].equals(board[i][j])
							&& board[i+1][j+1].equals(board[i][j])
							&& board[i+2][j+2].equals(board[i][j])) {
						return board[i][j].getToken();
					}	
				}
			}
		}
		if (width == 7) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < winSize; j++) {
					if (board[i][j].equals(board[i][j])
							&& board[i+1][j+1].equals(board[i][j])
							&& board[i+2][j+2].equals(board[i][j])
							&& board[i+3][j+3].equals(board[i][j])) {
						return board[i][j].getToken();
					}	
				}
			}
		}
		if (width == 9) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < winSize; j++) {
					if (board[i][j].equals(board[i][j])
							&& board[i+1][j+1].equals(board[i][j])
							&& board[i+2][j+2].equals(board[i][j])
							&& board[i+3][j+3].equals(board[i][j])
							&& board[i+4][j+4].equals(board[i][j])) {
						return board[i][j].getToken();
					}	
				}
			}
		}
		return ' ';
	}
	
	public Character checkNDiagonalLeft() {
		if (width == 5) {
			for (int i = 0; i < 2; i++) {
				for (int j = 4; j > 1; j--) {
					if (board[i][j].equals(board[i][j])
							&& board[i+1][j-1].equals(board[i][j])
							&& board[i+2][j-2].equals(board[i][j])) {
						return board[i][j].getToken();
					}	
				}
			}
		}
		if (width == 7) {
			for (int i = 0; i < 3; i++) {
				for (int j = 6; j > 3; j--) {
					if (board[i][j].equals(board[i][j])
							&& board[i+1][j-1].equals(board[i][j])
							&& board[i+2][j-2].equals(board[i][j])
							&& board[i+3][j-3].equals(board[i][j])) {
						return board[i][j].getToken();
					}	
				}
			}
		}
		if (width == 9) {
			for (int i = 0; i < 3; i++) {
				for (int j = 6; j > 3; j--) {
					if (board[i][j].equals(board[i][j])
							&& board[i+1][j-1].equals(board[i][j])
							&& board[i+2][j-2].equals(board[i][j])
							&& board[i+3][j-3].equals(board[i][j])
							&& board[i+4][j-4].equals(board[i][j])) {
						return board[i][j].getToken();
					}	
				}
			}
		}
		return ' ';
	}
	
	public boolean checkTie() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (board[i][j].isEmpty()) {
					return false;
				} 
			}
		}
		return true;
	}
	
	public int checkWin() {
		if (checkNHorizontal().equals('X')
			|| checkNVertical().equals('X')
			|| checkNDiagonalRight().equals('X')
			|| checkNDiagonalLeft().equals('X')) {
			return 1;
		}
		else if (checkNHorizontal().equals('O')
				|| checkNVertical().equals('O')
				|| checkNDiagonalRight().equals('O')
				|| checkNDiagonalLeft().equals('O')) {
			return 2;
		}
		else if (checkTie() == true) {
			return 3;
		}
		return 0;
	}
}
