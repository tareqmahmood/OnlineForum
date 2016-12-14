<%-- 
    Document   : addpost
    Created on : Dec 14, 2016, 11:46:12 AM
    Author     : HP
--%>

<%@page import="template.QuickLink"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Category"%>
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
    DataAccess db = DataAccess.getDataAccess(request.getSession());
    out.print(QuickLink.quicklinks);
%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Write a post</title>
    </head>
    <body>
<p>
<form method="post" action="AddPost.do">
    Title <input type="text" name="title" /><br>
    Type your thoughts <br>
    <textarea name="content" cols="40" rows="5"></textarea><br>
    <%
        ArrayList<Category> categories = db.getAllCategories();
        for(Category ctg : categories)
        {
            out.println(String.format("<input type=\"checkbox\" name=\"category\" value=\"%d\"> %s<br>", ctg.getCategory_id(), ctg.getCategory_name()));
        }
    %>
    <input type="submit" value="Post" />
</form>
</p>
    </body>
</html>