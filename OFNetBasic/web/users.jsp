<%-- 
    Document   : users
    Created on : Dec 12, 2016, 2:21:17 PM
    Author     : HP
--%>

<%@page import="template.QuickLink"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.User"%>
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
        <title>Users</title>
    </head>
    <body>
    <% out.print(QuickLink.quicklinks); %>
    <h2>Users</h2>
    <%
        DataAccess db = DataAccess.getDataAccess(request.getSession());
        ArrayList<User> userList = db.getUsers();
        for(User u : userList)
        {
            if(u.getUser_id() == user_id)
                out.println(String.format("<li>%s ", u.getUsername()));
            else
                out.println(String.format("<li>%s <a href=\"conversation.jsp?other_id=%d\">send message</a>", u.getUsername(), u.getUser_id()));
        }
    %>
    </body>
</html>
