<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="club.seedymusic.jpa.bean.*, club.seedymusic.jpa.dao.*, java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <title>DAO List Test Page</title>
   </head>
   <body>
      <h1>DAO List Test Page</h1>
      NOTE: This page is under development and will change as it is updated frequently.
      <br/><br/>
      
      <%
      CdDAO acd= new CdDAO();
      long recs = acd.getCount();
      %>
      <i>Discovered  <b><%=recs%></b> CDs in the database.</i>
      <br/>
      <form name="cd" action="${pageContext.request.contextPath}/test/TestDAOList" method="get">
         <br/>Select a CD list action:
         <ol>
            <li><input type="radio" name="action" value="list" checked/>List All CDs</li>
            <li><input type="radio" name="action" value="listPaged"/>List All CDs, Paged</li>
            <li><input type="radio" name="action" value="listByGenre"/>List CDs by Genre</li>
            <li><input type="radio" name="action" value="listByGenrePaged"/>List by Genre, Paged</li>
            <li><input type="radio" name="action" value="search"/>Search by Title (partial allowed)</li>
            <li><input type="radio" name="action" value="searchPaged"/>Search by Title, Paged</li>            
            <li><input type="radio" name="action" value="searchFull"/>Search by Title &amp; Genre</li>            
            <li><input type="radio" name="action" value="searchFullPaged"/>Search by Title &amp; Genre, Paged</li>            
         </ol>
         <br/>
         <input type="hidden" name="lookup" value="cd">

         <table>
	         <tr>
	            <td>Enter Title criteria:</td> 
	            <td><input name="title_criteria" type="text" value="Love" size="40"/></td>
	         </tr>
	         
            <tr>
               <td>Enter Genre criteria:</td> 
               <td><input name="genre_criteria" type="text" value="rock" size="40"/></td>
            </tr>
	         
	         <tr>
	            <td>Page Starting record:</td> 
	            <td><input name="pageStart" type="text" value="1" size="10"/></td>
	         </tr>
	
	         <tr>
	            <td>Maximum Page Size:</td> 
	            <td><input name="pageSize" type="text" value="1" size="10"/></td>
	         </tr>
         </table>

         <br/>
         <input type="submit" value="Perform a List/Search Action..."/>
      </form>
      <br>
      
      <%
      List<Cd> cds = (List<Cd>) session.getAttribute("cds");
      if (cds == null || cds.isEmpty())
      {
      %>
      <h3>No data to show.  Either no request made or request failed.</h3>
      <% 
      }
      else
      {
      %>
      <i>Found <b><%=cds.size() %></b> records that matched criteria.</i>
      <table cellpadding=2 cellspacing=2 border=2>
         <tr>
         <th>ID</th>
         <th>Cover</th>
         <th>Title</th>
         <th>Band</th>
         <th>Genre</th>
         <th>Price</th>
         <th>Quantity</th>
         <th>Date</th>
         </tr>

      <% for (Cd cd : cds) { %>
         <tr>
         <td rowspan="2"><%=cd.getId()%></td>
         <td rowspan="2">
            <a href="${pageContext.request.contextPath}/assets/covers/<%=cd.getCover()%>">
            <img src="${pageContext.request.contextPath}/assets/covers/<%=cd.getCover()%>" width="150" height="150" />
            </a>
         </td>
         <td><%=cd.getTitle()%></td>
         <td><%=cd.getBand()%></cd>
         <td><%=cd.getGenre()%></td>
         <td><%=cd.getPrice()%></td>
         <td><%=cd.getQuantity()%></td>
         <td><%=cd.getDate()%></td>
         </tr>
         <tr>
         <td colspan="6">
            <audio controls>
               <source src="${pageContext.request.contextPath}/assets/samples/<%=cd.getSample()%>"/>
               Not supported.
            </audio>
         </td>
         
         </tr>
       <% } %>
         
      </table>
      <br/>
      <%
      } 
      %>   

   </body>
</html>