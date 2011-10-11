package com.darringer.games.ttt.web;

import static com.darringer.games.ttt.model.TTTModel.X_SQUARE;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.darringer.games.ttt.control.TTTGameController;
import com.darringer.games.ttt.model.TTTModel;

/**
 * Main control servlet for the Tic Tac Toe application.
 * Creates a {@link TTTModel} and uses a {@link TTTGameController} 
 * to evaluate a player's move and the program's counter move.
 * 
 * @author cdarringer
 * @see TTTModel
 * @see TTTGameController
 */
@WebServlet("/TTTServlet")
public class TTTServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(TTTServlet.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TTTServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// get the optional board string from the request
		TTTModel model;
		String board = request.getParameter("board");
		if (board == null) {
			// this is a new game
			log.info("This appears to be a new game...");
			model = new TTTModel();
		} else {
			// this is an existing game - get the other player's move
			log.info("This appears to be an existing game...");
			model = new TTTModel(board);			
			int moveIndex = -1;
			if (request.getParameter("0.x") != null) {
				moveIndex = 0;
			} else if (request.getParameter("1.x") != null) {
				moveIndex = 1;
			} else if (request.getParameter("2.x") != null) {
				moveIndex = 2;
			} else if (request.getParameter("3.x") != null) {
				moveIndex = 3;
			} else if (request.getParameter("4.x") != null) {
				moveIndex = 4;
			} else if (request.getParameter("5.x") != null) {
				moveIndex = 5;
			} else if (request.getParameter("6.x") != null) {
				moveIndex = 6;
			} else if (request.getParameter("7.x") != null) {
				moveIndex = 7;
			} else if (request.getParameter("8.x") != null) {
				moveIndex = 8;
			}

			// apply the user's move and determine our counter move
			TTTGameController controller = new TTTGameController();
			model = controller.makeMove(model, moveIndex, X_SQUARE);
		}
		
		// Create the JSP view from the model and return it to the JSP...
		TTTJSPView view = new TTTJSPView(model);
		request.setAttribute("tttJSPView", view);

		// forward the request to our presentation servlet
		String nextJSP = "/ttt.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
