<%@page import="club.seedymusic.processor.CurrencyTools"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm Order JSP</title>
</head>
<body>
<jsp:include page="assets/fragments/libs.jsp"/>
<jsp:include page="assets/fragments/navbar.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/clipboard@1/dist/clipboard.min.js"></script>

<br/><br/>

<div class="container center-block">
   <h1>Order Payment</h1>

   <div class="table-responsive" style="width: 80%;">
      <table class="table">
       <tr>
        <td style="color: #F33;">
	        <h6>
	        WARNING: This is a test of a LIVE Bitcoin Payment Processor!
	        <br/>
	        <strong>DO NOT SEND MONEY TO THE ADDRES BELOW</strong>
	        <br/>
	        </h6>
         </td>
        </tr>

        <tr>
         <td>
          Instructions
          <ol>
           <li>Open your Bitcoin wallet.</li>
           <li>Enter the address below exactly as shown.</li>
           <li>Send the exact amount of bitcoins shown.</li>
          </ol>
          Alternatively, if your wallet supports QR codes, use the one below.
         </td>
        </tr>

        <tr>
         <td>
         <label><span class="fa fa-bitcoin"></span> Address:</label>&nbsp
         <strong>${order_address}</strong>
         </td>
        </tr>
        
        <tr>
         <td>
          <label><span class="fa fa-bitcoin"></span> bitcoins:</label>&nbsp
          <fmt:formatNumber var="bitcoinCost" type="number" 
                minFractionDigits="2" maxFractionDigits="8" 
                value="${CurrencyTools.getValueXBT(cart.getOrderTotal())}"/>
                <strong>${bitcoinCost}</strong> 
         </td>
        </tr>
        
        <tr>
         <td>
         <!--  In live scenario replace address below with {order.address} -->
          <img alt="Incorrect QR code input" 
           src="http://www.btcfrog.com/qr/bitcoinPNG.php?address=${order_address}&amount=${bitcoinCost}" 
           style="height: 150px; width 150px;" />
         </td>
        </tr>
        

        <tr>	       
         <td>
          <form action="${pageContext.request.contextPath}/OrderController" method="post">
            <button type="submit" class="btn btn-success btn-lg">Confirm Order</button>
          </form>
         </td>
        </tr>
      </table>
   </div>
</div>



</body>
</html>