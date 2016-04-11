/**
 *
 */
package com.darringer.games.ttt.web;

import static org.junit.Assert.*;
import static com.darringer.games.ttt.model.TTTModel.X_SQUARE;
import static com.darringer.games.ttt.model.TTTModel.O_SQUARE;
import static com.darringer.games.ttt.model.TTTModel.EMPTY_SQUARE;
import static com.darringer.games.ttt.model.TTTGameStatus.IN_PROGRESS;

import org.junit.Test;

import com.darringer.games.ttt.model.TTTModel;

/**
 * @author Group11
 *
 */
public class TTTJSPViewTest {

	/**
	 * Test method for {@link com.darringer.games.ttt.web.TTTJSPView#TTTJSPView()}.
	 */
	@Test
	public void testTTTJSPView() {
		TTTJSPView tttJSPView = new TTTJSPView();		// Nothing to test here as this is not being used
	}

	/**
	 * Test method for {@link com.darringer.games.ttt.web.TTTJSPView#getSquare(int)}.
	 */
	@Test
	public void testGetSquare() {
		TTTModel model;
		model = new TTTModel("x-------o");
		TTTJSPView tttJSPView = new TTTJSPView(model);

		assertEquals(tttJSPView.getSquare(0), X_SQUARE);
		assertEquals(tttJSPView.getSquare(8), O_SQUARE);
		assertEquals(tttJSPView.getSquare(7), EMPTY_SQUARE);
	}

	/**
	 * Test method for {@link com.darringer.games.ttt.web.TTTJSPView#getBoardAsString()}.
	 */
	@Test
	public void testGetBoardAsString() {
		TTTModel model;
		model = new TTTModel("x-------o");
		TTTJSPView tttJSPView = new TTTJSPView(model);
		assertEquals(tttJSPView.getBoardAsString(), "x-------o");

	}

	/**
	 * Test method for {@link com.darringer.games.ttt.web.TTTJSPView#getStatus()}.
	 */
	@Test
	public void testGetStatus() {
		TTTModel model;
		model = new TTTModel("x-------o");
		model.setStatus(IN_PROGRESS);
		TTTJSPView tttJSPView = new TTTJSPView(model);
		assertEquals(tttJSPView.getStatus(), IN_PROGRESS);
	}

}
