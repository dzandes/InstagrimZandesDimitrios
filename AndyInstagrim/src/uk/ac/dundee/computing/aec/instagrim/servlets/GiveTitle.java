package uk.ac.dundee.computing.aec.instagrim.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

import com.datastax.driver.core.Cluster;

/**
*
* @author Zandes Dimitrios
* @since Oct 16, 2014
*/
@WebServlet(name = "GiveTitle", urlPatterns = {"/GiveTitle"})

public class GiveTitle extends HttpServlet{
	
	Cluster cluster;
	
	public GiveTitle(){};
	
	/**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	
    	HttpSession sessionhttp;
    	sessionhttp=request.getSession();
    	LoggedIn lg = (LoggedIn) sessionhttp.getAttribute("LoggedIn");
    	String userName = lg.getUsername();
    	
    	String title = request.getParameter("phototitle");
    	String titleToGive;
    	
    	if(title.equals("")){
    		
    		titleToGive = "Untitled";
    		
    	}else{
    		
    		titleToGive = title;
    		
    	}
    	
    	System.out.println(userName + " - " + title);
    	
    	try{
    		
    		PicModel picm = new PicModel();
    		
    		picm.insertTitle(titleToGive, userName);
    		
    		System.out.println("Title inserted");
    		
    		response.sendRedirect("index.jsp");
    		
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		
    	}
    	
    }
    
    public String getTitle(java.util.UUID photoUUID){
    	
    	PicModel p = new PicModel();
    	
    	String s = p.retrieveTitle(photoUUID);
    	
    	return s;
    	
    }

}
