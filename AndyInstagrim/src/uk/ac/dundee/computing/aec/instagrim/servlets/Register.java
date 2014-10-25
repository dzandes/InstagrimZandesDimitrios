/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

// import org.apache.jasper.tagplugins.jstl.core.Set;

import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.User;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})


/**
 * Last Modified by Zandes Dimitrios
 * Date : 3/10/2014
 * 
 * */

public class Register extends HttpServlet {
	
	private static final int maxSize = 16; // This is a variable for the maximum length of username/password. We can give it any size we want.
	                                       // Not giving a maximum size for usernames and passwords may lead to a possible error(database overload).
	                                       // So, it is actually a possible bug. I used a maximum size of 16 characters for each.
	
	// We need the constructor to import the class in usernamePassRegisterError.jsp, so that we can make use of getMaxSize() method there.
	public Register(){};
	
    Cluster cluster=null;
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }
    
    public int getMaxSize(){
    	
    	return maxSize;
    	
    }


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String emailaddress = request.getParameter("emailaddress");
        String themeSelected = request.getParameter("theme");
        
        Session session = CassandraHosts.getCluster().connect("instagrim");
    	
    	// Used for checking if a username already exists.
    	User u = new User();
    	u.setCluster(cluster);
    	int flag = u.usernameExists(session, username);
        
        // This string is used in order to make a check if the given(by the user) username and password contain special characters.
        String regex = "[^a-zA-Z0-9]";
        
        // Use of Pattern and Matcher to check if special characters are contained to our inputs.
        Pattern p = Pattern.compile(regex);
        Matcher m1 = p.matcher(username);
        Matcher m2 = p.matcher(password);
        boolean userCheck = m1.find();
        boolean passCheck = m2.find();
        
        if((username.length() > maxSize) || (password.length() > maxSize)){
        	
        	response.sendRedirect("/Instagrim/usernamePassRegisterError.jsp");
        	
        }else if((themeSelected.equals("dark") == false) && (themeSelected.equals("default") == false)){
        	
        	response.sendRedirect("/Instagrim/usernamePassRegisterError.jsp");
        	
        }else if(flag == 1){
        	
        	response.sendRedirect("/Instagrim/usernameExistError.jsp");
        	
        }else if((username.length() == 0) || (password.length() == 0)){
        	
        	response.sendRedirect("/Instagrim/emptyUserPassRegisterError.jsp");
        	
        }else if((userCheck == false) && (passCheck == false) && ((username.length() <= maxSize)) && (password.length() <= maxSize)){
        	
        	
        	HashSet<String> email= new HashSet<String>();
        	email.add(emailaddress);
        	User us = new User();
            us.setCluster(cluster);
            us.RegisterUser(username, password, firstname, lastname, email, themeSelected);
             
     	    response.sendRedirect("/Instagrim");
        	
        }else{
        	
        // If we get into the "else" section, it means one of our inputs(or both) contain(s) special character(s).
        	
        	response.sendRedirect("/Instagrim/usernamePassRegisterError.jsp");
        	
        }
        
        /* We could do the check for each one(username or password) separately but there is no point on it.
         * If either of them is wrong, then the message will pop up and inform the user to start the process again.
         * So, we do not care if for example the user gave both of the inputs wrong(username and password) at the 
         * same time. Even if we catch only one, it's enough.  */
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
