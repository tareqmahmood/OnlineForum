
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
%>
</body>
</html>
