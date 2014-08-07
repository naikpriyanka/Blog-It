package ramani;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")

public final class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = null;
				try
				{
					if(password.length() != 0 && username.length() != 0)
						user = DBHandler.getUser(username,password);
					if(user != null)
					{
						HttpSession session = request.getSession();
						session.setAttribute("userNameSession", username);
						response.sendRedirect("MainHome.jsp");
					}
					else
					{
						response.sendRedirect("index.jsp");
					}
				}
				catch(SQLException e)
				{
					response.sendRedirect("ErrorPage.jsp");
					e.printStackTrace();
				}
				catch(UserNotFoundException e)
				{
					response.sendRedirect("index.jsp");
				}
	}
}
