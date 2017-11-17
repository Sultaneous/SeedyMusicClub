<!-- JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--  Navigation Bar -->
<nav class="navbar navbar-expand-sm fixed-top navbar-dark font-weight-bold" style="background: #22084C;">

   <!--  Left side of navbar -->
   <ul class="navbar-nav mr-auto" >
    <li class="nav-item">
      <a class="nav-link" href="index.jsp">Seedy Music Club Home</a>
    </li>
    
    <li class="nav-item">
      <a class="nav-link" href="${pageContext.request.contextPath}/browse"">Browse CDs</a>
    </li>
    
    <li class="nav-item">
      <a class="nav-link" href="register.jsp">Register</a>
    </li>
    
    <li class="nav-item">
     <c:choose>
      <c:when test="${sessionScope.userId == null}">
       <a class="nav-link" href="login.jsp">Login</a>
      </c:when>
      <c:otherwise>
       <a class="nav-link" href="logout.jsp">Logout</a>   
      </c:otherwise>
     </c:choose>
    </li>
   </ul>
   
   <ul class="nav navbar-nav mx-auto">
    <li class="nav-item">
      <a class="nav-link" href="#">[INACTIVE SITE - DEMO ONLY]</a>
    </li>
   </ul>

   <!-- Right side of navbar -->
   <ul class="nav navbar-nav ml-auto">
    <li class="nav-item">
      <a class="nav-link" href="shoppingCart.jsp"><span class="fa fa-shopping-cart"></span> Cart</a>
    </li>

    <li>
    <form class="form-inline" method="get" action="${pageContext.request.contextPath}/browse">
     <input class="form-control mr-sm-2" type="text" name="search" placeholder="Search">
     <button class="btn btn-outline-light my-2 my-sm-0" type="submit">
      <span class="fa fa-search"></span> CDs</button>
    </form>
    </li>
   </ul>

</nav>
<!-- End of Navbar fragment -->

