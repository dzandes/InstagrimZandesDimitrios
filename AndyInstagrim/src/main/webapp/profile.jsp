<%-- 
    Document   : profile
    Created on : Oct 8, 2014
    Author     : Zandes Dimitrios
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.servlets.*" %>
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
             <li><a href="upload.jsp">Upload</a></li>
             <li><a href="/Diminstagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>
             <li><a href="/Diminstagrim/logout.jsp">Logout</a></li>
          </ul>
      </nav>
      
      <p><b>Welcome <%=lg.getUsername()%>..!!! To view your profile details click the button below...</b></p>
      
      <form method="get" action="Profile">
            <input type="submit" value="View my profile">
      </form>
      
      <% Profile profile = new Profile(); %>
      
      <div id="prof">
        <p>USERNAME : <%=lg.getUsername() %></p>
        <p>FIRST NAME : <%=lg.getFirstName() %></p>
        <p>LAST NAME : <%=lg.getLastName() %></p>
        <p>EMAIL ADDRESS : <%=lg.getMail().toString() %></p>
        <p>THEME : <%=lg.getTheme() %></p>
      </div>
  
      <footer>
          <ul>
             <li class="footer"><a href="/Diminstagrim">Home</a></li>
          </ul>
      </footer>

   </body>
   
</html>