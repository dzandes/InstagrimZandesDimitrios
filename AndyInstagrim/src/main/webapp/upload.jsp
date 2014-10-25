<%-- 
    Document   : upload
    Created on : Sep 22, 2014, 6:31:50 PM
    Author     : Administrator
--%>

<%--
    Last Modified : Oct 4, 2014
    Modified by   : Zandes Dimitrios
    Modification  : Add a link to "logout.jsp"
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
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
        
        <nav>
            <ul>
                <li class="nav"><a href="/Instagrim/Images/majed">Sample Images</a></li>
                <li class="nav"><a href="/Instagrim/logout.jsp">Logout</a></li>
            </ul>
        </nav>
 
        <article>
            <h3>File Upload</h3>
            
            <div class="formContainerTwo">
            
            <form method="POST" enctype="multipart/form-data" action="Image">
                File to upload : <input type="file" name="upfile"><br/>
                <br/>
                
                <input type="submit" value="Press"> to upload the file!
            </form>
            
            </div>

        </article>
        
        <footer>
            <ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
            </ul>
        </footer>
        
    </body>
    
</html>
