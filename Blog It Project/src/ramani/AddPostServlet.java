package ramani;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ramani.DBHandler;

@WebServlet("/AddPostServlet")
public final class AddPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String body = request.getParameter("body");
		Object UNSobj = request.getSession().getAttribute("userNameSession");
		String UNS = UNSobj.toString();
		
		Post currentpost = new Post(body);
		
		try {
			DBHandler.insertPost(currentpost, UNS);
			response.sendRedirect("MainHome.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
