package uk.ac.dundee.computing.aec.instagrim.servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Statement;

/**
*
* @author Zandes Dimitrios
* @since Oct 9, 2014
*/

@WebServlet(name = "Profile", urlPatterns = {"/Profile", "/Profile/*"})
public class Profile extends HttpServlet{
	
	Cluster cluster;

	public Profile(){}
	

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
    	
    	Session session = CassandraHosts.getCluster().connect("instagrim");
    	HttpSession sessionhttp;
    	sessionhttp=request.getSession();
    	LoggedIn lg = (LoggedIn) sessionhttp.getAttribute("LoggedIn");
    	String userName = lg.getUsername();
    	
    	try{
    		
    		User user = new User();
        	
        	user.profileDetails(lg, session, userName);
            	 
            response.sendRedirect("/Instagrim/profile.jsp");
    		
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		
    	}
    	 
    }
	

}
