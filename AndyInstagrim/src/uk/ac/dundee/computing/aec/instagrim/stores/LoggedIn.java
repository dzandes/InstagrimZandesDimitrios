/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.stores;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Administrator
 */

/**
 * Modified by : Zandes Dimitrios
 * Date : 9/10/2014
 * Modification  : Extension of the class with more fields
 * 
 * */

public class LoggedIn {
	
    boolean logedin = false;
    String Username = null;
    String firstName = null;
    String lastName = null;
    Set email = new HashSet<String>();
    String theme = null;
    
    public void LogedIn(){
        
    }
    
    public void setUsername(String name){
        this.Username=name;
    }
    public String getUsername(){
        return Username;
    }
    
    
    public void setFirstName(String name){
    	this.firstName = name;
    }
    public void setLastName(String name){
    	this.lastName = name;
    }
    public String getFirstName(){
    	return firstName;
    }
    public String getLastName(){
    	return lastName;
    }
    public void setEmail(Set email){
    	this.email = email;
    }
    public Set getMail(){
    	return email;
    }
    public void setTheme(String t){
    	this.theme = t;
    }
    public String getTheme(){
    	return theme;
    }
    
    
    public void setLogedin(){
        logedin=true;
    }
    public void setLogedout(){
        logedin=false;
    }
    
    public void setLoginState(boolean logedin){
        this.logedin=logedin;
    }
    public boolean getlogedin(){
        return logedin;
    }
}
