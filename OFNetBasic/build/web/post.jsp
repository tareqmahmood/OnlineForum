<%-- 
    Document   : post
    Created on : Dec 1, 2016, 2:45:14 AM
    Author     : HP
--%>

<%@page import="model.Post"%>
<%@page import="db.DataAccess"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
    String username = (String)session.getAttribute("username");
    //System.out.println("Hello " + username);
    if(username == null)
    {
        RequestDispatcher rd = request.getRequestDispatcher("start.html");
        rd.forward(request, response);
    }
    int post_id = Integer.parseInt(request.getParameter("post_id"));
    DataAccess db = DataAccess.getDataAccess(request.getSession());
    Post post = db.getPost(post_id);
    String postUser = DataAccess.getDataAccess(request.getSession()).getUsername(post.getUser_id());
%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%
            out.println("<title>" + post.getTitle() +"</title>"); 
        %>
    </head>
    <body>
        <a href="home.jsp">home</a> 
        <a href="recent.jsp">recent</a> 
        <a href="ShowFavourite.do">favorites</a> 
        <a href="Logout.do">logout</a> </br>
        <%
            out.println("<h1>" + post.getTitle() + "</h1>");
            out.println("<h2> by <i>" + postUser + "</i></h2>");
            out.println("<p>" + post.getContent() + "</p>");
        %>
    </body>
</html>
