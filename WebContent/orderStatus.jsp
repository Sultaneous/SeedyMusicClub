<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order Status JSP</title>
</head>
<body>
<jsp:include page="assets/fragments/libs.jsp"/>
<jsp:include page="assets/fragments/navbar.jsp"/>
<br/><br/>

<h1>Order Status</h1>
<p>

<div class="container">
<div class="col center-block">
<table class="table">
<tr>

<td><h5>OrderStatus:</h5></td>
<td><strong>${orderStatus}</strong></td>

</tr>
</table>
</div>
</div>
</body>
</html>