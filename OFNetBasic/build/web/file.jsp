<%-- 
    Document   : file
    Created on : Dec 14, 2016, 11:45:46 AM
    Author     : HP
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.File"%>
<%@page import="template.QuickLink"%>
<%@page import="db.DataAccess"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Files</title>
    </head>
    <body>
    <%
        out.println(QuickLink.quicklinks);
    %>
    <h2>Your Files</h2>
    <%
        ArrayList<File> fileList = db.getFiles(user_id);
        for(File f : fileList)
        {
            out.println(String.format("<li> %s (%s) <a href='DownloadFile.do?file_id=%d'>Download</a> <a href='DeleteFile.do?file_id=%d'>Delete</a>", f.getFilename(), f.getNormalizedFilesize(), f.getFile_id(), f.getFile_id()));
        }
    %>
    <h2>Upload File</h2>
    <form action="AddFile.do" method="post" enctype="multipart/form-data">
        <p><input type="file" name="file" size="40"/></p><br>
        <input type="submit" value="Upload" />
    </form>
    </body>
</html>
