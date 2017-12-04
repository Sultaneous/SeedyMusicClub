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


<div class="container center-block">
   <div class="table-responsive" style="width: 80%;">
<table class="table" >
<tr><td><h1>Order Status</h1></td></tr>
<tr><td><h5><strong>Thank-you.</strong> Your bitcoin payment can take from 10 minutes to several 
hours to be received, depending on transaction backlog.  Please check the order status regularly
at <a clas="link" href="${pageContext.request.contextPath}/ListOrdersController">your Order History</a>.  
A status of <strong>PAID</strong> indicates success while a status of <strong>DECLINED</strong>
means your payment was not received or was not the right value.</h5></td></tr>
</table>
</div>
</div>
</body>
</html>