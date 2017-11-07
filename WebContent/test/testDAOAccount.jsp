<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="club.seedymusic.jpa.bean.*, club.seedymusic.jpa.dao.*, java.util.*" %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>

<link rel="stylesheet"
   href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
   integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb"
   crossorigin="anonymous">

<link rel="stylesheet" href="assets/css/splash.css">
</head>

<body>
   <div class="bgimg-4">

      <br />
      <h1>Test DAO Account</h1>
      <p>
      NOTE: This is for TESTING purposes only.  No validation, error checking or
      exception handling is done.  Use with caution.  Accounts 1-3 are protected.
      
      <br/>
      <h3>RESULTS OF LAST KNOWN OPERATION</h3>
      
      
<%
String action= (String) session.getAttribute("account.action");
               
if (action != null)
{
%>

   <div>
<table class="table table-inverse">
  <tbody>
    <tr>
      <th>Action performed</th>
      <td>${action}</td>
    </tr>
  </tbody>
</table>
</div>
   
<%
   if (action.equals("account.add"))
   {
      boolean result = (boolean) session.getAttribute("account.result");

%>      
<table class="table table-inverse">
  <tbody>
    <tr>
      <th>Action result</th>
      <td>${result}</td>
    </tr>
  </tbody>
</table>
</div>

<%
   }
   else if (action.equals("account.delete"))
   {
      boolean result = (boolean) session.getAttribute("account.result");

%>      
<table class="table table-inverse">
  <tbody>
    <tr>
      <th>Action result</th>
      <td>${result}</td>
    </tr>
  </tbody>
</table>
</div>


<% 
   }
   else if (action.equals("account.get"))
   {
      Account account = (Account) session.getAttribute("account");
%>



<% 
   }
%>


<%
} else {
%>     
No operation performed, no data returned, or session timed out.
<br/>
<% } %>
 
      <br/>
      <div id="accordion" role="tablist" aria-multiselectable="true">
  <div class="card">
    <div class="card-header" role="tab" id="account.get">
      <h5 class="mb-0">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
          Test Operation: GET Account...
        </a>
      </h5>
    </div>

    <div id="collapseOne" class="collapse" role="tabpanel" aria-labelledby="account.get">
      <div class="card-block">
        Enter the ID of the account you wish to retrieve:
      </div>
    </div>
  </div>
  
  
  <div class="card">
    <div class="card-header" role="tab" id="account.add">
      <h5 class="mb-0">
        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
          Test Operation: ADD Account...
        </a>
      </h5>
    </div>
    <div id="collapseTwo" class="collapse" role="tabpanel" aria-labelledby="account.add">
      <div class="card-block">

      <div class="container formtb">
         <div>
         <br/>
            <h3>Enter account info:</h3>
         </div>

<div>
         <form
            action="${pageContext.request.contextPath}/test/TestDAOAccount"
            method="get">
            <input type="hidden" name="account.action" value="account.add">

            <div class="form-group row">
               <label for="account.username"
                  class="col-sm-2 col-form-label col-form-label-lg">Username</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg" 
                     id="account.username" placeholder="your username">
               </div>
            </div>

            <div class="form-group row">
               <label for="account.password"
                  class="col-sm-2 col-form-label col-form-label-lg">Password</label>
               <div class="col-sm-10">
                  <input type="password" class="form-control form-control-lg"
                     id="account.password" placeholder="your password">
               </div>
            </div>


            <div class="form-group row">
               <label for="account.firstName"
                  class="col-sm-2 col-form-label col-form-label-lg">First Name</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     id="account.firstName" placeholder="your first name">
               </div>
            </div>

            <div class="form-group row">
               <label for="account.lastName"
                  class="col-sm-2 col-form-label col-form-label-lg">Last Name</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     id="account.lastName" placeholder="your last name">
               </div>
            </div>

            <div class="form-group row">
               <label for="account.street"
                  class="col-sm-2 col-form-label col-form-label-lg">Street</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     id="account.street" placeholder="unit &amp; street">
               </div>
            </div>

            <div class="form-group row">
               <label for="account.province"
                  class="col-sm-2 col-form-label col-form-label-lg">Province</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     id="account.province" placeholder="your province or state">
               </div>
            </div>

            <div class="form-group row">
               <label for="account.country"
                  class="col-sm-2 col-form-label col-form-label-lg">Country</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     id="account.country" placeholder="your country">
               </div>
            </div>

            <div class="form-group row">
               <label for="account.postalCode"
                  class="col-sm-2 col-form-label col-form-label-lg">Postal</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     id="account.postalCode" placeholder="your postal or zip code">
               </div>
            </div>

            <div class="form-group row">
               <label for="account.phone"
                  class="col-sm-2 col-form-label col-form-label-lg">Phone</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     id="account.phone" placeholder="your phone number">
               <span style="color: #FF0000;">${phoneError}</span>
               </div>
            </div>

            <div class="form-group row">
               <label for="account.email"
                  class="col-sm-2 col-form-label col-form-label-lg">Email</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     id="account.email" placeholder="your email address">
               <span style="color: #FF0000;">${emailError}</span>
               </div>
            </div>

            <div class="form-group row">
               <div class="offset-sm-2 col-sm-10">
                  <button type="submit" class="btn btn-primary btn-lg">Create account...</button>
               </div>
            </div>

         </form>
         
         <br/><br/><br/>
      </div>



      </div>
    </div>
  </div>
  
  <div class="card">
    <div class="card-header" role="tab" id="account.delete">
      <h5 class="mb-0">
        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
          Test Operation: DELETE Account...
        </a>
      </h5>
    </div>
    <div id="collapseThree" class="collapse" role="tabpanel" aria-labelledby="account.delete">
      <div class="card-block">

      <div class="container formtb">
         <div>
         <br/>
            <h3>Enter Account ID for deletion (#1-3 are protected)</h3>
         </div>

         <div>
         <form
            action="${pageContext.request.contextPath}/test/TestDAOAccount"
            method="get">
            <input type="hidden" name="account.action" value="account.delete">

            <div class="form-group row">
               <label for="account.id"
                  class="col-sm-2 col-form-label col-form-label-lg">Account ID</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg" 
                     name="account.id" id="account.id" placeholder="Account ID for deletion">
               </div>
            </div>
            
            <div class="form-group row">
               <div class="offset-sm-2 col-sm-10">
                  <button type="submit" class="btn btn-primary btn-lg">Delete account...</button>
               </div>
            </div>
            
         </form>
         </div>


      </div>
    </div>
  </div>
  
  <div class="card">
    <div class="card-header" role="tab" id="account.list">
      <h5 class="mb-0">
        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
          Test Operation: LIST Accounts...
        </a>
      </h5>
    </div>
    <div id="collapseThree" class="collapse" role="tabpanel" aria-labelledby="account.list">
      <div class="card-block">
         TODO: Add Form
      </div>
    </div>
  </div>
  
</div>
      

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

