<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row row-offcanvas row-offcanvas-left">

	<div class="col-xs-12 col-sm-9">
		<h1 class="page-header">Mis Items!</h1>
	</div>
</div>

<table class="table table-striped table-hover">
<thead>
<th>Nombre</th>
<th>Descripcion</th>
<th>Acciones</th>
</thead>
<tbody>
<c:forEach var="item" items="${items}">
				<tr>
				<td>${item.shortDescription}</td>
				<td>${item.description}</td>
				<td><a href="#"><span onclick="deleteItem(this)" class="glyphicon glyphicon-remove"></span></a></td>
				</tr>
			</c:forEach>
			</tbody>
</table>			

<script type="text/javascript">

function deleteItem(link){
	var id = $(link).closest("tr").find("td:eq(0)").text();

	$.ajax({  
	     type : "DELETE",   
	     url : "items/" + id + ".htm",  
	     contentType: 'application/json; charset=utf-8',
	     dataType: 'json',
	     cache: false,
	     async: true,
	     success : function(response) {  
	         $(link).closest("tr").remove();
	         alert(response);   
  		},  
  		error: function(jqXHR, textStatus, errorThrown) {
  			            //alert(jqXHR.status + ' ' + jqXHR.responseText);
  						alert(jqXHR.responseText);
  						$(link).closest("tr").remove();
  			        }

 	});
	
}

</script>