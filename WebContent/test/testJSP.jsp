<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSP Success</title>
</head>
<body>
<h1>JSP Test Successful</h1>
Your JSP setup for Seedy Music Club is correct.
<p>
The server date and time is: <%= new java.util.Date() %>
</p>
<p>
You can also <a href="${pageContext.request.contextPath}/test/TestServlet">test the servlet setup</a>.
</p>
</body>
</html>
