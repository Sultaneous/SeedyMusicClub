<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Shopping Cart</title>


<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>



<body>
	<h1>Shopping Cart</h1>
	<p>
	<div>

		<label> Hello <c:if test="${not empty username}">
				<c:out value="${username}" />
			</c:if> <c:if test="${empty username}">
Guest
</c:if>

		</label>

	</div>

	<div class="table-responsive">

		<c:if test="${cart.getLineItemCount()>0}">
			<table class="table .table-striped">

				<tr>
					<th>Item Number</th>
					<th>Title</th>
					<th>Price</th>
				</tr>


				<c:set var="cntr" value="${0}" />


				<c:forEach items="${cart.getCartItems()}" var="cd">
					<tr>
						<td><c:out value="${cntr+1}" /></td>
						<td>${cd.title}</td>
						<td>${cd.price}</td>
						<td>
							<form
								action="${pageContext.request.contextPath}/SessionController"
								method="post">
								<button class="btn btn-info" type="submit">Remove</button>
								<input type="hidden" value="${cd.id}" name="cdId" /> <input
									type="hidden" value="delete" name="action" /> <input
									type="hidden" value="${cntr}" name="itemIndex">
							</form>
						</td>
					</tr>
					<c:set var="cntr" value="${cntr=cntr+1}" />
				</c:forEach>

				<tr>
					<td colspan="2"></td>
					<td>Total:</td>
					<td>${cart.getOrderTotal()}$</td>
				</tr>
				<tr>
				<td colspan="3"></td>
				<td>
				<form action="${pageContext.request.contextPath}/OrderController" method="get">
				
				<input type="hidden" value=""/>
				<button class="btn btn-primary" type="submit">Confirm Order</button>
				</form>
				</td>
				</tr>
			</table>
		</c:if>
	</div>

	<div>
	<a class="btn btn-primary" href="${returnUrl}">Back to Browsing</a>
	</div>

</body>
</html>