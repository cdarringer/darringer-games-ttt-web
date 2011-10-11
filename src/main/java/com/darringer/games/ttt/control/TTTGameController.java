package com.darringer.games.ttt.control;

import static com.darringer.games.ttt.model.TTTGameStatus.IN_PROGRESS;
import static com.darringer.games.ttt.model.TTTGameStatus.NOT_POSSIBLE;
import static com.darringer.games.ttt.model.TTTGameStatus.O_WIN;
import static com.darringer.games.ttt.model.TTTGameStatus.TIE;
import static com.darringer.games.ttt.model.TTTGameStatus.X_WIN;
import static com.darringer.games.ttt.model.TTTModel.EMPTY_SQUARE;
import static com.darringer.games.ttt.model.TTTModel.O_SQUARE;
import static com.darringer.games.ttt.model.TTTModel.X_SQUARE;

import org.apache.log4j.Logger;

import com.darringer.games.ttt.model.TTTGameStatus;
import com.darringer.games.ttt.model.TTTModel;

/**
 * Contains the search logic for evaluating and making moves on the 
 * {@link TTTModel}.  Uses a min/max search approach to select the best 
 * next move.
 * 
 * @author cdarringer
 *
 */
public class TTTGameController {
	
	private static Logger log = Logger.getLogger(TTTGameController.class);
	
	/**
	 * We ("us") have a {@link TTTModel} and a move proposed by the other player
	 * ("them"). Evaluate the other player's move and then use search to determine
	 * what the program's best move should be.
	 * <p /> 
	 * This is the top level evaluation function.  Unlike the minmax
	 * search method, it also performs validation checks, actually performs a 
	 * move, and logs a bunch of stuff for troubleshooting purposes.
	 * 
	 * @param model
	 * @param index 
	 * 	index of the proposed move
	 * @param them 
	 * 	identifier of the opponent (X or O)
	 * @return the model with the users move and our counter move
	 */
	public TTTModel makeMove(TTTModel model, int index, char them) {
		// validate user inputs
		if ((index < 0) || (index > 8) || ((them != X_SQUARE) && (them != O_SQUARE))) {
			log.warn("Invalid inputs for makeMove");
			model.setStatus(NOT_POSSIBLE);
			return model;
		}
		
		// is this square available?
		if (model.getSquare(index) != EMPTY_SQUARE) {
			log.warn("The user was able to select a move that was not possible");			
			model.setStatus(NOT_POSSIBLE);
			return model;
		}
		
		// evaluate the current game state
		log.info("User inputs were valid");
		TTTGameStatus status = checkStatus(model);
		if (status != IN_PROGRESS) {
			log.warn("The game was already over before the user's move could be made");
			model.setStatus(status);
			return model;
		}
		
		// inputs are valid and the game is in progress - make the user's move
		model.setSquare(index, them);
		status = checkStatus(model);
		if (status != IN_PROGRESS) {
			log.info("The game was over after the user's move");
			model.setStatus(status);
			return model;
		}		
		
		// the game is still on after the player's move
		log.info("The game is still in progress after user's move.  Evaluating counter moves...");
		TTTGameStatus evaluations[] = new TTTGameStatus[9];
		char us = (them == X_SQUARE ? O_SQUARE : X_SQUARE);
		for (int i=0; i < 9; i++) {
			if (model.getSquare(i) != EMPTY_SQUARE) {
				// no evaluation for impossible move
				log.info("Counter move at index " + i + " is not possible");
				evaluations[i] = NOT_POSSIBLE;
			} else {
				// this is a possible move, evaluate it
				log.info("Counter move at index " + i + " is possible, we will evauate it");
				model.setSquare(i, us);
				evaluations[i] = makeMinMaxMove(model, us);
		  		if (isWinningMoveForPlayer(evaluations[i], us)) {
			  		// this would be a winning move
		  			log.info("Counter move at index " + i + " appears to be a winner for us, so we will make it");
		  			model.setStatus(checkStatus(model));
					return model; 
		  		}
		  		// this was not a guaranteed winning move, keep searching...
		  		log.info("Counter move at index " + i + " was not a guaranteed winner");
				model.setSquare(i, EMPTY_SQUARE);
			} 
		}

		// there are no guaranteed winning moves
		// unless opponent screws up, the best we can hope for is a tie
		log.info("There were no guaranteed winning counter moves");
	  	for (int i=0; i < 9; i++) {
			if (evaluations[i] == TIE) {
				log.info("Counter move at index " + i + " is a tie for us and will be selected");
				model.setSquare(i, us);
	  			model.setStatus(checkStatus(model));
				return model; 
			}
	  	}

	  	// uh-oh, there are no guaranteed wins or ties
		// it looks like the opponent could win no matter what
	  	log.warn("There were no guaranteed tie moves");
		for (int i=0; i < 9; i++) {
			if (isWinningMoveForPlayer(evaluations[i], them)) {
				log.warn("Counter move at index " + i + " is a loser for us and will be selected");
				model.setSquare(i, us);
				model.setStatus(checkStatus(model));
				return model;
			}
		}		

		// the game must be over
		log.error("There are no possible moves - how could this happen?");
		status = checkStatus(model);
		model.setStatus(status);
		return model;

	}
	
			
	/**
	 * We have been given a {@link TTTModel} with a move that has already 
	 * been made by the "max" player.  Evaluate how good this move is from 
	 * the standpoint of the max player by assuming the opposing "min" player
	 * will now make the best counter move.
	 * 
	 * @param model
	 * @param maxPlayer
	 * @return the status of the game, assuming the min player makes the 
	 *         best counter move.
	 */
	private TTTGameStatus makeMinMaxMove(TTTModel model, char maxPlayer) {
		char minPlayer = (maxPlayer == X_SQUARE ? O_SQUARE : X_SQUARE);
		log.debug("Evaluating model: \n" + model);
	
		// what's the current game state?
		TTTGameStatus status = checkStatus(model);
		if (status != IN_PROGRESS) {
			// the last move by the max player caused the game to end 
			return status;
		}
		
		// the game is still in progress after the max user's move.
		// evaluate all possible min counter moves...
		TTTGameStatus[] evaluations = new TTTGameStatus[9];
		
		// for each possible min counter move...
		for (int i=0; i < 9; i++) {
			if (model.getSquare(i) != EMPTY_SQUARE) {
				evaluations[i] = NOT_POSSIBLE;
			} else {
				// this is a possible min counter move, evaluate it
				model.setSquare(i, minPlayer);
				evaluations[i] = makeMinMaxMove(model, minPlayer);
				model.setSquare(i, EMPTY_SQUARE);
				if (isWinningMoveForPlayer(evaluations[i], minPlayer)) {
					// the original max move looks like a winner for the min player 
					return (maxPlayer == X_SQUARE ? O_WIN : X_WIN);
				} 
			} 
		}

		// there are no guaranteed winning counter moves for the min player
		// unless min screws up, the best max can hope for is a tie
		for (int i=0; i < 9; i++) {
			if (evaluations[i] == TIE) {
				return TIE;
			}	
		}	
		
		// there are no min wins or ties...
		// min is a loser!
		return  (maxPlayer == X_SQUARE ? X_WIN : O_WIN);		
	}		
	
	
	/**
	 * Helper function to relate games state with a player's state
	 * 
	 * @param status
	 * @param player
	 * @return
	 */
	private boolean isWinningMoveForPlayer(TTTGameStatus status, char player) {
		return ((player == O_SQUARE) && (status == O_WIN) ||
				(player == X_SQUARE) && (status == X_WIN));
		
	}
	
		
	/**
	 * It is our turn to move.
	 * Evaluate the possible moves and return the best one, assuming
	 * the opponent will subsequently do the same.
	 *
	 * @return int
	 * @param model com.schwab.service.ttt.model.TTTModel
	 */
	/**
	private TTTGameStatus makeMaxMove(TTTModel model) {
	  int i;
	  TTTGameStatus status = model.checkStatus();
	  TTTGameStatus[] evaluations = new TTTGameStatus[9];

	  // this game is over 
	  if (status != IN_PROGRESS) return status;

	  // for each possible move...
	  for (i=0; i < 9; i++) {
		if (model.getSquare(i) == EMPTY_SQUARE) {
			// this is a possible move, evaluate it
			model.setSquare(i, O_SQUARE);
			evaluations[i] = makeMinMove(model);
			model.setSquare(i, EMPTY_SQUARE);
			if (evaluations[i] == O_WIN) {
				return O_WIN;
			}
		} else {
			evaluations[i] = NOT_POSSIBLE;
		}
	  }

	  // unless opponent screws up, the best we can hope for is a tie
	  for (i=0; i < 9; i++) {
		if (evaluations[i] == TIE) {
		  return TIE;
		}
	  }

	  // I am a meat popsicle
	  return X_WIN;
	}
	**/

	/**
	 * It is opponents turn to move.
	 * Evaluate the possible moves and assumes he does what is best for him
	 *
	 * Creation date: (11/21/2000 6:48:16 PM)
	 * @return int
	 * @param model com.schwab.service.ttt.model.TTTModel
	 */
/**	
	private TTTGameStatus makeMinMove(TTTModel model) {
	  int i;
	  TTTGameStatus status = model.checkStatus();
	  TTTGameStatus[] evaluations = new TTTGameStatus[9];

	  // this game is over 
	  if (status != IN_PROGRESS) return status;

	  // for each possible move...
	  for (i=0; i < 9; i++) {
		if (model.getSquare(i) == EMPTY_SQUARE) {
			// this is a possible move, evaluate it
			model.setSquare(i, X_SQUARE);
			evaluations[i] = makeMaxMove(model);
			model.setSquare(i, EMPTY_SQUARE);
			if (evaluations[i] == X_WIN) {
				return X_WIN;
			}
		} else {
			evaluations[i] = NOT_POSSIBLE;
		}
	  }

	  // the best he can hope for is a tie
	  for (i=0; i < 9; i++) {
		if (evaluations[i] == TIE) {
		  return TIE;
		}
	  }

	  // Opponent is a meat popsicle
	  return O_WIN;
	}
**/
	/**
	 * This method performs the next move in a game of Tic Tac Toe.
	 * This method looks for a loaded Tic Tac Toe board in the BusinessResults, 
	 * but if there isn't one it uses an empty board.  After making a move, the 
	 * TTTModel is returned as a BusinessResults object.  This scenario always
	 * returns successfully.
	 * <p>
	 * @return boolean
	 * @param parameters com.schwab.base.model.BusinessParameters
	 * @param result com.schwab.base.model.BusinessResult
	 */
/**	
	public TTTModel makeMove(TTTModel model) {

		// are we given an existing board?
		if (model == null) {
			model = new TTTModel();  // start with a new board
		} 
		
		// check the status - is the game already over?
		if (model.checkStatus() != IN_PROGRESS) {
			return model; // game over
		}

		// check for possible moves...
		TTTGameStatus[] evaluations = new TTTGameStatus[9];
		int i=0;
		
		for (i=0; i < 9; i++) {
			if (model.getSquare(i) == EMPTY_SQUARE) {
				// this is a possible move, evaluate it
				model.setSquare(i, O_SQUARE);
				evaluations[i] = makeMinMove(model);
		  		if (evaluations[i] == O_WIN) {
			  		// this would be a winning move
			  		model.checkStatus();
					return model; 
		  		}
		  		// this was not a winning move, don't do it
				model.setSquare(i, EMPTY_SQUARE);
			} else {
				// no evaluation for impossible move
				evaluations[i] = NOT_POSSIBLE;
			}
		}

		// unless opponent screws up, the best we can hope for is a tie
	  	for (i=0; i < 9; i++) {
			if (evaluations[i] == TIE) {
				model.setSquare(i, O_SQUARE);
				model.checkStatus();
				return model;
			}
	  	}

		// I am a meat popsicle
		for (i=0; i < 9; i++) {
			if (evaluations[i] == X_WIN) {
				model.setSquare(i, O_SQUARE);
				model.checkStatus();
				return model;
			}
		}		

		// the game must be over
		model.checkStatus();
		return model;
	}
**/
	
	/**
	 * Determine the state of the Tic Tac Toe game
	 * 
	 * @param model
	 * @return TTTGameStatus 
	 * Status code, according to <code>TTTGameStatus</code>.
	 */
	private TTTGameStatus checkStatus(TTTModel model) {
		
		// check to see if 'O' won the game
		if (((model.getSquare(0) == O_SQUARE) && (model.getSquare(1) == O_SQUARE) && (model.getSquare(2) == O_SQUARE))
			|| ((model.getSquare(3) == O_SQUARE) && (model.getSquare(4) == O_SQUARE) && (model.getSquare(5) == O_SQUARE))
			|| ((model.getSquare(6) == O_SQUARE) && (model.getSquare(7) == O_SQUARE) && (model.getSquare(8) == O_SQUARE))
			|| ((model.getSquare(0) == O_SQUARE) && (model.getSquare(3) == O_SQUARE) && (model.getSquare(6) == O_SQUARE))
			|| ((model.getSquare(1) == O_SQUARE) && (model.getSquare(4) == O_SQUARE) && (model.getSquare(7) == O_SQUARE))
			|| ((model.getSquare(2) == O_SQUARE) && (model.getSquare(5) == O_SQUARE) && (model.getSquare(8) == O_SQUARE))
			|| ((model.getSquare(0) == O_SQUARE) && (model.getSquare(4) == O_SQUARE) && (model.getSquare(8) == O_SQUARE))
			|| ((model.getSquare(2) == O_SQUARE) && (model.getSquare(4) == O_SQUARE) && (model.getSquare(6) == O_SQUARE))) 
		{
			return O_WIN;
		}
		
		// check to see if 'X' won the game
		if (((model.getSquare(0) == X_SQUARE) && (model.getSquare(1) == X_SQUARE) && (model.getSquare(2) == X_SQUARE))
			|| ((model.getSquare(3) == X_SQUARE) && (model.getSquare(4) == X_SQUARE) && (model.getSquare(5) == X_SQUARE))
			|| ((model.getSquare(6) == X_SQUARE) && (model.getSquare(7) == X_SQUARE) && (model.getSquare(8) == X_SQUARE))
			|| ((model.getSquare(0) == X_SQUARE) && (model.getSquare(3) == X_SQUARE) && (model.getSquare(6) == X_SQUARE))
			|| ((model.getSquare(1) == X_SQUARE) && (model.getSquare(4) == X_SQUARE) && (model.getSquare(7) == X_SQUARE))
			|| ((model.getSquare(2) == X_SQUARE) && (model.getSquare(5) == X_SQUARE) && (model.getSquare(8) == X_SQUARE))
			|| ((model.getSquare(0) == X_SQUARE) && (model.getSquare(4) == X_SQUARE) && (model.getSquare(8) == X_SQUARE))
			|| ((model.getSquare(2) == X_SQUARE) && (model.getSquare(4) == X_SQUARE) && (model.getSquare(6) == X_SQUARE))) 
		{
			return X_WIN;
		}

		// check to see if the game is a draw
		int i = 0;
		while (model.getSquare(i) != EMPTY_SQUARE) {
			if (i == 8) {
				// there are no more free squares
				return TIE;
			}
			i++;
		}

		// the game must still be in progress
		return IN_PROGRESS;
	}
	
}
