
<%@page import="model.Post"%>
<%@page import="template.Hierarchy"%>
<%@page import="template.QuickLink"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Category"%>
<%@page import="db.DataAccess"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    int user_id = 0;
    if(session.getAttribute("user_id") == null)
    {
        RequestDispatcher rd = request.getRequestDispatcher("start.jsp");
        rd.forward(request, response);
    }
    user_id = (Integer) session.getAttribute("user_id");
    DataAccess db = DataAccess.getDataAccess(request.getSession());
%>
<head>
    <title>Online Forum</title>
</head>
<body>
    <%
        out.print(QuickLink.quicklinks);
        out.print(String.format("<h2>Welcome %s</h2>", db.getUsername(user_id)));
        //ArrayList<Category> categories = db.getAllCategories();
    %>
    <h2>Your Feed</h2>
    <table>
    <%
        db = DataAccess.getDataAccess(request.getSession());
        ArrayList<Post> posts = db.recentfavouritePosts(user_id);
        for(Post p : posts)
            {
                String puser = DataAccess.getDataAccess(request.getSession()).getUsername(p.getUser_id());
                out.println(String.format("<tr><td><a href = 'post.jsp?post_id=%s'>%s</a></td>"
                            + "<td>by <b>%s</b></td><td> on %s</td>"
                            + "</tr>", p.getPost_id()+"", p.getTitle(), puser, p.getDatetime()));
            }
    %>
    </table>
</body>
</html>
