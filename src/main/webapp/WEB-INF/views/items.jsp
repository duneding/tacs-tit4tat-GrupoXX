<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<!-- Custom CSS -->
<link href="css/4-col-portfolio.css" rel="stylesheet">

<title>Insert title here</title>
</head>
<body>
<div class="row row-offcanvas row-offcanvas-left">
		<!-- sidebar -->
		<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar"
			role="navigation">
			<ul class="nav">
				<li class="active"><a href="/items.htm">Ver</a></li>
				<li><a href="/items.htm">Publicar</a></li>
				<li><a href="/items.htm">Borrar</a></li>
			</ul>
		</div>
		

<div class="col-xs-12 col-sm-9">
<h1 class="page-header">
					Mis Items!
				</h1>
            </div>
            </div>

 Message is: <%= request.getAttribute("message") %>   ${message} <form:input path="message"/>
 <label for="message"></label>
   <%-- <c:forEach var="i" begin="0" end="5">${message} 
 		Item <c:out value="${i}"/><p>
	</c:forEach> --%>

</body>

</html>

