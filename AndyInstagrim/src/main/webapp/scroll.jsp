<%-- 
    Document   : scroll
    Created on : Oct 15, 2014
    Author     : Zandes Dimitrios
--%>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.models.*" %>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>

    <head>
       <title>Diminstagrim</title>
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <link rel="stylesheet" type="text/css" href="/Diminstagrim/scroll.css" />
       <script src="<c:url value="/Diminstagrim/latestversion.js" />"></script>   
    </head>

    <body>
    
       <header>
           <h1>DiminstaGrim!</h1>
           <h2>Your world in Black and White</h2>
       </header>
       
           <%
              java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
              if(lsPics == null) {
           %>
           <p>No Pictures found</p>
           
           <div id="wrapper">
             <div id="carousel">
             <%
                  } else {
                    
                	Iterator<Pic> iterator;
                    iterator = lsPics.iterator();
                    while (iterator.hasNext()) {
                         
                    	Pic p = (Pic) iterator.next();
             %>
                 <a href="/Diminstagrim/Image/<%=p.getSUUID()%>" ><img src="/Diminstagrim/Thumb/<%=p.getSUUID()%>"></a>
             <% }
                    }%>
             </div>
           </div>
           
           <script src="<c:url value="/Diminstagrim/scroll.js" />"></script> 
    
</html>