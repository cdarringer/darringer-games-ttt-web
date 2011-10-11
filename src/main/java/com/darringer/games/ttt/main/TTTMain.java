package com.darringer.games.ttt.main;

import static com.darringer.games.ttt.model.TTTGameStatus.IN_PROGRESS;
import static com.darringer.games.ttt.model.TTTModel.EMPTY_SQUARE;
import static com.darringer.games.ttt.model.TTTModel.X_SQUARE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.darringer.games.ttt.control.TTTGameController;
import com.darringer.games.ttt.model.TTTModel;

/**
 * Command line test application
 * 
 * @author cdarringer
 *
 */
public class TTTMain {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to Tic Tac Toe.  You are X.");
		TTTGameController controller = new TTTGameController();
		TTTModel model = new TTTModel();
		while (model.getStatus() == IN_PROGRESS) {
			System.out.println(model);
			int index = getMove(model);
			model = controller.makeMove(model, index, X_SQUARE);
		}
		System.out.println(model);
		System.out.println("Game Over: " + model.getStatus());
	}
	
	/**
	 * 
	 * @return
	 */
	private static int getMove(TTTModel model) {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		int index = -1;
		boolean isValidInput = false;
		while (!isValidInput) {
			System.out.println("Move [0-8]?");
			try {
				String input = stdin.readLine();
				index = Integer.parseInt(input);
				if (model.getSquare(index) == EMPTY_SQUARE) {
					isValidInput = true;
				} else {
					System.out.println("That square is occupied, try again...");
				}
			} catch (IllegalArgumentException iae) {
				System.out.println("That was not a recognized index.");
			} catch (IOException ioe) {
				System.out.println("An unexpected system problem occurred.  Please try again.");
			} 
		}	
		return index;
	}
}
