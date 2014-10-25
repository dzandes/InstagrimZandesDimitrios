/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import uk.ac.dundee.computing.aec.instagrim.lib.AeSimpleSHA1;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author Administrator
 */

/**
*
* Last Modified : Oct 13, 2014
* Modified by : Zandes Dimitrios
* 
*/

public class User {
	
    Cluster cluster;
    
    String firstname;
	String lastname;
	Set email = new HashSet<String>();
	String themeSelec;
    
    public User(){
        
    }
    
    public boolean RegisterUser(String username, String Password, String firstname, String lastname, HashSet<String> emailaddress, String themeSele){
    	
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        
        try {
        	
            EncodedPassword= sha1handler.SHA1(Password);
            
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
        	
            System.out.println("Can't check your password");
            return false;
            
        }
        
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("insert into userprofiles (login,password,first_name,last_name,email,theme) Values(?,?,?,?,?,?)");
       
        BoundStatement boundStatement = new BoundStatement(ps);
        
        session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username,EncodedPassword,firstname,lastname,emailaddress,themeSele));
        //We are assuming this always works.  Also a transaction would be good here !
        
        System.out.println("User registered");
        
        return true;
        
    }
    
    
    // Methods added (deleteAccount, logoutUser, profileDetails, usernameExists, setThemePref, setThemePrefVersionTwo getThemePref)  
    public void deleteAccount(String userName){
    	
    	Session session = CassandraHosts.getCluster().connect("instagrim");
    	
    	PreparedStatement ps = session.prepare("delete from userprofiles where login=?");
   	    BoundStatement boundStatement = new BoundStatement(ps);
   	    session.execute(boundStatement.bind(userName));
   	    
   	    session.close();
    	
    }
    
    public void logoutUser(HttpSession session){
    	
            if(session != null){
		          session.invalidate();
            }
    	
    }
    
    public void profileDetails(LoggedIn l, Session session, String username){
    	
    	try{
       	 
       	   PreparedStatement ps = session.prepare("select first_name,last_name,email,theme from userprofiles where login=?");
       	   ResultSet rs = null;
       	   BoundStatement boundStatement = new BoundStatement(ps);
       	   rs = session.execute(boundStatement.bind(username));
       	 
       	   for(Row row : rs){
       		 
       		   firstname = row.getString("first_name");
       		   lastname = row.getString("last_name");
       		   email = row.getSet("email", String.class);
       		   themeSelec = row.getString("theme");
       				
       	   }
            
       	   l.setFirstName(firstname);
       	   l.setLastName(lastname);
       	   l.setEmail(email);
       	   l.setTheme(themeSelec);
    	
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		
    	}
    	
    	session.close();
    	
    }
    
    public int usernameExists(Session session, String username){
    	
    	int flag = 0;
    	
    	try{
    		
    		PreparedStatement ps = session.prepare("select login from userprofiles");
       	    ResultSet rs = null;
       	    BoundStatement boundStatement = new BoundStatement(ps);
       	    rs = session.execute(boundStatement.bind());
       	 
       	    for(Row row : rs){
       	    	
       	    	if(row.getString("login").equals(username)){
       	    		flag = 1;
       	    	}
       				
       	    }
       	    
       	    return flag;
    		
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		
    	}
    	
    	session.close();
    	
    	return flag;
    	
    }
    
    public void setThemePref(LoggedIn l, String uname){
    	
    	Session ses = CassandraHosts.getCluster().connect("instagrim");
    	
    	try{
    		
    		PreparedStatement ps = ses.prepare("update userprofiles SET theme=? where login=?");
       	    BoundStatement boundStatement = new BoundStatement(ps);
       	    ses.execute(boundStatement.bind("dark", uname));
       	    
       	    l.setTheme("dark");
    		
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		
    	}
    	
    	ses.close();
    	
    }
    
    public void setThemePrefVersionTwo(LoggedIn l, String uname){
    	
    	Session ses = CassandraHosts.getCluster().connect("instagrim");
    	
    	try{
    		
    		PreparedStatement ps = ses.prepare("update userprofiles SET theme=? where login=?");
       	    BoundStatement boundStatement = new BoundStatement(ps);
       	    ses.execute(boundStatement.bind("default", uname));
       	    
       	    l.setTheme("default");
    		
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		
    	}
    	
    	ses.close();
    	
    }
    
    public String getThemePref(String uname){
    	
    	String themeResult = null;
    	
    	Session session = CassandraHosts.getCluster().connect("instagrim");
    	PreparedStatement ps = session.prepare("select theme from userprofiles where login=?");
   	    ResultSet rs = null;
   	    BoundStatement boundStatement = new BoundStatement(ps);
   	    rs = session.execute(boundStatement.bind(uname));
   	    
   	    for(Row row : rs){
   	    	
   	    	themeResult = row.getString("theme");
   	    	
   	    }
   	    
   	    session.close();
   	    
   	    return themeResult;
    	
    }
    
    
    
    public boolean IsValidUser(String username, String Password){
    	
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        
        try {
        	
            EncodedPassword= sha1handler.SHA1(Password);
            
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
        	
            System.out.println("Can't check your password");
            return false;
        
        }
        
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        
        if (rs.isExhausted()) {
        
        	System.out.println("No Images returned");
            return false;
        
        }else{
        	
            for (Row row : rs) {
               
                String StoredPass = row.getString("password");
                
                if (StoredPass.compareTo(EncodedPassword) == 0)
                    return true;
            
            }
        }
   
        return false;
        
    }
       public void setCluster(Cluster cluster) {
    	   
        this.cluster = cluster;
        
    }

    
}
