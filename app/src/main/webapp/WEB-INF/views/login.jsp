<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix = "spring" uri = "http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Admin Login</title>
    </head>
    <body>
        <h3>Admin Login</h3>
        <table width="40" align="center">
            <tr align="center">
                <td colspan="2" align="center"> Login as an Admin User </td>
            </tr>
            <form action="<core:url value='j_spring_security_check'/>" method="POST">
            <tr align="left">
                <td align="left"> Username: </td>
                <td align="left"> <input type="text" name="username" required/></td>
            </tr>
            <tr align="left">
                <td align="left"> Password: </td>
                <td align="left"> <input type="password" name="password" required/></td>
            </tr>
            <tr align="center">
                <td align="center" colspan="2">
                    <input name="submit" type="submit" value="submit"/>
                </td>
            </tr>
            <tr align="center"> 
                <td colspan="2"> <a href="/employee"> Continue as Guest </a> </td>
            </tr>
            </form>
        </table>
    </body>
</html>