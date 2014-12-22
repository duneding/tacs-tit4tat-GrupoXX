<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>

<h1 id="fb-welcome"></h1>
	<div class="container theme-showcase" role="main">
		<!-- Main jumbotron for a primary marketing message or call to action -->
		<div class="jumbotron">
			<h1>Bienvenidos a Tit4Tat!</h1>
 			<p>Aplicacion social que te permite crear e intercambiar items
				con tus amigos</p> 
		</div>
		<div class="alert alert-warning" id="alertLogin" style="display:none" align="center">
        <a href="#" class="close">&times;</a>
        <strong>Alerta!</strong>Se ha detectado que no esta logeado en facebook por favor 
        ingrese para poder disfrutar todas funcionalidades de nuestra app!
    </div>
		<div class="alert alert-danger" id="descriptionEmpty" style="display:none" align="center">
        <a href="#" class="close">&times;</a>
        <strong>Error!</strong>Debe ingresar una descripcion
    </div>
			<div class="row row-offcanvas row-offcanvas-left">

		<!-- main area -->
		<div class="col-xs-12 col-sm-9" id="searchBar" style="display:none">
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
		
		<!------------------------------- Create Item Section-------------------------------- -->
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
  		<input type="hidden" id="thumbnailNewItem"/>
  		<button type="button" onclick="AgregarItem()" class="btn btn-default">Agregar</button>
      </div>
    </div>
  </div>
</div>

<!------------------------------------ Friends Section-------------------------------------------- -->
		<div class="modal fade" id="_FriendPopUp" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">Items de tus amigos!</h4>
      </div>
      <div class="modal-body" id="friendsItemBody">

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


<!------------------------------------ My items Section-------------------------------------------- -->
		<div class="modal fade" id="_MyItemsPopUp" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">Mis items!</h4>
      </div>
      <div class="modal-body" id="myItemsBody">

<!-- <table class="table table-striped table-hover" id="myItemsGrid">
<thead>
<th>Nombre</th>
<th>Descripcion</th>
<th>Acciones</th>
</thead>
</tbody> -->
</table>	
      </div>
    </div>
  </div>
</div>

<!------------------------------------ Notificaction Section-------------------------------------------- -->
		<div class="modal fade" id="_NotificactionPopUp" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog" style="min-width: 750px; !important">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">Solicitudes de trueque!</h4>
      </div>
      <div class="modal-body" id="solicitudesBody">
      </div>
    </div>
  </div>
  
  
  <!------------------------------------ Notificaction Messages Section-------------------------------------------- -->
		<div class="modal fade" id="_NotificactionMessagePopUp" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog" style="min-width: 750px; !important">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">Mensajeria!Negociá con tu amigo!</h4>
      </div>
      <div class="modal-body" id="_NotificactionMessagePopUpBody">
      	<div class="row">
      		<div class="col-md-12">
       		 	<textarea id="mensajeriaNotification" class="form-control" rows="15"  readonly></textarea>  
        	</div>
        </div>
        <div class="row">
      		<div class="col-md-12">
       		 	<input id="newMessageNotification" class="form-control">
        	</div>
        </div>
      <div class="modal-footer">
        <button type="button" onclick="sendNewMessageNotification()" class="btn btn-primary">Enviar!</button>
      </div>
      </div>
    </div>
  </div>
</div>
  
  
</div>



<!------------------------------------ Item de intercambio Section-------------------------------------------- -->
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
<!------------------------------------ Solicitud create-------------------------------------------- -->
<div class="modal fade" id="_solicitudCreate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">Dejale un mensaje a tu amigo!</h4>
      </div>
      <div class="modal-body">
      	<textarea id="msjSolicitud" class="form-control" rows="10" cols="30" placeholder="Convece a tu amigo para que acepte el trueque...">Propuesta de trueque de item - Tit4Tacs app - Otra manera de intercambiar items</textarea>  
      </div>
      <div class="modal-footer">
        <button type="button" onclick="SetMensjBeforeNotification()" class="btn btn-primary">Enviar!</button>
      </div>
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
