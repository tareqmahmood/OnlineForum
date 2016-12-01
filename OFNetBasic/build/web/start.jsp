<%-- 
    Document   : start
    Created on : Dec 1, 2016, 3:16:42 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
    String username = (String)session.getAttribute("username");
    if(username != null)
    {
        RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
        rd.forward(request, response);
    }
%>
    <title>Online Forum</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<h1>Online Forum</h1>
<h3>Login</h3>
<body>
<form method="post" action="LoginProcess.do">
    <table>
        <tr>
            <td>Username</td>
            <td><input type="text" name="username" /><td>
        </tr>

        <tr>
            <td>Password</td>
            <td><input type="password" name="password" /></td>
        </tr>
    </table>
    <input type="submit" value="Login" />
</form>

<h3>Create New Account</h3>

<form method="post" action="CreateAccount.do">
    <table>
        <tr>
            <td>First Name</td>
            <td><input type="text" name="firstName" /><td>
        </tr>

        <tr>
            <td>Last Name</td>
            <td><input type="text" name="lastName" /><td>
        </tr>

        <tr>
            <td>Username</td>
            <td><input type="text" name="username" /><td>
        </tr>
        
        <tr>
            <td>Email</td>
            <td><input type="text" name="email" /><td>
        </tr>

        <tr>
            <td>Password</td>
            <td><input type="password" name="password" /></td>
        </tr>

    </table>
    <input type="submit" value="Create Account" />
</form>
</body>
</html>
