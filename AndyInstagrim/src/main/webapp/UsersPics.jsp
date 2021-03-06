<%-- 
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>

<%--
    Last Modified : Oct 12, 2014
    Modified by   : Zandes Dimitrios
    Modification  : Add a link to "logout.jsp", develop "delete photo".
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.servlets.*" %>
<%@ page import="java.util.*"%>
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
            <h1>DiminstaGrim! </h1>
            <h2>Your world in Black and White</h2>
        </header>
        
        <nav>
            <ul>
                <li class="nav"><a href="/Diminstagrim/Scroll/<%=lg.getUsername()%>">Your "scroll" images</a></li>
                <li class="nav"><a href="/Diminstagrim/upload.jsp">Upload</a></li>
                <li class="nav"><a href="/Diminstagrim/Images/majed">Sample Images</a></li>
                <li class="nav"><a href="/Diminstagrim/logout.jsp">Logout</a></li>
            </ul>
        </nav>
 
        <article>
        
            <h1>Your Pics</h1>
            
            <%
                java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
                if (lsPics == null) {
            %>
            <p>No Pictures found</p>
            <%
                } else {
                    
                	Iterator<Pic> iterator;
                    iterator = lsPics.iterator();
                    while (iterator.hasNext()) {
                         
                    	Pic p = (Pic) iterator.next();

            %>
           
            <br/>
            
            <div class="picsContainer">
            
            <% GiveTitle temp = new GiveTitle(); %>
            <%=temp.getTitle(p.getUUID()) %>
            <br />
            <a href="/Diminstagrim/Image/<%=p.getSUUID()%>" ><img src="/Diminstagrim/Thumb/<%=p.getSUUID()%>"></a><br/>
            <%=p.getSUUID() %><br/>
            <form method="get" action="/Diminstagrim/Original/<%=p.getSUUID()%>">
                <input type="submit" value="Original Pic">
            </form>
            <form method="get" action="/Diminstagrim/SketchFilter/<%=p.getSUUID()%>">
                <input type="submit" value="Sketch Filter">
            </form>
            <%
                }
               }
            %>
            
            </div>
            
            <br/>
            
            <div class="formContainerThree">
            <form method="post" action="/Diminstagrim/DeletePhoto">
               Copy and paste the UUID of the photo you want to delete : <input type="text" name="photoToDelete"><br/>
               <input type="submit" value="Delete Photo">
            </form>
            </div>
            <br/>
            
            
            
        </article>
        
        <footer>
            <ul>
                <li class="footer"><a href="/Diminstagrim">Home</a></li>
            </ul>
        </footer>
        
    </body>
    
</html>
