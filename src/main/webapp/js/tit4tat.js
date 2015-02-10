var friends = '';

/*----------------AUTENTICACION FACEBOOK--------------------------*/

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
	    	$('#toolbarPrincipal').show();
	    	$("#searchBar").show();
	    	$('#go').prop( "disabled", false );
	    	$('#alertLogin').hide();
	      testAPI();
	    } else if (response.status === 'not_authorized') {
	      // The person is logged into Facebook, but not your app.
	    	if( document.getElementById('status') != null)
	    		document.getElementById('status').innerHTML = 'Please log into this app.';
	    	$('#toolbarPrincipal').hide();
	    	$("#searchBar").hide();
	    	$('#go').prop( "disabled", true );
	    	$('#alertLogin').show();
	    } else {
	      // The person is not logged into Facebook, so we're not sure if
	      // they are logged into this app or not.
	    	if( document.getElementById('status') != null)
	    		document.getElementById('status').innerHTML = 'Please log into Facebook.';
	    	$('#toolbarPrincipal').hide();
	    	$("#searchBar").hide();
	    	$('#go').prop( "disabled", true );
	    	$('#alertLogin').show();
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
    
	var jsonRequest = {
		  	userId: userId ,
		  	userName: userName
    }
    	
    $("#currentUser").val(userId);
    $.ajax({  
  type : "POST",   
  url : "/user",  
  async: true,
  contentType: "application/json",
  data : JSON.stringify(jsonRequest),		    
  success : function(response) {  

  },
  error : function(e,h,j) {  
  	console.log('Error al querer persistir usuario');
  }
 });
    
    /*Verificamos las solicitudes pendientes del usuario*/
    $.ajax({  
    	  type : "GET",   
    	  url : "/notifications/count", 
    	  async: true,
    	  data : { 
    	  	userId: userId 
    		},
    	  success : function(response) {
    		  console.log('Cantidad de solicitudes Pendientes: ' + response);
    		  if(response > 0){
    			  $('#solicitudesCount').text(response);
    			  $('#solicitudesCount').css("background-color","orange");
    			  $('#solicitudesCount').show();
    		  }
    		},
    	  error : function(e,h,j) {  
    	  	console.log('Error al obtener la cantidad de solicitudes pendientes del user');
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
			 	 		friends+= friend.id + ',';
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
        u: 'http://t4t-tacs.appspot.com/',
        description: 'Dialogs provide a simple, consistent interface for applications to interface with users.',
        caption: 'Reference Documentation',
        name: 'Facebook Dialogs'
    };
    
    
    FB.ui(share, function(response) {
  	  console.log("Proceso terminado");
    });
    
    

}


/*---------------HOME-----------------------*/
$(document).ready(function(){
    $('.alert .close').on('click', function(e) {
   	    $(this).parent().hide();
   	});
    
    $( "#itemForSearch" ).keypress(function(e) {
    	if(e.keyCode == 32){
    		return false;
    	}
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
    url : "/items/getItemsSearch",   
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
   		 var src = "";
   		 //if(response[i].image != null)
   			//src = 'data:image/png;base64,'+ response[i].image.bytes;
   		
   		 src = response[i].thumbnail;
   		 
   				 $('#gridSearch tbody').after( "<tr>" +
				"<td style = 'display:none' id='id'>" + response[i].id + "</td>" + 
				"<td style = 'display:none' id='image" + i + "'>" + response[i].image.bytes + "</td>" +
				"<td>" + "<img src= '" + src + "'></td>" +
				"<td style = 'display:none' id='permalink'>" + response[i].permalink + "</td>" +
				"<td id='category'>" + response[i].category[0] +"</td>" +
				"<td id='description'>" + response[i].description + "</td>" + 
				"<td style = 'display:none' id='thumbnail'>" + response[i].thumbnail + "</td>" +
				 //"<td><a href='items/create/"+ response[i].id + "'><span class='glyphicon glyphicon-zoom-in'></span></a></td>" +  
				 "<td><a onclick='createItem(this)'><span class='glyphicon glyphicon-zoom-in'></span></a></td>" +
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


/*-----------------------AGREGAR ITEMS -------------------------------------------*/
function createItem(link) {
  	
	var name = $('#itemForSearch').val();
	/*var id = document.getElementById('gridSearch').rows[row].children[0].innerText;
	var image = document.getElementById('gridSearch').rows[row].children[1].innerText;
	var permalink = document.getElementById('gridSearch').rows[row].children[3].innerText;
	var category = document.getElementById('gridSearch').rows[row].children[4].innerText;
	var description = document.getElementById('gridSearch').rows[row].children[5].innerText;
	var thumbnail = document.getElementById('gridSearch').rows[row].children[6].innerText;*/
	var id = $(link).closest("tr").find("td:eq(0)").text();
	var image = $(link).closest("tr").find("td:eq(1)").text(); 
	var permalink = $(link).closest("tr").find("td:eq(3)").text(); 
	var category = $(link).closest("tr").find("td:eq(4)").text(); 
	var description = $(link).closest("tr").find("td:eq(5)").text(); 
	var thumbnail = $(link).closest("tr").find("td:eq(6)").text(); 
	
	$('#nameNewItem').val(name);
	$('#idNewItem').val(id);
	$('#imageNewItem').val(image);
	$('#permalinkNewItem').val(permalink);	
	$('#categoryNewItem').val(category);
	$('#descriptionNewItem').val(description);	
	$('#short_description').val("");
	$('#thumbnailNewItem').val(thumbnail);
	$('#_MyItemCreate').modal('show');
	 
 }	
 
function AgregarItem(){
	
	var id = $('#idNewItem').val();
	var image = $('#imageNewItem').val();
	var permalink = $('#permalinkNewItem').val();
	var category =  $("#categoryNewItem").val();
	var owner =  $("#currentUser").val();
	var description = $('#descriptionNewItem').val();
	var short_description = $('#short_description').val();
	var thumbnail = $('#thumbnailNewItem').val();
	
	if(short_description.length == 0  || description.length == 0 ){
		alert("Los campos de descripcion son obligatorios por favor completelos")
		return false;
	}
	
	var jsonRequest = {
            id: id,
            shortDescription: short_description,
            description: description,
            image: image,
            permalink: permalink,            
            owner: owner,
            category: category,
            thumbnail: thumbnail
    }

	$.ajax({  
		    type : "POST",   
		    url : "/items",   
		    async: false,
		    //dataType: 'json',
		    contentType: "application/json",
		    data : JSON.stringify(jsonRequest),		    
		    success : function(response) {  
		    	alert(response);
		    	$('#_MyItemCreate').modal('toggle');
		    	shareItemCreate(short_description, description, $('span.faceUser').text());
		   	 },
		    error : function(e,h,j) {  
		     alert('Error:' + j);   
		    }
		    	
	});


}

function shareItemCreate(short_description, description, nameUser){
	var text = nameUser + " acaba de crear un item!"
	
    FB.ui(
            {
              method: 'feed',
              name: text,
              link: 'http://t4t-tacs.appspot.com/',
              caption: 'Tit4Tat - Intercambia items con tus amigos!',
              description: 'Ingresa a nuestra app y conoce un nuevo concepto en intercambio de items online!'
            },
            function(response) {
              if (response && response.post_id) {
            	  console.log('Post was published.');
              } else {
            	  console.log('Post was not published.');
              }
            }
          );
}


/*-------------------MOSTRAR ITEMS DE MIS AMIGOS-------------------------*/
function showAmigos(){
  $('#friendsItemBody').empty();
     $('#friendsItemBody').append("<table class='table table-striped table-hover' id='itemFriendGrid'>" +
         "<thead>" + 
         "<th>Imagen</th>"+
         "<th>Nombre</th>"+ 
          "<th>Descripcion</th>" +  
          "<th>Propietario</th>" + 
          "<th>Acciones</th>" +
          "</thead><tbody></tbody></table>"); 

     
    $.ajax({  
        type : "GET",   
        url : "/friends/items",   
        dataType: 'json',
        data : {"idFriends":friends}, 
        contentType: 'application/json',
        mimeType: 'application/json',
        success : function(response) {          
        
           for (var i = 0; i < response.length; i ++){
        	   var imageItem = "";
        	   if(response[i].thumbnail != null)	 
        		   imageItem = response[i].thumbnail;

             $('#itemFriendGrid tbody').after( "<tr>" +
          "<td style = 'display:none'>" + response[i].id + "</td>" + 
          "<td style = 'display:none'>" + response[i].owner.id + "</td>" + 
          "<td>" + "<img src= '" + imageItem +"'></td>" +
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

/*-------------------CREAR TRUEQUES-------------------------*/
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
var jsonRequestToSolicitud =  {};
function createSolicitud(link, item_id, owner_id){
	var user_item_id = $(link).closest("tr").find("td:eq(0)").text();
	var user_id = $(link).closest("tr").find("td:eq(1)").text();
	/*if(user_id = "10203938494275880")
		user_id = "10203938494275881";*/
	jsonRequestToSolicitud = { 
			owner_id : owner_id, 
			item_id : item_id,
			user_id : user_id,
			user_item_id : user_item_id ,
			message : ""
	};
	
	
	var r = confirm("Esta a punto de enviar una solicitud de trueque, desea continuar?");
    if (!(r == true)) {
    	jsonRequestToSolicitud =  {};
    	return;
    }
    	$("#_MyItemList").modal("toggle");
    	$("#_solicitudCreate").modal("show");
    	
}

function SetMensjBeforeNotification(){
	var msj = $('#msjSolicitud').val();
	var owner_id = jsonRequestToSolicitud.owner_id;
	
	if(msj.length == 0){
		msj = "Me interesa tu producto!";
	}
	msj = "Interesado: " + msj + "\n";
	jsonRequestToSolicitud.message = msj;
	
	$.ajax({  
	    type : "POST",   
	    url : "/notifications",   
	    async: false,
	    //dataType: 'json',
	    contentType: "application/json",
	    data : JSON.stringify(jsonRequestToSolicitud),
	    success : function(response) {  
	    	//alert(response);  
	    	//document.location.href="items/create";
	    	//document.location.href=response;
	    	//document.write(response);
	    	
	    	/*Enviamos solicitud facebook*/
	    	sendNotification(owner_id);
	   	 },
	    error : function(e,h,j) {  
	     alert('Error: ' + j);   
	    }
})
	$('#msjSolicitud').val('');
	jsonRequestToSolicitud =  {};
	$("#_solicitudCreate").modal("toggle");

}


function sendNotification(userIds){
	var ids = [];
	ids.push(userIds);
    FB.ui({method: 'apprequests',
        message: "Tit4Tat! - Social App: Te han enviado una solicitud de trueque!",
        to: ids,
        new_style_message: true
    }, function (response) {
    	alert("Se ha enviado una solicitud a tu amigo!Seguramente pronto te constestara!!");
    });
}

/*-------------------Mis items-------------------------*/
function showMyItems(){
	 //$("#myItemsGrid tbody").find("tr").remove();
	 $('#myItemsBody').empty();
   	 $('#myItemsBody').append("<table class='table table-striped table-hover' id='myItemsGrid'>" +
   			 "<thead>" + 
   			 "<th>Nombre</th>"+ 
   			  "<th>Descripcion</th>" +  
   			  "<th>Acciones</th>" +
   			  "</thead><tbody></tbody></table>"); 
   	 
	 var currentUser = $("#currentUser").val();
	$.ajax({  
	    type : "GET",   
	    url: "/items",
	    data: {userId : currentUser},
	    success : function(response) {  	    	
	    	 for (var i = 0; i < response.length; i ++){
   				 $('#myItemsGrid tbody').after( "<tr>" +
				"<td style = 'display:none'>" + response[i].id + "</td>" + 
				"<td >" + response[i].shortDescription +"</td>" +
				"<td >" + response[i].description +"</td>" +
				 "<td><a onclick='deleteItem(this)'><span class='glyphicon glyphicon-remove'></span></a></td>" +
					"</tr>");   				 
   	 }
	    	
	    	$("#_MyItemsPopUp").modal('show');
	   	 },
	    error : function(e,h,j) {  
	     alert('Error: ' + j);   
	    }
});
	

}


function deleteItem(link){
	var id = $(link).closest("tr").find("td:eq(0)").text();

	$.ajax({  
	     type : "DELETE",   
	     url : "/items/" + id,  
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
/*-------------------Notificacioness-------------------------*/
function showMyNotifications(){
	 $('#solicitudesBody').empty();
  	 $('#solicitudesBody').append("<table class='table table-striped table-hover' id='tbsolicitudes'>" +
  			 "<thead>" + 
  			// "<th>Id</th>"+ 
  			  "<th>Item Solicitado</th>" +
  			  "<th></th>" +
  			  "<th></th>" +
  			  "<th>Due\u00f1o (Solicitado)</th>" +
  			  "<th>Item Ofrecido</th>" +
  			  "<th></th>" +
  			  "<th></th>" +
  			  "<th>Due\u00f1o (Ofrecido)</th>" +
  			  "<th>Mensaje</th>" +
  			  "<th></th>" +
  			  "</thead><tbody></tbody></table>"); 
  	 
	 var currentUserId = $("#currentUser").val();
	$.ajax({  
	    type : "GET",   
	    url: "/notifications",
	    data: {userId : currentUserId},
	    success : function(response) {  	    	
	    	 for (var i = 0; i < response.length; i ++){
	    		 
	    		 if(response[i].offeredItem == null || response[i].requestItem == null)
	    			 continue;
	    		 
	    		 var options = "";
	    		 if(response[i].state == "0" ){
	    			 	options = "<a  onclick='acceptNotification(this)'><span class='glyphicon glyphicon-ok'></span></a>" +
				 				  "<a  onclick='refuseNotification(this)'><span class='glyphicon glyphicon-remove'></span></a>";
	    		 }else{
	    			 options = "<a data-toggle='tooltip' data-placement='top' title='Ya han finalizado las operaciones sobre esta solicitud' ><span class='glyphicon glyphicon-star-empty'></span></a>";
	    		 }
	    		 
	    		 var mensaje = "";
	    		 if(response[i].message== null){ 
	    			 mensaje = "Me interesa tu item!";
	    		 }else{
	    			 mensaje = response[i].message;
	    		 }
	    		
	       		 var imageRequested = "";
	       		 var imageOffered = "";
	       		 
	       		 if(response[i].offeredItem.image != null)
	       			 //imageOffered = 'data:image/png;base64,'+ response[i].offeredItem.image.bytes;
	       			imageOffered = response[i].offeredItem.thumbnail;

	       		 if(response[i].requestItem.image != null)	 
	       			 //imageRequested = 'data:image/png;base64,'+ response[i].requestItem.image.bytes;
	       			imageRequested = response[i].requestItem.thumbnail;
	    			      
	    		$('#tbsolicitudes tbody').after( "<tr>" +
				"<td style = 'display:none'>" + response[i].id + "</td>" +				
				"<td style='display:none'>" + response[i].requestItem.owner.id +"</td>" +
				"<td style='display:none'>" + response[i].offeredItem.owner.id +"</td>" +
				"<td>" + "<img src= '" + imageRequested + "'></td>" +				
				"<td >" + response[i].requestItem.shortDescription +"</td>" +
				"<td ><a href='#' class='mensajePopOver' tabindex='0' data-toggle='popover' data-placement='right' data-trigger='focus' title='' data-content='" + response[i].requestItem.description +  "'data-original-title='Descripcion larga-Item Solicitado'><i class='glyphicon glyphicon-eye-open'></i></a></td>" +
				"<td >" + response[i].requestItem.owner.name +"</td>" +
				"<td>" + "<img src= '" + imageOffered + "'></td>" +
				"<td >" + response[i].offeredItem.shortDescription +"</td>" +
				"<td ><a href='#' class='mensajePopOver' tabindex='0' data-toggle='popover' data-placement='left' data-trigger='focus' title='' data-content='" + response[i].offeredItem.description +  "'data-original-title='Descripcion larga-Item Ofrecido'><i class='glyphicon glyphicon-eye-open'></i></a></td>" +
				"<td >" + response[i].offeredItem.owner.name +"</td>" +
				//"<td ><a href='#' class='mensajePopOver' tabindex='0' data-toggle='popover' data-placement='left' data-trigger='focus' title='' data-content='" + mensaje +  "'data-original-title='Mensaje'><i class='glyphicon glyphicon-envelope'></i></a></td>" +
				"<td style='display:none'>" + mensaje  +"</td>" +
				"<td ><a href='#' onclick='showNotificactionMessagePopUp(this)'><i class='glyphicon glyphicon-envelope'></i></a></td>" +
				 "<td>" + options + "</td>" +
					"</tr>");   				 
  	 }
	    	
	    	//$('.mensajePopOver').popover();
	    	$("#_NotificactionPopUp").modal('show');
	   	 },
	    error : function(e,h,j) {  
	     alert('Error: ' + j);   
	    }
});
}

function acceptNotification(link) {
	var id = $(link).closest("tr").children(":first").text(); 
	var state = "acepted";
	var newOwner = $(link).closest("tr").find("td:eq(1)").text(); 
	var oldOwner = $(link).closest("tr").find("td:eq(2)").text();
	
	var jsonRequest = { 
     		id: id ,
     		state: state
     	};
     	
	$.ajax({  
	     type : "PUT",   
	     url : "/notifications",
	     cache: false,
	     async: true,   
	     //async: false,
		 contentType: "application/json",
		 data : JSON.stringify(jsonRequest),	     
	 	success : function(response) {
	 		$(link).closest("tr").remove();  
	      	alert(response);   
			var countSolicitud = $('#solicitudesCount').text();
			if(countSolicitud != "0"){
				$('#solicitudesCount').text(parseInt(countSolicitud) - parseInt("1"))
			}
	      	shareAcceptNotification(oldOwner);
	      	sendNotificationToOwner(newOwner);
	      	
     	},  
	    error : function(jqXHR, textStatus, errorThrown) {
	    	alert(jqXHR.responseText);
	    }  
    });
}

function refuseNotification(link) {
	var id = $(link).closest("tr").children(":first").text(); 
	var state = "refused";

	var jsonRequest = { 
     		id: id ,
     		state: state
     	};
	
	$.ajax({  
	     type : "PUT",   
	     url : "/notifications",
	     cache: false,
	     async: true,    
	     //async: false,
		 contentType: "application/json",
		 data : JSON.stringify(jsonRequest),
		 
	 	success : function(response) {
	 		$(link).closest("tr").remove();  
	      	alert(response);   
	      	var countSolicitud = $('#solicitudesCount').text();
			if(countSolicitud != "0"){
				$('#solicitudesCount').text(parseInt(countSolicitud) - parseInt("1"))
			}
     	},  
	    error : function(jqXHR, textStatus, errorThrown) {
	    	alert(jqXHR.responseText);;   
	    }  
    });
}


function shareAcceptNotification(oldOwner){
	// + $('span.faceUser').text() +
	var text = "Tit4Tat!  Social App! acaba de realizar un trueque!"
	
	//TODO : Se debe capturar el id de la persona dueña de la solicitud
	//"10203938494275881"
/*	if(oldOwner == "10203938494275880")
		oldOwner= "10203938494275881";*/
	
  	var owners = [];
  	owners.push(oldOwner);
    FB.ui({method: 'apprequests',
    	title:"Enviar notificacion",
        message: text,
        to: oldOwner,
        new_style_message: true
    }, function (response) {
    	console.log(response);
    	alert("Se ha notificado a tu amigo que has aceptado el trueque!");
    });
}

function sendNotification(newOwner){
  	//var ids = ["10152511164937672","10204394795602905", "900069580003957", "10203938494275881"];
    var ids = [];
/*    if(newOwner == "10203938494275880")
    	newOwner= "10203938494275881";
    */
    ids.push(newOwner);
	
	FB.ui({method: 'apprequests',
        message: "Tit4Tat Social App",
        to: ids,
        new_style_message: true
    }, function (response) {debugger;});
}

function showNotificactionMessagePopUp(link){
	/*$.ajax({  
	    type : "GET",   
	    url: "/notifications/GetMessages",
	    data: {notificationId : currentUser},
	    success : function(response) {  	    	
	    	 for (var i = 0; i < response.length; i ++){
   				 $('#myItemsGrid tbody').after( "<tr>" +
				"<td style = 'display:none'>" + response[i].id + "</td>" + 
				"<td >" + response[i].shortDescription +"</td>" +
				"<td >" + response[i].description +"</td>" +
				 "<td><a onclick='deleteItem(this)'><span class='glyphicon glyphicon-remove'></span></a></td>" +
					"</tr>");   				 
   	 }
	    	
	    	$("#_MyItemsPopUp").modal('show');
	   	 },
	    error : function(e,h,j) {  
	     alert('Error: ' + j);   
	    }
});*/
	
	var mensaje = $(link).closest("tr").find("td:eq(11)").text();
	var idSolicitud = $(link).closest("tr").find("td:eq(0)").text();
	var duenioSolicitadoID = $(link).closest("tr").find("td:eq(1)").text();
	var duenioOfrecidoID = $(link).closest("tr").find("td:eq(2)").text();
	$('#solicitudID').text(idSolicitud);
	$('#mensajeriaNotification').text(mensaje);
	$('#duenioSolicitadoID').text(duenioSolicitadoID);
	$('#duenioOfrecidoID').text(duenioOfrecidoID);
	$("#_NotificactionMessagePopUp").modal('show');
	
}

function sendNewMessageNotification(){
	//se agrega funcion para intercambio de mensajes 
	var id = $('#solicitudID').text();
	var message = $('#mensajeriaNotification').text();
	var encabezado = ""; 
	
	if($("#newMessageNotification").val() == "")
	{
		alert("Debe ingresar un mensaje!");
		return false;
	}
	
	if($("#currentUser").val() == $('#duenioSolicitadoID').text())
		encabezado = "Dueño: ";
	else
		encabezado = "Interesado: ";
	
	var nuevoMensaje = message + "\n" + encabezado + $("#newMessageNotification").val();
	
	$.ajax({  
	     type : "PUT",   
	     url : "/notifications/message",
	     cache: false,
	     async: true,    
	     data : { 
		     		id: id ,
		     		message: message
		     	},  
	 	success : function(response) {
	 		
    	},  
	    error : function(jqXHR, textStatus, errorThrown) {
	    	alert(jqXHR.responseText);;   
	    }  
   });
}
