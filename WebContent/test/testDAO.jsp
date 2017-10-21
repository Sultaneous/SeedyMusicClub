<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <title>DAO Test Page</title>
   </head>
   <body>
      <h1>DAO Test Page</h1>
      NOTE: This page is under development and will change as it is updated frequently.
      <br/>
      <br/>
      <form action="${pageContext.request.contextPath}/test/TestDAO" method="get">
         <input type="submit" value="Execute DAO Test (click only once!)"/>
      </form>
   </body>
</html>