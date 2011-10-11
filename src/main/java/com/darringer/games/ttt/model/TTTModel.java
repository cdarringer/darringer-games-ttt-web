package com.darringer.games.ttt.model;

import static com.darringer.games.ttt.model.TTTGameStatus.IN_PROGRESS;
import static com.darringer.games.ttt.model.TTTGameStatus.NOT_POSSIBLE;
import static com.darringer.games.ttt.model.TTTGameStatus.UNKNOWN;

/**
 * Data model for Tic Tac Toe.  Should contain data only 
 * (no real logic).
 * 
 * @author cdarringer
 */
public class TTTModel {
	
	public static final char EMPTY_SQUARE = '-';
	public static final char X_SQUARE = 'x';
	public static final char O_SQUARE = 'o';
	
	private char[] tttBoard;
	private TTTGameStatus status;

	/**
	 * Create an empty Tic Tac Toe object.
	 */
	public TTTModel() {
		tttBoard = new char[9];
		tttBoard[0] = EMPTY_SQUARE;
		tttBoard[1] = EMPTY_SQUARE;
		tttBoard[2] = EMPTY_SQUARE;
		tttBoard[3] = EMPTY_SQUARE;
		tttBoard[4] = EMPTY_SQUARE;
		tttBoard[5] = EMPTY_SQUARE;
		tttBoard[6] = EMPTY_SQUARE;
		tttBoard[7] = EMPTY_SQUARE;
		tttBoard[8] = EMPTY_SQUARE;
		status = IN_PROGRESS;
	}
	
	/**
	 * Build a new {@link TTTModel} from an optional string representation.
	 * We assume that, if a string is provided, it is a valid board string.
	 * 
	 * @param boardAsString
	 * @return
	 */
	public TTTModel(String boardAsString) {
		tttBoard = new char[9];
		if ((boardAsString == null) || 
			(boardAsString.equals("")) || 
			(boardAsString.length() != 9)) 
		{
			status = NOT_POSSIBLE;
		} else {
			status = UNKNOWN;
			for (int i=0; i < 9; i++) {
				char currentValue = boardAsString.charAt(i);
				if ((currentValue == X_SQUARE) || 
					(currentValue == O_SQUARE) || 
					(currentValue == EMPTY_SQUARE)) 
				{
					tttBoard[i] = currentValue;
				} else {
					tttBoard[i] = EMPTY_SQUARE;
					status = NOT_POSSIBLE;
				}
			}
		}
	}
	
	
	/**
	 * Get the Tic Tac Toe board square at a particular index, starting with 0.
	 * 
	 * @return char
	 */
	public char getSquare(int index) {
		if ((index >= 0) && (index < 9)) {
			return tttBoard[index];
		} else {
			return EMPTY_SQUARE;
		}
	}

	/**
	 * Return the current status code of the game.
	 * 
	 * @return TTTGameStatus 
	 * 	Status code, according to <code>TTTGameStatus</code>.
	 */
	public TTTGameStatus getStatus() {
		return this.status;
	}

	/**
	 * Set the current status of the game
	 * 
	 * @param status
	 */
	public void setStatus(TTTGameStatus status) {
		this.status = status;
	}	
	
	
	/**
	 * Set a Tic Tac Toe board square at a particular index (starting with 0).
	 * 
	 * @param index
	 * @param char value
	 */
	public void setSquare(int index, char value) {
		if ((index >= 0) && (index < 9)) {
			tttBoard[index] = value;
		}
	}
	
	/**
	 * Pretty print the model
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String NEW_LINE = System.getProperty("line.separator");
		for (int x=0; x < 9; x++) {
			char piece = getSquare(x);
			result.append(String.format("%c", piece));
			if (((x+1) % 3) == 0) {
				result.append(NEW_LINE);
			}
		}
		return result.toString();
	}
}