<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Seedy Catalog</title>
</head>

<body>
<jsp:include page="assets/fragments/libs.jsp"/>
<jsp:include page="assets/fragments/navbar.jsp"/>

	<script>
		function change(sourceUrl) {
			var audio = $("#player");
			$("#audioSrc").attr("src", sourceUrl);
			audio[0].pause();
			audio[0].load();
			audio[0].oncanplaythrough = audio[0].play();
		}

		function pause() {
			var audio = $("#player");
			audio[0].pause();
		}
	</script>
	
<!--  Catalog search sub-nav-bar  -->
<nav class="navbar navbar-expand-sm fixed-top navbar-dark font-weight-bold" style="background: #42286C; margin-top: 56px;">
    <form class="form-inline" method="get" action="${pageContext.request.contextPath}/browse">

  <ul class="navbar-nav mr-auto" >
    
    <li class="nav-item">
      <a class="nav-link" href="${pageContext.request.contextPath}/browse">Search the Seedy Music Club CD Catalog</a>
    </li>
    
    <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>

    <li class="nav-item">
      <a class="nav-link" href="#">Genre</a>
    </li>

	<li>
		<select name="genre" class="form-control" style="text-transform: capitalize">
		   <c:forEach items="${genres}" var="genre">
		
		      <c:if test="${currentGenre != genre}">
		         <option style="text-transform: capitalize">${genre}</option>
		      </c:if>
		      <c:if test="${currentGenre == genre}">
		         <option style="text-transform: capitalize" selected>${genre}</option>
		      </c:if>
		
		   </c:forEach>
		</select>	
	</li>

   <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
   
   <li>
    <input class="form-control mr-sm-2" type="text" name="search" placeholder="Search"
     placeholder="title keywords">
    <button class="btn btn-outline-light my-2 my-sm-0" type="submit">
     <span class="fa fa-music"></span> Search Catalog <span class="fa fa-music"></span>
    </button>
   </li>
   </ul>
  
  </form>
</nav>
<!-- End of Catalog search sub-nav-bar -->

<!-- Start of card display -->
<div class="container" style="margin-top: 120px;">
   <div class="col-12 center-block">

	   <c:set var="counter" scope="session" value="${0}" />
		<c:forEach items="${listCd}" var="cd">

		<c:if test="${counter%3==0}">
		<div class="row">
		</c:if>
				
		<c:set var="counter" scope="session" value="${counter=counter+1}"/>

		<div class="col col-sm-4">
	      <div class="card mt-5" style="background: #eaf1ff; color:#000;">

				<h4 class="card-title" style="background: #aaccff">${cd.title}</h4>

				<div class="card-block">
				   <a href="${pageContext.request.contextPath}/assets/covers/${cd.cover}">
				   <img class="img-fluid" src="${pageContext.request.contextPath}/assets/covers/${cd.cover}"/></a>
					<table class="table">
                   <tr>
                      <td><strong>Band:</strong></td>
                       <td><h5>${cd.band}</h5></td>
                    </tr>
						 <tr>
						    <td><strong>Genre:</strong></td>
							<td style="text-transform: capitalize">${cd.genre}</td>
						 </tr>
						 <tr>
						    <td><strong>Price</strong></td>
							<td>$${cd.price} <img src="assets/images/icon_canada.png"/></td>
						 </tr>
						<tr>
							<td>
								<button type="button" class="btn btn-info"
									data-toggle="modal" data-target="#myModal"
									onclick="change('${pageContext.request.contextPath}/assets/samples/${cd.sample}')">
									<span class="fa fa-play"></span> Play Sample</button>
							</td>
							<td>
							    <form action="${pageContext.request.contextPath}/SessionController" method="post">
								    <button class="btn btn-success" type="submit">
								    <span class="fa fa-shopping-cart"></span> Buy Now</button>
								    <input type="hidden" name="cdId" value="${cd.id}"/>
								    <input type="hidden" name="action" value="add"/>
								</form>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
   <c:if test="${counter%3==0}">
	</div>
 	</c:if>
	</c:forEach>
</div>
<!--  End of card display -->


<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					onclick="pause()">&times;</button>
				<h4 class="modal-title">Audio Player</h4>
			</div>
			<div class="modal-body">
				<audio id="player" controls="controls"> <source
					id="audioSrc" type="audio/mpeg"> Your browser does not
				support the audio element. </audio>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"
					onclick="pause()">Close</button>
			</div>
		</div>

	</div>
</div>

<br/><br/>

<!--  Pagination footer bar  -->
<nav class="navbar navbar-expand-sm fixed-bottom navbar-dark font-weight-bold" 
 style="background: #42286C;">
	<ul class="navbar-nav mx-auto" >
	   <c:forEach items="${paginationItems}" var="item">
	
	      <c:if test="${currentPage==item.pageNumber}">
	         <li class="active">
	            <a class="nav-link" href="${item.pageURL}"><h5>[${item.pageNumber}]</h5>
	            </a>
	         </li>
	      </c:if>
		
	      <c:if test="${currentPage!=item.pageNumber}">
	         <li>
	            <a class="nav-link" href="${item.pageURL}"><h5>${item.pageNumber}</h5></a>
	         </li>
	      </c:if>
	
	      <li>&nbsp;&nbsp;&nbsp;</li>
	   </c:forEach>
	</ul>
</nav>
<!--  End of pagination footer -->

</body>
</html>

