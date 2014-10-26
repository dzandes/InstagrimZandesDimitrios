package uk.ac.dundee.computing.aec.instagrim.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

import com.datastax.driver.core.Cluster;

/**
*
* @author Zandes Dimitrios
* @since Oct 19, 2014
*/
@WebServlet(name = "Dark", urlPatterns = {"/Dark"})

public class Dark extends HttpServlet{
	
	Cluster cluster;
	
	public Dark(){};
	
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	
    	HttpSession sessionhttp;
    	sessionhttp=request.getSession();
    	LoggedIn lg = (LoggedIn) sessionhttp.getAttribute("LoggedIn");
    	String userName = lg.getUsername();
    	
    	try{
    		
    		User us = new User();
    		
    		us.setThemePref(lg, userName);
    		
    		response.sendRedirect("index.jsp");
    		
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		
    	}
    	
    }

}
