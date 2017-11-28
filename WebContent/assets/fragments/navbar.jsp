<!-- JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--  Navigation Bar -->
<nav class="navbar navbar-expand-sm fixed-top navbar-dark font-weight-bold" style="background: #22084C;">

   <!--  Left side of navbar -->
   <ul class="navbar-nav mr-auto" >
    <li class="nav-item">
      <a class="nav-link" href="index.jsp">
      <span class="fa fa-home"></span> Home</a>
    </li>
    
    <li class="nav-item">
      <a class="nav-link" href="${pageContext.request.contextPath}/browse">
      <span class="fa fa-circle-o"></span> CDs</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="register.jsp">
      <span class="fa fa-pencil-square"></span> Register</a>
    </li>
    
    <!--  Toggles Login / Account dropdown depending on login status -->
    <c:choose>
     <c:when test="${sessionScope.userId == null}">
      <!--  Logged Out -->
      <li class="nav-item">
       <a class="nav-link" href="login.jsp">
       <span class="fa fa-sign-in"></span> Login</a>
      </li>
     </c:when>
     <c:otherwise>
      <!--  Logged In -->
      
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" 
         data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
         <span class="fa fa-user"></span> Account</a>
        <div class="dropdown-menu"  style="background: #22084C;" 
          aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item nav-link" 
           href="${pageContext.request.contextPath}/ListOrdersController">
           <span class="fa fa-list"></span> Order History</a>
          <a class="dropdown-item nav-link" href="logout.jsp">
           <span class="fa fa-sign-out"></span> Logout</a>
        </div>
      </li>
      </c:otherwise>
     </c:choose>
   </ul>
   
   <ul class="nav navbar-nav mx-auto">
    <li class="nav-item">
      <a class="nav-link" href="#">[Seedy Music Club - DEMO]</a>
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

