<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>

<link rel="stylesheet" href="assets/css/splash.css">
</head>

<body>
<jsp:include page="assets/fragments/libs.jsp"/>
<jsp:include page="assets/fragments/navbar.jsp"/>
<br/><br/>


   <div class="bgimg-4">

      <br />
      <div class="container formtb" style="color: #fff;">
         <div>
            <h3 style="color: #fff; background: #120012">SEEDY MUSIC CLUB: REGISTER</h3>
         </div>

         <form name="register" id="register" onSubmit="return(formValidation());"
            action="${pageContext.request.contextPath}/account/AccountCreateControllerServlet"
            method="post">
            
            <div class="form-group row"><h4>
               <label name="formError" id="formError" style="color: #FF1035;"
                  class="col-sm-12"></label></h4>
            </div>

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

<!--  Script must be at bottom to ensure elements have loaded. -->
<script>
function formValidation()
{
	var error = document.getElementById("formError");
   var password = document.register.password;
   var password2 = document.register.passwordRetyped;
   
   var username=document.register.username;
   var firstName=document.register.firstName;
   var lastName=document.register.lastName;
   var street=document.register.street;
   var province=document.register.province;
   var country=document.register.country;
   var postalCode=document.register.postalCode;
   var phone=document.register.phone;
   var email=document.register.email;
   
   if (username.value === "" ||
       firstName.value === "" ||
       lastName.value === "" ||
       street.value === "" ||
       province.value === "" ||
       country.value === "" ||
       postalCode.value === "" ||
       phone.value === "" ||
       email.value === "")
	{
	   error.textContent="Error: All fields are required.";
	   window.scrollTo(0,0);
	   username.focus();
	   return false;	   
	}
   
   if (password.value !== password2.value) 
   {
	   password.value="";
	   password2.value="";
      error.textContent="Error: Your passwords did not match.";
    	window.scrollTo(0,0);
      password.focus();
      return false;
   }
   
   if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email.value))
   {
      error.textContent="Error: Please enter a valid email address.";
	   window.scrollTo(0,0);
	   password.focus(); 
      return false;
   }
   
   return true;
}
</script>

</body>
</html>

