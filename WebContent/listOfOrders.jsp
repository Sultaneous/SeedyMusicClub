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

<div class="container center-block">
   <div class="table-responsive" style="width: 80%;">
 
      <table class="table table-inverse table-bordered table-striped">
 
         <thead>
            <tr>
               <th>Order ID</th>
               <th>Status</th>
               <th>Date</th>
               <th>CDs Ordered</th>
            </tr>
         </thead>

         <tbody>
 	       <c:forEach items="${Orders}" var="orderAlbums">
            <tr>
               <td>${orderAlbums.getOrder().getId()}</td>
               <td>${orderAlbums.getOrder().getStatus()}</td>
               <td>${orderAlbums.getOrder().getDate()}</td>

               <td> 
                  <c:forEach items="${orderAlbums.getAlbums()}" var="cd">
                     <c:url value="/browse" var="url">
                        <c:param name="genre" value="all"/>
                        <c:param name="search" value="${cd}"/>
                     </c:url>
                     <a class="link" href="${url}">${cd}</a>
                     <br/>
                  </c:forEach>
               </td>
            </tr>
           </c:forEach>
     
         </tbody>  
      </table>     
	</div>
</div>



</body>
</html>
