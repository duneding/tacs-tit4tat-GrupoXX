<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="row row-offcanvas row-offcanvas-left">

	<div class="col-xs-12 col-sm-9">
		<h1 class="page-header">Agregar Item</h1>
	</div>
</div>

<form:form method="POST" commandName="item" action="/items/list">
  <div class="form-group">
    <label for="category">Categoria:</label>
    <form:input path="category" />
  </div>
  <div class="form-group">
    <label for="descripcion">Descripcion:</label>
    <form:textarea path="description"rows="5" cols="30" />
  </div>
<!--   <div class="form-group">
    <label for="imagen">Imagen</label>
    <img src="..." alt="..." class="img-thumbnail">
  </div> -->
  <button type="submit" class="btn btn-default">Agregar</button>
</form:form>




