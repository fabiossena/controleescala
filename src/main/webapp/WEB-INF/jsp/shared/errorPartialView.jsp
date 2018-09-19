<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div style="margin-top: 15px;">
	<c:if test="${result.hasErrors()}">
		<div id="login-alert" class="alert alert-danger">
			Preencha todos os campos obrigatórios <c:if test='${not empty camposComErro}'>(${camposComErro})</c:if>
			<button onclick="$(this).parent().hide()" type="button" class="bt-close close" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</c:if>
	<c:if test="${!result.hasErrors()}">
		<c:if test="${not empty errorMessage}">
		  <div id="login-alert" class="alert alert-danger col-sm-10">
		   ${errorMessage}
		   <button onclick="$(this).parent().hide()" type="button" class="bt-close close" aria-label="Close">
		   	<span aria-hidden="true">&times;</span>
		   	</button>
		   </div>				    	
		</c:if>
		
		<c:if test="${not empty error}">
		   <div id="login-alert" class="alert alert-danger col-sm-10">
		    ${error}
		    <button onclick="$(this).parent().hide()" type="button" class="bt-close close" aria-label="Close">
		     <span aria-hidden="true">&times;</span>
		    </button>
		   </div>
		</c:if>
		
		<c:if test="${not empty message}">
		   <div id="login-alert" class="alert alert-danger col-sm-10">
		     ${message}
		   	 <button onclick="$(this).parent().hide()" type="button" class="bt-close close" aria-label="Close">
		   	  <span aria-hidden="true">&times;</span>
		   	 </button>
		   	</div>
		</c:if>    
	</c:if>                            
</div>