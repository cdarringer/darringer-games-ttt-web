package com.darringer.games.ttt.web;

import com.darringer.games.ttt.model.TTTGameStatus;
import com.darringer.games.ttt.model.TTTModel;

/**
 * Present the {@link TTTModel} in a JSP friendly way.
 * Not much conversion here in this case.
 * 
 * @author cdarringer
 *
 */
public class TTTJSPView {
	TTTModel model;
	
	/**
	 * Default constructor
	 */
	public TTTJSPView() {
	}
	
	/**
	 * TTTJSPView constructor with a 
	 * <code>TTTModel</code> for wrapping.
	 */
	public TTTJSPView(TTTModel model) {
		this.model = model;
	}

	/**
	 * Return the character occupying the square at a given board index.
	 *
	 * @return java.lang.String
	 * @param index int
	 */
	public char getSquare(int index) {
		return model.getSquare(index);
	}
	
	/**
	 * Return the board state in a concise format
	 * 
	 * @return the string representation of the entire board
	 */
	public String getBoardAsString() {
		StringBuilder result = new StringBuilder();
		for (int i=0; i < 9; i++) {
			char piece = model.getSquare(i);
			result.append(String.format("%c", piece));
		}
		return result.toString();
	}

	/**
	 * Return the status code of the game, as defined in
	 * <code>TTTGameStatus</code>.
	 *
	 * @return TTTGameStatus
	 */
	public TTTGameStatus getStatus() {
		return model.getStatus();
	}
}
