<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm Order JSP</title>
</head>
<body>
<h1>Confirm Order JSP</h1>


<form action="${pageContext.request.contextPath}/OrderController" method="post">
<table>

<tr>
<td>Credit card  Account number</td>
</tr>
<tr>
<td>
<input type="text" name="ccInfo"/>
</td>
</tr>
<tr>
<td><button type="submit">Confirm</button></td>
</tr>

</table>
</form>

</body>
</html>