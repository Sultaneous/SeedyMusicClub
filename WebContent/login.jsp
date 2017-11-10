<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>

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
				<h3 style="color: #fff; background: #120012;">SEEDY MUSIC CLUB: LOGIN</h3>
			</div>

			<div style="color: #FF0000;">${loginErrorMessage}</div>

			<form name="login" id="login" onSubmit="return(formValidation());"
				action="${pageContext.request.contextPath}/account/AccountLoginControllerServlet"
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
					<div class="offset-sm-2 col-sm-10">
						<button type="submit" class="btn btn-primary btn-lg">Log
							in</button>
					</div>
				</div>

			</form>
		</div>
	</div>

<!--  Script must be at bottom to ensure elements have loaded. -->
<script>
function formValidation()
{
   var error = document.getElementById("formError");
   var username = document.login.username;
   var password = document.login.password;
   
   if (username.value === "" ||
       password.value === "")
   {
      error.textContent="Error: All fields are required.";
      window.scrollTo(0,0);
      username.focus();
      return false;     
   }
   
   return true;
}
</script>


</body>
</html>
