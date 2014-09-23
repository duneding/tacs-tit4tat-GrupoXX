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
    <div class="alert alert-danger" id="descriptionEmpty" style="display:none">
        <a href="#" class="close">&times;</a>
        <strong>Error!</strong>Debe ingresar una descripcion
    </div>

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

		<!-- main area -->
		<div class="col-xs-12 col-sm-9">
			<!-- Page Heading -->
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">
					Listado de Items <small>Trueques</small>
				</h1>
			</div>
		</div>
		
		<div class="row">
  			<div class="col-lg-6">
    			<div class="input-group">
      				<input type="text" id="itemForSearch" class="form-control">
      					<span class="input-group-btn">
        					<button class="btn btn-default" type="button" onclick="searchItem()">Go!</button>
      					</span>
    				</div>
  			</div>
		</div>

		<!-- Projects Row -->
		<div class="row">
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="../../images/auto.jpg" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="../../images/bike.jpg" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="../../images/guitarra.jpg" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="../../images/laptop.jpg" alt="">
				</a>
			</div>
		</div>
		<!-- /.row -->

		<!-- Projects Row -->
		<div class="row">
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="../../images/peliculas.jpg" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="../../images/saxo.jpg" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="../../images/tv.jpg" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="http://placehold.it/750x450" alt="">
				</a>
			</div>
		</div>
		<!-- /.row -->

		<!-- Projects Row -->
		<div class="row">
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="http://placehold.it/750x450" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="http://placehold.it/750x450" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="http://placehold.it/750x450" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="http://placehold.it/750x450" alt="">
				</a>
			</div>
		</div>
		<!-- /.row -->

		<hr>

		<!-- Pagination -->
		<div class="row text-center">
			<div class="col-lg-12">
				<ul class="pagination">
					<li><a href="#">&laquo;</a></li>
					<li class="active"><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li><a href="#">&raquo;</a></li>
				</ul>
			</div>
		</div>
		<!-- /.row -->

		<hr>
		</div>
		<!-- /.col-xs-12 main -->
	</div>
	<!--/.row-->
	<!--/.container-->
</body>

<SCRIPT type="text/javascript">

$(document).ready(function(){
     $('.alert .close').on('click', function(e) {
    	    $(this).parent().hide();
    	});
});

function searchItem(){
	debugger;
	var name = $('#itemForSearch').val();
	if(name.length == 0 ){
		$("#descriptionEmpty").show();
		return;
	}
		
	$.ajax({  
	     type : "GET",   
	     url : "getItemsSearch.htm",   
	     async: false,
	     data : "name=" + name ,  
	     success : function(response) {  
	      alert(response);   
	     },  
	     error : function(e) {  
	      alert('Error:');   
	     }  
	    });  
}

</SCRIPT>

</html>

