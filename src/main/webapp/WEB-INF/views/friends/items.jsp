<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container-fluid">
<div class="row row-offcanvas row-offcanvas-left">

  <div class="col-xs-12 col-sm-9">
    <h1 class="page-header">Items de mis amigos!</h1>
  </div>
</div>

<table class="table table-striped table-hover" id="itemFriendGrid">
<thead>
<th>Nombre</th>
<th>Descripcion</th>
<th>Propietario</th>
<th>Acciones</th>
</thead>
<tbody>
<%-- <c:forEach var="item" items="${items}">
        <tr>
		<td style = 'display:none' id="item_id">${item.id}</td>
		<td style = 'display:none' id="owner_id">${item.owner.id}</td>
        <td>${item.shortDescription}</td>
        <td>${item.description}</td>
        <td>${item.owner.name}</td>
        <td><a><span onclick="createTrueque(this)" class="glyphicon glyphicon-cloud-upload" title="Envia una solicitud de trueque a tu amigo!!"></span></a></td>
        </tr>
      </c:forEach> --%>
      </tbody>
</table> 



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
     
</div>
<script>

$(document).ready(function(){
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
	  
	  function testAPI() {
		    FB.api('/me', function(response) {
		      $('.faceUser').text(response.name);
		      $('#userPhoto').attr('src','http://graph.facebook.com/' + response.id + '/picture?type=large');
		    });
		    		    
			FB.api('/me/friends', 
				function(response) {
					if (response && !response.error) {
				  		if(response.data) { 
			        		var friendIds = '';
					 		$.each(response.data,function(index,friend) {
					 	 		friendIds = friendIds + "," + friend.id;
/* 					 	 		names += friend.name + ', '; */
					 		});
					 		
					 		/*------------------------------------------*/
					 			$.ajax({  
	    type : "GET",   
	    url : "/friends/ItemsJson/idFriends" + friendIds,   
	    async: false,
	     data : { 
	    	 idFriends: friendIds
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
	    	
	    	
	   	 },
	    error : function(e,h,j) {  
	     alert('Error: ' + j);   
	    }
})
					 		
					 		
					 		
					 		
					 		/*------------------------------------------*/
					 		
					 		
					 		
					 		
					 	} else {
					 		console.log("Error!"); 
					 	} 
			        } else {
			  			console.log('problems occurred when retrieving friends: ' + (response ? response.error : 'null'));
			      	}
				}); 
		  }
	  
	
	
	
	
});

function createTrueque(link){
  var item_id = $(link).closest("tr").find("td:eq(0)").text();  
  var owner_id = $(link).closest("tr").find("td:eq(1)").text();  
  var jsonRequest = { "owner" : owner_id, "item" : item_id};

   $.ajax({
      type: "GET",
      url: "/items/listItems",
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
		    	document.location.href="/notifications";
		   	 },
		    error : function(e,h,j) {  
		     alert('Error: ' + j);   
		    }
	})
    
	$("#_MyItemList").modal("toggle");
}


</script>