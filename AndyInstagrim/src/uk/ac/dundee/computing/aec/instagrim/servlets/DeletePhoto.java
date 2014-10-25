package uk.ac.dundee.computing.aec.instagrim.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ac.dundee.computing.aec.instagrim.models.PicModel;

import com.datastax.driver.core.Cluster;

/**
*
* @author Zandes Dimitrios
* @since Oct 14, 2014
* 	
*/

@WebServlet(name="DeletePhoto", urlPatterns={"/DeletePhoto"})

public class DeletePhoto extends HttpServlet {
	
	Cluster cluster;
	
	public DeletePhoto() {};
	
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
    	
    	String photoCode = request.getParameter("photoToDelete");
    	
    	java.util.UUID photoUUID = java.util.UUID.fromString(photoCode);
    	
    	try{
    		
    		PicModel picm = new PicModel();
    		
    		picm.deletePic(photoUUID);
    		
    		System.out.println("Photo deleted");
    		
    		response.sendRedirect("index.jsp");
    		
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		
    	}
    	
    }

}
