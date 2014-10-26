<%-- 
    Document   : emptyUserPassRegisterError.jsp
    Created on : Oct 5, 2014
    Author     : Zandes Dimitrios
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
           <h1>DiminstaGrim ! </h1>
           <h2>Your world in Black and White</h2>
      </header>
      
      <script type="text/javascript">
          alert("Username or password(or both) you provided is empty. Click \"Close\" and start the register process again.");
      </script>
       
      <p><b>Click the "Register Repeat" button to start the process again.</b></p>
      
      <form action="/Diminstagrim/register.jsp">
         <input type="submit" value="Register Repeat">
      </form>

   </body>
   
</html>