<%-- 
    Document   : post
    Created on : Dec 1, 2016, 2:45:14 AM
    Author     : HP
--%>

<%@page import="model.Vote"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Comment"%>
<%@page import="model.Post"%>
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
    
    
    int post_id = Integer.parseInt(request.getParameter("post_id"));
    DataAccess db = DataAccess.getDataAccess(request.getSession());
    Post post = db.getPost(post_id);
    String postUser = db.getUsername(post.getUser_id());
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
            out.println("<p style=\"font-size:13pt\">" + post.getContent() + "</p>");
            Vote vote = db.getVote(post_id);
        %>
        <form <% out.println(String.format("action=\"AddVote.do?post_id=%d\"", post_id)); %> method="post">
            <input type="submit" value=<% out.println(String.format("\"Up Vote (%d)\"", vote.getUpvote())); %> name="upvote"/>
            <input type="submit" value=<% out.println(String.format("\"Down Vote (%d)\"", vote.getDownvote())); %> name="downvote"/>
        </form>
        <h2>Comments</h2>
        
        <%
            ArrayList<Comment> commentList = db.getComments(post_id);
            for(Comment c : commentList)
            {
                out.println(String.format("<p> <span style=\"font-size:14pt\"><b> %s </b></span> <i>commented %s</i> <br><span style=\"margin-left: 0cm;font-size:13pt\"> %s </span> </p>", c.getUsername(), c.getTime(), c.getContent()));
            }
        %>
        
        <p>
            <form method="post" action="AddComment.do">
                <i>Add your comment</i><br>
                <textarea name="content" cols="40" rows="2"></textarea><br>
                <%
                out.println(String.format("<input type=\"hidden\" name=\"post_id\" value=\"%d\"><br>", post_id));
                %>
                <input type="submit" value="Comment" />
            </form>
        </p>
    </body>
</html>
