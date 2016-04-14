<%@ attribute name="title" %>
<nav class="navbar navbar-default">
        <div class="container-fluid">
          <div class="navbar-header">
            
            <a class="navbar-brand" href="#">${title }</a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            
            <ul class="nav navbar-nav navbar-right">
              <li class="active"><a>Logged in as <strong>${user.username}</strong></a></li>
              <li><a href="logout">Logout</a></li>
              
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>