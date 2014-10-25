package uk.ac.dundee.computing.aec.instagrim.servlets;

import java.io.IOException;

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
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Clause;

/**
*
* @author Zandes Dimitrios
* @since Oct 11, 2014
* 
*/

@WebServlet(name = "DeleteAccount", urlPatterns = {"/DeleteAccount"})
public class DeleteAccount extends HttpServlet{
	
	Cluster cluster;
	
	public DeleteAccount(){};
	
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
    	LoggedIn currentLg = (LoggedIn) sessionhttp.getAttribute("LoggedIn");
    	String userName = currentLg.getUsername();
    	
    	try{
    		
    		User user = new User();
    		
    		user.deleteAccount(userName);
       	    
       	    System.out.println("Account with username " + userName + " deleted.");
       	    
       	    currentLg.setLoginState(false);
       	    
    	    response.sendRedirect("index.jsp");
    		
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		
    	}
    	
    }

}
