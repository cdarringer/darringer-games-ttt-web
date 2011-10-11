<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.darringer.games.ttt.model.TTTGameStatus"%>
<%@page import="com.darringer.games.ttt.model.TTTModel"%>

<jsp:useBean id="tttJSPView" class="com.darringer.games.ttt.web.TTTJSPView" scope="request"/>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Welcome to Tic Tac Toe!</title>
		<link rel="stylesheet" type="text/css" href="tttStyles.css" />
	</head>
	<body>
		<center>
			<form method="GET" action="TTTServlet">
				<input name="board" type="hidden" value="<%=tttJSPView.getBoardAsString() %>" />

				<!-- Display the game state -->
				<% switch (tttJSPView.getStatus()) { 
					case O_WIN: %>
						<p class="bad">Sorry, game over.</p>				
					<% break; 	
					case TIE: %>
						<p class="normal">The game was a tie.</p>
					<% break; 	
					case IN_PROGRESS: %>
						<p class="normal">Welcome to Tic Tac Toe...</p>
					<% break; 	
					case X_WIN: %>
						<p class="good">Congratulations, you won!</p>
					<% break; 	
					default: %>
						<p class="bad">Uh-oh, internal error.</p>
					<% break; 
				 } %>
				
				<!--  Draw the board -->
				<% for (int i=0; i < 9; i++) { %>
					<% if ((i % 3) == 0) { %>
						<br />
					<% } %>
					<% if (tttJSPView.getSquare(i) == TTTModel.X_SQUARE) { %>
						<img src="images/x.gif" alt="[X]" width="40" height="42" />
					<% } else if (tttJSPView.getSquare(i) == TTTModel.O_SQUARE) { %>
						<img src="images/o.gif" alt="[O]" width="40" height="42" />
					<% } else if (tttJSPView.getSquare(i) == TTTModel.EMPTY_SQUARE) { %>
						<input src="images/empty.gif" alt="[-]" width="40" height="42" border="0" name="<%=Integer.toString(i)%>" type="image" />
					<% } %> 
				<% } %>
			</form>
			
			<!--  Useful links -->
			<br />
			<p>
				You are <img src="images/x.gif" alt="[X]" width="40" height="42" />
			</p>
			<p>
				<a href="TTTServlet">Start over</a>
			</p>
			<p>
				<a href="http://www.darringer.com/index.html">Return to darringer.com</a>
			</p>
			<br />
			<p class="footer">
				LAST UPDATE: October 3, 2011
			</p>  
		</center>
	</body>
</html>