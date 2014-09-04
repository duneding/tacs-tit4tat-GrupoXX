<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<title>Tit4Tat</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>

	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Tit4Tat</a>
        </div>
        <div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
					<li><a href="home.htm">Inicio</a></li>
					<li><a href="items.htm">Items</a></li>
					<li class="active"><a href="solicitudes.htm">Solicitudes<span class="badge">3</span></a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><p><a href="#" class="btn btn-primary" role="button">Entrar con Facebook</a></p></li>
			</ul>
        </div><!--/.navbar-collapse -->
      </div>
    </div>
	
	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="container">
		<div class="jumbotron">
			<h2>${recurso}</h2>
      </div>
    </div>
	
	<div class="container">
      <hr>
      <footer>
        <p>&copy; TACS 2014 - UTN FRBA</p>
      </footer>
    </div> <!-- /container -->
	
	<script src="http://code.jquery.com/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
