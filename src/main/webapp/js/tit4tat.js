
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
      
      
      $.ajax({  
    type : "POST",   
    url : "user",   
    async: true,
  //  data : {'userId': response.id},
  	data : 'userId=' + response.id,
    success : function(response) {  
    	 console.log('usuario actual: ' + response.id);
 
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
	  	var ids = ["10152511164937672","10204394795602905", "900069580003957"];
	    FB.ui({method: 'apprequests',
	        message: "Tit4Tat! - Social App",
	        to: ids,
	        new_style_message: true
	    }, function (response) {debugger;});
  }
  
  
  
  
