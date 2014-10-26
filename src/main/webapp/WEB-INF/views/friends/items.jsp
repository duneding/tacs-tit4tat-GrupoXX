<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container-fluid">
<div class="row row-offcanvas row-offcanvas-left">

  <div class="col-xs-12 col-sm-9">
    <h1 class="page-header">Items de mis amigos!</h1>
  </div>
</div>

<table class="table table-striped table-hover">
<thead>
<th>Codigo</th>
<th>Descripcion</th>
<th>Propietario</th>
<th>Acciones</th>
</thead>
<tbody>
<c:forEach var="item" items="${items}">
        <tr>
        <td>${item.id}</td>
        <td>${item.description}</td>
        <td>${item.owner.name}</td>
        <td><a><span onclick="createTrueque(this)" class="glyphicon glyphicon-cloud-upload" title="Envia una solicitud de trueque a tu amigo!!"></span></a></td>
        </tr>
      </c:forEach>
      </tbody>
</table>      
</div>
<script>

function createTrueque(link){
  var id = $(link).closest("tr").find("td:eq(0)").text();  
  var owner = $(link).closest("tr").find("td:eq(2)").text();  
  var jsonRequest = { "owner" : owner, "item" : id};
  
  /*$.ajax({  
	     type : "POST",   
	     url : "/notifications/create",   	
	     async: false,
	     //data : "idItem=" + id ,
	     data : JSON.stringify(json),
	     contentType: "application/json; charset=utf-8",
	     success : function(response) {  
	      alert(response); 
	      window.location = response;
	     },  
	     error : function(e) {  
	      alert('Error:' + e.responseText);   
	     }  
	    });*/
  
  $.ajax({
      type: "GET",
      //contentType : 'application/json; charset=utf-8',
      //dataType : 'json',
      url: "notify",
      data: {
    	  		json:JSON.stringify(jsonRequest)
    	  	},
      success :function(result) {
       // do what ever you want with data
    	  var redirect = "/notifications/create/1";
    	  window.location.replace(redirect);
     }
  });
  
/*   
  
  var redirect = "/notifications/create?idItem=" + id;
  window.location.replace(redirect); */
}


</script>