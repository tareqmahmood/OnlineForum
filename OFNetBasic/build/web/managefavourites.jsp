<%-- 
    Document   : managefavourite
    Created on : Dec 13, 2016, 2:24:09 PM
    Author     : HP
--%>

<%@page import="template.QuickLink"%>
<%@page import="java.util.ArrayList"%>
<%@page import="db.DataAccess"%>
<%@page import="model.Category"%>
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
        <title>Favourites</title>
    </head>
    <body>
        <% out.print(QuickLink.quicklinks); %>
        <h1>Your Favourites</h1>
        <%
        //System.out.println("OFDebug :" + username);
        DataAccess db = DataAccess.getDataAccess(request.getSession());
        ArrayList<Category> categories = db.getAllCategories();
        ArrayList<String> favouriteCategories = db.getFavouriteCategories(user_id);
        for(String ctgName : favouriteCategories)
        {
            out.println(String.format("<li> %s",  ctgName));
        }
        %>
        <h1>Favourites Menu</h1>
        <form method="post" action="AddFavourite.do">
            <%
            for(Category ctg : categories)
            {
                out.println(String.format("<input type=checkbox name=category value=\"%d\"> %s<br>", ctg.getCategory_id(), ctg.getCategory_name()));
            }
            %>
            <input type="submit" value="Submit" />  
        </form>
    </body>
</html>