<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<body>
<script>
function submit(){
	
	var id = '${item.id}';
	var image = '${image}'; 
	var permalink = '${item.permalink}';
	var category = '${category}';
	var owner = '${item.owner}';
	var description = $('#description').val();
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
		    url : "items",   
		    async: false,
		    //dataType: 'json',
		    //contentType: "application/json",
		    data : JSON.stringify(jsonRequest),		    
		    success : function(response) {  
		    	document.location.href="/items";  		    	
		   	 },
		    error : function(e,h,j) {  
		     alert('Error:' + j);   
		    }
		    	
	});

} 
</script>


<div class="row row-offcanvas row-offcanvas-left">

	<div class="col-xs-12 col-sm-9">
		<h1 class="page-header">Agregar Item</h1>
	</div>
</div>

  <div class="form-group">
    <label for="category">Short Description:</label>
    <input type="text" id="short_description"/>
  </div>
  
    <div class="form-group">
    <label for="descripcion">Descripcion:</label>
    <textarea id="description" rows="5" cols="30">${item.description}</textarea>    
  </div>  
  <button type="submit" onclick="submit()" class="btn btn-default">Agregar</button>
  
</body>
  
<!--<form:form method="POST" action="items" onsubmit="submit()" commandName="item">
</form:form>-->

</html>