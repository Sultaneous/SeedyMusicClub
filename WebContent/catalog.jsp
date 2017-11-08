<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Seedy Music Club -Browse Cds</title>

<!-- 
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	-->
</head>
<body>
<jsp:include page="assets/fragments/libs.jsp"/>
<jsp:include page="assets/fragments/navbar.jsp"/>

	<script>
		function change(sourceUrl) {
			var audio = $("#player");
			$("#audioSrc").attr("src", sourceUrl);
			/****************/
			audio[0].pause();
			audio[0].load();//suspends and restores all audio element

			//audio[0].play(); changed based on Sprachprofi's comment below
			audio[0].oncanplaythrough = audio[0].play();
			/****************/
		}

		function pause() {
			var audio = $("#player");
			audio[0].pause();
		}
	</script>

	<div class="container">

		<div class="row">
		
		<div class="col-12 col-lg-3">
		<div class="row">
         <div class="card text-white bg-primary mx-auto text-center">

				<div class="card-header">Search for Cds</div>
				<div class="card-body">
					<form action="${pageContext.request.contextPath}/browse">
						<table>
							<tr>
								<td>Genre</td>
								<td>
									<div class=form-group>
										<select name="genre" class="form-control">
											<c:forEach items="${genres}" var="genre">

												<c:if test="${currentGenre !=genre}">
													<option>${genre}</option>
												</c:if>
												<c:if test="${currentGenre==genre}">
													<option selected>${genre}</option>
												</c:if>

											</c:forEach>
										</select>
									</div>
								</td>
							</tr>

							<tr>
								<td>Title:</td>
								<td>
									<div class="form-group">
										<input type="text" class="form-control" id="search"
											name="search" value="${currentSearch}" />
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<button type="submit" class="btn btn-default">Search</button>
								</td>
							</tr>
						</table>


					</form>
				</div>
			</div>
	</div>
		
		
		</div>
		
			<div class="col-12 col-lg-9">

			<c:set var="counter" scope="session" value="${0}" />

			<c:forEach items="${listCd}" var="cd">

				<c:if test="${counter%3==0}">
					<div class="row">
				</c:if>
				<c:set var="counter" scope="session" value="${counter=counter+1}" />


				<div class="col-12 col-lg-4">
					<div class="card text-white bg-info">

						<div class="card-header">
							<label>${cd.title}</label>
						</div>

						<div class="card-body">
							<td><img class="img-fluid"
								src="${pageContext.request.contextPath}/assets/covers/${cd.cover}" />
							</td>
							<div class="table-responsive">
								<table class="table">
									<tr>
										<td>Genre:</td>
										<td>${cd.genre}</td>
									</tr>
									<tr>
										<td>Band:</td>
										<td>${cd.band}</td>
									</tr>
									<tr>
										<td>Price</td>
										<td>${cd.price}</td>
									</tr>
									<tr>
										<td>
											<button type="button" class="btn btn-info"
												data-toggle="modal" data-target="#myModal"
												onclick="change('${pageContext.request.contextPath}/assets/samples/${cd.sample}')">Play
												Sample</button> <input type="hidden" id="" value="" />
										</td>
									</tr>
									<tr>
										<td>
										    <form action="${pageContext.request.contextPath}/SessionController" method="post">
											<button type="submit">Add to Cart</button> <input
											type="hidden" value="${cd.id}" name="cdId" />
											<input type="hidden" name="action" value="add">
											</form>
										</td>
									</tr>
								</table>
							</div>
						</div>

					</div>

				</div>
				<c:if test="${counter%3==0}">
		</div>
		</c:if>
		</c:forEach>
		
		</div>

	</div>




	<div class="row">
		<div class="mx-auto text-center">
			<ul class="pagination">
				<c:forEach items="${paginationItems}" var="item">

					<c:if test="${currentPage ==item.pageNumber}">
						<li class="active"><a href="${item.pageURL}">
								${item.pageNumber} </a></li>
					</c:if>


					<c:if test="${currentPage !=item.pageNumber}">
						<li><a href="${item.pageURL}"> ${item.pageNumber} </a></li>
					</c:if>


				</c:forEach>
			</ul>
		</div>

	</div>
	</div>
	</div>

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

</body>
</html>