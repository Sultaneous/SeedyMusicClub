<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="club.seedymusic.jpa.bean.*, club.seedymusic.controller.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
<h1>Account Login</h1>
<div>
	<form name="login" action="${pageContext.request.contextPath}/account/AccountLoginControllerServlet" method="post">
         Username: <input name="username" placeholder="username"><br>
         Password: <input name="password" placeholder="password"><br>
         <input type="submit" value="submit"/>
    </form>
</div>
</body>
</html>