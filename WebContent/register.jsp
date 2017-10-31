<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
<h1>Account Registration</h1>
<div>
<h2>Please enter your user information:</h2>
	<form name="login" action="${pageContext.request.contextPath}/account/AccountCreateControllerServlet" method="post">
		<%-- User exists error --%>
		<div style="color: #FF0000;">${userExistsError}</div><br>
         Username: <input name="username" value=""><br>
         Password: <input name="password" value=""><br>
         <%-- Used for confirmation --%>
         <div style="color: #FF0000;">${passwordMismatchError}</div><br>
         Re-Type Password: <input name="passwordRetyped" value=""><br>
         First name: <input name="firstName" value=""><br>
         Last name: <input name="lastName" value=""><br>
         Street: <input name="street" value=""><br>
         Province: <input name="province" value=""><br>
         Country: <input name="country" value=""><br>
         <%-- Postal Code format error message --%>
         <div style="color: #FF0000;">${postalCodeError}</div><br>
         Postal Code: <input name="postalCode" value=""><br>
         <%-- Phone Number format error message --%>
         <div style="color: #FF0000;">${phoneError}</div><br>
         Phone: <input name="password" value=""><br>
         <%-- Email format error message --%>
         <div style="color: #FF0000;">${emailError}</div><br>
         Email: <input name="email" value=""><br>
         <input type="submit" value="submit"/>
    </form>
</div>
</body>
</html>