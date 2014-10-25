<%-- 
    Document   : deleteAccount
    Created on : Oct 11, 2014
    Author     : Zandes Dimitrios
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>

    <head>
       <title>Instagrim</title>
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <% 
           LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
           if(lg.getTheme().equals("dark")){ %>
        <link rel="stylesheet" type="text/css" href="/Instagrim/black.css" />
        <% }else{ %>
        <link rel="stylesheet" type="text/css" href="/Instagrim/Styles.css" />
        <%} %>
    </head>
    
    <body>
       
       <header>
           <h1>InstaGrim!</h1>
           <h2>Your world in Black and White</h2>
       </header>
       
       <% LoggedIn currentLg = (LoggedIn) session.getAttribute("LoggedIn");
          
          if(currentLg != null){
               String usernameToDelete = currentLg.getUsername(); } %>
       
       <nav>
          <ul>
            <li><a href="profile.jsp">View my profile</a></li>
            <li><a href="upload.jsp">Upload</a></li>
            <li><a href="/Instagrim/Images/<%=currentLg.getUsername()%>">Your Images</a></li>
            <li><a href="/Instagrim/logout.jsp">Logout</a></li>      
          </ul> 
       </nav>
       
       <p>Do you really want to delete your account..?</p>
       <br />
       
       <form method="get" action="DeleteAccount">
            <input type="submit" value="Yes">
       </form>
       
       <form action="/Instagrim">
            <input type="submit" value="No">
       </form>
       
    </body>

</html>