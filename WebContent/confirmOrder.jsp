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

<label name="formError" id="formError" style="color: #FF1035;"
                  class="col-sm-12"><h5>NOTE: Please do not enter a real credit
                  card number.  Any fake value - even empty - will work here.
                  This site is currently STAGED and is faking orders for
                  testing purposes, not fulfilling them.  No info entered here
                  is saved.  Every 5th global order will be declined.
                  <br/><br/>
                  TO BE REPLACED WITH BITCOIN PAYMENT...
                  </h5></label>
         <form
            action="${pageContext.request.contextPath}/OrderController"
            method="post">

            <div class="form-group row">
               <label
                  class="col-sm-2 col-form-label col-form-label-lg">Card Type</label>
              <div class="col-sm-9">
               
                  <div class="form-check form-check-inline col-sm-4">
                  <label class="form-check-label col-form-label col-form-label-lg">
						<input class="form-check-input form-control-lg" 
						 type="radio" name="ccType" 
						 id="ccType" value="visa" checked>
						 <span class="fa fa-cc-visa"></span> Visa
						 </label>
						</div>
						
						
                  <div class="form-check form-check-inline col-sm-4">
                  <label class="form-check-label col-form-label col-form-label-lg">
                  <input class="form-check-input form-control-lg" 
                   type="radio" name="ccType" 
                   id="ccType" value="mastercard">
                   <span class="fa fa-cc-mastercard"></span> Mastercard
                  </label>
                  </div>
					</div>
              </div>

            <div class="form-group row">
               <label for="ccInfo"
                  class="col-sm-2 col-form-label col-form-label-lg">Credit Card</label>
               <div class="col-sm-10">
                  <input type="text" class="form-control form-control-lg"
                     name="ccInfo" id="ccInfo" placeholder="your credit card number">
               </div>
            </div>

            <div class="form-group row">
               <label for="ccExpiry"
                  class="col-sm-2 col-form-label col-form-label-lg">Expiry Date</label>
               <div class="col-sm-4">
                  <input type="text" class="form-control form-control-lg"
                     name="ccExpiry" id="ccExpiry" size=10 placeholder="YYMM">
               </div>
               
               <label for="ccCvv"
                  class="col-sm-2 col-form-label col-form-label-lg">Card CVV</label>
               <div class="col-sm-4">
                  <input type="text" class="form-control form-control-lg"
                     name="ccCvv" id="ccCvv" size=10 placeholder="XXX">
               </div>
               
            </div>


            <div class="form-group row">
               <div class="offset-sm-2 col-sm-10">
                  <button type="submit" class="btn btn-success btn-lg">Confirm Order</button>
               </div>
            </div>

         </form>
      </div>
   </div>



</body>
</html>