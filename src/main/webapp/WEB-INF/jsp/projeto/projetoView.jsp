<!DOCTYPE html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@page session="true"%>

<head>
	<title>Ixia - Sistema de Escala - Cadastro projeto</title>
</head>
    <jsp:include page="../shared/headerPartialView.jsp"/>
	<script type="text/javascript">	
		var isDisableCamposPrestador = <c:if test="${isDisableCamposPrestador}">true</c:if><c:if test="${!isDisableCamposPrestador}">false</c:if>;		
	</script>
	<script src="<c:url value='/js/projeto/projeto-principal.js' />"></script>
	<script src="<c:url value='/js/projeto/projeto-escala.js' />"></script>
	<script src="<c:url value='/js/projeto/projeto-escala-prestador.js' />"></script>
<body>
	<jsp:include page="../shared/navbarPartialView.jsp"/>

    <div class="container">    
	        <div style="margin-top:50px" class="mainbox col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
		        	  
		          <div class="panel panel-info">
		              <div class="panel-heading row">
		                	
		                  <h3 class="panel-title col-7 col-sm-7 col-md-7 col-lg-7 col-xl-7">Cadastro projeto</h3>   

						  <input id="id" name="id" value="${projeto.id}" type="hidden" /> 
						  
			        	  <div class="col-5 col-sm-5 col-md-5 col-lg-5 col-xl-5" > 
			        	  	<c:if test='${projeto.id == 0}'>
			        	  		<a id="btn-voltar" class="btn btn-sm btn-default" href="<c:url value='projetos' />" style="margin: 1px">Voltar</a>
							</c:if>
			        	  	<c:if test='${!isDisableCamposProjeto && projeto.id != 0}'>
			        	  		<a id="btn-voltar" class="btn btn-sm btn-default" href="<c:url value='../../projetos' />" style="margin: 1px">Voltar</a>
							</c:if>
			        	  	<c:if test="${isDisableCamposProjeto  && projeto.id != 0}"> 
		        	  			<a id="btn-voltar" class="btn btn-sm btn-default" href="<c:url value='../projetos' />" style="margin: 1px">Voltar</a>
							</c:if>
			        	  	
			        	  	<c:if test="${isDisableCamposProjeto && (isAdministracao || isGerencia)}">
								<a id="btn-editar" class="btn btn-sm btn-primary" href="<c:url value='/projeto' />/${projeto.id}/editar" style="margin: 1px">Editar</a> 
							</c:if>
			        	  	<c:if test="${projeto.id != 0}">  
               					<a class="btn btn-sm btn-primary" style="margin: 1px" href="<c:url value='/dashboard' />/${projeto.id}">dashboard</a>
							</c:if>
		        	  	</div>
		              </div>  
		              <div class="panel-body" >
		              
		        		<jsp:include page="../shared/errorPartialView.jsp"/>	
            				        	  	                      
            			<div class="row align-items-start" style="margin-top: 30px">	  
							<div class="tab-content col-8 col-sm-8 col-md-9 col-lg-9 col-xl-9 border" style="padding-top:20px; min-height: 450px; padding-left: 30px" id="pills-tabContent">
							  <div class='tab-pane fade' id="pills-dados" role="tabpanel" aria-labelledby="pills-dados-tab">
   								<jsp:include page="projetoDadosPrincipaisTabView.jsp"/>	
							  </div>
							  <div class='tab-pane fade' id="pills-escala" role="tabpanel" aria-labelledby="pills-endereco-tab">
   								<jsp:include page="projetoEscalasTabView.jsp"/>	
							  </div>
							  <div class='tab-pane fade' id="pills-prestadores" role="tabpanel" aria-labelledby="pills-projetos-tab">
   								<jsp:include page="projetoPrestadoresTabView.jsp"/>	
							  </div>
							</div>
            			
            			    <div class="nav nav-pills nav-stacked col-4 col-sm-4 col-md-2 col-lg-2 col-xl-2" id="pills-tab" role="tablist" aria-orientation="vertical">
							    <a class="nav-link" id="pills-dados-tab" data-toggle="pill" href="#pills-dados" role="tab" aria-controls="pills-dados" aria-selected="true">Dados principais</a>
							    <a class='nav-link' id="pills-escala-tab" data-toggle="pill" href="#pills-escala" role="tab" aria-controls="pills-escala" aria-selected="false">Escalas</a>
							    <a class='nav-link' id="pills-prestadores-tab" data-toggle="pill" href="#pills-prestadores" role="tab" aria-controls="pills-prestadores" aria-selected="false">Prestadores</a>
							</div>
            				                      
						</div>
		               </div>
		          </div>
                
         </div> 
    </div>
    
</body>

</html>