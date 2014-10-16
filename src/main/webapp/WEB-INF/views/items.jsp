<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row row-offcanvas row-offcanvas-left">

	<div class="col-xs-12 col-sm-9">
		<h1 class="page-header">Mis Items!</h1>
	</div>
</div>
<c:forEach items="${items}" var="item">
		${item.id} ${item.description}
		<br />
</c:forEach>

