<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>

<!-- JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html >
<html>
<head>
<link rel="shortcut icon" href="assets/images/favicon.ico" type="image/x-icon">
<link rel="icon" href="assets/images/favicon.ico" type="image/x-icon">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Seedy Music Club</title>
</head>
<body>

<jsp:include page="assets/fragments/libs.jsp"/>
<jsp:include page="assets/fragments/navbar.jsp"/>

<%
   boolean isLoggedIn;
   // Look for userId in session; if it is null, user isn't logged in
   Object object = session.getAttribute("userId");
   if (object==null)
      isLoggedIn = false;
   else
      isLoggedIn = true;
   
%>

   <div class="bgimg-1">
      <div class="caption">
         <span class="border">SEEDY MUSIC CLUB</span>
      </div>
   </div>


   <div
      style="color: #777; background-color: #fffff7; text-align: center; padding: 50px 80px; text-align: justify;">
      <h3 style="text-align: center;">Welcome to the Seedy Music Club!
      </h3>
      <p class="text-center" style="font-size: 16px; line-height: 2;'">
         As a club member, you will get access to some of the oddest CDs
         available on Earth for incredibly low prices!</p>

      <div class="container">
         <div class="row justify-content-center">
            <div class="col-3 justify-center center-block">
               <img class="mx-auto d-block"
               style="width: 80px; height: 80px;"            
               src="assets/images/icon_cd.png" />
            </div>

            <div class="col-3 center-block">
               <img class="mx-auto d-block" 
               style="width: 80px; height: 80px;"              
               src="assets/images/icon_club.png" />
            </div>

<%
   // If user is logged out, show a login icon
   if (!isLoggedIn)
   {
%>
            <div class="col-3 center-block">
               <img class="mx-auto d-block"
               style="width: 80px; height: 80px;"            
               src="assets/images/icon_login.png" />
            </div>
<%
   // User is logged in then show logout icon
   } else {
%>
            <div class="col-3 center-block">
               <img class="mx-auto d-block"
               style="width: 80px; height: 80px;" 
               src="assets/images/icon_logout.png" />
            </div>
<% } %>
         </div>
         

         <div class="row justify-content-center">
            <div class="col-3 text-center">
               <br /> <a class="btn btn-success text-center" style="color: #fff"
                  role="button" href="${pageContext.request.contextPath}/browse">Browse Seedy CDs</a>
            </div>

            <div class="col-3 text-center">
               <br /> <a class="btn btn-warning center-block" style="color: #fff"
                  role="button" href="register.jsp">Join the Seedy Club</a>
            </div>

<%
   // User is not logged in, offer login option
   if (!isLoggedIn)
   {
%>
            <div class="col-3 text-center">
               <br /> <a class="btn btn-danger center-block" style="color: #fff"
                  role="button" href="login.jsp">Log In</a>
            </div>
<%
   // User is logged in, offer a logout option instead
   } else {
%>
            <div class="col-3 text-center">
               <br /> <a class="btn btn-danger center-block" style="color: #fff"
                  role="button" href="logout.jsp">Log Out</a>
            </div>
<% } %>
         </div>
      </div>
   </div>

   <div class="bgimg-2">
      <div class="caption">
         <span class="border">SEEDY MUSIC CLUB</span>
      </div>
   </div>

   <div style="position: relative;">
      <div
         style="color: #ddd; background-color: #4d4d4d; text-align: center; padding: 50px 80px; text-align: justify;">
         <p class="text-center">
            <i>There's something seedy about CDs in a post-record, pre-MP3
               way... And we get that. Shop in complete privacy. Buy anonymously
               with Bitcoin. Well, after you share your address info. We have to
               ship it somewhere. You see how seedy the CD scene of Seedy Music
               Club is? (Try saying that 10 times fast...)</i>
         </p>
      </div>
   </div>

   <div class="bgimg-1">
      <div class="caption">
         <span class="border">SEEDY MUSIC CLUB</span>
      </div>
   </div>

</body>
</html>

