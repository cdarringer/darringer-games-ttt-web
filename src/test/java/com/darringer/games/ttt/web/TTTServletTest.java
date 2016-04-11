/**
 *
 */
package com.darringer.games.ttt.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import com.darringer.games.ttt.model.TTTModel;

/**
 * @author Nanda
 *
 */
public class TTTServletTest extends Mockito{

	/**
	 * Test method for {@link com.darringer.games.ttt.web.TTTServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 */
	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() throws Exception{
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        ServletContext servletContext = mock(ServletContext.class);

		TTTModel model;
		model = new TTTModel("x--------");

        when(request.getParameter("board")).thenReturn(model.toString());

        TTTServlet tttServlet = mock(TTTServlet.class);
        Mockito.doCallRealMethod().when(tttServlet).doGet(request, response);   // Call the real method in this case

        when(tttServlet.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);

        // Repeat this for the different locations to verify
        for (Integer i=0; i<9; i++){
	        when(request.getParameter(i.toString() + ".x")).thenReturn("True");
	        // Now call the actual method to be tested
	        tttServlet.doGet(request, response);
	        // Set the request return for this index to NULL so that it does not get repeated again
	        when(request.getParameter(i.toString() + ".x")).thenReturn(null);

        }

        // Ensure that the request was parsed
        verify(request, atLeast(9)).getParameter("board"); // Verify if the board parameter was received as part of input request
        verify(servletContext, times(9)).getRequestDispatcher("/ttt.jsp"); // verify it a valid jsp was requested
        verify(requestDispatcher, times(9)).forward(request, response);  // Verify if the request dispatcher was called once
	}

	/**
	 * Test method for {@link com.darringer.games.ttt.web.TTTServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 */
	@Test
	public void testDoGetHttpServletRequestHttpServletResponseNewGame() throws Exception{
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        ServletContext servletContext = mock(ServletContext.class);

        when(request.getParameter("board")).thenReturn(null);   // Return new Game board

        TTTServlet tttServlet = mock(TTTServlet.class);
        Mockito.doCallRealMethod().when(tttServlet).doGet(request, response);   // Call the real method in this case

        when(tttServlet.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);

        // Repeat this for the different locations to verify
        for (Integer i=0; i<9; i++){
	        when(request.getParameter(i.toString() + ".x")).thenReturn("True");
	        // Now call the actual method to be tested
	        tttServlet.doGet(request, response);
	        // Set the request return for this index to NULL so that it does not get repeated again
	        when(request.getParameter(i.toString() + ".x")).thenReturn(null);

        }

        // Ensure that the request was parsed
        verify(request, atLeast(9)).getParameter("board"); // Verify if the board parameter was received as part of input request
        verify(servletContext, times(9)).getRequestDispatcher("/ttt.jsp"); // verify it a valid jsp was requested
        verify(requestDispatcher, times(9)).forward(request, response);  // Verify if the request dispatcher was called once

	}
	/**
	 * Test method for {@link com.darringer.games.ttt.web.TTTServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

		// Just ensure that the Servlet creation works fine
        TTTServlet tttServlet = new TTTServlet();
        tttServlet.doPost(request, response);

	}

}
