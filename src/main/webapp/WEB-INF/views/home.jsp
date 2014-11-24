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
var friends = [];

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
	
	$('#nameNewItem').val(name);
	$('#idNewItem').val(id);
	$('#imageNewItem').val(image);
	$('#permalinkNewItem').val(permalink);
	$('#categoryNewItem').val(category);
	$('#descriptionNewItem').val(description);
	$('#short_description').val("");
	$('#_MyItemCreate').modal('show');
	
/* 	var jsonRequest = {
            id: id,
            category: category,
            description: description,
            image: image,
            permalink: permalink
    };
	
	$.ajax({  
		    type : "POST",   
		    url : "items/create",   
		    async: false,
		    //dataType: 'json',
		    //contentType: "application/json",
		    //contentType: "application/json",
		    data : JSON.stringify(jsonRequest),
		    success : function(response) {  
		    	document.location.href="items/create";
		   	 },
		    error : function(e,h,j) {  
		     alert('Error: ' + j);   
		    }
	}) */
	 
 }	
 
function AgregarItem(){
	
	var id = $('#idNewItem').val();
	var image = $('#imageNewItem').val();
	var permalink = $('#permalinkNewItem').val();
	var category =  $("#categoryNewItem").val();
	var owner =  $("#currentUser").val();
	var description = $('#descriptionNewItem').val();
	var short_description = $('#short_description').val();

	var jsonRequest = {
            id: id,
            short_description: short_description,
            description: description,
            image: image,
            permalink: permalink,
            owner: owner,
            category: category
    }

	$.ajax({  
		    type : "POST",   
		    url : "/items",   
		    async: false,
		    //dataType: 'json',
		    //contentType: "application/json",
		    data : jsonRequest,		    
		    success : function(response) {  
		    	alert(response);
		    	document.location.href="/items";  		    	
		   	 },
		    error : function(e,h,j) {  
		     alert('Error:' + j);   
		    }
		    	
	});


}

function showAmigos(){
		$.ajax({  
		    type : "GET",   
		    url : "/friends/items",   
		    async: false,
		     data : { 
		    	 idFriends: JSON.stringify(friends)
		     	}, 
		    success : function(response) {  	    	
		    
		    	 for (var i = 0; i < response.length; i ++){
	   				 $('#itemFriendGrid tbody').after( "<tr>" +
					"<td style = 'display:none'>" + response[i].id + "</td>" + 
					"<td style = 'display:none'>" + response[i].owner.id + "</td>" + 
					"<td >" + response[i].shortDescription +"</td>" +
					"<td >" + response[i].description +"</td>" +
					"<td >" + response[i].owner.name +"</td>" + 
					 "<td><a onclick='createTrueque(this)' title='Envia una solicitud de trueque a tu amigo!!'><span class='glyphicon glyphicon-cloud-upload'></span></a></td>" +
						"</tr>");   				 
	   	 }
		    	
		    	$("#_FriendPopUp").modal('show');
		   	 },
		    error : function(e,h,j) {  
		     alert('Error: ' + j);   
		    }
	});
		
	
}

function createTrueque(link){
	  var  item_id = $(link).closest("tr").find("td:eq(0)").text();  
	  var  owner_id = $(link).closest("tr").find("td:eq(1)").text();  
	  var jsonRequest = { "owner" : owner_id, "item" : item_id};
	  var currentUser = $("#currentUser").val();
	   $.ajax({
	      type: "GET",
	      url: "/items",
	      data: {userId : currentUser},
	      async: true,
	      success :function(response) {    	  

	    	  $('#myItems').empty();
	    	   	 $('#myItems').append("<table class='table table-striped table-hover' id='myItemsTable'>" +
	    	   			 "<thead>" + 
	    	   			 "<th>Nombre</th>"+ 
	    	   			  "<th>Descripcion</th>" +    	   			
	    	   			  "</thead><tbody></tbody></table></div>"); 
	    	   	 
	    	   	 for (var i = 0; i < response.length; i ++){
	    	   				 $('#myItems tbody').after( "<tr>" +
	    	   		  		"<td style = 'display:none' id='user_item_id'>" + response[i].id + "</td>" + 
	    	   		  		"<td style = 'display:none' id='user_id'>" + response[i].owner.id + "</td>" +
	    	  				"<td>" + response[i].shortDescription + "</td>" +
	    	  				"<td>" + response[i].description + "</td>" +
	    	  				"<td><a onclick='createSolicitud(this, " + item_id + "," + owner_id + ")' title='Envie la solicitud de trueque a su amigo!'><span class='glyphicon glyphicon-ok-sign'></span></a></td>" +
	    	  				 "</tr>");  
	    	  
	    	  
	    	  $("#_MyItemList").modal("show");
	     } 
	  }});
	  
	}
function createSolicitud(link, item_id, owner_id){

	var user_item_id = $(link).closest("tr").find("td:eq(0)").text();
	var user_id = $(link).closest("tr").find("td:eq(1)").text();
	var jsonRequest = { 
			"owner_id" : owner_id, 
			"item_id" : item_id,
			"user_id" : user_id,
			"user_item_id" : user_item_id 
	};
	
	
	var r = confirm("Esta a punto de enviar una solicitud de trueque, desea continuar?");
    if (!(r == true)) {
    	return;
    }
	
    /*-----AJAX POST A NOTIFICATION POST!--------*/
debugger;
    	$.ajax({  
		    type : "POST",   
		    url : "/notifications",   
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
		    	
		    	/*Enviamos solicitud facebook*/
		    	sendNotification(owner_id);
		    	alert("Se ha enviado una solicitud a tu amigo!Seguramente pronto te constestara!!");
		   	 },
		    error : function(e,h,j) {  
		     alert('Error: ' + j);   
		    }
	})
    
	$("#_MyItemList").modal("toggle");
}

function sendNotification(userIds){
	debugger;
	var ids = [];
	ids.push(userIds);
    FB.ui({method: 'apprequests',
        message: "Tit4Tat! - Social App: Te han enviado una solicitud de trueque!",
        to: ids,
        new_style_message: true
    }, function (response) {debugger;});
}

//Cargamos SDK en forma asincronica
(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

//Inicializamos SDK
  window.fbAsyncInit = function() {
  FB.init({
    appId      : '813592025350948',
    cookie     : true,  // enable cookies to allow the server to access 
                        // the session
    xfbml      : true,  // parse social plugins on this page
    version    : 'v2.1' // use version 2.1
  });

  // Now that we've initialized the JavaScript SDK, we call 
  // FB.getLoginStatus().  This function gets the state of the
  // person visiting this page and can return one of three states to
  // the callback you provide.  They can be:
  //
  // 1. Logged into your app ('connected')
  // 2. Logged into Facebook, but not your app ('not_authorized')
  // 3. Not logged into Facebook and can't tell if they are logged into
  //    your app or not.
  //
  // These three cases are handled in the callback function.

  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });
  
  };
  
  function statusChangeCallback(response) {
	    console.log('statusChangeCallback');
	    console.log(response);

	    if (response.status === 'connected') {
	    	if( document.getElementById('fbLoginButton') != null)
	    		document.getElementById('fbLoginButton').style.display = 'none';
	      testAPI();
	    } else if (response.status === 'not_authorized') {
	      // The person is logged into Facebook, but not your app.
	    	if( document.getElementById('status') != null)
	    		document.getElementById('status').innerHTML = 'Please log into this app.';
	    } else {
	      // The person is not logged into Facebook, so we're not sure if
	      // they are logged into this app or not.
	    	if( document.getElementById('status') != null)
	    		document.getElementById('status').innerHTML = 'Please log into Facebook.';
	    }
	  }

  
  // This function is called when someone finishes with the Login
  // Button.  See the onlogin handler attached to it in the sample
  // code below.
  //Se llama cuando se finaliza con el login (boton)
  function checkLoginState() {
    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
  }

  // Here we run a very simple test of the Graph API after login is
  // successful.  See statusChangeCallback() for when this call is made.
  function testAPI() {
    console.log('Welcome!  Fetching your information.... ');
    FB.api('/me', function(response) {
      console.log('Successful login for: ' + response.name);
      console.log('colocando foto:  ' + response.name);
      
      /*Seteamos al usuario actual*/
      
      var userId =  response.id;
      var userName = response.first_name + " " + response.last_name;
      $("#currentUser").val(userId);
      $.ajax({  
    type : "POST",   
    url : "/user",  
    async: true,
    data : { 
    	userId: userId ,
    	userName: userName
 	},
    error : function(e,h,j) {  
    	console.log('Error al querer persistir usuario');
    }
   });
      
      
      /*-----------------------------*/
      $('.faceUser').text(response.name);
      $('#userPhoto').attr('src','http://graph.facebook.com/' + response.id + '/picture?type=large');
      
      if( document.getElementById('status') != null)
    	  document.getElementById('status').innerHTML +=' Thanks for logging in, ' + response.name + '!';
    });
    
/*    FB.ui({method: 'apprequests',
        message: "aaaa",
        to: ["10203938494275881"],
        new_style_message: true
    }, function (response) {debugger;});
    alert('Notification Sent!');*/
    
	FB.api('/me/friends', 
		function(response) {
			console.log('Obtained friends: ' + response.length);
		  	console.log('print response: ' + response);
			if (response && !response.error) {
		  		if(response.data) { 
	        		var names = '';
	        		
		 			console.log('Response without error');
			 		$.each(response.data,function(index,friend) {
			 	 		console.log(friend.name + ' has id:' + friend.id); 
			 	 		names += friend.name + ', ';
			 	 		friends.push(friend.id);
			 		});

			  		console.log('These are some of your friends:' + names);
			  		if( document.getElementById('status') != null)
			  			document.getElementById('status').innerHTML += ' These are some of your friends: ' + names; 
			 	} else {
			 		console.log("Error!"); 
			 	} 
	        } else {
	  			console.log('problems occurred when retrieving friends: ' + (response ? response.error : 'null'));
	      	}
		}); 
  }
  
  function share(){
      var share = {
          method: 'stream.share',
          u: 'http://t4t-tacs.appspot.com/'
      };
      FB.ui(share, function(response) {
    	  console.log("Proceso terminado");
      });
  }
  
  function sendNotification(userIds){
	  	var ids = ["10152511164937672","10204394795602905", "900069580003957", "10203938494275881"];
	    FB.ui({method: 'apprequests',
	        message: "Tit4Tat! - Social App",
	        to: ids,
	        new_style_message: true
	    }, function (response) {debugger;});
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
		
		<!------------------------------- PopUp Create Item -------------------------------- -->
		<div class="modal fade" id="_MyItemCreate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">Crea tu item!</h4>
      </div>
      <div class="modal-body" id="myItemCreate">
      	<div class="form-group">
    		<label for="short_description" class="label-control">Descripcion Corta:</label>
   			<input type="text" class="form-control" id="short_description"/>
  		</div>
  
    	<div class="form-group">
    		<label for="descripcion" class="label-control">Descripcion:</label>
   		    <textarea id="descriptionNewItem" class="form-control" rows="10" cols="30"></textarea>    
  		</div>  
  		<input type="hidden" id="idNewItem"/>
  		<input type="hidden" id="imageNewItem"/>
  		<input type="hidden" id="permalinkNewItem"/>
  		<input type="hidden" id="categoryNewItem"/>
  		<input type="hidden" id="currentUser"/>
  		<button type="button" onclick="AgregarItem()" class="btn btn-default">Agregar</button>
      </div>
    </div>
  </div>
</div>
<!-- ----------------------------------------------------------------------------------- -->
<!-------------------------------Solapaaa Friends-------------------------------- -->
		<div class="modal fade" id="_FriendPopUp" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">Items de tus amigos!</h4>
      </div>
      <div class="modal-body" id="myItemCreate">

<table class="table table-striped table-hover" id="itemFriendGrid">
<thead>
<th>Nombre</th>
<th>Descripcion</th>
<th>Propietario</th>
<th>Acciones</th>
</thead>
<tbody>
      </tbody>
</table> 
      </div>
    </div>
  </div>
</div>



<div class="modal fade" id="_MyItemList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">Seleccione un Item para Intercambiar!</h4>
      </div>
      <!--<div class="modal-body">
                    <table class="table table-striped table-hover" id="myItemsTable">
<thead>
<th></th>
<th>Nombre</th>
<th>Descripcion</th>
</thead>
<tbody>
      </tbody>
</table>
      </div>-->
      <div class="modal-body" id="myItems"></div>
    </div>
  </div>
</div>
<!-- ----------------------------------------------------------------------------------- -->


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
