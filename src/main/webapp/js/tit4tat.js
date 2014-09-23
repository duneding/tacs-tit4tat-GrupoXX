/**
 * 
 */

// "nav navbar-nav"
$(document).ready(function() {
	console.log("ready!3");
	console.log(window.location.pathname);
	var href = window.location.pathname.toString().replace(".htm", "").replace("/", "");
	console.log(href);
	$("#"+href).addClass("active");
	
	console.log("ready!3");
});
