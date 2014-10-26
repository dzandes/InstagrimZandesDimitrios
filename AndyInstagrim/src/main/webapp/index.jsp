<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator
--%>

<%--
    Last Modified : Oct 4, 2014
    Modified by   : Zandes Dimitrios
    Modification  : Add a link to "logout.jsp"
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>

    <head>
        <title>Diminstagrim</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <% 
           LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
           if (lg == null) {%>
        <link rel="stylesheet" type="text/css" href="/Diminstagrim/Styles.css" />
        <% }else if(lg.getTheme().equals("dark")){ %>
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
        
         <div class="menuContainer">
        
            <ul>
  
                <% // There is no point in having upload option here as the user has not logged in yet.
                   // <li><a href="upload.jsp">Upload</a></li> %>
                   
                    <%
                        
                        if (lg != null) {
                            String UserName = lg.getUsername();
                            if (lg.getlogedin()) {
                    %>

                           <li><a href="profile.jsp">View my profile</a></li>
                           <li><a href="upload.jsp">Upload</a></li>
                           <li><a href="/Diminstagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>
                           <li><a href="deleteAccount.jsp">Delete my account</a></li>
                           <li><a href="/Diminstagrim/logout.jsp">Logout</a></li>
                        
         </div>
                           
                           <div id="alwaysLast">
                             <div id="themeTi"><b><i>Page Theme : </i></b></div>
                                <form method="get" action="Dark">
                                  <input type="submit" value="Dark Theme">
                                </form>
                                <form method="get" action="Default">
                                  <input type="submit" value="Default">
                                </form>
                           </div>
                           
                           <%}else{%>
                    	
                               <li><a href="register.jsp">Register</a></li>
                               <li><a href="login.jsp">Login</a></li>
                    	
                           <%}
                            
                            }else{
                                %>
                                
             <div class="menuContainer">
                                
                     <li><a href="register.jsp">Register</a></li>
                     <li><a href="login.jsp">Login</a></li>
                <%                                 
                    }%>
            </ul>
            
            </div>
            
        
        <footer>
            <ul>
                <li class="footer"><a href="/Diminstagrim">Home</a></li>
                <li>&COPY; Andy C, modified by dzandes</li>
            </ul>  
        </footer>
        
        
    </body>
    
</html>
