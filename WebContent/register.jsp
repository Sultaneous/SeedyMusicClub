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
         Username: <input type="hidden" name="username" value=""><br>
         Password: <input type="hidden" name="password" value=""><br>
         <%-- Used for confirmation --%>
         Re-Type Password: <input type="hidden" value=""><br>
         First name: <input type="hidden" name="firstName" value=""><br>
         Last name: <input type="hidden" name="lastName" value=""><br>
         Street: <input type="hidden" name="street" value=""><br>
         Province: <input type="hidden" name="province" value=""><br>
         Country: <input type="hidden" name="country" value=""><br>
         Postal Code: <input type="hidden" name="postalCode" value=""><br>
         Phone: <input type="hidden" name="password" value=""><br>
         Email: <input type="hidden" name="email" value=""><br>
         <input type="submit" value="submit"/>
    </form>
</div>
</body>
</html>