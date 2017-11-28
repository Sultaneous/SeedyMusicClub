<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="club.seedymusic.jpa.bean.*, club.seedymusic.jpa.dao.*, java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <title>DAO Get Test Page</title>
   </head>
   <body>
      <h1>DAO Get Test Page</h1>
      NOTE: This page is under development and will change as it is updated frequently.
      <br/><br/>
      <h3>Get CD</h3>   
      <%
      CdDAO acd= new CdDAO();
      long recs = acd.getCount();
      %>
      <i>Discovered  <%=recs%> CDs in the database.</i>
      <br/>
      <form name="cd" action="${pageContext.request.contextPath}/test/TestDAOGet" method="get">
         <input type="hidden" name="lookup" value="cd">
         Enter CD id to retrieve: <input name="cdid" type="text" value="1" size="10">
         <input type="submit" value="Get CD..."/>
      </form>
      <br>
      
      <%
      Cd cd = (Cd) session.getAttribute("cd");
      if (cd == null)
      {
      %>
      <h3>NO DATA: No request OR requested Cd id was not found in the database.</h3>
      <% 
      }
      else
      {
      %>
      <table cellpadding=2 cellspacing=2 border=2>
         <tr>
         <th>ID</th>
         <th>Title</th>
         <th>Band</th>
         <th>Genre</th>
         <th>Price</th>
         <th>Quantity</th>
         <th>Cover</th>
         <th>Sample</th>
         <th>Date</th>
         </tr>

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
         
         <tr>
          <th colspan=20>JSON</th>
         </tr>
         <tr>
          <td colspan=20>${cd.toJson()}</td>
         </tr>
         
      </table>
      <br/>
      <%
      } 
      %>   
      
      <br/><br/>
      <h3>Get Account</h3>
      <%
      AccountDAO anaccount = new AccountDAO();
      recs = anaccount.getCount();
      %>
      <i>Discovered  <%=recs%> Accounts in the database.</i>
      <form name="account" action="${pageContext.request.contextPath}/test/TestDAOGet" method="get">
         <input type="hidden" name="lookup" value="account">
         Enter Account id to retrieve: <input name="accountid" type="text" value="1" size="10">
         <input type="submit" value="Get Account..."/>
      </form>
      <br/>
      <%
      Account account = (Account) session.getAttribute("account");
      if (account == null)
      {
      %>
      <h3>NO DATA: No request OR requested Account id was not found in the database.</h3>
      <% 
      }
      else
      {
      %>
      <table cellpadding=2 cellspacing=2 border=2>
         <tr>
         <th>ID</th>
         <th>Username</th>
         <th>First Name</th>
         <th>Last Name</th>
         <th>Password</th>
         <th>Street</th>
         <th>City</th>
         <th>Province</th>
         <th>Country</th>
         <th>Postal Code</th>
         <th>Phone</th>
         <th>Email</th>
         <th>Date</th>
         </tr>

         <tr>
         <td><%=account.getId()%></td>
         <td><%=account.getUsername()%></td>
         <td><%=account.getFirstName()%></cd>
         <td><%=account.getLastName()%></td>
         <td><%=account.getPassword()%></td>
         <td><%=account.getStreet()%></td>
         <td><%=account.getCity()%></td>
         <td><%=account.getProvince()%></td>
         <td><%=account.getCountry()%></td>
         <td><%=account.getPostalCode()%></td>
         <td><%=account.getPhone()%></td>
         <td><%=account.getEmail()%></td>
         <td><%=account.getDate()%></td>
         </tr>
         
         <tr>
          <th colspan=20>JSON</th>
         </tr>
         <tr>
          <td colspan=20>${account.toJson()}</td>
         </tr>
      </table>
      <br/>
      <%
      } 
      %>

      <br/><br/>
      <h3>Get Order</h3>
      <%
      OrderDAO anorder = new OrderDAO();
      recs = anorder.getCount();
      %>
      <i>Discovered  <%=recs%> Orders in the database.</i>
      
      <form name="order" action="${pageContext.request.contextPath}/test/TestDAOGet" method="get">
         <input type="hidden" name="lookup" value="order">
         Enter Order id to retrieve: <input name="orderid" type="text" value="1" size="10">
         <input type="submit" value="Get Order..."/>
      </form>
      <br/>
      <%
      Order order = (Order) session.getAttribute("order");
      if (order == null)
      {
      %>
      <h3>NO DATA: No request OR requested Order id was not found in the database.</h3>
      <% 
      }
      else
      {
      %>
      <table cellpadding=2 cellspacing=2 border=2>
         <th>ID</th>
         <th>Account ID</th>
         <th>Order Items</th>
         <th>Status</th>
         <th>Date</th>

         <tr>
         <td><%=order.getId()%></td>
         <td><%=order.getAccountId()%></td>
         <td>
         <%
         Set<OrderItem> orderItems = order.getOrderItems();
         for (Iterator<OrderItem> nestedIterator = orderItems.iterator(); nestedIterator
                  .hasNext();)
         {
            OrderItem orderItem = nestedIterator.next();
            out.println(orderItem.getCdid());

            if (nestedIterator.hasNext())
               out.println(", ");
         }
         %>
         </td>
         <td><%=order.getStatus()%></cd>
         <td><%=order.getDate()%></cd>
         </tr>
         
         <tr>
          <th colspan=20>JSON</th>
         </tr>
         <tr>
          <td colspan=20>${order.toJson()}</td>
         </tr>
         
      </table>
      <br/>
      <%
      } 
      %>
      
      <br/><br/>
      <h3>List Orders by Account ID</h3>
      
      <form name="order" action="${pageContext.request.contextPath}/test/TestDAOGet" method="get">
         <input type="hidden" name="lookup" value="accountorder">
         Enter Account ID to list orders for: <input name="accountid" type="text" value="1" size="10">
         <input type="submit" value="List Orders for Account ID..."/>
      </form>
      <br/>
      <%
      List<Order> accountorders = (List<Order>) session.getAttribute("accountorders");
      if (accountorders == null)
      {
      %>
      <h3>NO DATA: No request OR requested Account ID has not made any orders.</h3>
      <% 
      }
      else
      {
      %>
      
      <table cellpadding=2 cellspacing=2 border=2>
         <th>ID</th>
         <th>Account ID</th>
         <th>Order Items</th>
         <th>Status</th>
         <th>Date</th>

        <c:forEach items="${accountorders}" var="accountorder">
         <tr>
         <td>${accountorder.id}</td>
         <td>${accountorder.accountId}></td>
         <td>
         <c:forEach items="${accountorder.orderItems}" var="orderitem">
           ${orderitem.cdid}&nbsp;&nbsp;
         </c:forEach>
         </td>
         <td>${accountorder.status}</cd>
         <td>${accountorder.date}</cd>
         </tr>
         
         <tr>
          <th colspan=20>JSON</th>
         </tr>
         <tr>
          <td colspan=20>${accountorder.toJson()}</td>
         </tr>
         
        </c:forEach>
         
      </table>
      <br/>
      <%
      } 
      %>
      
      <br/><br/>
      <h3>Set Order Status</h3>
      <form name="order" action="${pageContext.request.contextPath}/test/TestDAOGet" method="get">
         <input type="hidden" name="lookup" value="setStatus">
         Order ID: <input name="orderid" type="text" value="1" size="10">
         Status: <input name="orderStatus" type="text" value="paid" size="40">
         <input type="submit" value="Set Order Status..."/>
      </form>
      <h3>Result of setStatus() operation: <%=(String) session.getAttribute("order.result")%></h3>

   </body>
</html>

