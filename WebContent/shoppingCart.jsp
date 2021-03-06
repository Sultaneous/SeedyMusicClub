<%@page import="club.seedymusic.processor.CurrencyTools"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

<div class="container center-block">
	<h1>Shopping Cart</h1>
	<p>
	<div>

		<label> Hello <c:if test="${not empty firstName}">
				<c:out value="${firstName}." />
			</c:if> <c:if test="${empty firstName}">
Guest.<br/><br/>
</c:if>

      <c:if test="${empty cart}">
      <h4>Your shopping cart is empty...</h4>
      </c:if>
      <c:if test="${cart.getLineItemCount()==0}">
      <h4>Your shopping cart is empty...</h4>
      </c:if>

		</label>

	</div>

	<div class="table-responsive" style="width: 80%;">

		<c:if test="${cart.getLineItemCount()>0}">
			<table class="table .table-striped">
           <thead>
				<tr>
					<th>Item Number</th>
					<th>Title</th>
					<th>Price <img src="assets/images/icon_canada.png"/></th>
					<th>&nbsp;</th>
				</tr>
           </thead>

				<c:set var="cntr" value="${0}" />


				<c:forEach items="${cart.getCartItems()}" var="cd">
					<tr>
						<td><c:out value="${cntr+1}" /></td>
						<td><strong>${cd.title}</strong></td>
						<td>$${cd.price}</td>
						<td>
							<form
								action="${pageContext.request.contextPath}/SessionController"
								method="post">
								<button class="btn btn-danger" type="submit">
								<span class="fa fa-remove"></span></button>
								<input type="hidden" value="${cd.id}" name="cdId" /> <input
									type="hidden" value="delete" name="action" /> <input
									type="hidden" value="${cntr}" name="itemIndex">
							</form>
						</td>
					</tr>
					<c:set var="cntr" value="${cntr=cntr+1}" />
				</c:forEach>

            <tr>
               <td></td>
               <td class="align-right align-bottom">
               <h5><img src="assets/images/icon_canada.png"/> Total:</h5></td>
               
               
               <!--  This formats to two decimal places -->
               <td class="align-bottom">
                <fmt:formatNumber var="totalCost" type="number" 
                minFractionDigits="2" maxFractionDigits="2" value="${cart.getOrderTotal()}"/>
                <h5>$${totalCost}</h5> 
               </td>
               
               <td>&nbsp;</td>
               
            </tr>

				<tr>
					<td></td>
					<td class="align-right align-bottom">
					<h5><span class="fa fa-bitcoin"></span> bitcoins:</h5></td>
					
					
					<!--  This formats to two decimal places -->
					<td class="align-bottom">
					 <fmt:formatNumber var="bitcoinCost" type="number" 
                minFractionDigits="2" maxFractionDigits="8" 
                value="${CurrencyTools.getValueXBT(cart.getOrderTotal())}"/>
					
					 <h5>${bitcoinCost}</h5> 
					</td>
					
					<td>
                <form action="${pageContext.request.contextPath}/OrderController" method="get">
                 <button class="btn btn-success" type="submit">
                 <span class="fa fa-credit-card"></span> Submit Order...</button>
                </form>
               </td>
				</tr>
			</table>
		</c:if>
	</div>

	<div>
	<c:if test="${not empty returnUrl}">
	<a class="btn btn-primary" href="${returnUrl}">Return to Catalog</a>
	</c:if>
	</div>
</div>

</body>
</html>