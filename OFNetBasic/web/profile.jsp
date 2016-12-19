<%-- 
    Document   : profile
    Created on : Dec 19, 2016, 6:52:01 PM
    Author     : HP
--%>

<%@page import="template.QuickLink"%>
<%@page import="model.Profile"%>
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
    
    
    int profile_id = Integer.parseInt(request.getParameter("profile_id"));
    DataAccess db = DataAccess.getDataAccess(request.getSession());
%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><% out.print(db.getUsername(profile_id)); %></title>
    </head>
    <body>
    <% out.print(QuickLink.quicklinks); %>
    <h2><% out.print("Profile : " + db.getUsername(profile_id)); %></h2>
    <%
        Profile profile = db.getProfile(profile_id);
    %>
    <table>
        <tr><td><b>Name</b></td><td></td><td><% out.print(profile.getFirst_name() + " " + profile.getLast_name()); %></td></tr>
        <tr><td><b>Email</b></td><td></td><td><% out.print(profile.getEmail()); %></td></tr>
        <tr><td><b>Point</b></td><td></td><td><% out.print(profile.getPoint()); %></td></tr>
    </table>
    </body>
</html>
