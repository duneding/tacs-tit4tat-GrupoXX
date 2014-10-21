<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-8">
			<h3>Solicitudes de trueque</h3>
			<table id="tbsolicitudes" class="table table-striped table-hover">
				<thead>
					<tr>
						<th>Id</th>
						<th>Item Solicitado</th>
						<th>Dueño (Solicitado)</th>
						<th>Item Ofrecido</th>
						<th>Dueño (Ofrecido)</th>
						<th>...</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${notifications}" var="notification">
						<tr>
							<td>${notification.id}</td>
							<td>${notification.requestItem.description}</td>
							<td>${notification.requestItem.owner.name}</td>
							<td>${notification.offeredItem.description}</td>
							<td>${notification.offeredItem.owner.name}</td>
							<td>
							<a href="#"><span id="acceptBtn" class="glyphicon glyphicon-ok"></span></a>
							<a href="#"><span id="refuseBtn" class="glyphicon glyphicon-remove"></span></a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="col-md-4"></div>
	</div>
</div>
<script>
	$(function() {
		$('#acceptBtn').click(function() {
			var id = $(this).closest("tr").children(":first").text(); 
			var state = "acepted";

			$.ajax({  
			     type : "PUT",   
			     url : "notifications.htm",   
			     async: false,
			     data : { 
				     		id: id ,
				     		state: state
				     	},  
			     success : function(response) {  
			      alert(response);   
		     },  
			     error : function(e) {  
			      alert('Error:');   
			     }  
		    });
			
		});

		$('#refuseBtn').click(function() {
			alert("Handler for .click() on " + this + " called.");
		});
	});
</script>