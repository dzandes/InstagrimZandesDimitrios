<%-- 
    Document   : uploadSuccess.jsp
    Created on : Oct 7, 2014
    Author     : Zandes Dimitrios
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>

   <head>
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <title>Diminstagrim</title>
     <% 
         LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
         if(lg.getTheme().equals("dark")){ %>
     <link rel="stylesheet" type="text/css" href="/Diminstagrim/black.css" />
     <% }else{ %>
     <link rel="stylesheet" type="text/css" href="/Diminstagrim/Styles.css" />
     <%} %>
   </head>
   
   <body>
   
     <header>
        <h1>DiminstaGrim!</h1>
        <h2>Your world in Black and White</h2>
     </header>
     
     <nav>
        <ul>
          <%  
                 if (lg != null) {
                            String UserName = lg.getUsername();
                 }
                 %> 
           <li><a href="/Diminstagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>
           <li><a href="/Diminstagrim/logout.jsp">Logout</a></li>
        </ul>
     </nav>
      
     <br />
     <p><b>Your photo was successfully uploaded...!!!</b></p>
     <br />
     
     <form method="POST"  action="/Diminstagrim/GiveTitle">
         <p><b>Add a title to your photo by texting here :</b></p> <input type="text" name="phototitle">
         <br />
         <input type="submit" value="Submit">
     </form>
     
     <br />
     
     <footer>
        <ul>
          <li class="footer"><a href="/Diminstagrim">Home</a></li>
        </ul>
     </footer>

   </body>
   
</html>