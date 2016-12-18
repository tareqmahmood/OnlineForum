<%-- 
    Document   : recent
    Created on : Nov 30, 2016, 11:28:18 PM
    Author     : HP
--%>

<%@page import="template.Hierarchy"%>
<%@page import="template.QuickLink"%>
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
    int category_id = 0;
    if(request.getParameter("category_id") == null) category_id = -1;
    else category_id = Integer.parseInt(request.getParameter("category_id"));
    System.out.println("recent : " + category_id);
%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recent Posts</title>
    </head>
    <body>
        <% out.print(QuickLink.quicklinks); %>
        <h2>Recent Posts</h2>
        <table>
        <%
            DataAccess db = DataAccess.getDataAccess(request.getSession());
            boolean isAdmin = db.isAdmin(user_id);
            ArrayList<Post> posts;
            
            if(category_id == -1) posts = db.recentPosts();
            else posts = db.recentCategorisedPosts(category_id);
            for(Post p : posts)
            {
                String puser = DataAccess.getDataAccess(request.getSession()).getUsername(p.getUser_id());
                if(p.getUser_id() == user_id || isAdmin)
                    out.println(String.format("<tr> <td><a href = 'post.jsp?post_id=%d'>%s</a></td> "
                            + "<td>by <b>%s</b></td><td> on %s</td>"
                            + "<td><a href='DeletePost.do?post_id=%d' style='padding-left:2em'>Delete Post</a></td>"
                            + "</tr>", p.getPost_id(), p.getTitle(), puser, p.getDatetime(), p.getPost_id()));
                else
                    out.println(String.format("<tr><td><a href = 'post.jsp?post_id=%s'>%s</a></td>"
                            + "<td>by <b>%s</b></td><td> on %s</td>"
                            + "</tr>", p.getPost_id()+"", p.getTitle(), puser, p.getDatetime()));
            }
        %>
        </table>
        <h3>Filter by category</h3>
        <% out.print(Hierarchy.getString(session)); %>
    </body>
</html>
