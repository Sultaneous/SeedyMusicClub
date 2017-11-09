<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm Order JSP</title>
</head>
<body>
<jsp:include page="assets/fragments/libs.jsp"/>
<jsp:include page="assets/fragments/navbar.jsp"/>
<br/><br/>

   <div class="bgimg-4">

      <br />
      <div class="container formtb" style="color: #fff;">
         <div>
            <h3 style="color: #fff; background: #120012;">CONFIRM ORDER</h3>
         </div>

         <form
            action="${pageContext.request.contextPath}/OrderController"
            method="post">

            <div class="form-group row">
               <label for="ccInfo"
                  class="col-sm-2 col-form-label col-form-label-lg">Credit Card</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     name="ccInfo" id="ccInfo" placeholder="your credit card number">
               </div>
            </div>

            <div class="form-group row">
               <div class="offset-sm-2 col-sm-10">
                  <button type="submit" class="btn btn-primary btn-lg">Confirm</button>
               </div>
            </div>

         </form>
      </div>
   </div>



</body>
</html>