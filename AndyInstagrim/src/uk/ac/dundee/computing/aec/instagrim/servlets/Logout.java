package uk.ac.dundee.computing.aec.instagrim.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uk.ac.dundee.computing.aec.instagrim.models.User;

/**
*
* @author Zandes Dimitrios
* @since Oct 4, 2014
* 
*/
@WebServlet(name = "Logout", urlPatterns = {"/Logout"})

public class Logout extends HttpServlet{
	
	/**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		User user = new User();
		user.logoutUser(session);
		
		RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
        rd.forward(request,response);
		   
	}

}
