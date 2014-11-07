<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<script>
  // This is called with the results from from FB.getLoginStatus().
  function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);
    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
      // Logged into your app and Facebook.
      document.getElementById('fbLoginButton').style.display = 'none';
      testAPI();
    } else if (response.status === 'not_authorized') {
      // The person is logged into Facebook, but not your app.
      document.getElementById('status').innerHTML = 'Please log ' +
        'into this app.';
    } else {
      // The person is not logged into Facebook, so we're not sure if
      // they are logged into this app or not.
      document.getElementById('status').innerHTML = 'Please log ' +
        'into Facebook.';
    }
  }

  // This function is called when someone finishes with the Login
  // Button.  See the onlogin handler attached to it in the sample
  // code below.
  function checkLoginState() {
    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
  }

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

  // Load the SDK asynchronously
  (function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));

  // Here we run a very simple test of the Graph API after login is
  // successful.  See statusChangeCallback() for when this call is made.
  function testAPI() {
    console.log('Welcome!  Fetching your information.... ');
    FB.api('/me', function(response) {
      console.log('Successful login for: ' + response.name);
      document.getElementById('status').innerHTML +=
        ' Thanks for logging in, ' + response.name + '!';
    });

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
			 		});
			  		console.log('These are some of your friends:' + names);
		          	document.getElementById('status').innerHTML += ' These are some of your friends: ' + names; 
			 	} else {
			 		console.log("Error!"); 
			 	} 
	        } else {
	  			console.log('problems occurred when retrieving friends: ' + (response ? response.error : 'null'));
	      	}
		}); 
  }
</script>
<h1 id="fb-welcome"></h1>

<script type="text/javascript">
$(document).ready(function(){
    $('.alert .close').on('click', function(e) {
   	    $(this).parent().hide();
   	});
    $('#aa').on('click', function(e,t) {
   	 debugger;
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

/*$(document).ready(function(){
    var loading = $("#divloader");
    $(document).ajaxStart(function () {
        loading.show();
    });

    $(document).ajaxStop(function () {
        loading.hide();
    });

});*/

/*
 $('#divloader').bind('ajaxStart', function(){
	    $(this).show();
 }).bind('ajaxStop', function(){
     $(this).hide();
 });
 */

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
 
 function onLogin(response) {
	  if (response.status == 'connected') {
	    FB.api('/me?fields=first_name', function(data) {
	      var welcomeBlock = document.getElementById('fb-welcome');
	      welcomeBlock.innerHTML = 'Hello, ' + data.first_name + '!';
	    });
	  }
	}
	FB.getLoginStatus(function(response) {
	  // Check login status on load, and if the user is
	  // already logged in, go directly to the welcome message.
	  if (response.status == 'connected') {
	    onLogin(response);
	  } else {
	    // Otherwise, show Login dialog first.
	    FB.login(function(response) {
	      onLogin(response);
	    }, {scope: 'user_friends, email'});
	  }
	});
	
/*   window.fbAsyncInit = function() {
    FB.init({
      appId      : '1454789934802984',
      xfbml      : true,
      version    : 'v2.1'
    });
  };

  function onLogin(response) {
	  if (response.status == 'connected') {
	    FB.api('/me?fields=first_name', function(data) {
	      var welcomeBlock = document.getElementById('fb-welcome');
	      welcomeBlock.innerHTML = 'Hello, ' + data.first_name + '!';
	    });
	  }
	}

	FB.getLoginStatus(function(response) {
	  // Check login status on load, and if the user is
	  // already logged in, go directly to the welcome message.
	  if (response.status == 'connected') {
	    onLogin(response);
	  } else {
	    // Otherwise, show Login dialog first.
	    FB.login(function(response) {
	      onLogin(response);
	    }, {scope: 'user_friends, email'});
	  }
	}); */
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
		<!-- Projects Row -->
		<!--  <div class="row">
			<div class="col-md-3 portfolio-item">
				<a href="item.htm"> <img class="img-responsive"
					src="../../images/auto.jpg" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="item.htm"> <img class="img-responsive"
					src="../../images/bike.jpg" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="../../images/guitarra.jpg" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="../../images/laptop.jpg" alt="">
				</a>
			</div>
		</div>-->
		<!-- /.row -->

		<!-- Projects Row -->
		<!-- <div class="row">
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="../../images/peliculas.jpg" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="../../images/saxo.jpg" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="../../images/tv.jpg" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="http://placehold.it/750x450" alt="">
				</a>
			</div>
		</div> -->
		<!-- /.row -->

		<!-- Projects Row -->
		<!-- <div class="row">
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="http://placehold.it/750x450" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="http://placehold.it/750x450" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="http://placehold.it/750x450" alt="">
				</a>
			</div>
			<div class="col-md-3 portfolio-item">
				<a href="#"> <img class="img-responsive"
					src="http://placehold.it/750x450" alt="">
				</a>
			</div>
		</div> -->
		<!-- /.row -->
</div>
		<hr>

		<!-- Pagination -->
		<!-- <div class="row text-center">
			<div class="col-lg-12">
				<ul class="pagination">
					<li><a href="#">&laquo;</a></li>
					<li class="active"><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li><a href="#">&raquo;</a></li>
				</ul>
			</div>
		</div> -->
		<!-- /.row -->

		<hr>
		</div>
			
		<!-- /.col-xs-12 main -->
	</div>

	</div>

<div id="fbLoginButton">
    <fb:login-button scope="public_profile,email,user_friends" onlogin="checkLoginState();">
    </fb:login-button>
</div>

<div id="status">
</div>
</body>
</html>
