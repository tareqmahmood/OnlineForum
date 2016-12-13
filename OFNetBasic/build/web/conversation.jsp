<%-- 
    Document   : conversation
    Created on : Dec 12, 2016, 2:23:26 PM
    Author     : HP
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.Message"%>
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
        <title>Conversation</title>
    </head>
    <body>
        <%
            int other_id = Integer.parseInt(request.getParameter("other_id"));
            DataAccess db = DataAccess.getDataAccess(request.getSession());
            ArrayList<Message> messageList = db.getMessages(user_id, other_id);
            for(Message m : messageList)
            {
                out.println(String.format("<p><span style='font-size:14pt'><b>%s</b></span> : <span style='font-size:10pt'><i>%s</i></span><br>%s</p>", m.getUsername(), m.getDatetime(), m.getContent()));
            }
        %>
    <form method="post" action=<% out.println(String.format("AddMessage.do?other_id=%d", other_id)); %> >
        <textarea name="content" cols="30" rows="2"></textarea><br>
        <input type="submit" value="Send" />
    </form>
    </body>
</html>
