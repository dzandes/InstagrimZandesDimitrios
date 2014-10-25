<%-- 
    Document   : usernamePassRegisterError.jsp
    Created on : Oct 3, 2014, 13:20:39
    Author     : Zandes Dimitrios
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.servlets.*" %>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>

   <head>
   
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <title>Instagrim</title>
       <link rel="stylesheet" type="text/css" href="/Instagrim/Styles.css" />
       
   </head>
   
   <body>
   
       <header>
           <h1>InstaGrim ! </h1>
           <h2>Your world in Black and White</h2>
       </header>
       
       <% Register temp = new Register(); 
       
          int inputSize = temp.getMaxSize(); 
        %>
        
       <% // Some use of JavaScript for the pop up message %>
       
       <script type="text/javascript">
          alert("Username and passwords MUST NOT contain more than " + <%= inputSize %> + " characters and any special characters. Theme section MUST always be \"dark\" or \"default\". Click \"Close\" and start the register process again.");
       </script>
       
       <p><b>Click the "Register Repeat" button to start the process again.</b></p>
       
       <form action="/Instagrim/register.jsp">
         <input type="submit" value="Register Repeat">
       </form>

   </body>
   
</html>