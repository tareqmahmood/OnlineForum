<%-- 
    Document   : recent
    Created on : Nov 30, 2016, 11:28:18 PM
    Author     : HP
--%>

<%@page import="model.Post"%>
<%@page import="java.util.ArrayList"%>
<%@page import="db.DataAccess"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
    int user_id = 0;
    if(session.getAttribute("user_id") == null)
    {
        RequestDispatcher rd = request.getRequestDispatcher("start.jsp");
        rd.forward(request, response);
    }
    user_id = (Integer) session.getAttribute("user_id");
%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recent Posts</title>
    </head>
    <body>
        <a href="home.jsp">home</a> 
        <a href="recent.jsp">recent</a> 
        <a href="ShowFavourite.do">favorites</a> 
        <a href="Logout.do">logout</a> </br>
        <h2>Recent Posts</h2>
        <p>
        <ul>
        <%
            DataAccess db = DataAccess.getDataAccess(request.getSession());
            ArrayList<Post> posts = db.recentPosts();
            for(Post p : posts)
            {
                String puser = DataAccess.getDataAccess(request.getSession()).getUsername(p.getUser_id());
                out.println(String.format("<li> <a href = \"post.jsp?post_id=%s\">%s</a> by <b>%s</b> at %s </li>", p.getPost_id()+"", p.getTitle(), puser, p.getDatetime()));
            }
        %>
        </ul>
        </p>
    </body>
</html>
