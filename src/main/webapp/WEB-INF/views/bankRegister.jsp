<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
		
		<table style=”width:300px” border="1" align="center">
			<thead>
				<tr>
					<td>Username</td>
					<td>Account Number</td>
					<td>Current Amount</td>
					<td>Account Currency</td>
					<td>Created By</td>
				</tr>
			</thead>
			<c:if test="${not empty accounts}">
		    	<tbody>
			        <c:forEach var="ac" items="${accounts}">
			            <tr>
			                <td>${ac.username}</td>
			                <td>${ac.accountNumber}</td>
			                <td>${ac.amount}</td>
			                <td>${ac.currency}</td>
			                <td>${ac.createdBy}</td>
			                <td>
			                	<a href="${contextPath}${url_route_edit}?id=${acc.id}">Edit</a> 
			                	| 
			                	<a href="${contextPath}${url_route_remove}?id=${acc.id}">Delete</a> 
			                </td>			                
			            </tr>
			        </c:forEach>
		        </tbody>
			</c:if>
		</table>
		
		<div class="container">
			<form:form class="form-signin" method="get">
		    	<h2 class="form-signin-heading">Enjoy our banking!</h2>     	
		      			
      			<sec:authorize access="hasRole('ROLE_BANK_EMPLOYEE')">
					<button type="button" onclick="location='/webbank/createAccount'" name="login" value="1" class="btn btn-lg btn-primary btn-block">New Account</button>
		      	</sec:authorize>
		      	
		      	<button type="button" onclick="location='/webbank/operation'" name="login" value="1" class="btn btn-lg btn-primary btn-block">Operation</button>        
			</form:form>	    
		</div> <!-- /container -->	
		
		<ct:Footer />
	</div>
</body>
</html>