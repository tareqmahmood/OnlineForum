
<%@page import="java.util.ArrayList"%>
<%@page import="model.Category"%>
<%@page import="db.DataAccess"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String username = (String)session.getAttribute("username");
    if(username == null)
    {
        RequestDispatcher rd = request.getRequestDispatcher("start.html");
        rd.forward(request, response);
    }
%>
<head>
    <title>Online Forum</title>
</head>
<body>
<a href="home.jsp">home</a> 
<a href="recent.jsp">recent</a> 
<a href="ShowFavourite.do">favorites</a> 
<a href="Logout.do">logout</a> </br>
<p>
<%
    out.print(String.format("<h2>Welcome %s</h2>", username));
%>
<form method="post" action="AddPost.do">
    Title <input type="text" name="title" /><br>
    Type your thoughts </br>
    <textarea name="content" cols="40" rows="5"></textarea><br>
    <%
        DataAccess db = DataAccess.getDataAccess(request.getSession());
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
