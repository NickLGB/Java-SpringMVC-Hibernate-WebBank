<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>

<!DOCTYPE html>
<html>
<ct:HeaderMeta />
<body>
<div class="container">
	<ct:Header />
	
	<form class="form-signin" name="login" method="POST" action="login">
    	<h4 class="form-signin-heading">Please Login to your account</h4>
      	<label for="inputEmail" class="sr-only">Username</label>
      	<input type="text" name="username" id="username" class="form-control" placeholder="Username" required autofocus>
     	<label for="inputEmail" class="sr-only">Password</label>
      	<input type="password" name="password" id="password" class="form-control" placeholder="Password" required>
      	
      	<button type="submit" name="submit" value="Login" class="btn btn-lg btn-primary btn-block">Login</button>        
	</form>
	
	<ct:Footer />
</div>
</body>
</html>