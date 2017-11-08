Skip to content
This repository
Search
Pull requests
Issues
Marketplace
Explore
 @Sultaneous
 Sign out
 Unwatch 4
  Star 0  Fork 0 Sultaneous/SeedyMusicClub
 Code  Issues 0  Pull requests 0  Projects 0  Wiki  Insights  Settings
Branch: Sultaneous-cle… Find file Copy pathSeedyMusicClub/WebContent/index.jsp
e22aa53  4 days ago
@Sultaneous Sultaneous Added login/logout toggle on splash screen.
1 contributor
RawBlameHistory     
159 lines (138 sloc)  4.78 KB
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Seedy Music Club</title>

<link rel="stylesheet"
   href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
   integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb"
   crossorigin="anonymous">
<link rel="stylesheet" href="assets/css/splash.css">
</head>
<body>

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
   // User is logged in tehn show logout icon
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

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
   integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
   crossorigin="anonymous"></script>
<script
   src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"
   integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh"
   crossorigin="anonymous"></script>
<script
   src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"
   integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ"
   crossorigin="anonymous"></script>
</body>
</html>

<!--
Stack of CDs icon by Juan Pablo Bravo from the Noun Project 
 -->
© 2017 GitHub, Inc.
Terms
Privacy
Security
Status
Help
Contact GitHub
API
Training
Shop
Blog
About