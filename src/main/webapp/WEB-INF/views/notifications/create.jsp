<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="row row-offcanvas row-offcanvas-left">

	<div class="col-xs-12 col-sm-9">
		<h1 class="page-header">Crear Solicutud de truque!</h1>
	</div>
</div>

<form:form method="POST" commandName="notification" action="/notifications">
  <div class="form-group">
    <label for="category">Detalle:</label>
    <form:input path="detail" />
  </div>
  <div class="form-group">
    <label for="descripcion">Item Ofrecido:</label>
    <form:input path="offeredItem.description" />
  </div>
<div class="form-group">
    <label for="descripcion">Item a adquirir:</label>
    <form:input path="requestItem.description" />
  </div>
  <button type="submit" class="btn btn-default">Enviar</button>
</form:form>



