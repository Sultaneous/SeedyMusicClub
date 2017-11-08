<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Shopping Cart</title>


</head>

<body>
<jsp:include page="assets/fragments/libs.jsp"/>
<jsp:include page="assets/fragments/navbar.jsp"/>
<br/><br/>

	<h1>Shopping Cart</h1>
	<p>
	<div>

		<label> Hello <c:if test="${not empty firstName}">
				<c:out value="${firstName}" />
			</c:if> <c:if test="${empty firstName}">
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