<%-- 
    Document   : logout
    Created on : Oct 4, 2014
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
                <li class="nav"><a href="upload.jsp">Upload</a></li>
                <li class="nav"><a href="/Diminstagrim/Images/majed">Sample Images</a></li>
            </ul>
        </nav>
        
        <p>Do you really want to log out..?</p>
         
        <form method="get" action="Logout">
            <input type="submit" value="Yes">
        </form>
        
        <form action="/Diminstagrim">
            <input type="submit" value="No">
        </form>

    </body>
    
</html>