<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0">
Bootstrap
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen"> -->
</head>
<body>
	<div class="navbar navbar-inverse" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="home.htm">Tit4Tat</a>
        </div>
        <div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
					<li><a href="perfil.htm">Perfil</a></li>
					<li><a href="items.htm">Items</a></li>
					<li><a href="solicitudes.htm">Solicitudes<span class="badge">3</span></a></li>
			</ul>
			<form action="http://facebook.com" class="navbar-form navbar-right" role="form">
				<button type="submit" class="btn btn-sm btn-primary">Entrar con Facebook</button>
				<!--<a href="#" class="btn btn-primary" role="button">Entrar con Facebook</a>-->
			</form>
        </div><!--/.navbar-collapse -->
      </div>
    </div>
</body>
</html>