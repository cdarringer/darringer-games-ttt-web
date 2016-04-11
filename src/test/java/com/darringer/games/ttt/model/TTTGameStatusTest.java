/**
 *
 */
package com.darringer.games.ttt.model;

import org.junit.Test;
import com.darringer.games.ttt.model.TTTGameStatus;

/**
 * @author Group11
 *
 */
public class TTTGameStatusTest {
	@Test
	public void testGameStatus() {
		for (TTTGameStatus value : TTTGameStatus.values()) {
			switch (value) {
			case O_WIN:
				break;
			case TIE:
				break;
			case X_WIN:
				break;
			case IN_PROGRESS:
				break;
			case NOT_POSSIBLE:
				break;
			case UNKNOWN:
				break;
			default:
				throw new IllegalArgumentException(value.toString());
			}

			for (String s : new String[] { "O_WIN", "TIE", "X_WIN",
					"IN_PROGRESS", "NOT_POSSIBLE", "UNKNOWN" }) {
				TTTGameStatus.valueOf(s);
			}
		}

	}
}
