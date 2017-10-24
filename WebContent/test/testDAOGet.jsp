<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="club.seedymusic.jpa.bean.Cd" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <title>DAO Get Test Page</title>
   </head>
   <body>
      <h1>DAO Get Test Page</h1>
      NOTE: This page is under development and will change as it is updated frequently.
      <br/>
      <br/>
      <form action="${pageContext.request.contextPath}/test/TestDAOGet" method="get">
         Enter CD id to retrieve: <input name="cdid" type="text" value="1" size="10">
         <input type="submit" value="Get CD..."/>
      </form>
      
      <br>
      <br>
      
      <%
         Cd cd = (Cd) session.getAttribute("cd");
      if (cd == null)
      {
      %>
      <h3>ERROR: The requested CD id was not found in the database.</h3>
      <% 
      }
      else
      {
      %>
      <table cellpadding=2 cellspacing=2 border=2>
         <th>ID</th>
         <th>Title</th>
         <th>Band</th>
         <th>Genre</th>
         <th>Price</th>
         <th>Quantity</th>
         <th>Cover</th>
         <th>Sample</th>
         <th>Date</th>

         <tr>
         <td><%=cd.getId()%></td>
         <td><%=cd.getTitle()%></td>
         <td><%=cd.getBand()%></cd>
         <td><%=cd.getGenre()%></td>
         <td><%=cd.getPrice()%></td>
         <td><%=cd.getQuantity()%></td>
         <td><%=cd.getCover()%></td>
         <td><%=cd.getSample()%></td>
         <td><%=cd.getDate()%></td>
         </tr>
      <%
      } 
      %>   
      </table>
   </body>
</html>