package ramani;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fullname = request.getParameter("fullname");
		String gender = request.getParameter("gender");
		String DoB = request.getParameter("dob");
		String place =  request.getParameter("place");
		String username = request.getParameter("username");
		String epass = request.getParameter("epassword");
		String cpass = request.getParameter("cpassword");
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date date = null;

		try 
		{
			date = sdf1.parse(DoB);
		}
		catch (ParseException e1) 
		{
			e1.printStackTrace();
		}
		
		java.sql.Date dob = new java.sql.Date(date.getTime());  
		
		User user = new User(fullname,gender,dob,place,username,epass);
		
		try
		{
			if(epass.equals(cpass))
			{
				DBHandler.insertUser(user);
				DBHandler.CreateUserTable(username);
				response.sendRedirect("index.jsp");
			}
			else
			{
				response.sendRedirect("register.jsp");
			}
		}
		catch(SQLException e)
		{
			response.sendRedirect("ErrorPage.jsp");
			e.printStackTrace();
		}
	}

}
