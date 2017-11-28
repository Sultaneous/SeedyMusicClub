<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Orders</title>

<link rel="stylesheet" href="assets/css/splash.css">
</head>

<body>
<jsp:include page="assets/fragments/libs.jsp"/>
<jsp:include page="assets/fragments/navbar.jsp"/>
<br/><br/>

	<div class="">
     
     <table class="table table-inverse table-bordered table-striped ">
     
     
     <tr>
      <th>Order Id</th>
      <th>Order Status</th>
      <th>Order Date</th>
      <th>Order Album(s)</th>
    </tr>
    
     	<c:forEach items="${Orders}" var="orderAlbums">
		<tr>
		<td>${orderAlbums.getOrder().getId()}</td>
		<td>${orderAlbums.getOrder().getStatus()}</td>
		<td>${orderAlbums.getOrder().getDate()}</td>
		<td>
		 
		   <ul>
		<c:forEach items="${orderAlbums.getAlbums()}" var="cd">

		<li>${cd}</li> 

		 
		 	</c:forEach>
	
		</td>
		
		<td>
		
		
		 </td>
		</tr>
	
	
	</c:forEach>
     
     
     </table>
     
	</div>




</body>
</html>
