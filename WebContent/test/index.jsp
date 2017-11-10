<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test Drivers</title>

<!-- Site icon -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/favicon.ico" type="image/x-icon">
<link rel="icon" href="${pageContext.request.contextPath}/assets/images/favicon.ico" type="image/x-icon">

<!-- Bootstrap 4 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

</head>

<body>
<!--  Bootstrap 4  -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>

<!--  Content  -->
<div class="container-fluid centre-block">
 <br/>
 <h1>Test Drivers</h1>
 <p>
 The following test drivers are available for functionality and use case testing.
 <br/><br/>
 <table class="table-responsive">
  <thead class="thead-inverse">
   <tr>
    <th>#</th>
    <th>Test Driver</th>
    <th>Purpose</th>
    <th>URL</th>
   </tr>
  </thead>
  <tbody>
    <tr>
     <th>1&nbsp;&nbsp;&nbsp;</th>
     <td>DAO Get</td>
     <td>Tests DAO / Hibernate / DB.  Retrieves records by ID for CDs, Accounts and Orders.
     Allows setting of Order status.</td>
     <td><a href="${pageContext.request.contextPath}/test/testDAOGet.jsp">testDAOGet.jsp</a></td>
    </tr>
    <tr>
     <th>2&nbsp;&nbsp;&nbsp;</th>
     <td>DAO List</td>
     <td>Tests CD DAO / Hibernate / DB.  Allows full or paginated listing of CDs, including
     Genre searches and Title keyword searches.</td>
     <td><a href="${pageContext.request.contextPath}/test/testDAOList.jsp">testDAOList.jsp</a></td>
    </tr>
    <tr>
     <th>3&nbsp;&nbsp;&nbsp;</th>
     <td>DAO Accounts</td>
     <td>Tests Accounts DAO / Hibernate / DB.  Tests Add, Delete, Get and List for Accounts.</td>
     <td><a href="${pageContext.request.contextPath}/test/testDAOAccount.jsp">testDAOAccount.jsp</a></td>
    </tr>    
    <tr>
     <th>4&nbsp;&nbsp;&nbsp;</th>
     <td>Catalog Browse</td>
     <td>Tests Catalog REST Web Service. Lists all CDs; returns data in JSON format.
      Supports searching by genre and title keyword.</td>
     <td><a href="${pageContext.request.contextPath}/rest/catalog/all">/rest/catalog/all</a></td>
    </tr>    
  </tbody>
 </table>
</div>



</body>
</html>