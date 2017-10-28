<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Seedy Music Club</h1>

<p>
Please select:

<ul>
<li><a href="${pageContext.request.contextPath}/catalog.jsp">Browse our Catalog</a></li>
<li><a href="${pageContext.request.contextPath}/login.jsp">Login to your Account</a></li>
<li><a href="${pageContext.request.contextPath}/register.jsp">Register for an Account</a></li>
<li><a href="${pageContext.request.contextPath}/shoppingCart.jsp">View your Shopping Cart</a></li>
</ul>
</body>
</html>