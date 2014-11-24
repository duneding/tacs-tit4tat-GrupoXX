	<div class="navbar navbar-inverse" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="../../home">Tit4Tat</a>
        </div>
        <div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
					<li id="friends/items"><a onclick="showAmigos();">Amigos</a></li>
					<li id="items"><a onclick="showMyItems();">Items</a></li>
					<li id="notifications"><a onclick="showMyNotifications();">Solicitudes<span class="badge">3</span></a></li>
			</ul>
			<!-- <form action="http://facebook.com" class="navbar-form navbar-right" role="form">
				<button type="submit" class="btn btn-sm btn-primary">Entrar con Facebook</button>				
			</form>-->
			                <div class="navbar-right">
                    <ul class="nav navbar-nav">
                        <li class="dropdown user user-menu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="glyphicon glyphicon-user"></i>
                                <span class="faceUser">  <i class="caret"></i></span>
                            </a>
                            <ul class="dropdown-menu">
                                <!-- User image -->
                                <li class="user-header bg-light-blue">
                                    <img class="img-circle" alt="User Image" id="userPhoto">
                                    <p class="faceUser">
                                    </p>
                                </li>
<!--                                 Menu Footer
                                <li class="user-footer">
                                    <div class="pull-right">
                                        <a href="#" class="btn btn-default btn-flat" id="logOutButton">Deslogear</a>
                                    </div>
                                </li> -->
                            </ul>
                        </li>
                    </ul>
                </div>
        </div><!--/.navbar-collapse -->

      </div>
    </div>