<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>
<%@ page errorPage="exception.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<ct:HeaderMeta />
<body>
<div class="container">
	<ct:Header />
	<ct:NavigationBar />
	
	<div class="container">
		<form class="form-signin" method="post" action="${pageContext.request.contextPath}/operation">
	    	<h2 class="form-signin-heading">Enjoy our banking!</h2>
	    	
	      	<label for="inputEmail" class="sr-only">Account number</label>
	      	<input type="text" name="accountNumber" id="inputEmail" class="form-control" placeholder="Account number" required autofocus>
	     	
	     	<label for="inputEmail" class="sr-only">Amount</label>
	      	<input type="number" name="amount" id="inputEmail" class="form-control" placeholder="Enter amount to operate" required>
	      	<br>
				<label>Choose operation:</label>
				<label class="radio-inline">
					<input type="radio" name="operation" value="Deposit" required><strong>Deposit</strong><br />
				</label>
				<label class="radio-inline">
					<input type="radio" name="operation" value="Withdraw"><strong>Withdraw</strong><br />	
				</label>
			<br><br>			
				<label>Choose currency:</label>		
				<select class="form-control" name="currency">
	  					<option value="BGN">BGN</option>
	  					<option value="USD">USD</option>
	  					<option value="EUR">EUR</option>  
				 </select> 
			<br>
	      	<button type="submit" name="login" value="1" class="btn btn-lg btn-primary btn-block">Bank!</button>        
		</form>    
	</div> <!-- /container -->

	<ct:Footer />
</div>
</body>
</html>