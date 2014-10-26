<%-- 
    Document   : register.jsp
    Created on : Sep 28, 2014, 6:29:51 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>

    <head>
    
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Diminstagrim</title>
        <link rel="stylesheet" type="text/css" href="/Diminstagrim/Styles.css" />
        
    </head>
    
    <body>
    
        <header>
           <h1>DiminstaGrim!</h1>
           <h2>Your world in Black and White</h2>
        </header>
        
        <nav>
            <ul>
                <li><a href="/Diminstagrim/Images/majed">Sample Images</a></li>
            </ul>
        </nav>
       
        <article>
        
            <h3>Register as user</h3>
            
            <br />
            <p><b>Username, password and theme fields are compulsory. For theme, give the word "dark" or "default".</b></p>
            <br />
            
            <div class="formContainer">  
            
               <form method="POST"  action="Register">
                  <ul>
                     <li><input type="text" name="username" placeholder="Enter username"></li>
                     <li><input type="password" name="password" placeholder="Password"></li>
                     <li><input type="text" name="firstname" placeholder="First Name"></li>
                     <li><input type="text" name="lastname" placeholder="Last Name"></li>
                     <li><input type="email" name="emailaddress" placeholder="Email Address"></li>
                     <li><input type="text" name="theme" placeholder="Theme"></li>
                  </ul>
                  <input type="submit" value="Register">
                  <br/>
                  
                  
               </form>
               
            </div>

        </article>
        
        <footer>
            <ul>
                <li class="footer"><a href="/Diminstagrim">Home</a></li>
            </ul>
        </footer>
        
    </body>
</html>
