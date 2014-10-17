<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row row-offcanvas row-offcanvas-left">

	<div class="col-xs-12 col-sm-9">
		<h1 class="page-header">Mis Items!</h1>
	</div>
</div>



${message}
${items}

<table>
<thead>
<th>Codigo</th>
<th>Descripcion</th>
<th>Acciones</th>
</thead>
<tbody>
<c:forEach var="item" items="${items}">
				<tr>
				<td>${item.id}</td>
				<td>${item.description}</td>
				<td><a href="#" onclick="deleteItem(this)">Eliminar</a></td>
				</tr>
			</c:forEach>
			</tbody>
</table>			

<script type="text/javascript">

function deleteItem(link){
	debugger;
	var id = $(link).closest("tr").find("td:eq(0)").text();
	alert("el item de id:" + id + "se elimino correctamente");

	
/* 	$.ajax({  
	     type : "DELETE",   
	     url : "items.htm",  
	     data : "id=" + id,
	     dataType: 'json',
	      async: true,
	     success : function(response) {  
	      alert(response);   
	     },  
	     error : function(e) {  
	      alert('Error:');   
	     }  
	    }); */  
}

</script>