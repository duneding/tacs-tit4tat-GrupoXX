<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>

<h1 id="fb-welcome"></h1>

<script type="text/javascript">
$(document).ready(function(){
    $('.alert .close').on('click', function(e) {
   	    $(this).parent().hide();
   	});
    $('#aa').on('click', function(e,t) {
   	 var id ="id";
   	 $.ajax({  
   	     type : "GET",   
   	     url : "create",   	
   	     async: false,
   	     data : "id=" + id ,  
   	     success : function(response) {  
   	      alert(response); 
   	      window.location = "/create";
   	     },  
   	     error : function(e) {  
   	      alert('Error:');   
   	     }  
   	    });
	});
});

function searchItem(){
	
	var name = $('#itemForSearch').val();
	if(name.length == 0 ){
		$("#descriptionEmpty").show();
		return;
	}
	
 	$("#divloader").show();	
	$.ajax({  
    type : "GET",   
    url : "items/getItemsSearch",   
    async: true,
    data : "name=" + name,
    success : function(response) {  
     //$("#divloader").hide(); 
  	 $('#gridItems').empty();
   	 $('#gridItems').append("<table class='table table-striped table-hover' id='gridSearch'>" +
   			 "<thead>" + 
   			 "<th>Categoria</th>"+ 
   			  "<th>Descripcion</th>" +
   			  "</thead><tbody></tbody></table></div>"); 
   	 
   	 for (var i = 0; i < response.length; i ++){
   				 $('#gridSearch tbody').after( "<tr>" +
				"<td style = 'display:none' id='id'>" + response[i].id + "</td>" + 
				"<td style = 'display:none' id='image" + i + "'>" + response[i].image.bytes + "</td>" +
				"<td style = 'display:none' id='permalink'>" + response[i].permalink + "</td>" +
				"<td id='category'>" + response[i].category[0] +"</td>" +
				"<td id='description'>" + response[i].description + "</td>" + 
				 //"<td><a href='items/create/"+ response[i].id + "'><span class='glyphicon glyphicon-zoom-in'></span></a></td>" +  
				 "<td><a onclick='createItem("+ i + ")'><span class='glyphicon glyphicon-zoom-in'></span></a></td>" +
					"</tr>");   				 
   	 }
   	 
   },
    error : function(e,h,j) {  
     alert('Error:');   
    },
    complete: function() {
    	$("#divloader").hide();  	
    }
   });

} 

function createItem(row) {
	 
	var name = $('#itemForSearch').val();
	var id = document.getElementById('gridSearch').rows[row].children[0].innerText;
	var image = document.getElementById('gridSearch').rows[row].children[1].innerText;
	var permalink = document.getElementById('gridSearch').rows[row].children[2].innerText;
	var category = document.getElementById('gridSearch').rows[row].children[3].innerText;
	var description = document.getElementById('gridSearch').rows[row].children[4].innerText;
	
	var jsonRequest = {
            id: id,
            category: category,
            description: description,
            image: image,
            permalink: permalink
    };
	
	//var request = "new_item="+JSON.stringify(jsonRequest)
	//document.location.href="items/create?"+request;
	
	$.ajax({  
		    type : "POST",   
		    url : "items/create",   
		    async: false,
		    //dataType: 'json',
		    //contentType: "application/json",
		    //contentType: "application/json",
		    data : JSON.stringify(jsonRequest),
		    success : function(response) {  
		    	//alert(response);  
		    	//document.location.href="items/create";
		    	//document.location.href=response;
		    	//document.write(response);
		    	document.location.href="items/create";
		   	 },
		    error : function(e,h,j) {  
		     alert('Error: ' + j);   
		    }
	})
	 
 }	
</script>

	<div class="container theme-showcase" role="main">
		<!-- Main jumbotron for a primary marketing message or call to action -->
		<div class="jumbotron">
			<h1>Bienvenidos a Tit4Tat!</h1>
 			<p>Aplicacion social que te permite crear e intercambiar items
				con tus amigos</p> 
		</div>
		<div class="alert alert-danger" id="descriptionEmpty" style="display:none" align="center">
        <a href="#" class="close">&times;</a>
        <strong>Error!</strong>Debe ingresar una descripcion
    </div>
			<div class="row row-offcanvas row-offcanvas-left">

		<!-- main area -->
		<div class="col-xs-12 col-sm-9">
			<!-- Page Heading -->
		<div class="row" align="center">
			<div class="col-lg-6 col-lg-offset-4">
				<h3 class="page-header">
					Comienza buscando tus items
				</h3>
			</div>
		</div>
		 
		<div class="row">
		 <div class="col-lg-6 col-lg-offset-4"></div>
  			<div class="col-lg-6 col-lg-offset-4">
    			<div class="input-group">
      				<input type="text" id="itemForSearch" class="form-control" align="center"
      				onkeydown="if (event.keyCode == 13) document.getElementById('go').click()">
      					<span class="input-group-btn">
        					<button id="go" class="btn btn-default" type="button" onclick="searchItem()" data-toggle="tooltip" data-placement="top"         					
        					title="Busca tus items en MercadoLibre">Go!</button>
      					</span>
    				</div>
  			</div> 
  			           <div class="col-lg-6 col-lg-offset-4"></div>
		</div>
						
			<div id="divloader" style="height: 100px; text-align: center; display: none">
				<img id="ajax-loader" src="../../images/ajax-loader.gif" class="ajax-loader"/>    			
			</div>
			
		<div id="gridItems">		
		</div>
		<hr>
		<hr>
		</div>
			
	</div>

	</div>
	<a href="#" onclick="sendNotification();">Enviar notificacion</a>
	<div class="fb-send" data-href="http://t4t-tacs.appspot.com/" data-colorscheme="light"></div>
<a href="#" onclick="share();">Compartir web</a>
<div class="fb-share-button" data-href="http://t4t-tacs.appspot.com/" data-layout="icon_link"></div>
<div id="fbLoginButton">
    <fb:login-button scope="public_profile,email,user_friends" onlogin="checkLoginState();">
    </fb:login-button>
</div>

<div id="status">
</div>
</body>
</html>
