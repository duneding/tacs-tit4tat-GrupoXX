<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Crear item</title>
</head>
<body>
<form role="form">
<h2>Agregar Item</h2>
  <div class="form-group">
    <label for="nombre">Nombre</label>
    <input type="text" class="form-control" placeholder="Text input">
  </div>
  <div class="form-group">
    <label for="descripcion">Descripcion</label>
    <textarea class="form-control" rows="3"></textarea>
  </div>
  <div class="form-group">
    <label for="imagen">Imagen</label>
    <img src="..." alt="..." class="img-thumbnail">
  </div>
  
  <button type="submit" class="btn btn-default">Agregar</button>
</form>
</body>
</html>
<<style>
.form-control {
    width:400px;
}
</style>
