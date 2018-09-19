<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div style="margin-top: 15px;">
	<c:if test="${result.hasErrors()}">
		<div id="login-alert" class="alert alert-danger col-8 col-sm-8 col-md-8 col-lg-8 col-xl-8">
			Preencha todos os campos obrigatórios (${camposComErro})
		</div>
	</c:if>
	<c:if test="${!result.hasErrors()}">
		<c:if test="${not empty errorMessage}">
		  <div id="login-alert" class="alert alert-danger col-sm-10">${errorMessage}<span onclick="$(this).parent().hide()" class="bt-close text-danger">x<span></div>				    	
		</c:if>
		
		<c:if test="${not empty error}">
		   <div id="login-alert" class="alert alert-danger col-sm-10">${error}<span onclick="$(this).parent().hide()" class="bt-close text-danger">x<span></div>
		</c:if>
		
		<c:if test="${not empty message}">
		   <div id="login-alert" class="alert alert-danger col-sm-10">${message}<span onclick="$(this).parent().hide()" class="bt-close text-danger">x<span></div>
		</c:if>    
	</c:if>                            
</div>