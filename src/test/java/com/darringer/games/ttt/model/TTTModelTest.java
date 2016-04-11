/**
 *
 */
package com.darringer.games.ttt.model;

import static com.darringer.games.ttt.model.TTTModel.O_SQUARE;
import static com.darringer.games.ttt.model.TTTModel.X_SQUARE;
import static com.darringer.games.ttt.model.TTTModel.EMPTY_SQUARE;
import static com.darringer.games.ttt.model.TTTGameStatus.NOT_POSSIBLE;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Nanda
 *
 */
public class TTTModelTest {

	/**
	 * Test method for {@link com.darringer.games.ttt.model.TTTModel#getSquare(int)}.
	 */
	@Test
	public void testGetSquare() {
		TTTModel model;

		model = new TTTModel("-ox------");
		assertTrue(X_SQUARE == model.getSquare(2));

		model = new TTTModel("-xo------");
		assertTrue(O_SQUARE == model.getSquare(2));

		model = new TTTModel("-xx------");
		assertTrue(EMPTY_SQUARE == model.getSquare(-1));
	}


	/**
	 * Test method for {@link com.darringer.games.ttt.model.TTTModel#getStatus()}.
	 */
	@Test
	public void testGetStatus() {
		TTTModel model;

		model = new TTTModel("");
		assertTrue("Invalid games are not possible", NOT_POSSIBLE == model.getStatus());
	}

	/**
	 * Test method for {@link com.darringer.games.ttt.model.TTTModel#setStatus(com.darringer.games.ttt.model.TTTGameStatus)}.
	 */
	@Test
	public void testSetStatus() {
		TTTModel model;

		model = new TTTModel("");
		model.setStatus(NOT_POSSIBLE);
		assertTrue("Invalid games are not possible", NOT_POSSIBLE == model.getStatus());
	}

	/**
	 * Test method for {@link com.darringer.games.ttt.model.TTTModel#setSquare(int, char)}.
	 */
	@Test
	public void testSetSquare() {
		TTTModel model;
		model = new TTTModel("---------");
		model.setSquare(0, O_SQUARE);
		assertTrue("Square should be set to 'o'", O_SQUARE == model.getSquare(0));

		model = new TTTModel("---------");
		model.setSquare(8, X_SQUARE);
		assertTrue("Square should be set to 'x'", X_SQUARE == model.getSquare(8));

		model = new TTTModel("---------");
		model.setSquare(-1, X_SQUARE);
		assertTrue("Square should be set to 'x'", EMPTY_SQUARE == model.getSquare(-1));

		model = new TTTModel("---------");
		model.setSquare(9, X_SQUARE);
		assertTrue("Square should be set to 'x'", EMPTY_SQUARE == model.getSquare(9));

	}

}
