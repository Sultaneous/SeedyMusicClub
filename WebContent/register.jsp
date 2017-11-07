<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
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
      <div class="container formtb" style="color: #fff;">
         <div>
            <h3 style="color: #fff; background: #120012">SEEDY MUSIC CLUB: REGISTER</h3>
         </div>

         <form
            action="${pageContext.request.contextPath}/account/AccountCreateControllerServlet"
            method="post">

            <div class="form-group row">
               <label for="username"
                  class="col-sm-2 col-form-label col-form-label-lg">Username</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg" 
                     name="username" id="username" placeholder="your username">
                   <span style="color: #FF0000;">${userExistsError}</span>
               </div>
            </div>

            <div class="form-group row">
               <label for="password"
                  class="col-sm-2 col-form-label col-form-label-lg">Password</label>
               <div class="col-sm-10">
                  <input type="password" class="form-control form-control-lg"
                     name="password" id="password" placeholder="your password">
               </div>
            </div>

            <div class="form-group row">
               <label for="passwordRetyped"
                  class="col-sm-2 col-form-label col-form-label-lg">Confirm</label>
               <div class="col-sm-10">
                  <input type="password" class="form-control form-control-lg"
                     name="passwordRetyped" id="passwordRetyped" placeholder="confirm your password">
                   <span style="color: #FF0000;">${passwordMismatchError}</span>
               </div>
            </div>

            <div class="form-group row">
               <label for="firstName"
                  class="col-sm-2 col-form-label col-form-label-lg">First Name</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     name="firstName" id="firstName" placeholder="your first name">
               </div>
            </div>

            <div class="form-group row">
               <label for="lastName"
                  class="col-sm-2 col-form-label col-form-label-lg">Last Name</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     name="lastName" id="lastName" placeholder="your last name">
               </div>
            </div>

            <div class="form-group row">
               <label for="street"
                  class="col-sm-2 col-form-label col-form-label-lg">Street</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     name="street" id="street" placeholder="unit &amp; street">
               </div>
            </div>

            <div class="form-group row">
               <label for="province"
                  class="col-sm-2 col-form-label col-form-label-lg">Province</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     name="province" id="province" placeholder="your province or state">
               </div>
            </div>

            <div class="form-group row">
               <label for="country"
                  class="col-sm-2 col-form-label col-form-label-lg">Country</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     name="country" id="country" placeholder="your country">
               </div>
            </div>

            <div class="form-group row">
               <label for="postalCode"
                  class="col-sm-2 col-form-label col-form-label-lg">Postal</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     name="postalCode" id="postalCode" placeholder="your postal or zip code">
               <span style="color: #FF0000;">${postalCodeError}</span>
               </div>
            </div>

            <div class="form-group row">
               <label for="phone"
                  class="col-sm-2 col-form-label col-form-label-lg">Phone</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     name="phone" id="phone" placeholder="your phone number">
               <span style="color: #FF0000;">${phoneError}</span>
               </div>
            </div>

            <div class="form-group row">
               <label for="email"
                  class="col-sm-2 col-form-label col-form-label-lg">Email</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     name="email" id="email" placeholder="your email address">
               <span style="color: #FF0000;">${emailError}</span>
               </div>
            </div>

            <div class="form-group row">
               <div class="offset-sm-2 col-sm-10">
                  <button type="submit" class="btn btn-primary btn-lg">Register for Seedy Music Club</button>
               </div>
            </div>

         </form>
         
         <br/><br/><br/>
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

